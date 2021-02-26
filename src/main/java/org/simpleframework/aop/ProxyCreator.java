package org.simpleframework.aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * @author jianghui
 * @date 2021-02-26 16:44
 */
public class ProxyCreator {
    /**
     * 创建动态的代理对象并返回
     *
     * @param targetClass       被代理的对象
     * @param methodInterceptor 方法拦截器
     * @return 创建的代理实例
     */
    public static Object createProxy(Class<?> targetClass, MethodInterceptor methodInterceptor) {
        return Enhancer.create(targetClass, methodInterceptor);
    }
}
