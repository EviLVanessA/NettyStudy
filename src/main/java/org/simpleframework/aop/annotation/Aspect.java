package org.simpleframework.aop.annotation;

import java.lang.annotation.*;

/**
 * @author jianghui
 * @date 2021-02-26 13:58
 */
@Target(ElementType.TYPE)//只能作用再类上
@Retention(RetentionPolicy.RUNTIME)//运行时可用
public @interface Aspect {
    /**
     * 需要被织入横切逻辑的注解标签
     */
    Class<? extends Annotation> value();
}
