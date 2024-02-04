package com.azhang.maker.template;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.json.JSONUtil;
import com.azhang.maker.meta.Meta;
import com.azhang.maker.template.enums.FileFilerRuleEnum;
import com.azhang.maker.template.enums.FileFilterRangeEnum;
import com.azhang.maker.template.model.FileFilterConfig;
import com.azhang.maker.template.model.TemplateMakerConfig;
import com.azhang.maker.template.model.TemplateMakerFileConfig;
import com.azhang.maker.template.model.TemplateMakerModelConfig;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class TemplateMakerTest {


    @Test
    public void makeTemplate() {

/*        // 1.提供输入参数：包括生成器基本信息、原始项目目录、原始文件、模型参数
        Meta meta = new Meta();
        // 基本信息
        meta.setName("acm-template-generator");
        meta.setDescription("ACM 示例模板生成器");

        String projectPath = System.getProperty("user.dir");
        String originProjectPath = FileUtil.normalize(new File(projectPath).getParent() + File.separator + "azhang-generator-demo-projects" + File.separator + "acm-template");

        String fileInputPath = "src/com/azhang/acm/MainTemplate.java";


        // 输入模型参数信息 1
        Meta.ModelConfig.ModelInfo modelInfo = new Meta.ModelConfig.ModelInfo();
        modelInfo.setFieldName("outputText");
        modelInfo.setDefaultValue("sum: ");
        modelInfo.setType("String");
        String searchStr = "Sum: ";


        // 输入模型参数信息 2
        Meta.ModelConfig.ModelInfo modelInfo2 = new Meta.ModelConfig.ModelInfo();
        modelInfo2.setFieldName("className");
        modelInfo2.setDefaultValue("MainTemplate");
        modelInfo2.setType("String");
        String searchStr2 = "MainTemplate";

        long id = TemplateMaker.makeTemplate(meta, originProjectPath, fileInputPath, modelInfo, searchStr, null);
        TemplateMaker.makeTemplate(meta, originProjectPath, fileInputPath, modelInfo2, searchStr2, id);*/

        System.out.println("---------------------------------    测试Spring boot init 项目    ----------------------------------------------------");
        Meta meta = new Meta();
        // 基本信息
        meta.setName("spring boot init ");
        meta.setDescription("spring boot 初始化项目");

        String projectPath = System.getProperty("user.dir");
        String originProjectPath = FileUtil.normalize(new File(projectPath).getParent() + File.separator + "azhang-generator-demo-projects" + File.separator + "springboot-init");

        String fileInputPath1 = "src/main/java/com/yupi/springbootinit/common";
        String fileInputPath2 = "src/main/resources/application.yml";

        TemplateMakerModelConfig templateMakerModelConfig = new TemplateMakerModelConfig();
        ArrayList<TemplateMakerModelConfig.ModelInfoConfig> modelInfoConfigList = new ArrayList<>();
        TemplateMakerModelConfig.ModelGroupConfig modelGroupConfig = new TemplateMakerModelConfig.ModelGroupConfig();
        modelGroupConfig.setGroupKey("mysql");
        modelGroupConfig.setGroupName("数据库配置");

        TemplateMakerModelConfig.ModelInfoConfig modelInfoConfig1 = new TemplateMakerModelConfig.ModelInfoConfig();
        modelInfoConfig1.setFieldName("url");
        modelInfoConfig1.setType("String");
        modelInfoConfig1.setDescription("数据库url设置");
        modelInfoConfig1.setDefaultValue("jdbc:mysql://localhost:3306/my_db");
        modelInfoConfig1.setAbbr("-h");
        modelInfoConfig1.setReplaceText("jdbc:mysql://localhost:3306/my_db");
        modelInfoConfigList.add(modelInfoConfig1);

        TemplateMakerModelConfig.ModelInfoConfig modelInfoConfig2 = new TemplateMakerModelConfig.ModelInfoConfig();
        modelInfoConfig2.setFieldName("username");
        modelInfoConfig2.setType("String");
        modelInfoConfig2.setDescription("账号");
        modelInfoConfig2.setDefaultValue("root");
        modelInfoConfig2.setAbbr("-u");
        modelInfoConfig2.setReplaceText("root");
        modelInfoConfigList.add(modelInfoConfig2);

        templateMakerModelConfig.setModels(modelInfoConfigList);
        templateMakerModelConfig.setModelGroupConfig(modelGroupConfig);


        TemplateMakerFileConfig makerFileConfig = new TemplateMakerFileConfig();
        TemplateMakerFileConfig.FileInfoConfig fileInfoConfig1 = new TemplateMakerFileConfig.FileInfoConfig();
        fileInfoConfig1.setPath(fileInputPath1);
        ArrayList<FileFilterConfig> configArrayList = new ArrayList<>();
        FileFilterConfig filterConfig1 = FileFilterConfig.builder()
                .range(FileFilterRangeEnum.FILE_NAME.getValue())
                .rule(FileFilerRuleEnum.CONTAINS.getValue())
                .value("Base")
                .build();
        configArrayList.add(filterConfig1);
        fileInfoConfig1.setFileFilterConfigList(configArrayList);

        TemplateMakerFileConfig.FileInfoConfig fileInfoConfig2 = new TemplateMakerFileConfig.FileInfoConfig();
        fileInfoConfig2.setPath(fileInputPath2);
        ArrayList<FileFilterConfig> configArrayList2 = new ArrayList<>();
        FileFilterConfig filterConfig2 = FileFilterConfig.builder()
                .range(FileFilterRangeEnum.FILE_NAME.getValue())
                .rule(FileFilerRuleEnum.CONTAINS.getValue())
                .value("application")
                .build();
        configArrayList2.add(filterConfig2);
        fileInfoConfig2.setFileFilterConfigList(configArrayList2);

        makerFileConfig.setFiles(Arrays.asList(fileInfoConfig1, fileInfoConfig2));

        // 配置分组
        TemplateMakerFileConfig.FileGroupConfig fileGroupConfig = new TemplateMakerFileConfig.FileGroupConfig();
        fileGroupConfig.setGroupKey("mysql");
        fileGroupConfig.setGroupName("测试分组1");
        fileGroupConfig.setCondition("mysql");
        makerFileConfig.setFileGroupConfig(fileGroupConfig);

        long id = TemplateMaker.makeTemplate(meta, originProjectPath, makerFileConfig, templateMakerModelConfig,null, 1L);

        System.out.println("---------------------------------    测试完成Spring boot init 项目    ----------------------------------------------------");


    }


    @Test
    public void makeTemplateBug1() {
        System.out.println("---------------------------------    测试Spring boot init 项目    ----------------------------------------------------");
        Meta meta = new Meta();
        // 基本信息
        meta.setName("spring boot init ");
        meta.setDescription("spring boot 初始化项目");

        String projectPath = System.getProperty("user.dir");
        String originProjectPath = FileUtil.normalize(new File(projectPath).getParent() + File.separator + "azhang-generator-demo-projects" + File.separator + "springboot-init");

        String fileInputPath2 = "src/main/resources/application.yml";

        TemplateMakerModelConfig templateMakerModelConfig = new TemplateMakerModelConfig();
        ArrayList<TemplateMakerModelConfig.ModelInfoConfig> modelInfoConfigList = new ArrayList<>();
        TemplateMakerModelConfig.ModelGroupConfig modelGroupConfig = new TemplateMakerModelConfig.ModelGroupConfig();
        modelGroupConfig.setGroupKey("mysql");
        modelGroupConfig.setGroupName("数据库配置");

        TemplateMakerModelConfig.ModelInfoConfig modelInfoConfig1 = new TemplateMakerModelConfig.ModelInfoConfig();
        modelInfoConfig1.setFieldName("url");
        modelInfoConfig1.setType("String");
        modelInfoConfig1.setDescription("数据库url设置");
        modelInfoConfig1.setDefaultValue("jdbc:mysql://localhost:3306/my_db");
        modelInfoConfig1.setAbbr("-h");
        modelInfoConfig1.setReplaceText("jdbc:mysql://localhost:3306/my_db");
        modelInfoConfigList.add(modelInfoConfig1);

        TemplateMakerModelConfig.ModelInfoConfig modelInfoConfig2 = new TemplateMakerModelConfig.ModelInfoConfig();
        modelInfoConfig2.setFieldName("username");
        modelInfoConfig2.setType("String");
        modelInfoConfig2.setDescription("账号");
        modelInfoConfig2.setDefaultValue("root");
        modelInfoConfig2.setAbbr("-u");
        modelInfoConfig2.setReplaceText("root");
        modelInfoConfigList.add(modelInfoConfig2);

        templateMakerModelConfig.setModels(modelInfoConfigList);
        templateMakerModelConfig.setModelGroupConfig(modelGroupConfig);


        TemplateMakerFileConfig makerFileConfig = new TemplateMakerFileConfig();
        TemplateMakerFileConfig.FileInfoConfig fileInfoConfig2 = new TemplateMakerFileConfig.FileInfoConfig();
        fileInfoConfig2.setPath(fileInputPath2);
        ArrayList<FileFilterConfig> configArrayList2 = new ArrayList<>();
        FileFilterConfig filterConfig2 = FileFilterConfig.builder()
                .range(FileFilterRangeEnum.FILE_NAME.getValue())
                .rule(FileFilerRuleEnum.CONTAINS.getValue())
                .value("application")
                .build();
        configArrayList2.add(filterConfig2);
        fileInfoConfig2.setFileFilterConfigList(configArrayList2);

        makerFileConfig.setFiles(Collections.singletonList(fileInfoConfig2));

        // 配置分组
        TemplateMakerFileConfig.FileGroupConfig fileGroupConfig = new TemplateMakerFileConfig.FileGroupConfig();
        fileGroupConfig.setGroupKey("mysql");
        fileGroupConfig.setGroupName("mysql配置文件");
        fileGroupConfig.setCondition("mysql");
        makerFileConfig.setFileGroupConfig(fileGroupConfig);

        long id = TemplateMaker.makeTemplate(meta, originProjectPath, makerFileConfig, templateMakerModelConfig,null, null);
        id = TemplateMaker.makeTemplate(meta, originProjectPath, makerFileConfig, templateMakerModelConfig, null,id);
        System.out.println("id:" + id);

        System.out.println("---------------------------------    测试完成Spring boot init 项目    ----------------------------------------------------");

    }


    @Test
    public void makeTemplateBug2() {
        System.out.println("-------------------    测试Spring boot init 项目  makeTemplateBug2   ------------------");
        Meta meta = new Meta();
        // 基本信息
        meta.setName("spring boot init ");
        meta.setDescription("spring boot 初始化项目");

        String projectPath = System.getProperty("user.dir");
        String originProjectPath = FileUtil.normalize(new File(projectPath).getParent() + File.separator + "azhang-generator-demo-projects" + File.separator + "springboot-init");

//        String fileInputPath2 = "src/main/java/com/yupi/springbootinit/common";
        String fileInputPath2 = "./";

        TemplateMakerModelConfig templateMakerModelConfig = new TemplateMakerModelConfig();
        ArrayList<TemplateMakerModelConfig.ModelInfoConfig> modelInfoConfigList = new ArrayList<>();

        TemplateMakerModelConfig.ModelInfoConfig modelInfoConfig1 = new TemplateMakerModelConfig.ModelInfoConfig();
        modelInfoConfig1.setFieldName("className");
        modelInfoConfig1.setType("String");
        modelInfoConfig1.setDescription("类名替换");
        modelInfoConfig1.setReplaceText("BaseResponse");
        modelInfoConfigList.add(modelInfoConfig1);

        templateMakerModelConfig.setModels(modelInfoConfigList);


        TemplateMakerFileConfig makerFileConfig = new TemplateMakerFileConfig();
        TemplateMakerFileConfig.FileInfoConfig fileInfoConfig = new TemplateMakerFileConfig.FileInfoConfig();
        fileInfoConfig.setPath(fileInputPath2);
        makerFileConfig.setFiles(Collections.singletonList(fileInfoConfig));

        long id = TemplateMaker.makeTemplate(meta, originProjectPath, makerFileConfig, templateMakerModelConfig,null, 1753283378534051840L);
        System.out.println("id:" + id);

        System.out.println("---------------------    测试完成Spring boot init 项目   makeTemplateBug2 -----------------------");
    }


    @Test
    public void makeTemplateWithJSON() {
        String configStr = ResourceUtil.readUtf8Str("springboot-init-meta.json");
        TemplateMakerConfig templateMakerConfig = JSONUtil.toBean(configStr, TemplateMakerConfig.class);
        long id = TemplateMaker.makeTemplate(templateMakerConfig);
        System.out.println(id);
    }

    /**
     * 制作 Spring Boot 模板
     */
    @Test
    public void makeSpringBootTemplate() {
        String rootPath = "examples/springboot-init/";
        String configStr = ResourceUtil.readUtf8Str(rootPath + "TemplateMaker.json");
        TemplateMakerConfig templateMakerConfig = JSONUtil.toBean(configStr, TemplateMakerConfig.class);
        long id = TemplateMaker.makeTemplate(templateMakerConfig);

        configStr = ResourceUtil.readUtf8Str(rootPath + "TemplateMaker1.json");
        templateMakerConfig = JSONUtil.toBean(configStr, TemplateMakerConfig.class);
        TemplateMaker.makeTemplate(templateMakerConfig);

        configStr = ResourceUtil.readUtf8Str(rootPath + "TemplateMaker2.json");
        templateMakerConfig = JSONUtil.toBean(configStr, TemplateMakerConfig.class);
        TemplateMaker.makeTemplate(templateMakerConfig);

        configStr = ResourceUtil.readUtf8Str(rootPath + "TemplateMaker3.json");
        templateMakerConfig = JSONUtil.toBean(configStr, TemplateMakerConfig.class);
        TemplateMaker.makeTemplate(templateMakerConfig);

        configStr = ResourceUtil.readUtf8Str(rootPath + "TemplateMaker4.json");
        templateMakerConfig = JSONUtil.toBean(configStr, TemplateMakerConfig.class);
        TemplateMaker.makeTemplate(templateMakerConfig);


        configStr = ResourceUtil.readUtf8Str(rootPath + "TemplateMaker5.json");
        templateMakerConfig = JSONUtil.toBean(configStr, TemplateMakerConfig.class);
        TemplateMaker.makeTemplate(templateMakerConfig);


        configStr = ResourceUtil.readUtf8Str(rootPath + "TemplateMaker6.json");
        templateMakerConfig = JSONUtil.toBean(configStr, TemplateMakerConfig.class);
        TemplateMaker.makeTemplate(templateMakerConfig);

        configStr = ResourceUtil.readUtf8Str(rootPath + "TemplateMaker7.json");
        templateMakerConfig = JSONUtil.toBean(configStr, TemplateMakerConfig.class);
        TemplateMaker.makeTemplate(templateMakerConfig);

        configStr = ResourceUtil.readUtf8Str(rootPath + "TemplateMaker8.json");
        templateMakerConfig = JSONUtil.toBean(configStr, TemplateMakerConfig.class);
        TemplateMaker.makeTemplate(templateMakerConfig);

        System.out.println(id);
    }


    @Test
    public void makeSpringBootTemplateTest() {
        String rootPath = "examples/springboot-init-plus/";
        String configStr = ResourceUtil.readUtf8Str(rootPath + "TemplateMaker.json");
        TemplateMakerConfig templateMakerConfig = JSONUtil.toBean(configStr, TemplateMakerConfig.class);
        templateMakerConfig.setId(2L);
        long id = TemplateMaker.makeTemplate(templateMakerConfig);

        configStr = ResourceUtil.readUtf8Str(rootPath + "TemplateMaker1.json");
        templateMakerConfig = JSONUtil.toBean(configStr, TemplateMakerConfig.class);
        templateMakerConfig.setId(2L);
        TemplateMaker.makeTemplate(templateMakerConfig);

        configStr = ResourceUtil.readUtf8Str(rootPath + "TemplateMaker2.json");
        templateMakerConfig = JSONUtil.toBean(configStr, TemplateMakerConfig.class);
        templateMakerConfig.setId(2L);
        TemplateMaker.makeTemplate(templateMakerConfig);

        configStr = ResourceUtil.readUtf8Str(rootPath + "TemplateMaker3.json");
        templateMakerConfig = JSONUtil.toBean(configStr, TemplateMakerConfig.class);
        templateMakerConfig.setId(2L);
        TemplateMaker.makeTemplate(templateMakerConfig);

        configStr = ResourceUtil.readUtf8Str(rootPath + "TemplateMaker4.json");
        templateMakerConfig = JSONUtil.toBean(configStr, TemplateMakerConfig.class);
        templateMakerConfig.setId(2L);
        TemplateMaker.makeTemplate(templateMakerConfig);


        configStr = ResourceUtil.readUtf8Str(rootPath + "TemplateMaker5.json");
        templateMakerConfig = JSONUtil.toBean(configStr, TemplateMakerConfig.class);
        templateMakerConfig.setId(2L);
        TemplateMaker.makeTemplate(templateMakerConfig);


        configStr = ResourceUtil.readUtf8Str(rootPath + "TemplateMaker6.json");
        templateMakerConfig = JSONUtil.toBean(configStr, TemplateMakerConfig.class);
        templateMakerConfig.setId(2L);
        TemplateMaker.makeTemplate(templateMakerConfig);

        configStr = ResourceUtil.readUtf8Str(rootPath + "TemplateMaker7.json");
        templateMakerConfig = JSONUtil.toBean(configStr, TemplateMakerConfig.class);
        templateMakerConfig.setId(2L);
        TemplateMaker.makeTemplate(templateMakerConfig);

        configStr = ResourceUtil.readUtf8Str(rootPath + "TemplateMaker8.json");
        templateMakerConfig = JSONUtil.toBean(configStr, TemplateMakerConfig.class);
        templateMakerConfig.setId(2L);
        TemplateMaker.makeTemplate(templateMakerConfig);



        System.out.println(id);
    }

}