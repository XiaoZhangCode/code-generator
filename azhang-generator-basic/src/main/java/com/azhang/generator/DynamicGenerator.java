package com.azhang.generator;

import freemarker.template.Configuration;
import com.azhang.model.MainTemplateConfig;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author zhang
 * @date 2023/11/10 9:34
 * 动态文件生成器
 */
public class DynamicGenerator {
    public static void main(String[] args) throws IOException, TemplateException {
        String inputPath = "src/main/resources/templates/MainTemplate.java.ftl";
        String outputPath = "src/main/java/com/azhang/MainTemplate.java";
        // 创建数据模型
        MainTemplateConfig mainTemplateConfig = new MainTemplateConfig();
        mainTemplateConfig.setLoop(false);
        mainTemplateConfig.setAuthor("zhang");
        mainTemplateConfig.setOutputText("输出结果为:");
        doGenerate(inputPath, outputPath, mainTemplateConfig);

    }


    /**
     * 动态生成文件
     *
     * @param inputPath  模板文件路径
     * @param outputPath 输出文件路径
     * @param model      数据模型
     */
    public static void doGenerate(String inputPath, String outputPath, Object model) throws IOException, TemplateException {
        // 1.获取配置对象
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_32);
        // 2. 指定模板文件所在的路径
        File templateDir = new File(inputPath).getParentFile();
        configuration.setDirectoryForTemplateLoading(templateDir);
        // 3. 设置字符编码
        configuration.setDefaultEncoding("UTF-8");
        // 4. 创建模板对象，并设置模板文件
        Template template = configuration.getTemplate(new File(inputPath).getName());
        // 5. 输出文件
        FileWriter out = new FileWriter(outputPath);
        template.process(model, out);
        // 6. 关闭流
        out.close();
    }


}
