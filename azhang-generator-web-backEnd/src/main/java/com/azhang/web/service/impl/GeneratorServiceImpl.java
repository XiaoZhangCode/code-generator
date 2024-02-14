package com.azhang.web.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.azhang.web.common.ErrorCode;
import com.azhang.web.constant.CommonConstant;
import com.azhang.web.exception.BusinessException;
import com.azhang.web.exception.ThrowUtils;
import com.azhang.web.mapper.GeneratorFavourMapper;
import com.azhang.web.mapper.GeneratorMapper;
import com.azhang.web.mapper.GeneratorThumbMapper;
import com.azhang.web.mapstruct.GeneratorConvert;
import com.azhang.web.model.dto.generator.GeneratorQueryRequest;
import com.azhang.web.model.entity.Generator;
import com.azhang.web.model.entity.GeneratorFavour;
import com.azhang.web.model.entity.GeneratorThumb;
import com.azhang.web.model.entity.User;
import com.azhang.web.model.vo.GeneratorVO;
import com.azhang.web.model.vo.UserVO;
import com.azhang.web.service.GeneratorService;
import com.azhang.web.service.UserService;
import com.azhang.web.utils.SqlUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 代码生成器服务实现
 *
 * @author codeZhang
 */
@Service
@Slf4j
public class GeneratorServiceImpl extends ServiceImpl<GeneratorMapper, Generator> implements GeneratorService {

    @Resource
    private UserService userService;

    @Resource
    private GeneratorThumbMapper generatorThumbMapper;

    @Resource
    private GeneratorFavourMapper generatorFavourMapper;


    @Override
    public void validGenerator(Generator Generator, boolean add) {
        if (Generator == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String name = Generator.getName();
        List<String> tags = Generator.getTags();
        String description = Generator.getDescription();
        // 创建时，参数不能为空
        if (add) {
            ThrowUtils.throwIf(StringUtils.isAnyBlank(name), ErrorCode.PARAMS_ERROR);
        }
        if(CollUtil.isEmpty(tags)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "标签不可为空");
        }
        // 有参数则校验
        if (StringUtils.isNotBlank(name) && name.length() > 80) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "标题过长");
        }
        if (StringUtils.isNotBlank(description) && description.length() > 8192) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "内容过长");
        }
    }

    /**
     * 获取查询包装类
     *
     * @param generatorQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<Generator> getQueryWrapper(GeneratorQueryRequest generatorQueryRequest) {
        QueryWrapper<Generator> queryWrapper = new QueryWrapper<>();
        if (generatorQueryRequest == null) {
            return queryWrapper;
        }
        String searchText = generatorQueryRequest.getSearchText();
        String sortField = generatorQueryRequest.getSortField();
        String sortOrder = generatorQueryRequest.getSortOrder();
        Long id = generatorQueryRequest.getId();
        String title = generatorQueryRequest.getTitle();
        String content = generatorQueryRequest.getContent();
        List<String> tagList = generatorQueryRequest.getTags();
        Long userId = generatorQueryRequest.getUserId();
        // 拼接查询条件
        if (StringUtils.isNotBlank(searchText)) {
            queryWrapper.and(qw -> qw.like("name", searchText).or().like("description", searchText));
        }
        queryWrapper.like(StringUtils.isNotBlank(title), "name", title);
        queryWrapper.like(StringUtils.isNotBlank(content), "description", content);
        if (CollUtil.isNotEmpty(tagList)) {
            for (String tag : tagList) {
                queryWrapper.like("tags", "\"" + tag + "\"");
            }
        }
        queryWrapper.eq(ObjectUtils.isNotEmpty(id), "id", id);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    @Override
    public GeneratorVO getGeneratorVO(Generator Generator, HttpServletRequest request) {
        GeneratorVO generatorVO = GeneratorConvert.INSTANCE.convertGeneratorVOByGenerator(Generator);
        
        long generatorId = Generator.getId();
        // 1. 关联查询用户信息
        Long userId = Generator.getUserId();
        User user = null;
        if (userId != null && userId > 0) {
            user = userService.getById(userId);
        }
        UserVO userVO = userService.getUserVO(user);
        generatorVO.setUser(userVO);
        // 2. 已登录，获取用户点赞、收藏状态
        User loginUser = userService.getLoginUserPermitNull(request);
        if (loginUser != null) {
            // 获取点赞
            QueryWrapper<GeneratorThumb> generatorThumbQueryWrapper = new QueryWrapper<>();
            generatorThumbQueryWrapper.in("generatorId", generatorId);
            generatorThumbQueryWrapper.eq("userId", loginUser.getId());
            GeneratorThumb generatorThumb = generatorThumbMapper.selectOne(generatorThumbQueryWrapper);
            generatorVO.setHasThumb(generatorThumb != null);
            // 获取收藏
            QueryWrapper<GeneratorFavour> generatorFavourQueryWrapper = new QueryWrapper<>();
            generatorFavourQueryWrapper.in("generatorId", generatorId);
            generatorFavourQueryWrapper.eq("userId", loginUser.getId());
            GeneratorFavour generatorFavour = generatorFavourMapper.selectOne(generatorFavourQueryWrapper);
            generatorVO.setHasFavour(generatorFavour != null);
        }
        return generatorVO;
    }

    @Override
    public Page<GeneratorVO> getGeneratorVOPage(Page<Generator> generatorPage, HttpServletRequest request) {
        List<Generator> generatorList = generatorPage.getRecords();
        Page<GeneratorVO> generatorVOPage = new Page<>(generatorPage.getCurrent(), generatorPage.getSize(), generatorPage.getTotal());
        if (CollUtil.isEmpty(generatorList)) {
            return generatorVOPage;
        }
        // 1. 关联查询用户信息
        Set<Long> userIdSet = generatorList.stream().map(Generator::getUserId).collect(Collectors.toSet());
        Map<Long, List<User>> userIdUserListMap = userService.listByIds(userIdSet).stream()
                .collect(Collectors.groupingBy(User::getId));
        // 2. 已登录，获取用户点赞、收藏状态
        Map<Long, Boolean> generatorIdHasThumbMap = new HashMap<>();
        Map<Long, Boolean> generatorIdHasFavourMap = new HashMap<>();
        User loginUser = userService.getLoginUserPermitNull(request);
        if (loginUser != null) {
            Set<Long> generatorIdSet = generatorList.stream().map(Generator::getId).collect(Collectors.toSet());
            loginUser = userService.getLoginUser(request);
            // 获取点赞
            QueryWrapper<GeneratorThumb> generatorThumbQueryWrapper = new QueryWrapper<>();
            generatorThumbQueryWrapper.in("generatorId", generatorIdSet);
            generatorThumbQueryWrapper.eq("userId", loginUser.getId());
            List<GeneratorThumb> generatorGeneratorThumbList = generatorThumbMapper.selectList(generatorThumbQueryWrapper);
            generatorGeneratorThumbList.forEach(generatorGeneratorThumb -> generatorIdHasThumbMap.put(generatorGeneratorThumb.getGeneratorId(), true));
            // 获取收藏
            QueryWrapper<GeneratorFavour> generatorFavourQueryWrapper = new QueryWrapper<>();
            generatorFavourQueryWrapper.in("generatorId", generatorIdSet);
            generatorFavourQueryWrapper.eq("userId", loginUser.getId());
            List<GeneratorFavour> generatorFavourList = generatorFavourMapper.selectList(generatorFavourQueryWrapper);
            generatorFavourList.forEach(generatorFavour -> generatorIdHasFavourMap.put(generatorFavour.getGeneratorId(), true));
        }
        // 填充信息
        List<GeneratorVO> generatorVOList = generatorList.stream().map(Generator -> {
            GeneratorVO generatorVO = GeneratorConvert.INSTANCE.convertGeneratorVOByGenerator(Generator);
            Long userId = Generator.getUserId();
            User user = null;
            if (userIdUserListMap.containsKey(userId)) {
                user = userIdUserListMap.get(userId).get(0);
            }
            generatorVO.setUser(userService.getUserVO(user));
            generatorVO.setHasThumb(generatorIdHasThumbMap.getOrDefault(Generator.getId(), false));
            generatorVO.setHasFavour(generatorIdHasFavourMap.getOrDefault(Generator.getId(), false));
            return generatorVO;
        }).collect(Collectors.toList());
        generatorVOPage.setRecords(generatorVOList);
        return generatorVOPage;
    }

}




