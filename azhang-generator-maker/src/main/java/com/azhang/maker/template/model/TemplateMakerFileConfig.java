package com.azhang.maker.template.model;


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
    private List<FileInfoConfig> fileInfoConfigList;

    /**
     * 文件分组配置
     */
    private FileGroupConfig fileGroupConfig;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FileInfoConfig{
        /**
         * 文件路径/目录路径
         */
        public String path;
        /**
         * 文件过滤配置
         */
        public List<FileFilterConfig> fileFilterConfigList;
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FileGroupConfig{
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
