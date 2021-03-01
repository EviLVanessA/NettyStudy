package org.simpleframework.aop;

import org.simpleframework.aop.annotation.Aspect;
import org.simpleframework.aop.annotation.Order;
import org.simpleframework.aop.aspect.AspectInfo;
import org.simpleframework.aop.aspect.DefaultAspect;
import org.simpleframework.core.BeanContainer;
import org.simpleframework.util.ValidationUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Proxy;
import java.util.*;

/**
 * @author jianghui
 * @date 2021-02-26 17:00
 */
public class AspectWeaver {
    private BeanContainer beanContainer;

    public AspectWeaver() {
        this.beanContainer = BeanContainer.getInstance();
    }

    public void doAop() {
        //1获取所有的切面类
        Set<Class<?>> aspectSet = beanContainer.getClassByAnnotation(Aspect.class);
        if (ValidationUtil.isEmpty(aspectSet)) {
            return;
        }
        //2拼装AspectInfoList
        List<AspectInfo> aspectInfoList = packAspectInfoList(aspectSet);
        //3遍历容器里的类
        Set<Class<?>> classSet = beanContainer.getClasses();
        if (ValidationUtil.isEmpty(classSet)) {
            return;
        }
        for (Class<?> targetClass : classSet) {
            if (!targetClass.isAnnotationPresent(Aspect.class)) {
                //4粗筛符合条件的Aspect
                List<AspectInfo> roughMatchedAspectList = collectRoughMatchedAspectListForSpecificClass(aspectInfoList, targetClass);
                //5尝试进行Aspect的织入
                wrapIfNecessary(roughMatchedAspectList, targetClass);
            }
        }

    }

    /**
     * 如有必要进行织入
     * @param roughMatchedAspectList 进行了粗粒度筛选的AspectInfo
     * @param targetClass
     */
    private void wrapIfNecessary(List<AspectInfo> roughMatchedAspectList, Class<?> targetClass) {
        if (ValidationUtil.isEmpty(roughMatchedAspectList)){
            return;
        }
        //创建动态代理对象
        AspectListExecutor aspectListExecutor = new AspectListExecutor(targetClass,roughMatchedAspectList);
        Object proxy = ProxyCreator.createProxy(targetClass, aspectListExecutor);
        beanContainer.addBean(targetClass,proxy);
    }

    /**
     * 对AspectInfo(切入信息)进行粗粒度筛选
     *
     * @param aspectInfoList
     * @param targetClass
     * @return
     */
    private List<AspectInfo> collectRoughMatchedAspectListForSpecificClass(List<AspectInfo> aspectInfoList, Class<?> targetClass) {
        List<AspectInfo> roughMatchedAspectList = new ArrayList<>();
        for (AspectInfo aspectInfo : aspectInfoList) {
            if (aspectInfo.getPointcutLocator().roughMatches(targetClass)) {
                roughMatchedAspectList.add(aspectInfo);
            }
        }
        return roughMatchedAspectList;
    }

    /**
     * 把所有的Aspect.class封装成AspectInfo
     *
     * @param aspectSet
     * @return
     */
    private List<AspectInfo> packAspectInfoList(Set<Class<?>> aspectSet) {
        List<AspectInfo> aspectInfoList = new ArrayList<>();
        for (Class<?> aspectClass : aspectSet) {
            if (verifyAspect(aspectClass)) {
                Order orderTag = aspectClass.getAnnotation(Order.class);
                Aspect aspectTag = aspectClass.getAnnotation(Aspect.class);
                DefaultAspect defaultAspect = (DefaultAspect) beanContainer.getBean(aspectClass);
                //进行初始化表达式定位器
                PointcutLocator pointcutLocator = new PointcutLocator(aspectTag.pointcut());
                AspectInfo aspectInfo = new AspectInfo(orderTag.value(), defaultAspect, pointcutLocator);
                aspectInfoList.add(aspectInfo);
            } else {
                throw new RuntimeException("@Aspect and @Order must added to the Aspect class," +
                        " and Aspect class must extend from DefaultAspect");
            }
        }
        return aspectInfoList;
    }

    /**
     * 根据分类进行织入操作
     *
     * @param category       要织入的类
     * @param aspectInfoList 织入信息
     */
    private void weaveByCategory(Class<? extends Annotation> category, List<AspectInfo> aspectInfoList) {
        //获取被代理类的集合
        Set<Class<?>> classSet = beanContainer.getClassByAnnotation(category);
        if (ValidationUtil.isEmpty(classSet)) {
            return;
        }
        //遍历被代理类，分别为每个被代理类生成动态代理实例
        for (Class<?> targetClass : classSet) {
            AspectListExecutor aspectListExecutor = new AspectListExecutor(targetClass, aspectInfoList);
            //创建代理对象
            Object proxyBean = ProxyCreator.createProxy(targetClass, aspectListExecutor);
            //将动态代理对象实例添加到容器里，取代未被代理前的类实例
            beanContainer.addBean(targetClass, proxyBean);
        }
    }

    /**
     * 验证Aspect类是否满足规范 ：遵守给Aspect类添加@Aspect和@Order标签的规范,同时,必须继承子DefaultAspect.class
     * 此外,@Aspect的属性值不能是它本身(防止出现死循环)
     *
     * @param aspectClass 切入类的class
     * @return 布尔值
     */
    private boolean verifyAspect(Class<?> aspectClass) {
        return aspectClass.isAnnotationPresent(Aspect.class) &&
                aspectClass.isAnnotationPresent(Order.class) &&
                DefaultAspect.class.isAssignableFrom(aspectClass);
    }
}
