package com.azhang.web.meta;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.json.JSONUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


/**
 * @author zhang
 * @date 2023/12/15 17:45
 */
@NoArgsConstructor
@Data
public class Meta {


    private String name;
    private String description;
    private String basePackage;
    private String version;
    private String author;
    private String createTime;
    private FileConfig fileConfig;
    private ModelConfig modelConfig;
    /**
     * 用户强制输入开关
     */
    private Boolean forcedInteractiveSwitch;

    /**
     * 版本控制
     */
    private Boolean versionControl;




    @NoArgsConstructor
    @Data
    public static class FileConfig {
        private String inputRootPath;
        private String outputRootPath;
        private String sourceRootPath;
        private String type;
        private List<FileInfoDTO> files;

        @NoArgsConstructor
        @Data
        public static class FileInfoDTO {
            private String inputPath;
            private String outputPath;
            private String type;
            private String generateType;
            private String condition;
            private String groupKey;
            private String groupName;
            private List<FileInfoDTO> files;
        }
    }

    @NoArgsConstructor
    @Data
    public static class ModelConfig implements Serializable {
        private List<ModelInfo> models;

        @NoArgsConstructor
        @Data
        public static class ModelInfo implements Serializable {
            private String fieldName;
            private String type;
            private String description;
            private Object defaultValue;
            private String abbr;
            private String groupKey;
            private String groupName;
            private List<ModelInfo> models;
            private String condition;

            // 中间参数
            // 该分组下所有参数拼接字符串
            private String allArgsStr;
        }
    }


    public static void main(String[] args) {
        String metaJson = ResourceUtil.readUtf8Str("meta.json");
        Meta bean = JSONUtil.toBean(metaJson, Meta.class);
        System.out.println(bean);
    }
}
