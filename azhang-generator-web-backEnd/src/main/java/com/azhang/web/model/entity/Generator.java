package com.azhang.web.model.entity;

import com.azhang.maker.meta.Meta;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 代码生成器
 *
 * @author azhang
 */
@TableName(value = "generator",autoResultMap = true)
@Data
@ToString
@EqualsAndHashCode
public class Generator implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
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
    private Boolean versionControl = false;

    /**
     * 强制交互式开关
     */
    private Boolean forcedInteractiveSwitch = true;

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
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> tags;

    /**
     * 图片
     */
    private String picture;

    /**
     * 文件配置（json字符串）
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Meta.FileConfigDTO fileConfig;

    /**
     * 模型配置（json字符串）
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Meta.ModelConfig modelConfig;

    /**
     * 代码生成器产物路径
     */
    private String distPath;

    /**
     * 状态
     */
    private Integer status = 0;

    /**
     * 点赞数
     */
    private Integer thumbNum = 0;

    /**
     * 收藏数
     */
    private Integer favourNum = 0;

    /**
     * 创建用户 id
     */
    private Long userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}