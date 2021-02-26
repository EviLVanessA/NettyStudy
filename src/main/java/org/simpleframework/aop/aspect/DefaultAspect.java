package org.simpleframework.aop.aspect;

import java.lang.reflect.Method;

/**
 * @author jianghui
 * @date 2021-02-26 14:04
 */
public abstract class DefaultAspect {
    /**
     * 方法前进行拦截
     *
     * @param targetClass 被代理的目标类
     * @param method      被代理的目标方法
     * @param args        被代理的目标方法对应的参数列表
     */
    public void before(Class<?> targetClass, Method method, Object[] args) throws Throwable {

    }

    /**
     * 事后拦截
     *
     * @param targetClass 被代理的目标类
     * @param method      被代理的目标方法
     * @param args        被代理的目标方法对应的参数列表
     * @param returnValue 返回值
     */
    public Object afterReturning(Class<?> targetClass, Method method,
                                 Object[] args, Object returnValue) throws Throwable {
        return returnValue;
    }

    /**
     * 有异常时织入
     *
     * @param targetClass 被代理的目标类
     * @param method      被代理的目标方法
     * @param args        被代理的目标方法对应的参数列表
     * @param e           被代理的目标方法抛出的异常
     */
    public void afterThrowing(Class<?> targetClass, Method method,
                              Object[] args, Throwable e) throws Throwable {
    }
}