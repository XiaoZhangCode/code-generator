package com.azhang.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhang
 * @date 2023/11/10 9:27
 * 动态模板配置
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MainTemplateConfig {

    /**
     * 是否生成循环
     */
    private boolean loop = false;

    /**
     * 作者
     */
    private String author = "zhang";

    /**
     * 输出信息
     */
    private String outputText = "sum = ";


}
