package org.simpleframework.aop.aspect;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.simpleframework.aop.PointcutLocator;

/**
 * @author jianghui
 * @date 2021-02-26 14:28
 */
@AllArgsConstructor
@Getter
public class AspectInfo {
    /**
     * 执行顺序
     */
    private int orderIndex;
    /**
     * 切入的方法逻辑
     */
    private DefaultAspect aspectObject;

    private PointcutLocator pointcutLocator;
}
