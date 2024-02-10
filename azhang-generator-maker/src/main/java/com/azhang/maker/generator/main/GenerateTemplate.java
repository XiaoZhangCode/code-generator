package com.azhang.maker.generator.main;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import com.azhang.maker.generator.JarGenerator;
import com.azhang.maker.generator.ScriptGenerator;
import com.azhang.maker.generator.VersionControlGenerator;
import com.azhang.maker.generator.file.DynamicFileGenerator;
import com.azhang.maker.meta.Meta;
import com.azhang.maker.meta.MetaManager;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;

public abstract class GenerateTemplate {


    public void doGenerate() throws TemplateException, IOException, InterruptedException {
        Meta meta = MetaManager.getMetaObject();

        // 输出根路径
        String projectPath = System.getProperty("user.dir");
        String outputPath = projectPath + "/Generated" + File.separator + meta.getName();
        if (!FileUtil.exist(outputPath)) {
            FileUtil.mkdir(outputPath);
        }
        // 读取resource目录
        ClassPathResource resource = new ClassPathResource("");
        String inputResourcePath = resource.getAbsolutePath();

        // 1. 拷贝原始的源文件
        String sourceOutputPath = copySource(meta, outputPath);

        // 2.代码生成
        generateCode(meta, outputPath, inputResourcePath);


        // 3.构建jar包
        String jarPath = buildJar(outputPath, meta);
        // 4.封装脚本
        String shellOutputPath = buildScript(outputPath, jarPath);


        // 5.版本控制
        versionControl(meta, outputPath, inputResourcePath);

        // 6.生成精简版程序
        buildDist(outputPath, jarPath, shellOutputPath, sourceOutputPath);
    }

    /**
     * 构建精简版程序
     *
     * @param outputPath       输出路径
     * @param jarPath          jar包路径
     * @param shellOutputPath  shell输出路径
     * @param sourceOutputPath 源文件路径
     */
    protected String buildDist(String outputPath, String jarPath, String shellOutputPath, String sourceOutputPath) {
        // 生成精简版本的程序
        String distOutputPath = outputPath + "-dist";
        // jar包文件
        String distJarPath = distOutputPath + File.separator + "target";
        FileUtil.mkdir(distJarPath);
        String jarAbsolutePath = outputPath + File.separator + jarPath;
        FileUtil.copy(jarAbsolutePath, distJarPath, true);
        // 脚本文件
        FileUtil.copy(shellOutputPath, distOutputPath, true);
        FileUtil.copy(shellOutputPath + ".bat", distOutputPath, true);
        // 拷贝.source文件夹
        FileUtil.copy(sourceOutputPath, distOutputPath, true);
        return distOutputPath;
    }

    /**
     * 版本控制
     *
     * @param meta              元数据
     * @param outputPath        输出路径
     * @param inputResourcePath 资源路径
     */
    protected void versionControl(Meta meta, String outputPath, String inputResourcePath) throws IOException, InterruptedException {
        if (meta.getVersionControl()) {
            VersionControlGenerator.doGenerate(outputPath);
            String inputFilePath = inputResourcePath + File.separator + "templates/static/.gitignore";
            FileUtil.copy(inputFilePath, outputPath, true);
        }
    }

    /**
     * 构建脚本
     *
     * @param outputPath 输出路径
     * @param jarPath    jar包路径
     * @return shell输出路径
     */
    protected String buildScript(String outputPath, String jarPath) {
        String shellOutputPath = outputPath + File.separator + "generator";
        ScriptGenerator.doGenerate(shellOutputPath, jarPath);
        return shellOutputPath;
    }

    /**
     * 构建jar包
     *
     * @param outputPath 输出路径
     * @param meta       元数据
     * @return jar包路径
     */
    protected String buildJar(String outputPath, Meta meta) throws IOException, InterruptedException {
        JarGenerator.doGenerate(outputPath);
        String jarName = String.format("%s-%s-jar-with-dependencies.jar", meta.getName(), meta.getVersion());
        return "target/" + jarName;
    }

    /**
     * 代码生成
     *
     * @param meta              元数据
     * @param outputPath        输出路径
     * @param inputResourcePath 资源路径
     */
    protected void generateCode(Meta meta, String outputPath, String inputResourcePath) throws IOException, TemplateException {
        // Java 包基础路径
        String basePackage = meta.getBasePackage();
        String outputPathPackage = StrUtil.join("/", StrUtil.split(basePackage, "."));
        String outputBaseJavaPackagePath = outputPath + File.separator + "src/main/java/" + outputPathPackage;

        String inputFilePath;
        String outputFilePath;

        // model DataModel
        inputFilePath = inputResourcePath + File.separator + "templates/java/model" + File.separator + "DataModel.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + File.separator + "/model/DataModel.java";
        DynamicFileGenerator.doGenerate(inputFilePath, outputFilePath, meta);

        // command ConfigCommand
        inputFilePath = inputResourcePath + File.separator + "templates/java/cli/command" + File.separator + "ConfigCommand.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + File.separator + "/cli/command/ConfigCommand.java";
        DynamicFileGenerator.doGenerate(inputFilePath, outputFilePath, meta);

        // command GenerateCommand
        inputFilePath = inputResourcePath + File.separator + "templates/java/cli/command" + File.separator + "GenerateCommand.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + File.separator + "/cli/command/GenerateCommand.java";
        DynamicFileGenerator.doGenerate(inputFilePath, outputFilePath, meta);


        // command ListCommand
        inputFilePath = inputResourcePath + File.separator + "templates/java/cli/command" + File.separator + "ListCommand.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + File.separator + "/cli/command/ListCommand.java";
        DynamicFileGenerator.doGenerate(inputFilePath, outputFilePath, meta);


        // util ConsoleUtil
        inputFilePath = inputResourcePath + File.separator + "templates/java/cli/util" + File.separator + "ConsoleUtil.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + File.separator + "/cli/util/ConsoleUtil.java";
        DynamicFileGenerator.doGenerate(inputFilePath, outputFilePath, meta);


        // util ReflexUtil
        inputFilePath = inputResourcePath + File.separator + "templates/java/cli/util" + File.separator + "ReflexUtil.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + File.separator + "/cli/util/ReflexUtil.java";
        DynamicFileGenerator.doGenerate(inputFilePath, outputFilePath, meta);


        // util ConvertUtil
        inputFilePath = inputResourcePath + File.separator + "templates/java/cli/util" + File.separator + "ConvertUtil.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + File.separator + "/cli/util/ConvertUtil.java";
        DynamicFileGenerator.doGenerate(inputFilePath, outputFilePath, meta);


        // cli CommandExecutor
        inputFilePath = inputResourcePath + File.separator + "templates/java/cli" + File.separator + "CommandExecutor.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + File.separator + "/cli/CommandExecutor.java";
        DynamicFileGenerator.doGenerate(inputFilePath, outputFilePath, meta);


        // java Main
        inputFilePath = inputResourcePath + File.separator + "templates/java" + File.separator + "Main.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + File.separator + "/Main.java";
        DynamicFileGenerator.doGenerate(inputFilePath, outputFilePath, meta);

        // generator StaticGenerator
        inputFilePath = inputResourcePath + File.separator + "templates/java/generator" + File.separator + "StaticGenerator.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + File.separator + "/generator/StaticGenerator.java";
        DynamicFileGenerator.doGenerate(inputFilePath, outputFilePath, meta);

        // generator DynamicGenerator
        inputFilePath = inputResourcePath + File.separator + "templates/java/generator" + File.separator + "DynamicGenerator.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + File.separator + "/generator/DynamicGenerator.java";
        DynamicFileGenerator.doGenerate(inputFilePath, outputFilePath, meta);

        // generator MainGenerator
        inputFilePath = inputResourcePath + File.separator + "templates/java/generator" + File.separator + "MainGenerator.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + File.separator + "/generator/MainGenerator.java";
        DynamicFileGenerator.doGenerate(inputFilePath, outputFilePath, meta);

        // pom pom.xml
        inputFilePath = inputResourcePath + File.separator + "templates" + File.separator + "pom.xml.ftl";
        outputFilePath = outputPath + File.separator + "pom.xml";
        DynamicFileGenerator.doGenerate(inputFilePath, outputFilePath, meta);

        // templates README.md.ftl
        inputFilePath = inputResourcePath + File.separator + "templates" + File.separator + "README.md.ftl";
        outputFilePath = outputPath + File.separator + "README.md";
        DynamicFileGenerator.doGenerate(inputFilePath, outputFilePath, meta);
    }

    /**
     * 将源文件拷贝到输出目录
     *
     * @param meta       meta
     * @param outputPath 输出目录
     * @return sourceOutputPath
     */
    protected String copySource(Meta meta, String outputPath) {
        // 1. 将源文件拷贝到输出目录
        String sourceRootPath = meta.getFileConfig().getSourceRootPath();
        String sourceOutputPath = outputPath + File.separator + ".source";
        FileUtil.copy(sourceRootPath, sourceOutputPath, false);
        return sourceOutputPath;
    }

    /**
     * 制作压缩包
     *
     * @param outputPath 输出目录
     * @return 压缩包路径
     */
    protected String buildZip(String outputPath) {
        String zipPath = outputPath + ".zip";
        ZipUtil.zip(outputPath, zipPath);
        return zipPath;
    }


}
