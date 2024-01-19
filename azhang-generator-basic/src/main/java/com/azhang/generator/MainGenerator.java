package com.azhang.generator;

import com.azhang.model.MainTemplateConfig;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;

/**
 * @author zhang
 * @date 2023/11/10 10:33
 * 核心生成器
 */
public class MainGenerator {

    /**
     * 核心生成器
     * @param model model数据模型
     * @throws IOException IOException
     * @throws TemplateException TemplateException
     */
    public static void doGenerate(Object model) throws IOException, TemplateException {
        String projectPath = System.getProperty("user.dir");
        File parentFile = new File(projectPath).getParentFile();
        // 输入路径
        String inputPath = new File(parentFile, "azhang-generator-demo-projects/acm-template").getAbsolutePath();
        // 生成静态文件
        StaticGenerator.copyFilesByRecursive(inputPath, projectPath);
        // 生成动态文件
        String dynamicInputPath = "src/main/resources/templates/MainTemplate.java.ftl";
        String dynamicOutputPath = projectPath + File.separator + "acm-template/src/com/azhang/acm/MainTemplate.java";
        DynamicGenerator.doGenerate(dynamicInputPath, dynamicOutputPath,model);
    }

    /**
     * 核心生成器
     * @param model model数据模型
     * @throws IOException IOException
     * @throws TemplateException TemplateException
     */
//    public static void doGenerate(Object model) throws IOException, TemplateException {
//
//        String inputRootPath = "D:/Program Files/code/me/zhangPro/azhang-generator/azhang-generator-demo-projects/acm-template-pro";
//        String outputRootPath = "D:/Program Files/code/me/zhangPro/azhang-generator/azhang-generator-maker/Generated/acm-template-pro";
//
//
//        String inputPath;
//        String outputPath;
//
//        inputPath = new File(inputRootPath, "src/com/azhang/acm/MainTemplate.java.ftl").getAbsolutePath();
//        outputPath = new File(outputRootPath, "src/com/azhang/acm/MainTemplate.java").getAbsolutePath();
//        DynamicGenerator.doGenerate(inputPath, outputPath, model);
//
//        inputPath = new File(inputRootPath, ".gitignore").getAbsolutePath();
//        outputPath = new File(outputRootPath, "gitignore").getAbsolutePath();
//        StaticGenerator.copyFilesByHuTool(inputPath, outputPath);
//
//        inputPath = new File(inputRootPath, "README.md").getAbsolutePath();
//        outputPath = new File(outputRootPath, "README.md").getAbsolutePath();
//        StaticGenerator.copyFilesByHuTool(inputPath, outputPath);
//
//
//    }

    public static void main(String[] args) throws TemplateException, IOException {
        MainTemplateConfig mainTemplateConfig = new MainTemplateConfig();
        mainTemplateConfig.setLoop(true);
        mainTemplateConfig.setAuthor("zhang");
        mainTemplateConfig.setOutputText("结果 :");
        doGenerate(mainTemplateConfig);
    }
}
