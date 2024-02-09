package com.azhang.web.service.impl;

import com.azhang.web.common.ErrorCode;
import com.azhang.web.exception.BusinessException;
import com.azhang.web.mapper.GeneratorFavourMapper;
import com.azhang.web.model.entity.Generator;
import com.azhang.web.model.entity.GeneratorFavour;
import com.azhang.web.model.entity.User;
import com.azhang.web.service.GeneratorFavourService;
import com.azhang.web.service.GeneratorService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 生成器收藏服务实现
 *
 * @author codeZhang
 */
@Service
public class GeneratorFavourServiceImpl extends ServiceImpl<GeneratorFavourMapper, GeneratorFavour>
        implements GeneratorFavourService {

    @Resource
    private GeneratorService generatorService;

    /**
     * 生成器收藏
     *
     * @param generatorId
     * @param loginUser
     * @return
     */
    @Override
    public int doGeneratorFavour(long generatorId, User loginUser) {
        // 判断是否存在
        Generator generator = generatorService.getById(generatorId);
        if (generator == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 是否已生成器收藏
        long userId = loginUser.getId();
        // 每个用户串行生成器收藏
        // 锁必须要包裹住事务方法
        GeneratorFavourService generatorFavourService = (GeneratorFavourService) AopContext.currentProxy();
        synchronized (String.valueOf(userId).intern()) {
            return generatorFavourService.doGeneratorFavourInner(userId, generatorId);
        }
    }

    @Override
    public Page<Generator> listFavourGeneratorByPage(IPage<Generator> page, Wrapper<Generator> queryWrapper, long favourUserId) {
        if (favourUserId <= 0) {
            return new Page<>();
        }
        return baseMapper.listFavourGeneratorByPage(page, queryWrapper, favourUserId);
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
    public int doGeneratorFavourInner(long userId, long generatorId) {
        GeneratorFavour generatorFavour = new GeneratorFavour();
        generatorFavour.setUserId(userId);
        generatorFavour.setGeneratorId(generatorId);
        QueryWrapper<GeneratorFavour> generatorFavourQueryWrapper = new QueryWrapper<>(generatorFavour);
        GeneratorFavour oldGeneratorFavour = this.getOne(generatorFavourQueryWrapper);
        boolean result;
        // 已收藏
        if (oldGeneratorFavour != null) {
            result = this.remove(generatorFavourQueryWrapper);
            if (result) {
                // 生成器收藏数 - 1
                result = generatorService.update()
                        .eq("id", generatorId)
                        .gt("favourNum", 0)
                        .setSql("favourNum = favourNum - 1")
                        .update();
                return result ? -1 : 0;
            } else {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
        } else {
            // 未生成器收藏
            result = this.save(generatorFavour);
            if (result) {
                // 生成器收藏数 + 1
                result = generatorService.update()
                        .eq("id", generatorId)
                        .setSql("favourNum = favourNum + 1")
                        .update();
                return result ? 1 : 0;
            } else {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
        }
    }

}




