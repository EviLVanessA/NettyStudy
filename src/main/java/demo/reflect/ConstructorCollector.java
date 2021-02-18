package demo.reflect;

import java.lang.reflect.Constructor;

/**
 * @author jianghui
 * @date 2021-02-18 16:10
 */
public class ConstructorCollector {
    public static void main(String[] args) throws ClassNotFoundException {
        Class clazz = Class.forName("demo.reflect.ReflectTarget");
        //获取所有的公有构造方法
        System.out.println("***************所有公有的构造方法**************");
        Constructor[] constructors = clazz.getConstructors();
        for (Constructor constructor : constructors ){
            System.out.println(constructor);
        }
        //获取所有的构造方法 包括私有
        System.out.println("***************所有的构造方法(包含：私有、受保护、默认、公有)**************");
        Constructor[] declaredConstructors = clazz.getDeclaredConstructors();
        for (Constructor constructor : declaredConstructors ){
            System.out.println(constructor);
        }

    }
}
