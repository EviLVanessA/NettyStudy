package demo.annotation;

/**
 * @author jianghui
 * @date 2020-11-26 10:37
 * stu  注解：用注解来保存类相关的信息以供反射调用 、提供了一种为程序元素设置元数据的方法
 *          ①元数据是添加到程序元素如方法、字段、类和包上的额外信息
 *          ②注解是一种分散式的元数据的设置方式，XML是集中式的设置方式
 *          ③注解不能直接干扰程序代码的运行
 *      注解的功能：
 *          ①作为特定的标记，用于告诉编译器一些信息
 *          ②编译时动态处理，如动态生成代码
 *          ③运行时动态处理，作为额外信息的载体，如获取注解信息
 *      注解的分类：
 *          ①标准注解：Override、Deprecated、SuppressWarnings
 *          ②元注解：修饰注解的注解 1) @Retention：注解的生命周期
 *                  2) @Target：注解的作用目标,描述所修饰的注解的使用范围
 *                     packages、types 类、接口、枚举、Annotation类型
 *                     类型成员、方法参数和本地变量
 *                  3) @Inherited：是否允许子类继承该注解
 *                  4) @Documented：注解是否应当被包含JavaDoc文档中
 *          ③自定义注解
 *      注解的工作原理
 *          ①通过键值对的形式为注解属性赋值
 *          ②遍历器检查注解的使用范围，将注解信息写入元素属性表
 *          ③运行时JVM将Runtime的所有注解属性取出并最终存入到map里
 *          ④创建AnnotationInvocationHandler实例并传入前面的map
 *          ⑤JVM使用JDK动态代理为注解生成代理类，并初始化处理器
 *          ⑥调用invoke方法，通过传入方法名返回注解对应的属性
 *
 */
public class AnnotationDemo {
}
