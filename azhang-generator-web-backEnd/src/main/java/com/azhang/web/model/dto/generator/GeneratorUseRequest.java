package com.azhang.web.model.dto.generator;

import lombok.Data;
import java.io.Serializable;
import java.util.Map;

/**
 * 在线使用代码生成器 请求参数
 *
 * @author codeZhang
 */
@Data
public class GeneratorUseRequest implements Serializable {

    /**
     * 生成器 id
     */
    private Long id;

    /**
     * 模型参数
     */
    private Map<String,Object> dataModel;

    private static final long serialVersionUID = 1L;
}