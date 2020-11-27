package demo.pattern.singleton;

/**
 * stu:单例设计模式之饿汉式 线程安全
 * @author jianghui
 * @date 2020-11-26 16:22
 */
public class StarvingSingleton {
    private static final StarvingSingleton starvingSingleton = new StarvingSingleton();
    private StarvingSingleton(){}
    public static StarvingSingleton getInstance(){
        return starvingSingleton;
    }

}
