package com.azhang.cli.util;

import picocli.CommandLine;

import static com.azhang.cli.util.ConsoleUtil.getConsoleValue;
import static com.azhang.cli.util.ConvertUtil.convertValueToFieldType;

/**
 * @author zhang
 * @date 2023/11/21 9:30
 */
public class ReflexUtil {

    public static void setFieldsWithInteractiveAnnotation(Object instance,Class<?> classzz) throws IllegalAccessException {
        for (java.lang.reflect.Field field : classzz.getDeclaredFields()) {
            field.setAccessible(true);
            CommandLine.Option option = field.getAnnotation(CommandLine.Option.class);
            if (option != null && option.interactive() && field.get(instance) == null) {
                String value = getConsoleValue("enter for value for --" + field.getName() + ": ");
                Object fieldValue = convertValueToFieldType(value, field.getType());
                field.set(instance, fieldValue);
            }
        }
    }

}
