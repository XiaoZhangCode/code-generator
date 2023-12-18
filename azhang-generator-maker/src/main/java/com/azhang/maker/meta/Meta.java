package com.azhang.maker.meta;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.json.JSONUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private FileConfigDTO fileConfig;
    private ModelConfigDTO modelConfig;

    @NoArgsConstructor
    @Data
    public static class FileConfigDTO {
        private String inputRootPath;
        private String outputRootPath;
        private String type;
        private List<FileInfoDTO> files;

        @NoArgsConstructor
        @Data
        public static class FileInfoDTO {
            private String inputPath;
            private String outputPath;
            private String type;
            private String generateType;
        }
    }

    @NoArgsConstructor
    @Data
    public static class ModelConfigDTO {
        private List<ModelInfoDTO> models;

        @NoArgsConstructor
        @Data
        public static class ModelInfoDTO {
            private String fieldName;
            private String type;
            private String description;
            private Object defaultValue;
            private String abbr;
        }
    }

    public static void main(String[] args) {
        String metaJson = ResourceUtil.readUtf8Str("meta.json");
        Meta bean = JSONUtil.toBean(metaJson, Meta.class);
        System.out.println(bean);
    }
}
