package com.azhang.generator;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ArrayUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * @author zhang
 * @date 2023/11/9 18:07
 */
public class StaticGenerator {

    /**
     * 复制文件
     * @param inputPath 输入路径
     * @param outputPath 输出路径
     */
    public static void copyFilesByHuTool(String inputPath, String outputPath) {
        FileUtil.copy(inputPath,outputPath,false);

    }

    /**
     *  递归复制文件 (递归实现 会将输入目录完整的拷贝到目录下)
     * @param inputPath 输入路径
     * @param outputPath 输出路径
     */
    public static void copyFilesByRecursive(String inputPath, String outputPath) {
        File inputFile = new File(inputPath);
        File outputFile = new File(outputPath);
        try {
            copyFileByRecursive(inputFile,outputFile);
        }catch (Exception e){
            System.out.println("复制失败" + e.getMessage());
        }
    }

    /**
     *  递归复制文件
     *  核心思路 ： 先创建目录 然后遍历目录内的文件
     * @param inputFile 输入文件
     * @param outputFile 输出文件
     * 注意: 这里的递归调用是针对文件,如果是目录,则递归调用copyFileByRecursive
     */
    private static void copyFileByRecursive(File inputFile, File outputFile) throws IOException {
        // 判断是否是目录
        if (inputFile.isDirectory()){
            System.out.println(inputFile.getName());
            // 不存在那就创建目录
            if (!outputFile.exists()){
                FileUtil.mkdir(outputFile);
            }
            // 获取目录下的所有文件和子目录
            File[] files = inputFile.listFiles();
            // 如果没有子文件 直接返回
            if (ArrayUtil.isEmpty(files)){
                return;
            }
            for (File file : files) {
                // 递归调用
                copyFileByRecursive(file,new File(outputFile.getAbsolutePath()));
            }
        }else {
            // 是文件,直接复制到目录下
            Path destPath= outputFile.toPath().resolve(inputFile.getName());
            Files.copy(inputFile.toPath(),destPath, StandardCopyOption.REPLACE_EXISTING);

        }
    }


}
