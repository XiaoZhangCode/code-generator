package com.azhang.maker.template;

import cn.hutool.core.util.StrUtil;
import com.azhang.maker.meta.Meta;

import java.util.*;
import java.util.stream.Collectors;

public class TemplateMakerUtils {

    /**
     * 将未分组的文件和分组文件的同名文件放到组内
     *
     * @param fileInfoList 文件列表
     * @return 文件列表
     */
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


    // 多个生成条件下 将文件移出组内 放在外层
    public static List<Meta.FileConfigDTO.FileInfoDTO> removeFileFromGroup(List<Meta.FileConfigDTO.FileInfoDTO> fileInfoList) {
        // 1. 获取到所有的分组
        List<Meta.FileConfigDTO.FileInfoDTO> groupInfoList = fileInfoList.stream()
                .filter(fileInfo -> StrUtil.isNotBlank(fileInfo.getGroupKey()))
                .collect(Collectors.toList());

        // 2. 获取到未分组的文件列表
        List<Meta.FileConfigDTO.FileInfoDTO> noGroupInfoList = fileInfoList.stream()
                .filter(fileInfo -> StrUtil.isBlank(fileInfo.getGroupKey()))
                .collect(Collectors.toList());

        // 3. 未分组的文件input列表
        Set<String> noGroupInputPathSet = fileInfoList.stream()
                .filter(fileInfo -> StrUtil.isBlank(fileInfo.getGroupKey()))
                .map(Meta.FileConfigDTO.FileInfoDTO::getInputPath)
                .collect(Collectors.toSet());

        // 4. 未分组的文件列表 map
        Map<String, Meta.FileConfigDTO.FileInfoDTO> noGroupInputPathMap = fileInfoList.stream()
                .filter(fileInfo -> StrUtil.isBlank(fileInfo.getGroupKey()))
                .collect(Collectors.toMap(Meta.FileConfigDTO.FileInfoDTO::getInputPath, o -> o));


        // 5. 每个组来分别和未分组的文件来比对
        groupInfoList.forEach(groupInfo -> {
            List<Meta.FileConfigDTO.FileInfoDTO> files = groupInfo.getFiles();
            files = files.stream().filter(fileInfo -> {
                if (noGroupInputPathSet.contains(fileInfo.getInputPath())) {
                    // 如果未分组的文件和分组内的文件重名后 我们要根据condition来判断是否移除
                    return conditionCheck(fileInfo, noGroupInputPathMap.get(fileInfo.getInputPath()));
                } else {
                    return true;
                }
            }).collect(Collectors.toList());
            groupInfo.setFiles(files);
        });
        noGroupInfoList.addAll(groupInfoList);
        return noGroupInfoList;
    }

    public static boolean conditionCheck(Meta.FileConfigDTO.FileInfoDTO fileInfo1, Meta.FileConfigDTO.FileInfoDTO fileInfo2) {
        String groupCondition = fileInfo1.getCondition();
        String noGroupCondition = fileInfo2.getCondition();
        // 如果未分组文件没有条件 则直接返回true
        if (StrUtil.isBlank(noGroupCondition)) {
            return true;
        }
        // 如果两个condition都是null 让后续的输出规则判断去吧
        if (StrUtil.isBlank(groupCondition) && StrUtil.isBlank(noGroupCondition)) {
            return true;
        }
        if (StrUtil.isBlank(groupCondition) && StrUtil.isNotBlank(noGroupCondition)) {
            return false;
        }
        if (StrUtil.isNotBlank(groupCondition) && StrUtil.isNotBlank(noGroupCondition)) {
            // 判断里面有个数 condition
            return getConditionNums(groupCondition) >= getConditionNums(noGroupCondition);
        }
        return true;
    }

    private static int getConditionNums(String groupCondition) {
        HashSet<String> set = new HashSet<>();
        String[] split = groupCondition.split("&&");
        for (String string : split) {
            String[] split1 = string.split("||");
            set.addAll(Arrays.asList(split1));
        }
        return set.size();
    }

}
