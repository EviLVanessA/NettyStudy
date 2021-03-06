package org.simpleframework.mvc.processor.impl;

import org.omg.PortableInterceptor.RequestInfo;
import org.simpleframework.core.BeanContainer;
import org.simpleframework.core.annotation.Controller;
import org.simpleframework.mvc.RequestProcessorChain;
import org.simpleframework.mvc.processor.RequestProcessor;
import org.simpleframework.mvc.type.ControllerMethod;

import java.util.Map;

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
public class ControllerRequestProcessor implements RequestProcessor {
    /**
     * IOC容器实例
     */
    private BeanContainer beanContainer;
    /**
     * 请求和controller方法的映射
     */
    private Map<RequestInfo, ControllerMethod> pathControllerMethodMap;

    @Override
    public boolean process(RequestProcessorChain requestProcessorChain) throws Exception {
        return false;
    }
}
