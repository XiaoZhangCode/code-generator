package com.azhang.web.service;

import com.azhang.web.model.entity.GeneratorThumb;
import com.azhang.web.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 生成器点赞服务
 *
 * @author codeZhang
 */
public interface GeneratorThumbService extends IService<GeneratorThumb> {

    /**
     * 点赞
     *
     * @param generatorId
     * @param loginUser
     * @return
     */
    int doGeneratorThumb(long generatorId, User loginUser);

    /**
     * 生成器点赞（内部服务）
     *
     * @param userId
     * @param generatorId
     * @return
     */
    int doGeneratorThumbInner(long userId, long generatorId);
}
