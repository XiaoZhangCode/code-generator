package com.azhang.maker.template.model;


import com.azhang.maker.template.enums.CodeCheckTypeEnums;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 封装所有相关的文件和配置
 */
@Data
public class TemplateMakerFileConfig {

    /**
     * 文件过滤信息配置列表
     */
    private List<FileInfoConfig> files;

    /**
     * 文件分组配置
     */
    private FileGroupConfig fileGroupConfig;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FileInfoConfig {
        /**
         * 文件路径/目录路径
         */
        public String path;
        /**
         * 文件过滤配置
         */
        public List<FileFilterConfig> fileFilterConfigList;
        /**
         * 文件生成条件
         */
        private String condition;

        /**
         * 需要被控制生成的代码列表
         */
        private List<ControlCodeInfoConfig> controlCodeConfigList;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ControlCodeInfoConfig {
        /**
         * 为true 则是 当存在时生成 为false时 需要增加一个 !
         */
        public boolean conditionExist;

        /**
         * 代码生成条件
         */
        private String condition;

        /**
         * 需要被控制的代码
         */
        public String controlCode;

        /**
         * 代码校验类型 默认是包含
         */
        public String codeCheckType = CodeCheckTypeEnums.EQUALS.getValue();

    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FileGroupConfig {
        /**
         * 组的唯一标识
         */
        public String groupKey;
        /**
         * 组名
         */
        public String groupName;
        /**
         * 控制该组的生成条件
         */
        public String condition;
    }


}
