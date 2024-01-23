package com.azhang.maker.generator;


import java.io.*;

public class VersionControlGenerator {

    public static void doGenerate(String projectDir) throws IOException, InterruptedException {
        String gitCommand = "git init";
        Process process = new ProcessBuilder(gitCommand.split(" ")).directory(new File(projectDir)).start();
        InputStream inputStream = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        System.out.println("git 初始化完成!");
        int exitCode = process.waitFor();
        System.out.println("git命令执行结束,退出码:" + exitCode);

    }

}
