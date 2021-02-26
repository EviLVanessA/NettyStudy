package org.simpleframework.aop;

import lombok.Getter;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.simpleframework.aop.aspect.AspectInfo;
import org.simpleframework.util.ValidationUtil;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 负责往被代理对象去添加横切逻辑
 *
 * @author jianghui
 * @date 2021-02-26 14:23
 */
public class AspectListExecutor implements MethodInterceptor {
    /**
     * 表示被代理的对象
     */
    private Class<?> targetClass;

    @Getter
    private List<AspectInfo> sortedAspectInfoList;

    public AspectListExecutor(Class<?> targetClass, List<AspectInfo> aspectInfoList) {
        this.targetClass = targetClass;
        this.sortedAspectInfoList = sortAspectInfoList(aspectInfoList);
    }

    /**
     * 按照order的值进行升序排序,确保order值小的aspect先被织入
     *
     * @param aspectInfoList 织入列表
     */
    private List<AspectInfo> sortAspectInfoList(List<AspectInfo> aspectInfoList) {
        aspectInfoList.sort(Comparator.comparingInt(AspectInfo::getOrderIndex));
        return aspectInfoList;
    }

    @Override
    public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        Object returnValue = null;
        if (ValidationUtil.isEmpty(sortedAspectInfoList)) {
            return null;
        }
        //1 按照order的执行顺序升序执行完所有Aspect的before方法
        invokeBeforeAdvices(method, args);
        try {
            //2 执行被代理类的方法
            returnValue = methodProxy.invokeSuper(proxy, args);
            //3 如果被代理方法正常返回，则按照order的顺序降序执行完所有Aspect的afterReturning方法
            returnValue = invokeAfterReturningAdvices(method, args, returnValue);
        } catch (Exception e) {
            //4 如果被代理方法抛出异常，则按照order的顺序降序执行完所有Aspect的afterThrowing方法
            invokeAfterThrowingAdvices(method, args, e);
            e.printStackTrace();
        }
        return returnValue;
    }

    /**
     * 如果被代理方法抛出异常，则按照order的顺序降序执行完所有Aspect的afterThrowing方法
     *
     * @param method
     * @param args
     * @param e
     */
    private void invokeAfterThrowingAdvices(Method method, Object[] args, Exception e) throws Throwable {
        for (int i = sortedAspectInfoList.size() - 1; i >= 0; i--) {
            sortedAspectInfoList.get(i).getAspectObject().afterThrowing(targetClass, method, args, e);
        }
    }

    /**
     * 如果被代理方法正常返回，则按照order的顺序降序执行完所有Aspect的afterReturning方法
     *
     * @param method
     * @param args
     * @param returnValue
     */
    private Object invokeAfterReturningAdvices(Method method, Object[] args, Object returnValue) throws Throwable {
        Object result = null;
        for (int i = sortedAspectInfoList.size() - 1; i >= 0; i--) {
            result = sortedAspectInfoList.get(i).getAspectObject().afterReturning(targetClass, method, args, returnValue);
        }
        return result;
    }

    /**
     * 按照order的执行顺序升序执行完所有Aspect的before方法
     *
     * @param method
     * @param args
     */
    private void invokeBeforeAdvices(Method method, Object[] args) throws Throwable {
        for (AspectInfo aspectInfo : sortedAspectInfoList) {
            aspectInfo.getAspectObject().before(targetClass, method, args);
        }
    }
}
