package demo.pattern.singleton;

/**
 * 枚举
 * @author jianghui
 * @date 2020-11-26 16:55
 */
public class EnumStarvingSingleton {

    private EnumStarvingSingleton(){}

    public static EnumStarvingSingleton getInstance(){
        return ContainerHolder.HOLDER.instance;
    }

    private enum ContainerHolder{
        /**
         * 枚举类型
         */
        HOLDER;
        private final EnumStarvingSingleton instance;
        private ContainerHolder(){
            instance = new EnumStarvingSingleton();
        }
    }
}
