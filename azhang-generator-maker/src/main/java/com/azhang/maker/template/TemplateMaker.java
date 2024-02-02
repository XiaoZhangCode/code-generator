package com.azhang.maker.template;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.azhang.maker.meta.enums.FileGenerateTypeEnum;
import com.azhang.maker.meta.enums.FileTypeEnum;
import com.azhang.maker.template.enums.FileFilerRuleEnum;
import com.azhang.maker.template.enums.FileFilterRangeEnum;
import com.azhang.maker.meta.Meta;
import com.azhang.maker.template.model.FileFilterConfig;
import com.azhang.maker.template.model.TemplateMakerFileConfig;
import com.azhang.maker.template.model.TemplateMakerModelConfig;

import java.io.File;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 模板制作工具
 */
public class TemplateMaker {
    /**
     * 工作空间的目录
     */
    public static final String WORKSPACE_DIRECTORY = ".temp";
    /**
     * 模板文件的后缀
     */
    public static final String TEMPLATE_FILE_SUFFIX = ".ftl";
    /**
     * 元信息名称
     */
    public static final String META_INFORMATION_NAME = "meta.json";


    public static void main(String[] args) {

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

        makerFileConfig.setFileInfoConfigList(Arrays.asList(fileInfoConfig1, fileInfoConfig2));

        // 配置分组
        TemplateMakerFileConfig.FileGroupConfig fileGroupConfig = new TemplateMakerFileConfig.FileGroupConfig();
        fileGroupConfig.setGroupKey("mysql");
        fileGroupConfig.setGroupName("测试分组1");
        fileGroupConfig.setCondition("mysql");
        makerFileConfig.setFileGroupConfig(fileGroupConfig);

        long id = TemplateMaker.makeTemplate(meta, originProjectPath, makerFileConfig, templateMakerModelConfig, 1L);

        System.out.println("---------------------------------    测试完成Spring boot init 项目    ----------------------------------------------------");

    }


    /**
     * 生成模板
     *
     * @param newMeta                  元信息
     * @param originProjectPath        原始项目目录
     * @param templateMakerFileConfig  原始文件列表 + 过滤配置
     * @param templateMakerModelConfig 原始模型参数列表 + 替换配置
     * @param id                       id
     * @return id
     */
    public static long makeTemplate(Meta newMeta, String originProjectPath,
                                    TemplateMakerFileConfig templateMakerFileConfig,
                                    TemplateMakerModelConfig templateMakerModelConfig, Long id) {
        // 没有id 则生成
        if (id == null) {
            id = IdUtil.getSnowflakeNextId();
        }
        // 当前项目目录
        String projectPath = System.getProperty("user.dir");
        // 工作空间目录
        String templatePath = FileUtil.normalize(projectPath + File.separator + WORKSPACE_DIRECTORY + File.separator + id);
        if (!FileUtil.exist(templatePath)) {
            FileUtil.mkdir(templatePath);
            FileUtil.copy(originProjectPath, templatePath, true);
        }

        // 工作空间的项目的目录
        String sourceRootPath = FileUtil.normalize(templatePath + File.separator + FileUtil.getLastPathEle(Paths.get(originProjectPath)));

        // 生成模板文件
        List<TemplateMakerFileConfig.FileInfoConfig> infoConfigList = templateMakerFileConfig.getFileInfoConfigList();
        List<Meta.FileConfigDTO.FileInfoDTO> newFileInfoList = new ArrayList<>();

        // 模型处理
        List<TemplateMakerModelConfig.ModelInfoConfig> models = templateMakerModelConfig.getModels();
        List<Meta.ModelConfig.ModelInfo> inputModelInfoList = models.stream().map(modelInfoConfig -> {
            Meta.ModelConfig.ModelInfo modelInfo = new Meta.ModelConfig.ModelInfo();
            BeanUtil.copyProperties(modelInfoConfig, modelInfo);
            return modelInfo;
        }).collect(Collectors.toList());
        List<Meta.ModelConfig.ModelInfo> modelInfos = new ArrayList<>();
        TemplateMakerModelConfig.ModelGroupConfig modelGroupConfig = templateMakerModelConfig.getModelGroupConfig();
        if (modelGroupConfig != null) {
            String groupKey = modelGroupConfig.getGroupKey();
            String groupName = modelGroupConfig.getGroupName();
            String condition = modelGroupConfig.getCondition();
            Meta.ModelConfig.ModelInfo groupModelInfo = new Meta.ModelConfig.ModelInfo();
            groupModelInfo.setGroupName(groupName);
            groupModelInfo.setGroupKey(groupKey);
            groupModelInfo.setCondition(condition);
            groupModelInfo.setModels(inputModelInfoList);
            modelInfos.add(groupModelInfo);
        } else {
            // 不分组
            modelInfos.addAll(inputModelInfoList);
        }


        for (TemplateMakerFileConfig.FileInfoConfig fileInfoConfig : infoConfigList) {
            String fileInputPath = fileInfoConfig.getPath();
            String inputFileAbsolutePath = FileUtil.normalize(sourceRootPath + File.separator + fileInputPath);

            List<File> fileList = FileFilter.doFilter(inputFileAbsolutePath, fileInfoConfig.getFileFilterConfigList());
            // 制作过的.ftl模板文件 不需要再生成meta元信息配置
            fileList = fileList.stream().filter(file -> !file.getAbsolutePath().endsWith(TEMPLATE_FILE_SUFFIX)).collect(Collectors.toList());
//            // 不将Meta.json制作成模板
//            fileList = fileList.stream().filter(file -> !(file.getName().equals(META_INFORMATION_NAME))).collect(Collectors.toList());

            for (File file : fileList) {
                Meta.FileConfigDTO.FileInfoDTO fileInfo = makeFileTemplate(file, templateMakerModelConfig, sourceRootPath);
                newFileInfoList.add(fileInfo);
            }

        }

        TemplateMakerFileConfig.FileGroupConfig fileGroupConfig = templateMakerFileConfig.getFileGroupConfig();
        if (fileGroupConfig != null) {
            String groupKey = fileGroupConfig.getGroupKey();
            String groupName = fileGroupConfig.getGroupName();
            String condition = fileGroupConfig.getCondition();
            // 新增组配置
            Meta.FileConfigDTO.FileInfoDTO fileGroup = new Meta.FileConfigDTO.FileInfoDTO();
            fileGroup.setGroupKey(groupKey);
            fileGroup.setGroupName(groupName);
            fileGroup.setCondition(condition);
            fileGroup.setType(FileTypeEnum.GROUP.getValue());

            fileGroup.setFiles(newFileInfoList);
            newFileInfoList = new ArrayList<>();
            newFileInfoList.add(fileGroup);

        }


        // 3.使用输入信息来创建meta.json元信息文件
        String metaOutputPath = FileUtil.normalize(templatePath + File.separator + META_INFORMATION_NAME);

        Meta meta;
        if (FileUtil.exist(metaOutputPath)) {
            // 读取元信息文件
            String metaContent = FileUtil.readUtf8String(metaOutputPath);
            meta = JSONUtil.toBean(metaContent, Meta.class);
            meta.getModelConfig().getModels().addAll(modelInfos);
            meta.getFileConfig().getFiles().addAll(newFileInfoList);

            // 去重
            meta.getModelConfig().setModels(distinctModels(meta.getModelConfig().getModels()));
            meta.getFileConfig().setFiles(distinctFiles(meta.getFileConfig().getFiles()));
        } else {
            // 基本信息
            meta = newMeta;
            // fileConfig 配置
            Meta.FileConfigDTO fileConfigDTO = new Meta.FileConfigDTO();
            fileConfigDTO.setSourceRootPath(sourceRootPath);
            List<Meta.FileConfigDTO.FileInfoDTO> fileInfoList = new ArrayList<>(newFileInfoList);
            fileConfigDTO.setFiles(fileInfoList);
            meta.setFileConfig(fileConfigDTO);

            // modelConfig 配置
            Meta.ModelConfig modelConfig = new Meta.ModelConfig();
            List<Meta.ModelConfig.ModelInfo> modelInfoList = new ArrayList<>(modelInfos);
            modelConfig.setModels(modelInfoList);
            meta.setModelConfig(modelConfig);
        }
        // 输出meta.json元信息文件
        FileUtil.writeUtf8String(JSONUtil.toJsonPrettyStr(meta), metaOutputPath);
        return id;
    }

    /**
     * 单次制作模板
     *
     * @param inputFile                需要制作模板的文件对象
     * @param templateMakerModelConfig 模型参数
     * @param sourceRootPath           项目源目录
     * @return 文件信息
     */
    private static Meta.FileConfigDTO.FileInfoDTO makeFileTemplate(File inputFile, TemplateMakerModelConfig templateMakerModelConfig, String sourceRootPath) {
        // 输入文件的绝对路径
        String fileInputAbsolutePath = FileUtil.normalize(inputFile.getAbsolutePath());
        // 输入文件变成模板文件后保存的路径
        String fileOutputAbsolutePath = FileUtil.normalize(fileInputAbsolutePath + TEMPLATE_FILE_SUFFIX);

        //输入文件的路径

        String fileInputPath = FileUtil.normalize(fileInputAbsolutePath.replace(sourceRootPath + "/", ""));
        // 输出文件路径
        String fileOutputPath = FileUtil.normalize(fileInputPath + TEMPLATE_FILE_SUFFIX);


        // 2.基于字符串替换算法，使用模型参数的字段名称来替换原始文件的指定内容，并使用替换后的内容来创建FTL动态模板文件
        String fileContent;
        boolean hasTemplate = FileUtil.exist(fileOutputAbsolutePath);
        if (hasTemplate) {
            fileContent = FileUtil.readUtf8String(fileOutputAbsolutePath);
        } else {
            fileContent = FileUtil.readUtf8String(fileInputAbsolutePath);
        }
        TemplateMakerModelConfig.ModelGroupConfig modelGroupConfig = templateMakerModelConfig.getModelGroupConfig();
        String newFileContext = fileContent;
        for (TemplateMakerModelConfig.ModelInfoConfig modelInfoConfig : templateMakerModelConfig.getModels()) {
            String fieldName = modelInfoConfig.getFieldName();
            String replacement;
            if (modelGroupConfig == null) {
                replacement = String.format("${%s}", fieldName);
            } else {
                replacement = String.format("${%s.%s}", modelGroupConfig.getGroupKey(), fieldName);
            }
            newFileContext = StrUtil.replace(newFileContext, modelInfoConfig.getReplaceText(), replacement);
        }


        Meta.FileConfigDTO.FileInfoDTO fileInfo = new Meta.FileConfigDTO.FileInfoDTO();
        fileInfo.setInputPath(fileOutputPath);
        fileInfo.setOutputPath(fileInputPath);
        fileInfo.setType(FileTypeEnum.FILE.getValue());
        fileInfo.setGenerateType(FileGenerateTypeEnum.DYNAMIC.getValue());
        boolean contentEquals = newFileContext.equals(fileContent);
        // 存在模板
        if(hasTemplate){
            // 存在模板 而且又新增了新的“坑”
            if(!contentEquals){
                FileUtil.writeUtf8String(newFileContext, fileOutputAbsolutePath);
            }
        }else {
            // 不存在模板 而且没有更改过文件内容
            if(contentEquals){
                fileInfo.setGenerateType(FileGenerateTypeEnum.STATIC.getValue());
                fileInfo.setInputPath(fileInputPath);
            }else {
                FileUtil.writeUtf8String(newFileContext, fileOutputAbsolutePath);
            }
        }

        return fileInfo;
    }

    /**
     * 文件去重
     *
     * @param files 文件列表
     * @return 去重后的文件列表
     */
    public static List<Meta.FileConfigDTO.FileInfoDTO> distinctFiles(List<Meta.FileConfigDTO.FileInfoDTO> files) {
        // {"groupKey":"1",[{groupKey":"1","files":[{"inputPath":"文件路径"},{"inputPath":"xxx"}]},{groupKey":"1","files":[{"inputPath":"文件路径"},{"inputPath":"xxx"}]}]}
        Map<String, List<Meta.FileConfigDTO.FileInfoDTO>> groupKeyFileInfoList = files.stream()
                .filter(fileInfo -> StrUtil.isNotEmpty(fileInfo.getGroupKey()))
                .collect(Collectors.groupingBy(Meta.FileConfigDTO.FileInfoDTO::getGroupKey));

        Map<String, Meta.FileConfigDTO.FileInfoDTO> groupKeyMergeFileInfo = new HashMap<>();
        for (Map.Entry<String, List<Meta.FileConfigDTO.FileInfoDTO>> tempGroupFileInfo : groupKeyFileInfoList.entrySet()) {
            List<Meta.FileConfigDTO.FileInfoDTO> groupFileInfoList = tempGroupFileInfo.getValue();
            // 将每个组里面的很多的files合并成一个集合 如果有重复的文件 保留后面出现的那个
            List<Meta.FileConfigDTO.FileInfoDTO> newFileInfos = new ArrayList<>(groupFileInfoList.stream().flatMap(fileInfo -> fileInfo.getFiles().stream())
                    .collect(Collectors.toMap(Meta.FileConfigDTO.FileInfoDTO::getInputPath, o -> o, (o, n) -> n)).values());

            // 获取最后一个组 因为可能groupKey 相同但是我修改了groupName 所以我们使用最后一次更新的groupName
            Meta.FileConfigDTO.FileInfoDTO newFileInfo = CollUtil.getLast(groupFileInfoList);
            newFileInfo.setFiles(newFileInfos);

            groupKeyMergeFileInfo.put(tempGroupFileInfo.getKey(), newFileInfo);
        }

        // 将合并好的FileInfo 放入需要返回的结果列表中
        List<Meta.FileConfigDTO.FileInfoDTO> result = new ArrayList<>(groupKeyMergeFileInfo.values());

        // 这个是未分组的文件列表集合 去重
        List<Meta.FileConfigDTO.FileInfoDTO> noMergeFileInfos = files.stream()
                .filter(fileInfo -> StrUtil.isEmpty(fileInfo.getGroupKey()))
                .collect(Collectors.toList());
        List<Meta.FileConfigDTO.FileInfoDTO> fileInfos = new ArrayList<>(noMergeFileInfos.stream()
                .collect(
                        Collectors.toMap(Meta.FileConfigDTO.FileInfoDTO::getInputPath, o -> o, (o, n) -> n)
                ).values());

        // 将未分组的文件添加到集合中
        result.addAll(fileInfos);
        return result;
    }

    /**
     * 模型去重
     *
     * @param models 模型列表
     * @return 去重后的模型列表
     */
    public static List<Meta.ModelConfig.ModelInfo> distinctModels(List<Meta.ModelConfig.ModelInfo> models) {
        // {"groupKey":"1",[{groupKey":"1","models":[{"inputPath":"模型路径"},{"inputPath":"xxx"}]},{groupKey":"1","models":[{"inputPath":"模型路径"},{"inputPath":"xxx"}]}]}
        Map<String, List<Meta.ModelConfig.ModelInfo>> groupKeyModelInfoList = models.stream()
                .filter(modelInfo -> StrUtil.isNotEmpty(modelInfo.getGroupKey()))
                .collect(Collectors.groupingBy(Meta.ModelConfig.ModelInfo::getGroupKey));

        Map<String, Meta.ModelConfig.ModelInfo> groupKeyMergeModelInfo = new HashMap<>();
        for (Map.Entry<String, List<Meta.ModelConfig.ModelInfo>> tempGroupModelInfo : groupKeyModelInfoList.entrySet()) {
            List<Meta.ModelConfig.ModelInfo> groupModelInfoList = tempGroupModelInfo.getValue();
            // 将每个组里面的很多的models合并成一个集合 如果有重复的模型 保留后面出现的那个
            List<Meta.ModelConfig.ModelInfo> newModelInfos = new ArrayList<>(groupModelInfoList.stream().flatMap(modelInfo -> modelInfo.getModels().stream())
                    .collect(Collectors.toMap(Meta.ModelConfig.ModelInfo::getFieldName, o -> o, (o, n) -> n)).values());

            // 获取最后一个组 因为可能groupKey 相同但是我修改了groupName 所以我们使用最后一次更新的groupName
            Meta.ModelConfig.ModelInfo newModelInfo = CollUtil.getLast(groupModelInfoList);
            newModelInfo.setModels(newModelInfos);

            groupKeyMergeModelInfo.put(tempGroupModelInfo.getKey(), newModelInfo);
        }

        // 将合并好的ModelInfo 放入需要返回的结果列表中
        List<Meta.ModelConfig.ModelInfo> result = new ArrayList<>(groupKeyMergeModelInfo.values());

        // 这个是未分组的模型列表集合 去重
        List<Meta.ModelConfig.ModelInfo> noMergeModelInfos = models.stream()
                .filter(modelInfo -> StrUtil.isEmpty(modelInfo.getGroupKey()))
                .collect(Collectors.toList());
        List<Meta.ModelConfig.ModelInfo> modelInfos = new ArrayList<>(noMergeModelInfos.stream()
                .collect(
                        Collectors.toMap(Meta.ModelConfig.ModelInfo::getFieldName, o -> o, (o, n) -> n)
                ).values());

        // 将未分组的模型添加到集合中
        result.addAll(modelInfos);
        return result;
    }
}
