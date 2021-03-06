package org.simpleframework.mvc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 保存请求的方法参数
 *
 * @author jianghui
 * @date 2021-03-05 12:19
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestParam {
    /**
     * 方法参数名称
     */
    String value() default "";

    /**
     * 是否必须填写
     */
    boolean required() default true;
}
