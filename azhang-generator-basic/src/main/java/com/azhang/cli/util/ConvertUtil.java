package com.azhang.cli.util;

/**
 * @author zhang
 * @date 2023/11/21 9:22
 */
public class ConvertUtil {
    public static Object convertValueToFieldType(String value, Class<?> fieldType) {
        if (fieldType.equals(String.class)) {
            return value;
        } else if (fieldType.equals(Integer.class)) {
            return Integer.parseInt(value);
        } else if (fieldType.equals(Double.class)) {
            return Double.parseDouble(value);
        } else if (fieldType.equals(Float.class)) {
            return Float.parseFloat(value);
        } else if (fieldType.equals(Short.class)) {
            return Short.parseShort(value);
        } else if (fieldType.equals(Long.class)) {
            return Long.parseLong(value);
        } else if (fieldType.equals(Boolean.class)) {
            return Boolean.parseBoolean(value);
        } else if (fieldType.equals(Character.class)) {
            return value.charAt(0);
        } else {
            throw new IllegalArgumentException("Unsupported field type: " + fieldType);
        }
    }
}
