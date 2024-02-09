package com.azhang.web.service;

import com.azhang.web.model.entity.Generator;
import com.azhang.web.model.entity.GeneratorFavour;
import com.azhang.web.model.entity.User;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 帖子收藏服务
 *
 * @author codeZhang
 */
public interface GeneratorFavourService extends IService<GeneratorFavour> {

    /**
     * 帖子收藏
     *
     * @param postId
     * @param loginUser
     * @return
     */
    int doGeneratorFavour(long postId, User loginUser);

    /**
     * 分页获取用户收藏的帖子列表
     *
     * @param page
     * @param queryWrapper
     * @param favourUserId
     * @return
     */
    Page<Generator> listFavourGeneratorByPage(IPage<Generator> page, Wrapper<Generator> queryWrapper,
                                              long favourUserId);

    /**
     * 帖子收藏（内部服务）
     *
     * @param userId
     * @param postId
     * @return
     */
    int doGeneratorFavourInner(long userId, long postId);
}
