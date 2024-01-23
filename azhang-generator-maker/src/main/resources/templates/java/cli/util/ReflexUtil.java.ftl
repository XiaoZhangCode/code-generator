package ${basePackage}.cli.util;

import picocli.CommandLine;
import java.util.Arrays;

import static ${basePackage}.cli.util.ConsoleUtil.getConsoleValue;
import static ${basePackage}.cli.util.ConvertUtil.convertValueToFieldType;

/**
 * @author ${author}
 * @date ${.now}
 */
public class ReflexUtil {

    public static void setFieldsWithInteractiveAnnotation(Object instance,Class<?> classzz) throws IllegalAccessException {
        for (java.lang.reflect.Field field : classzz.getDeclaredFields()) {
            field.setAccessible(true);
            CommandLine.Option option = field.getAnnotation(CommandLine.Option.class);
            if (option != null && option.interactive() && field.get(instance) == null) {
                String value = getConsoleValue("enter for value for --" + field.getName() + "("+ Arrays.toString(option.description()) +")" + ": ");
                Object fieldValue = convertValueToFieldType(value, field.getType());
                field.set(instance, fieldValue);
            }
        }
    }

}
