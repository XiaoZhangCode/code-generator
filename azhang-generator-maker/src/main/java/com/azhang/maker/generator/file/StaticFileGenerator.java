package com.azhang.maker.generator.file;

import cn.hutool.core.io.FileUtil;

/**
 * @author zhang
 * @date 2023/11/9 18:07
 */
public class StaticFileGenerator {

    /**
     * 复制文件
     *
     * @param inputPath  输入路径
     * @param outputPath 输出路径
     */
    public static void copyFilesByHuTool(String inputPath, String outputPath) {
        FileUtil.copy(inputPath, outputPath, false);
    }


}
