package com.azhang.maker.generator;

import cn.hutool.core.io.FileUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;

public class ScriptGenerator {

    public static void doGenerate(String outputPath,String jarPath) {

        StringBuilder sb = new StringBuilder();
        // 根据系统类型生成脚本 windows
        sb.append("@echo off").append("\n");
        sb.append("java -jar ").append(jarPath).append(" %*").append("\n");
        FileUtil.writeBytes(sb.toString().getBytes(StandardCharsets.UTF_8), outputPath + ".bat");


        // linux
        sb = new StringBuilder();
        sb.append("#!/bin/bash").append("\n");
        sb.append("java -jar ").append(jarPath).append(" \"$@\"").append("\n");
        FileUtil.writeBytes(sb.toString().getBytes(StandardCharsets.UTF_8), outputPath);
        try {
            // 添加可执行权限
            Set<PosixFilePermission> permissions = PosixFilePermissions.fromString("rwxrwxrwx");
            Files.setPosixFilePermissions(Paths.get(outputPath), permissions);
        } catch (Exception e) {

        }
    }

    public static void main(String[] args) {
        doGenerate("D:\\Program Files\\code\\me\\zhangPro\\azhang-generator\\azhang-generator-maker\\Generated\\acm-template-pro-generator\\target\\generator", "");
    }

}
