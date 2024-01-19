package ${basePackage}.cli.util;

import java.io.Console;
import java.util.Scanner;

/**
 * @author ${author}
 * @date ${.now}
 */
public class ConsoleUtil {

    // 获取控制台输出对象 如果为空返回一个可以读取命令行的对象
    public static String getConsoleValue(String msg) {
        Console console = System.console();
        if (console == null) {
            Scanner scanner = new Scanner(System.in);
            System.out.print(msg);
            return scanner.next();
        } else {
            return console.readLine(msg);
        }
    }

}
