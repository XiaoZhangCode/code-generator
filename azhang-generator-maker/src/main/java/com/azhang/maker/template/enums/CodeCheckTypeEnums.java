package com.azhang.maker.template.enums;

import lombok.Getter;

/**
 * 代码生成 校验类型枚举
 */
@Getter
public enum CodeCheckTypeEnums {

    EQUALS("相等", "equals"),
    REGEX_ALL("全部替换", "regexAll"),
    REGEX("正则", "regex");

    private final String text;

    private final String value;

    CodeCheckTypeEnums(String text, String value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 获取枚举
     *
     * @param value code过滤值
     * @return 枚举类
     */
    public static CodeCheckTypeEnums getEnumByValue(String value) {
        if (value == null) {
            return null;
        }
        for (CodeCheckTypeEnums item : CodeCheckTypeEnums.values()) {
            if (item.getValue().equals(value)) {
                return item;
            }
        }
        return null;
    }



}
