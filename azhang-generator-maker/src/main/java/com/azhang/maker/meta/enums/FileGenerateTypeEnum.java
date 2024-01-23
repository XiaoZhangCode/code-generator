package com.azhang.maker.meta.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FileGenerateTypeEnum {


    DYNAMIC("动态", "dynamic"),

    STATIC("静态", "static");

    private final String text;
    private final String value;


}
