package com.azhang.web.model.vo;

import com.azhang.web.meta.Meta;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 代码生成器视图
 *
 * @author codeZhang
 */
@Data
public class GeneratorVO implements Serializable {

    /**
     * 创建人信息
     */
    private UserVO user;

    /**
     * 当前用户是否已点赞
     */
    private Boolean hasThumb;

    /**
     * 当前用户是否已收藏
     */
    private Boolean hasFavour;

    /**
     * id
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 基础包
     */
    private String basePackage;

    /**
     * git版本控制
     */
    private Boolean versionControl;

    /**
     * 强制交互式开关
     */
    private Boolean forcedInteractiveSwitch;

    /**
     * 版本
     */
    private String version;

    /**
     * 作者
     */
    private String author;

    /**
     * 标签列表（json 数组）
     */
    private List<String> tags;

    /**
     * 图片
     */
    private String picture;

    /**
     * 文件配置（json字符串）
     */
    private Meta.FileConfig fileConfig;

    /**
     * 模型配置（json字符串）
     */
    private Meta.ModelConfig modelConfig;

    /**
     * 代码生成器产物路径
     */
    private String distPath;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 点赞数
     */
    private Integer thumbNum;

    /**
     * 收藏数
     */
    private Integer favourNum;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}
