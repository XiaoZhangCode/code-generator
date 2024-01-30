package com.azhang.maker.meta;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.*;
import com.azhang.maker.meta.enums.FileGenerateTypeEnum;
import com.azhang.maker.meta.enums.FileTypeEnum;
import com.azhang.maker.meta.enums.ModelTypeEnum;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Meta 校验类
 */
public class MetaValidator {

    public static void validate(Meta meta) throws MetaException {
        // 校验基础信息
        validBasicInfo(meta);
        // 校验fileConfig
        validFileConfig(meta);
        // 校验modelFileInfo
        validFileModelInfo(meta);
    }

    private static void validFileModelInfo(Meta meta) {
        // fileModelInfo的默认值
        Meta.ModelConfigDTO modelConfig = meta.getModelConfig();
        if (modelConfig == null) {
            return;
        }
        Meta.ModelConfigDTO.ModelInfoDTO models = modelConfig.getModels();
        if (models == null) {
            return;
        }
        Class<? extends Meta.ModelConfigDTO.ModelInfoDTO> modelsClass = models.getClass();
        Field[] fields = modelsClass.getDeclaredFields();
        if(ArrayUtil.isEmpty(fields)){
            throw new MetaException("modelInfo 不能为空!");
        }
        if(ArrayUtil.isNotEmpty(fields)){
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    Meta.ModelConfigDTO.ModelInfoDTO.DataModelDTO modelInfo = (Meta.ModelConfigDTO.ModelInfoDTO.DataModelDTO) field.get(models);
                    modelInfo.setDescription(StrUtil.blankToDefault(modelInfo.getDescription(),"动态模板配置"));
                    List<Meta.ModelConfigDTO.ModelInfoDTO.DataModelDTO.FiledInfoDTO> filedInfo = modelInfo.getFiledInfo();
                    if(ArrayUtil.isEmpty(filedInfo)){
                        throw new MetaException("filedInfo 不能为空!");
                    }
                    for (Meta.ModelConfigDTO.ModelInfoDTO.DataModelDTO.FiledInfoDTO infoDTO : filedInfo) {
                        if(StrUtil.isNotEmpty(infoDTO.getGroupKey())){
                            List<Meta.ModelConfigDTO.ModelInfoDTO.DataModelDTO.FiledInfoDTO> dtoModels = infoDTO.getModels();
                            String argsStr = dtoModels.stream().map(subInfoDTO -> String.format("\"--%s\"", subInfoDTO.getFieldName())).collect(Collectors.joining(", "));
                            infoDTO.setAllArgsStr(argsStr);
                            continue;
                        }
                        String fieldName = infoDTO.getFieldName();
                        if(StrUtil.isBlank(fieldName)){
                            throw new MetaException("fieldName 不能为空!");
                        }
                        infoDTO.setType(StrUtil.blankToDefault(infoDTO.getType(), ModelTypeEnum.STRING.getValue()));
                        infoDTO.setDescription(StrUtil.blankToDefault(infoDTO.getDescription(),"描述"));

                        Object defaultValue = infoDTO.getDefaultValue();
                        if(ObjectUtil.isEmpty(defaultValue)){
                            infoDTO.setDefaultValue("默认值");;
                        }
                    }
                } catch (IllegalAccessException e) {
                    throw new MetaException("获取ModelInfo 异常 请检查！");
                }
            }
        }
    }

    private static void validFileConfig(Meta meta) {
        // fileConfig的默认值
        Meta.FileConfigDTO fileInfo = meta.getFileConfig();
        if (fileInfo == null) {
            return;
        }
        // sourceRootPath 必填项
        String sourceRootPath = fileInfo.getSourceRootPath();
        if(StrUtil.isBlank(sourceRootPath)){
            throw new MetaException("sourceRootPath 不能为空!");
        }
        // .source/ + sourceRootPath
        fileInfo.setInputRootPath(StrUtil.emptyToDefault(fileInfo.getInputRootPath(), ".source/" + sourceRootPath));
        // 默认为 generated
        fileInfo.setOutputRootPath(StrUtil.blankToDefault(fileInfo.getOutputRootPath(),"generated"));
        // 默认为dir
        fileInfo.setType(StrUtil.blankToDefault(fileInfo.getType(),"dir"));

        // 校验FileInfo
        List<Meta.FileConfigDTO.FileInfoDTO> fileInfoList = fileInfo.getFiles();
        if (fileInfoList == null) {
            return;
        }
        for (Meta.FileConfigDTO.FileInfoDTO fileInfoDTO : fileInfoList) {
            String type1 = fileInfoDTO.getType();
            if(FileTypeEnum.GROUP.getValue().equals(type1)){
                continue;
            }
            // 必填项
            String inputPath = fileInfoDTO.getInputPath();
            if(StrUtil.isBlank(inputPath)){
                throw new MetaException("inputPath 不能为空!");
            }
            // outputPath 默认和inputPath相同
            fileInfoDTO.setOutputPath(StrUtil.blankToDefault(fileInfoDTO.getOutputPath(),inputPath));

            // 默认为 dir
             String type = fileInfoDTO.getType();
            if(StrUtil.isBlank(type)){
               // 判断是否有后缀 有后缀就是文件 否则就是目录
                if(StrUtil.isBlank(FileUtil.getSuffix(inputPath))){
                    type = FileTypeEnum.DIR.getValue();
                }else {
                    type =  FileTypeEnum.FILE.getValue();
                }
                fileInfoDTO.setType(type);
            }
            String generateType = fileInfoDTO.getGenerateType();
            // 如果有 .ftl 后缀 则是模板文件 Dynamic 否则就是 Static
            if(StrUtil.isBlank(generateType)){
                if(inputPath.endsWith(".ftl")){
                    type = FileGenerateTypeEnum.DYNAMIC.getValue();
                }else {
                    type =  FileGenerateTypeEnum.STATIC.getValue();
                }
                fileInfoDTO.setType(type);
            }
        }
    }

    private static void validBasicInfo(Meta meta) {
        // 基础信息校验和默认值
        meta.setName(StrUtil.blankToDefault(meta.getName(),"acm-template-pro-generator"));
        meta.setDescription(StrUtil.blankToDefault(meta.getDescription(),"ACM 模板生成器"));
        meta.setBasePackage(StrUtil.blankToDefault( meta.getBasePackage(),"com.azhang"));
        meta.setVersion(StrUtil.blankToDefault( meta.getVersion(),"1.0"));
        meta.setAuthor(StrUtil.blankToDefault(meta.getAuthor(),"azhang"));
        meta.setCreateTime(StrUtil.blankToDefault(meta.getCreateTime(),DateUtil.now()));

        Boolean forcedInteractiveSwitch = meta.getForcedInteractiveSwitch();
        meta.setForcedInteractiveSwitch(BooleanUtil.isTrue(forcedInteractiveSwitch));
        Boolean versionControl = meta.getVersionControl();
        meta.setVersionControl(BooleanUtil.isTrue(versionControl));
    }


}
