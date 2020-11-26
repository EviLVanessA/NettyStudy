package org.simpleframework.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

/**
 * @author jianghui
 * @date 2020-11-26 14:13
 */
public class ClassUtilTest {
    @DisplayName("提取目标类方法：extractPackageClassTest")
    @Test
    public void extractPackageClassTest(){
        Set<Class<?>> classSet = ClassUtil.extractPackageClass("com.study.entity");
        if (classSet != null){
            classSet.forEach(System.out::println);
            Assertions.assertEquals(4,classSet.size());
        }
    }
}
