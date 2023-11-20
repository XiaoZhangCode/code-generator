package com.azhang;


import java.io.Console;
import java.io.File;
import java.util.Scanner;

import static com.azhang.generator.StaticGenerator.copyFilesByHuTool;
import static com.azhang.generator.StaticGenerator.copyFilesByRecursive;

/**
 * @author zhang
 * @date 2023/11/9 17:56
 */
public class Main {
    /*    public static void main(String[] args) {
            String projectPath = System.getProperty("user.dir");
            File parentFile = new File(projectPath).getParentFile();

            // 输入路径 : ACM示例代码模版目录
            String inputPath = new File(parentFile, "azhang-generator-demo-projects/acm-template").getAbsolutePath();
            // 输出到根目录
            copyFilesByRecursive(inputPath, projectPath);

        }*/
    public static void main(String[] args) {
        CommandExecutor commandExecutor = new CommandExecutor();
        commandExecutor.doExecute(args);
    }
}
