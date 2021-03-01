package com.study.aspect;

import lombok.extern.slf4j.Slf4j;
import org.simpleframework.aop.annotation.Aspect;
import org.simpleframework.aop.annotation.Order;
import org.simpleframework.aop.aspect.DefaultAspect;
import org.simpleframework.core.annotation.Controller;

import java.lang.reflect.Method;

/**
 * @author jianghui
 * @date 2021-03-01 10:12
 */
@Aspect(pointcut = "execution(* com.study.controller.superadmin..*.*(..))")
@Order(0)
@Slf4j
public class ControllerTimeCalculatorAspect extends DefaultAspect {
    private long timeStampCache;

    @Override
    public void before(Class<?> targetClass, Method method, Object[] args) throws Throwable {
        log.info("开始计时,执行的类是[{}],执行的方法是[{}],参数是[{}]", targetClass.getName(), method.getName(), args);
        timeStampCache = System.currentTimeMillis();
    }

    @Override
    public Object afterReturning(Class<?> targetClass, Method method, Object[] args, Object returnValue) throws Throwable {
        long endTime = System.currentTimeMillis();
        long costTime = endTime - timeStampCache;
        log.info("结束计时,执行的类是[{}],执行的方法是[{}],参数是[{}],执行时间[{}]",
                targetClass.getName(), method.getName(), args, costTime);
        return returnValue;
    }
}
