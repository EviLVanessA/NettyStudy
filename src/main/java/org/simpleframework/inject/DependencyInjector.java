package org.simpleframework.inject;

import lombok.extern.slf4j.Slf4j;
import org.simpleframework.core.BeanContainer;
import org.simpleframework.inject.annotation.Autowired;
import org.simpleframework.util.ClassUtil;
import org.simpleframework.util.ValidationUtil;

import java.lang.reflect.Field;
import java.util.Set;

/**
 * @author jianghui
 * @date 2020-11-27 10:58
 */
@Slf4j
public class DependencyInjector {
    /**
     * bean容器
     */
    private final BeanContainer beanContainer;

    public DependencyInjector() {
        beanContainer = BeanContainer.getInstance();
    }

    /**
     * 执行IoC
     */
    public void doIoC() {
        Set<Class<?>> classes = beanContainer.getClasses();
        if (ValidationUtil.isEmpty(classes)) {
            log.warn("have no class in beanContainer");
            return;
        }

        //1.遍历Bean容器中所有的Class对象
        for (Class<?> clazz : classes) {
            //2.遍历Class对象的所有成员变量
            Field[] declaredFields = clazz.getDeclaredFields();
            if (ValidationUtil.isEmpty(declaredFields)) {
                continue;
            }
            for (Field field : declaredFields) {
                //3.找出被Autowired标记的成员变量
                if (field.isAnnotationPresent(Autowired.class)) {
                    Autowired autowired = field.getAnnotation(Autowired.class);
                    String value = autowired.value();
                    //4.获取这些成员变量的类型
                    Class<?> fieldType = field.getType();
                    //5.获取这些成员变量的类型再容器里对应的实例
                    Object fieldValue = getFieldInstance(fieldType, value);
                    if (fieldValue == null) {
                        throw new RuntimeException("unable to inject relevant type," +
                                "target fieldClass is :" + fieldType.getName() + "->" + value);
                    } else {
                        //6.通过反射将对应的成员变量实例注入到成员变量所在的类的实例里
                        Object targetBean = beanContainer.getBean(clazz);
                        ClassUtil.setField(field, targetBean, fieldValue, true);
                    }
                }
            }
        }
    }

    /**
     * 根据Class在beanContainer里获取其实例或者实现类
     *
     * @param fieldType 成员变量的类型 即 class
     * @param value     Autowired的value
     * @return 返回实例
     */
    private Object getFieldInstance(Class<?> fieldType, String value) {
        Object fieldValue = beanContainer.getBean(fieldType);
        if (fieldValue != null) {
            return fieldValue;
        } else {
            Class<?> implementClass = getImplementClass(fieldType, value);
            if (implementClass != null) {
                return beanContainer.getBean(implementClass);
            } else {
                return null;
            }
        }
    }

    /**
     * 根据接口获取其实现类
     *
     * @param fieldType 成员变量的class
     * @return 实例类
     */
    private Class<?> getImplementClass(Class<?> fieldType, String autowiredValue) {
        Set<Class<?>> classes = beanContainer.getClassesBySuper(fieldType);
        if (!ValidationUtil.isEmpty(classes)) {
            if (ValidationUtil.isEmpty(autowiredValue)) {
                if (classes.size() == 1) {
                    return classes.iterator().next();
                } else {
                    throw new RuntimeException("multiple impl class for"
                            + fieldType.getName() + "please set @Autowired value to pick one");
                }
            } else {
                for (Class<?> clazz : classes) {
                    if (autowiredValue.equals(clazz.getSimpleName())) {
                        return clazz;
                    }
                }
            }
        }
        return null;
    }
}
