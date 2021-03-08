package org.simpleframework.util;

import java.lang.reflect.Parameter;

/**
 * @author jianghui
 * @date 2021-03-08 10:33
 */
public class ConvertUtil {

    /**
     * 返回基本数据类型的空值
     * byte、short、int、long、float、double、char、boolean及其包装类型
     *
     * @param type class
     * @return 转换后的object
     */
    public static Object primitiveNull(Class<?> type) {
        if (type == int.class || type == double.class || type == short.class || type == long.class ||
                type == byte.class || type == float.class) {
            return 0;
        } else if (type == boolean.class) {
            return false;
        }
        return null;
    }

    /**
     * String类型转化为对应的参数类型  Primitive原始的,远古的
     *
     * @param type         参数类型
     * @param requestValue 值
     * @return 转换后的Object
     */
    public static Object convert(Class<?> type, String requestValue) {
        if (isPrimitive(type)) {
            if (ValidationUtil.isEmpty(requestValue)) {
                return primitiveNull(type);
            }
            if (type.equals(Integer.class) || type.equals(int.class)) {
                return Integer.parseInt(requestValue);
            } else if (type.equals(String.class)) {
                return requestValue;
            } else if (type.equals(Double.class) || type.equals(double.class)) {
                return Double.parseDouble(requestValue);
            } else if (type.equals(Float.class) || type.equals(float.class)) {
                return Float.parseFloat(requestValue);
            } else if (type.equals(Long.class) || type.equals(long.class)) {
                return Long.parseLong(requestValue);
            } else if (type.equals(Boolean.class) || type.equals(boolean.class)) {
                return Boolean.parseBoolean(requestValue);
            } else if (type.equals(Short.class) || type.equals(short.class)) {
                return Short.parseShort(requestValue);
            } else if (type.equals(Byte.class) || type.equals(byte.class)) {
                return Byte.parseByte(requestValue);
            } else if (type.equals(Character.class) || type.equals(char.class)) {
                return requestValue;
            }
            return requestValue;
        } else {
            throw new RuntimeException("count not support non primitive type conversion yet");
        }
    }

    /**
     * 判断参数的类型是否是支持的数据类型(包括包装类以及String)
     *
     * @param type 参数类型
     * @return 是否为基本数据类型
     */
    private static boolean isPrimitive(Class<?> type) {
        return type == Boolean.class || type == boolean.class ||
                type == Integer.class || type == int.class ||
                type == Double.class || type == double.class ||
                type == Float.class || type == float.class ||
                type == Long.class || type == long.class ||
                type == Short.class || type == short.class ||
                type == Byte.class || type == byte.class ||
                type == Character.class || type == char.class ||
                type == String.class;

    }
}
