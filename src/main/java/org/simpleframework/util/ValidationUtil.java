package org.simpleframework.util;

import java.util.Collection;

/**
 * @author jianghui
 * @date 2020-11-27 09:15
 */
public class ValidationUtil {
    /**
     * 判断集合类是否为空 为null或者size=0
     *
     * @param obj Collection
     * @return true or false
     */
    public static boolean isEmpty(Collection<?> obj) {
        return obj == null || obj.isEmpty();
    }

    /**
     * 判断数组是否为空
     *
     * @param obj array
     * @return true or false
     */
    public static boolean isEmpty(Object[] obj) {
        return obj == null || obj.length == 0;
    }

    /**
     * 判断字符串是否为空
     *
     * @param obj String
     * @return true or false
     */
    public static boolean isEmpty(String obj) {
        return obj == null || "".equalsIgnoreCase(obj);
    }
}
