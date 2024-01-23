package com.azhang.maker.meta.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ModelTypeEnum {
    STRING("字符串", "String"),
    BOOLEAN("布尔", "boolean");
    private final String text;
    private final String value;
}
