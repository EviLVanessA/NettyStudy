package org.simpleframework.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author jianghui
 * @date 2020-11-26 12:42
 */
@Slf4j
public class ClassUtil {

    public static final String FILE_PROTOCOL = "file";

    /**
     * 获取该包下类集合
     *
     * @param packageName 包名
     * @return 类集合
     */
    public static Set<Class<?>> extractPackageClass(String packageName) {
        //1.获取到类加载器  -> 获项目发布的实际路径 不可以传入绝对路径 此方法并不友好 打包后会找不到路径
        ClassLoader classLoader = getClassLoader();
        //2.通过类加载器获取到加载的资源信息
        packageName = packageName.replace(".", "/");
        URL url = classLoader.getResource(packageName);
        if (null == url) {
            log.warn("unable to retrieve anything from package" + packageName);
            return null;
        }
        //3.依据不同的资源类型，采用不同的方式获取资源的集合
        Set<Class<?>> classSet = null;
        //过滤出文件类型的资源
        if (url.getProtocol().equalsIgnoreCase(FILE_PROTOCOL)) {
            classSet = new HashSet<>();
            File packageDirectory = new File(url.getPath());
            extractClassFile(classSet, packageDirectory, packageName);
        }
        return classSet;
    }

    /**
     * 递归获取目标package里面的所有class文件（包括子package里的class文件）
     *
     * @param emptyClassSet 装载目标类的集合
     * @param fileSource    文件或者目录
     * @param packageName   包名
     */
    private static void extractClassFile(Set<Class<?>> emptyClassSet, File fileSource, String packageName) {
        if (!fileSource.isDirectory()) {
            return;
        }
        //如果是一个文件夹，则调用其listFiles方法获取文件夹下的文件或文件夹(不包含子文件夹)
        File[] files = fileSource.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                if (file.isDirectory()) {
                    return true;
                } else {
                    //是文件
                    String absoluteFilePath = file.getAbsolutePath();
                    if (absoluteFilePath.endsWith(".class")) {
                        //是class文件 直接加载到Set
                        addToClassSet(absoluteFilePath);
                    }
                }
                return false;
            }

            /**
             * 根据class文件的绝对路径，获取并生成class对象，并放入classSet中
             * @param absoluteFilePath 文件的绝对路径
             */
            private void addToClassSet(String absoluteFilePath) {
                //1.从class文件的绝对路径里提取包含package的类名
                String newName = packageName.replace("/", ".");
                absoluteFilePath = absoluteFilePath.replace(File.separator, ".");
                String className = absoluteFilePath.substring(absoluteFilePath.indexOf(newName));
                className = className.substring(0, className.lastIndexOf("."));
                //2.通过反射机制获取对应的Class对象并加入到classSet里
                Class<?> targetClass = loadClass(className);
                emptyClassSet.add(targetClass);
            }
        });
        //foreach 必须做好空值判断
        if (files != null) {
            for (File f : files) {
                extractClassFile(emptyClassSet, f, packageName);
            }
        }

    }

    public static void main(String[] args) {
        extractPackageClass("com.study.entity");
    }

    /**
     * 获取Class对象
     *
     * @param className class的全类名 = package + 类名
     * @return Class
     */
    public static Class<?> loadClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            log.error("load class error", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取当前classLoader
     *
     * @return 返回当前ClassLoader
     */
    public static ClassLoader getClassLoader() {
        //获取上下文类加载器
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 设置类中属性的值
     * @param field 成员变量
     * @param target 类的实例
     * @param value 成员变量的值
     * @param accessible 是否允许设置私有属性
     */
    public static void setField(Field field,Object target,Object value,boolean accessible){
        field.setAccessible(accessible);
        try {
            field.set(target,value);
        } catch (IllegalAccessException e) {
            log.error("setField error",e);
            throw new RuntimeException(e);
        }
    }


    /**
     * 实例化class
     * @param clazz 类
     * @param accessible 是否支持创建私有class实例
     * @param <T> class类型
     * @return 实例化的类
     */
    public static <T> T newInstance(Class<?> clazz,Boolean accessible){
        try {
            Constructor<?> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(accessible);
            return (T) constructor.newInstance();
        } catch (Exception e) {
            log.error("newInstance error",e);
            throw new RuntimeException(e);
        }
    }
}
