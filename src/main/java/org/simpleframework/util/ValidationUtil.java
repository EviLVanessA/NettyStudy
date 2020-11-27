package org.simpleframework.util;

import java.util.Collection;

/**
 * @author jianghui
 * @date 2020-11-27 09:15
 */
public class ValidationUtil {
    /**
     * 判断集合类是否为空
     * @param obj Collection
     * @return true or false
     */
    public static boolean isEmpty(Collection<?> obj){
        return obj == null || obj.isEmpty();
    }
}
