package com.azhang.maker.template;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import com.azhang.maker.template.enums.FileFilerRuleEnum;
import com.azhang.maker.template.enums.FileFilterRangeEnum;
import com.azhang.maker.template.model.FileFilterConfig;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 根据文件配置 过滤文件
 */

public class FileFilter {

    /**
     * 文件过滤
     *
     * @param filePath             文件路径/文件夹路径
     * @param fileFilterConfigList 文件过滤配置
     * @return 过滤出来的文件
     */
    public static List<File> doFilter(String filePath, List<FileFilterConfig> fileFilterConfigList) {
        // 根据路径获取所有文件
        List<File> fileList = FileUtil.loopFiles(filePath);
        return fileList.stream().filter(file -> doSingleFilter(fileFilterConfigList, file)).collect(Collectors.toList());
    }


    /**
     * 单个文件过滤
     *
     * @param fileFilterConfigList 文件过滤配置
     * @param file                 文件信息
     * @return 是否保留文件 true 保留 false 不保留
     */
    public static boolean doSingleFilter(List<FileFilterConfig> fileFilterConfigList, File file) {
        String fileName = file.getName();
        String fileContent = FileUtil.readUtf8String(file);

        boolean result = true;
        if (CollUtil.isEmpty(fileFilterConfigList)) {
            return true;
        }
        for (FileFilterConfig fileFilterConfig : fileFilterConfigList) {
            String range = fileFilterConfig.getRange();
            String rule = fileFilterConfig.getRule();
            String value = fileFilterConfig.getValue();


            FileFilterRangeEnum filterRangeEnum = FileFilterRangeEnum.getEnumByValue(range);
            if (filterRangeEnum == null) {
                continue;
            }
            String content = fileName;
            switch (filterRangeEnum) {
                case FILE_NAME:
                    content = fileName;
                    break;
                case FILE_CONTENT:
                    content = fileContent;
                    break;
                default:
            }
            FileFilerRuleEnum filerRuleEnum = FileFilerRuleEnum.getEnumByValue(rule);
            if (filerRuleEnum == null) {
                continue;
            }
            switch (filerRuleEnum) {
                case CONTAINS:
                    result = content.contains(value);
                    break;
                case STARTS_WITH:
                    result = content.startsWith(value);
                    break;
                case ENDS_WITH:
                    result = content.endsWith(value);
                    break;
                case REGEX:
                    result = content.matches(value);
                    break;
                case EQUALS:
                    result = content.equals(value);
                    break;
                default:
            }

            if (!result) {
                return false;
            }

        }


        return result;
    }


}
