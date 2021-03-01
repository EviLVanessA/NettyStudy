package org.simpleframework.aop;

import com.study.controller.superadmin.HeadLineOperationController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.simpleframework.core.BeanContainer;
import org.simpleframework.inject.DependencyInjector;

/**
 * @author jianghui
 * @date 2021-03-01 10:04
 */
public class AspectWeaverTest {
    @DisplayName("织入通用逻辑测试：doAop")
    @Test
    public void doAopTest(){
        BeanContainer beanContainer = BeanContainer.getInstance();
        beanContainer.loadBeans("com.study");
        //先进行aop
        new AspectWeaver().doAop();
        //再进行di
        new DependencyInjector().doIoC();
        HeadLineOperationController bean = (HeadLineOperationController) beanContainer.getBean(HeadLineOperationController.class);
        bean.addHeadLine(null,null);
    }
}
