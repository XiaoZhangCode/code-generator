package com.azhang.maker.template;

import cn.hutool.core.util.StrUtil;
import com.azhang.maker.meta.Meta;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TemplateMakerUtils {


    public static List<Meta.FileConfigDTO.FileInfoDTO> removeGroupFileFromRoot(List<Meta.FileConfigDTO.FileInfoDTO> fileInfoList) {
        // 1. 获取到所有的分组
        List<Meta.FileConfigDTO.FileInfoDTO> groupInfoList = fileInfoList.stream()
                .filter(fileInfo -> StrUtil.isNotBlank(fileInfo.getGroupKey()))
                .collect(Collectors.toList());
        // 2. 获取到所有分组内的文件列表
        List<Meta.FileConfigDTO.FileInfoDTO> groupInnerFileInfoList = groupInfoList.stream()
                .flatMap(fileInfo -> fileInfo.getFiles().stream())
                .collect(Collectors.toList());
        // 3. 获取所有分组内的文件输入路径集合
        Set<String> groupInnerFileInputPathSet = groupInnerFileInfoList.stream()
                .map(Meta.FileConfigDTO.FileInfoDTO::getInputPath)
                .collect(Collectors.toSet());
        // 4. 利用上述集合，移除所有输入路径在集合中的外层文件
        return fileInfoList.stream()
                .filter(fileInfo -> !groupInnerFileInputPathSet.contains(fileInfo.getInputPath()))
                .collect(Collectors.toList());
    }


}
