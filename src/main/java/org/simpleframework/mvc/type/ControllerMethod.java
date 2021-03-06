package org.simpleframework.mvc.type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * 用于封装待执行的Controller及其方法实例和参数的映射
 * @author jianghui
 * @date 2021-03-05 12:25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ControllerMethod {
    /**
     * Controller对应的Class对象
     */
    private Class<?> controllerClass;
    /**
     * 保存待执行的Controller方法实例
     */
    private Method invokeMethod;
    /**
     * 用于保存Controller方法参数名称以及对应的参数类型
     */
    private Map<String,Class<?>> methodParameters;
}
