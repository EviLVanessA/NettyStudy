package org.simpleframework.aop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.simpleframework.aop.aspect.AspectInfo;
import org.simpleframework.aop.mock.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jianghui
 * @date 2021-02-26 14:36
 */
public class AspectListExecutorTest {
    @DisplayName("Aspect排序：sortAspectList")
    @Test
    public void sortTest(){
        List<AspectInfo> list = new ArrayList<>();
        list.add(new AspectInfo(3,new Mock1(),null));
        list.add(new AspectInfo(5,new Mock2(),null));
        list.add(new AspectInfo(2,new Mock3(),null));
        list.add(new AspectInfo(4,new Mock4(),null));
        list.add(new AspectInfo(1,new Mock5(),null));

        AspectListExecutor aspectListExecutor = new AspectListExecutor(AspectListExecutorTest.class,list);
        List<AspectInfo> list1 = aspectListExecutor.getSortedAspectInfoList();
        for (AspectInfo aspectInfo : list1){
            System.out.println(aspectInfo.getAspectObject().getClass().getName());
        }
    }
}
