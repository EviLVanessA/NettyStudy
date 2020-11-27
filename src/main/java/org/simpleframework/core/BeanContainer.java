package org.simpleframework.core;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * stu:容器的组成部分 ①保存Class对象及其实例的载体 ②容器的加载 ③容器的操作方式
 * @author jianghui
 * @date 2020-11-26 17:33
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class BeanContainer {



    public static BeanContainer getInstance(){
        return ContainerHolder.HOLDER.instance;
    }
    /**
     * 枚举单例
     */
    private enum ContainerHolder{
        /**
         * 枚举
         */
        HOLDER;
        private BeanContainer instance;
        ContainerHolder(){
            instance = new BeanContainer();
        }
    }

}
