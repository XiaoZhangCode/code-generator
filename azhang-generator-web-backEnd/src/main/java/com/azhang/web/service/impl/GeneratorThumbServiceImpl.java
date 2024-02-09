package com.azhang.web.service.impl;

import com.azhang.web.common.ErrorCode;
import com.azhang.web.exception.BusinessException;
import com.azhang.web.mapper.GeneratorThumbMapper;
import com.azhang.web.model.entity.Generator;
import com.azhang.web.model.entity.GeneratorThumb;
import com.azhang.web.model.entity.User;
import com.azhang.web.service.GeneratorService;
import com.azhang.web.service.GeneratorThumbService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 帖子点赞服务实现
 *
 * @author codeZhang
 */
@Service
public class GeneratorThumbServiceImpl extends ServiceImpl<GeneratorThumbMapper, GeneratorThumb>
        implements GeneratorThumbService {

    @Resource
    private GeneratorService generatorService;

    /**
     * 点赞
     *
     * @param generatorId
     * @param loginUser
     * @return
     */
    @Override
    public int doGeneratorThumb(long generatorId, User loginUser) {
        // 判断实体是否存在，根据类别获取实体
        Generator generator = generatorService.getById(generatorId);
        if (generator == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 是否已点赞
        long userId = loginUser.getId();
        // 每个用户串行点赞
        // 锁必须要包裹住事务方法
        GeneratorThumbService generatorThumbService = (GeneratorThumbService) AopContext.currentProxy();
        synchronized (String.valueOf(userId).intern()) {
            return generatorThumbService.doGeneratorThumbInner(userId, generatorId);
        }
    }

    /**
     * 封装了事务的方法
     *
     * @param userId
     * @param generatorId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int doGeneratorThumbInner(long userId, long generatorId) {
        GeneratorThumb generatorThumb = new GeneratorThumb();
        generatorThumb.setUserId(userId);
        generatorThumb.setGeneratorId(generatorId);
        QueryWrapper<GeneratorThumb> thumbQueryWrapper = new QueryWrapper<>(generatorThumb);
        GeneratorThumb oldGeneratorThumb = this.getOne(thumbQueryWrapper);
        boolean result;
        // 已点赞
        if (oldGeneratorThumb != null) {
            result = this.remove(thumbQueryWrapper);
            if (result) {
                // 点赞数 - 1
                result = generatorService.update()
                        .eq("id", generatorId)
                        .gt("thumbNum", 0)
                        .setSql("thumbNum = thumbNum - 1")
                        .update();
                return result ? -1 : 0;
            } else {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
        } else {
            // 未点赞
            result = this.save(generatorThumb);
            if (result) {
                // 点赞数 + 1
                result = generatorService.update()
                        .eq("id", generatorId)
                        .setSql("thumbNum = thumbNum + 1")
                        .update();
                return result ? 1 : 0;
            } else {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
        }
    }

}




