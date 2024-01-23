package com.azhang.maker.meta.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FileTypeEnum {


    DIR("目录", "dir"),
    FILE("文件", "file");

    private final String text;
    private final String value;

}
