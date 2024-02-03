package com.azhang.maker.template.model;

import lombok.Data;

import java.util.List;

/**
 * 封装模型参数
 */
@Data
public class TemplateMakerModelConfig {

    private List<ModelInfoConfig> models;

    private ModelGroupConfig modelGroupConfig;

    /**
     * 用来替换文件路径 里面的fieldName models中的一个 只要分步制作用过即可
     */
    private ModelInfoConfig fileDirPathConfig;

    @Data
    public static class ModelInfoConfig {
        private String fieldName;
        private String type;
        private String description;
        private Object defaultValue;
        private String abbr;
        /**
         * 用于替换哪些文本
         */
        private String replaceText;
    }

    @Data
    public static class ModelGroupConfig {
        private String condition;
        private String groupKey;
        private String groupName;

        private String type;

        private String description;
    }

}
