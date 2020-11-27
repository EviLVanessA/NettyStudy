package org.simpleframework.core;

import com.study.HelloServlet;
import com.study.controller.fronted.MainPageController;
import org.junit.jupiter.api.*;

/**
 * @author jianghui
 * @date 2020-11-27 09:31
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BeanContainTest {
    private static BeanContainer beanContainer;

    @BeforeAll
    static void init() {
        beanContainer = BeanContainer.getInstance();
    }
    @DisplayName("加载目标类及其实例化")
    @Order(1)
    @Test
    public void loadBeanTest() {
        Assertions.assertFalse(beanContainer.isLoaded());
        beanContainer.loadBeans("com.study");
        Assertions.assertEquals(6, beanContainer.size());
        Assertions.assertTrue(beanContainer.isLoaded());
    }

    @DisplayName("根据类获取其实例：getBeanTest")
    @Order(2)
    @Test
    public void getBeanTest(){
        MainPageController controller = (MainPageController) beanContainer.getBean(MainPageController.class);
        HelloServlet helloServlet = (HelloServlet) beanContainer.getBean(HelloServlet.class);
        Assertions.assertNotNull(controller);
        Assertions.assertNull(helloServlet);

    }
}
