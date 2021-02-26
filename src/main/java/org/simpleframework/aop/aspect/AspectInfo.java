package org.simpleframework.aop.aspect;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * @author jianghui
 * @date 2021-02-26 14:28
 */
@AllArgsConstructor
@Getter
public class AspectInfo {
    private int orderIndex;
    private DefaultAspect aspectObject;
}
