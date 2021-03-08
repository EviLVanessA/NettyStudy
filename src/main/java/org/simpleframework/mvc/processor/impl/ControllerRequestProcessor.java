package org.simpleframework.mvc.processor.impl;

import lombok.extern.slf4j.Slf4j;
import org.simpleframework.core.BeanContainer;
import org.simpleframework.mvc.RequestProcessorChain;
import org.simpleframework.mvc.annotation.RequestMapping;
import org.simpleframework.mvc.annotation.RequestParam;
import org.simpleframework.mvc.annotation.ResponseBody;
import org.simpleframework.mvc.processor.RequestProcessor;
import org.simpleframework.mvc.render.ResultRender;
import org.simpleframework.mvc.render.impl.JsonResultRender;
import org.simpleframework.mvc.render.impl.ResourceNotFoundResultRender;
import org.simpleframework.mvc.render.impl.ViewResultRender;
import org.simpleframework.mvc.type.ControllerMethod;
import org.simpleframework.mvc.type.RequestPathInfo;
import org.simpleframework.util.ConvertUtil;
import org.simpleframework.util.ValidationUtil;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 负责将请求转发给对应的Controller进行处理
 * <p>
 * 主要功能如下：
 * ①针对特定的请求,选择匹配的Controller方法进行处理
 * ②解析请求里的参数及其对应的值,并赋值给Controller方法的参数
 * ③根据实际情况,选择合适的Render,为后续请求处理结果的渲染做准备
 *
 * @author jianghui
 * @date 2021-03-04 10:23
 */
@Slf4j
public class ControllerRequestProcessor implements RequestProcessor {
    /**
     * IOC容器实例
     */
    private BeanContainer beanContainer;
    /**
     * 请求和controller方法的映射
     */
    private Map<RequestPathInfo, ControllerMethod> pathControllerMethodMap = new ConcurrentHashMap<>();

    public ControllerRequestProcessor() {
        //返回容器的实例单例 执行完init()方法之后已经具备完备的bean实例
        this.beanContainer = BeanContainer.getInstance();
        Set<Class<?>> requestMappingSet = beanContainer.getClassByAnnotation(RequestMapping.class);
        initPathControllerMethodMap(requestMappingSet);
    }

    private void initPathControllerMethodMap(Set<Class<?>> requestMappingSet) {
        if (ValidationUtil.isEmpty(requestMappingSet)) {
            return;
        }
        //1.遍历所有被@RequestMapping标记的类,获取类上边该注解的属性值作为一级路径
        for (Class<?> requestMappingClass : requestMappingSet) {
            RequestMapping requestMapping = requestMappingClass.getAnnotation(RequestMapping.class);
            //获取一级路经
            String basePath = requestMapping.value();
            if (!basePath.startsWith("/")) {
                basePath = "/" + basePath;
            }
            //2.遍历所有被@RequestMapping标记的方法,获取方法上边该注解的属性值作为二级路径
            Method[] methods = requestMappingClass.getDeclaredMethods();
            if (ValidationUtil.isEmpty(methods)) {
                continue;
            }
            for (Method method : methods) {
                if (method.isAnnotationPresent(RequestMapping.class)) {
                    RequestMapping methodRequest = method.getAnnotation(RequestMapping.class);
                    String methodPath = methodRequest.value();
                    if (!methodPath.startsWith("/")) {
                        methodPath = "/" + methodPath;
                    }
                    String url = basePath + methodPath;
                    //3.解析方法里被@RequestParam标记的参数
                    Map<String, Class<?>> methodParams = new HashMap<>();
                    Parameter[] parameters = method.getParameters();
                    if (!ValidationUtil.isEmpty(parameters)) {
                        for (Parameter parameter : parameters) {
                            RequestParam param = parameter.getAnnotation(RequestParam.class);
                            //目前暂定为Controller方法里所有的参数都需要@RequestParam注解
                            if (param == null) {
                                throw new RuntimeException("The parameter must have @RequestParam");
                            }
                            //获取该注解的属性值,作为参数名
                            String value = param.value();
                            //key是@RequestParam中的value属性 value是对应的参数的类型
                            //获取被标记的参数的数据类型,建立参数名和参数类型的映射
                            methodParams.put(value, parameter.getType());
                        }
                    }
                    //4.将获取到的信息封装成RequestPathInfo实例和ControllerMethod实例,放置到映射表
                    String httpMethod = String.valueOf(methodRequest.method());
                    RequestPathInfo requestPathInfo = new RequestPathInfo(httpMethod, url);
                    if (this.pathControllerMethodMap.containsKey(requestMapping)) {
                        log.warn("duplicate url:{} registration,current class{} method{} will override the" +
                                        "former one", requestPathInfo.getHttpMethod(),
                                requestMappingClass.getName(), method.getName());
                    }
                    ControllerMethod controllerMethod = new ControllerMethod(requestMappingClass, method, methodParams);
                    this.pathControllerMethodMap.put(requestPathInfo, controllerMethod);
                }
            }
        }
    }

    @Override
    public boolean process(RequestProcessorChain requestProcessorChain) throws Exception {
        //1、解析HttpServletRequest的请求方法,请求路径，获取对应的ControllerMethod实例
        String httpMethod = requestProcessorChain.getRequestMethod();
        String httpPath = requestProcessorChain.getRequestPath();
        ControllerMethod controllerMethod = this.pathControllerMethodMap.get(new RequestPathInfo(httpMethod, httpPath));
        if (controllerMethod == null) {
            requestProcessorChain.setResultRender(new ResourceNotFoundResultRender(httpMethod, httpPath));
            return false;
        }
        //2、解析请求参数,并传递给获取到的ControllerMethod实例去执行
        Object result = invokeControllerMethod(controllerMethod, requestProcessorChain.getRequest());
        //3、根据处理的结果,选则对应的render进行渲染
        setResultRender(result, controllerMethod, requestProcessorChain);
        return true;
    }

    /**
     * 根据不同的情况设置不同的render
     *
     * @param result                返回值
     * @param controllerMethod      请求对应的controller的封装类型
     * @param requestProcessorChain 执行链 用来执行注册的Processor
     */
    private void setResultRender(Object result, ControllerMethod controllerMethod, RequestProcessorChain requestProcessorChain) {
        if (result == null) {
            return;
        }
        ResultRender resultRender;
        boolean isJson = controllerMethod.getInvokeMethod().isAnnotationPresent(ResponseBody.class);
        if (isJson) {
            resultRender = new JsonResultRender(result);
        } else {
            resultRender = new ViewResultRender(result);
        }
        requestProcessorChain.setResultRender(resultRender);
    }

    private Object invokeControllerMethod(ControllerMethod controllerMethod, HttpServletRequest request) {
        //1、从请求中获取GET或者POST的参数名及其对应的值
        Map<String, String> requestParamMap = new HashMap<>();
        //此方式仅支持获取GET或者POST方法的请求
        Map<String, String[]> parameterMap = request.getParameterMap();
        for (Map.Entry<String, String[]> parameter : parameterMap.entrySet()) {
            if (!ValidationUtil.isEmpty(parameter.getValue())) {
                //目前只支持一个参数对应一个值的形式
                requestParamMap.put(parameter.getKey(), parameter.getValue()[0]);
            }
        }
        //2、根据获取到的请求参数名及其对应的值,以及ControllerMethod里面的参数和类型的映射关系,去实例化出对应的方法参数
        List<Object> methodParams = new ArrayList<>();
        Map<String, Class<?>> methodParameters = controllerMethod.getMethodParameters();
        for (String paramName : methodParameters.keySet()) {
            Class<?> type = methodParameters.get(paramName);
            String requestValue = requestParamMap.get(paramName);
            Object value;
            //仅支持String和基础类型byte、short、int、long、float、double、char、boolean及其包装类型
            //                     1     2     4    8     4      8      2      1
            if (requestValue == null) {
                value = ConvertUtil.primitiveNull(type);
            } else {
                value = ConvertUtil.convert(type, requestValue);
            }
            methodParams.add(value);
        }
        //3、采用反射机制,执行Controller里面对应的方法并返回结果
        //获取bean实例
        Object controller = beanContainer.getBean(controllerMethod.getControllerClass());
        Method invokeMethod = controllerMethod.getInvokeMethod();
        invokeMethod.setAccessible(true);
        Object result;
        try {
            //如果方法没有参数
            if (methodParams.size() == 0) {
                result = invokeMethod.invoke(controller);
            } else {
                //如果有参数 传入参数列表
                result = invokeMethod.invoke(controller, methodParams.toArray());
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e.getTargetException());
        }
        return result;
    }
}
