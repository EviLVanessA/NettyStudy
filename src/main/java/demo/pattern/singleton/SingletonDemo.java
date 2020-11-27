package demo.pattern.singleton;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author jianghui
 * @date 2020-11-26 16:25
 */
public class SingletonDemo {
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        demo1();
        demo2();
    }

    private static void demo1() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        StarvingSingleton instance = StarvingSingleton.getInstance();

        Class<StarvingSingleton> clazz = StarvingSingleton.class;
        Constructor<StarvingSingleton> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);


        System.out.println(instance);
        System.out.println(constructor.newInstance());
    }
    private static void demo2() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Class<EnumStarvingSingleton> clazz = EnumStarvingSingleton.class;
        Constructor<EnumStarvingSingleton> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        System.out.println(constructor.newInstance());
        System.out.println(EnumStarvingSingleton.getInstance());
    }
}
