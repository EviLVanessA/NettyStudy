package Demo;

/**
 *
 * stu: java.lang.reflect类库里面主要的类：
 *      Field:表示类中的成员变量
 *      Method：表示类中的方法
 *      Constructor：表示类的构造函数
 *      Array：该类提供了动态创建数组和访问数据元素的静态方法
 *  反射依赖的Class
 *      Class类也是类的一种，class则是关键字
 *      Class类只有一个私有的构造函数，只有JVM能够创建Class类的实例
 *      JVM中只有唯一一个和类相应的Class对象来描述其类型信息
 *  获取Class对象的三种方式
 *      Object   ->  getClass()
 *      任何数据类型都有一个静态的class属性
 *      imp:通过Class的静态方法forName(String className)
 * stu: 在运行期间，一个类，只有一个与之相对应的Class对象产生
 *      反射的主要用法: 获取类的构造方法、获取类的成员变量、获取类的成员方法
 *  =============================================================
 *     一、获取构造方法：
 *        ①批量的方法：
 *          public Constructor[] getConstructs():所有"共有的"构造方法
 *          public Constructor[] getDeclaredConstructs():获取所有的构造方法(包括私有、受保护、默认、共有)
 *        ②获取单个的方法，并调用
 *          public Constructor getConstructor(Class... parameterTypes):获取单个的共有的构造方法
 *          public Constructor getDeclaredConstruct(Class.. parameterTypes):获取某个构造方法
 *        调用构造方法：Constructor --> newInstance(Object... initargs)
 *      二、获取成员变量并调用
 *        ①批量
 *          1) Field[] getFields:获取所有的公共字段
 *          2) Field[] getDeclaredFields:获取所有字段
 *        ②单个
 *          1) public Field getField(String fieldName) 获取共有的
 *          2) public Field getDeclaredField(String fieldName) 获取某个 包括private
 *        ③设置字段的值
 *          setAccessible(true) 设置访问的权限
 *          Field -> public void set(Object obj, Object value) obj 字段所在的对象 value设置字段的值
 *      三、获取成员方法并调用
 *        ①批量
 *          1) public Method[] getMethods():获取所有的共有方法
 *          2) public Method[] getDeclaredMethods():获取所有的成员方法，包括私有但不包括继承
 *        ②单个
 *          1) public Method getMethod(String name,Class<?>... parameterTypes) name 方法名 Class 形参的Class对象
 *          2) public Method getDeclaredMethod(String name,Class<?>... parameterTypes)
 *        ③调用方法
 *          Method ->public Object invoke(Object obj,Object... args) obj 调用方法的对象 args调用方法时所传递的实参
 *
 * @author jianghui
 * @date 2020-11-26 09:20
 */
public class ReflectTarget {

}
