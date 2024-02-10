package com.azhang.web.controller;

import com.azhang.web.common.BaseResponse;
import com.azhang.web.common.ErrorCode;
import com.azhang.web.common.ResultUtils;
import com.azhang.web.exception.BusinessException;
import com.azhang.web.model.dto.generatorthumb.GeneratorThumbAddRequest;
import com.azhang.web.model.entity.User;
import com.azhang.web.service.GeneratorThumbService;
import com.azhang.web.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 代码生成器点赞接口
 *
 * @author codeZhang
 */
@RestController
@RequestMapping("/generator_thumb")
@Slf4j
public class GeneratorThumbController {

    @Resource
    private GeneratorThumbService generatorThumbService;

    @Resource
    private UserService userService;

    /**
     * 点赞 / 取消点赞
     *
     * @param generatorThumbAddRequest
     * @param request
     * @return resultNum 本次点赞变化数
     */
    @PostMapping("/")
    public BaseResponse<Integer> doThumb(@RequestBody GeneratorThumbAddRequest generatorThumbAddRequest,
            HttpServletRequest request) {
        if (generatorThumbAddRequest == null || generatorThumbAddRequest.getGeneratorId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 登录才能点赞
        final User loginUser = userService.getLoginUser(request);
        long generatorId = generatorThumbAddRequest.getGeneratorId();
        int result = generatorThumbService.doGeneratorThumb(generatorId, loginUser);
        return ResultUtils.success(result);
    }

}
