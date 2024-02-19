package com.azhang.web.model.dto.generator;


import com.azhang.maker.meta.Meta;
import lombok.Data;

import java.io.Serializable;

/**
 * 制作代码生成器请求
 *
 */
@Data
public class GeneratorMakeRequest implements Serializable {

    /**
     * 压缩文件路径
     */
    private String zipFilePath;

    /**
     * 元信息
     */
    private Meta meta;

    private static final long serialVersionUID = 1L;
}