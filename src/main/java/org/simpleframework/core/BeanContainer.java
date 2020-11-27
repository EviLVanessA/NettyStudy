package org.simpleframework.core;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.simpleframework.core.annotation.Component;
import org.simpleframework.core.annotation.Controller;
import org.simpleframework.core.annotation.Repository;
import org.simpleframework.core.annotation.Service;
import org.simpleframework.util.ClassUtil;
import org.simpleframework.util.ValidationUtil;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * stu:容器的组成部分 ①保存Class对象及其实例的载体 ②容器的加载 ③容器的操作方式
 * 实现容器的加载：①配置与管理的获取 ②获取指定范围内的Class对象 ③依据配置提取Class对象，连同实例一并存入容器
 *
 * @author jianghui
 * @date 2020-11-26 17:33
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class BeanContainer {
    /**
     * 存放所有被配置标记的目标对象的Map
     */
    private final Map<Class<?>, Object> beanMap = new ConcurrentHashMap<>();
    private static final List<Class<? extends Annotation>> BEAN_ANNOTATION
            = Arrays.asList(Component.class, Controller.class, Repository.class, Service.class);

    public static BeanContainer getInstance() {
        return ContainerHolder.HOLDER.instance;
    }

    /**
     * 枚举单例
     */
    private enum ContainerHolder {
        /**
         * 枚举
         */
        HOLDER;
        private final BeanContainer instance;

        ContainerHolder() {
            instance = new BeanContainer();
        }
    }

    /**
     * 容器是否已加载bean
     */
    private boolean loaded = false;

    public boolean isLoaded() {
        return loaded;
    }

    /**
     * 获取beanMap中的元素的个数
     *
     * @return int
     */
    public int size() {
        return beanMap.size();
    }

    /**
     * 加载bean
     *
     * @param packageName 包名
     */
    public synchronized void loadBeans(String packageName) {
        //判断Bean容器是否被加载过
        if (isLoaded()) {
            log.warn("BeanContainer has been loaded");
            return;
        }
        Set<Class<?>> classSet = ClassUtil.extractPackageClass(packageName);
        if (ValidationUtil.isEmpty(classSet)) {
            log.warn("have nothing in package " + packageName);
            return;
        }
        for (Class<?> clazz : classSet) {
            for (Class<? extends Annotation> ann : BEAN_ANNOTATION) {
                if (clazz.isAnnotationPresent(ann)) {
                    beanMap.put(clazz, ClassUtil.newInstance(clazz, true));
                }
            }
        }
        this.loaded = true;
    }

    /**
     * 添加一个class对象及其Bean实例
     *
     * @param clazz Class对象
     * @param bean  Bean实例
     * @return 原有的bean实例，没有则返回null
     */
    public Object addBean(Class<?> clazz, Object bean) {
        return beanMap.put(clazz, bean);
    }

    /**
     * 移除IOC容器管理的对象
     *
     * @param clazz Class对象
     * @return 返回移除的bean, 没有返回null
     */
    public Object removeBean(Class<?> clazz) {
        return beanMap.remove(clazz);
    }

    /**
     * 根据Class对象获取其实例bean
     *
     * @param clazz class对象
     * @return class对象对应的bean
     */
    public Object getBean(Class<?> clazz) {
        return beanMap.get(clazz);
    }

    /**
     * 获取容器管理的所有Class对象集合
     *
     * @return Class集合
     */
    public Set<Class<?>> getClasses() {
        return beanMap.keySet();
    }

    /**
     * 获取所有的bean集合
     *
     * @return bean集合
     */
    public Set<Object> getBeans() {
        return new HashSet<>(beanMap.values());
    }

    /**
     * 根据注释筛选Bean的Class集合
     *
     * @param annotation 注解
     * @return Class集合
     */
    public Set<Class<?>> getClassByAnnotation(Class<? extends Annotation> annotation) {
        //获取beanMap的所有class对象
        Set<Class<?>> keySet = getClasses();
        if (ValidationUtil.isEmpty(keySet)) {
            log.warn("nothing in beanMap");
            return null;
        }
        //通过注解筛选被注解标记的class对象，并添加到classSet里
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> clazz : keySet) {
            //判断类是否有被相关注解标记
            if (clazz.isAnnotationPresent(annotation)) {
                classSet.add(clazz);
            }
        }
        return classSet.size() > 0 ? classSet : null;
    }

    /**
     * 通过接口或者父类获取实现类或者子类的Class集合，但不包括起本身
     * @param interfaceOrClass 接口或者类
     * @return class集合
     */
    public Set<Class<?>> getClassesBySuper(Class<?> interfaceOrClass){
        //获取beanMap的所有class对象
        Set<Class<?>> keySet = getClasses();
        if (ValidationUtil.isEmpty(keySet)) {
            log.warn("nothing in beanMap ");
            return null;
        }
        //判断KeySet里的元素是否是传入的接口的类或者子类，并添加到keySet
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> clazz : keySet) {
            //判断类是否有被相关注解标记
            if (interfaceOrClass.isAssignableFrom(clazz) && !clazz.equals(interfaceOrClass)) {
                classSet.add(clazz);
            }
        }
        return classSet.size() > 0 ? classSet : null;
    }

}
