package demo.pattern.singleton;

/**
 * stu: 双重检查锁机制的懒汉模式 确保懒汉式线程安全
 * @author jianghui
 * @date 2020-11-26 16:27
 */
public class LazyDoubleCheckSingleton {
    /**
     * volatile防止指令重排序
     */
    private volatile static LazyDoubleCheckSingleton instance;

    private LazyDoubleCheckSingleton(){}

    public static LazyDoubleCheckSingleton getInstance(){
        if (instance == null){
            synchronized (LazyDoubleCheckSingleton.class){
                if (instance == null){
                    //分三步进行 ①分配内存空间 ②初始化对象 ③设置instance指向刚分配的内存地址
                    instance = new LazyDoubleCheckSingleton();
                }
            }
        }
        return instance;
    }

}
