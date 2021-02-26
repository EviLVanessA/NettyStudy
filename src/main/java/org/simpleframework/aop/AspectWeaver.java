package org.simpleframework.aop;

import demo.annotation.AnnotationDemo;
import org.simpleframework.aop.annotation.Aspect;
import org.simpleframework.aop.aspect.AspectInfo;
import org.simpleframework.core.BeanContainer;
import org.simpleframework.util.ValidationUtil;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author jianghui
 * @date 2021-02-26 17:00
 */
public class AspectWeaver {
    private BeanContainer beanContainer;

    public AspectWeaver() {
        this.beanContainer = BeanContainer.getInstance();
    }

    public void doAOP() {
        //1获取所有的切面类
        Set<Class<?>> aspectSet = beanContainer.getClassByAnnotation(Aspect.class);
        //2将切面类按照不同的织入目标进行切分
        Map<Class<? extends Annotation>, List<AspectInfo>> categorizedMap = new HashMap<>();
        if (ValidationUtil.isEmpty(aspectSet)) {
            return;
        }
        for (Class<?> clazz : aspectSet) {
            if (verifyAspect(clazz)) {
                categorizeAspect(categorizedMap, clazz);
            } else {

            }
        }
        //3按照不同的织入目标分别织入Aspect的逻辑
    }

    /**
     * 验证Aspect类是否满足规范
     *
     * @param clazz
     * @return
     */
    private boolean verifyAspect(Class<?> clazz) {
        return false;
    }

    private void categorizeAspect(Map<Class<? extends Annotation>, List<AspectInfo>> categorizedMap, Class<?> clazz) {
    }


}
