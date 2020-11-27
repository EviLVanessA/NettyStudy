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
                    instance = new LazyDoubleCheckSingleton();
                }
            }
        }
        return instance;
    }

}
