package org.simpleframework.inject;

import com.study.controller.frontend.MainPageController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.simpleframework.core.BeanContainer;

/**
 * @author jianghui
 * @date 2020-11-27 12:32
 */
public class DependencyInjectorTest {
    @DisplayName("依赖注入doIOC")
    @Test
    public void doIoCTest(){
        BeanContainer beanContainer = BeanContainer.getInstance();
        beanContainer.loadBeans("com.study");
        Assertions.assertTrue(beanContainer.isLoaded());
        MainPageController mainPageController = (MainPageController) beanContainer.getBean(MainPageController.class);
        Assertions.assertNotNull(mainPageController);
        Assertions.assertNull(mainPageController.getHeadLineShopCategoryCombineService());
        new DependencyInjector().doIoC();
        Assertions.assertNotEquals(null,mainPageController.getHeadLineShopCategoryCombineService());

    }
}
