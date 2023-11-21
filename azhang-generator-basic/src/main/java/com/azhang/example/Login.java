package com.azhang.example;

import lombok.Data;
import picocli.CommandLine;
import picocli.CommandLine.Option;

import java.io.Console;
import java.util.Scanner;
import java.util.concurrent.Callable;

/**
 * @author zhang
 * @date 2023/11/20 10:43
 */
@Data
public class Login implements Callable<Integer> {

    @Option(names = {"-u", "--user"}, description = "账号")
    String userName;

    @Option(names = {"-p", "--password"}, description = "密码", arity = "0..1", interactive = true, echo = true)
    String password;

    @Option(names = {"-cp", "--checkPassword"}, description = "确认密码", arity = "0..1", interactive = true, echo = true)
    String checkPassword;

    @Override
    public Integer call() throws Exception {
        Class<Login> loginClass = Login.class;
        // 字段上的
        for (java.lang.reflect.Field field : loginClass.getDeclaredFields()) {
            field.setAccessible(true);
            System.out.println(field.getName() + " = " + field.get(this));
            Option option = field.getAnnotation(Option.class);
            // 获取option中的interactive是否为true
            if (option != null && option.interactive() && field.get(this) == null) {
                String string = getConsoleValue("enter for value for --" + field.getName() + ": ");
                field.set(this, string);
            }
        }
        System.out.println(this);
        return null;
    }

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

    public static void main(String[] args) {
        new CommandLine(new Login()).execute("-u", "zhang", "-p");
    }


}
