package com.azhang.web.controller;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.mail.MailUtil;
import com.azhang.web.common.BaseResponse;
import com.azhang.web.common.ErrorCode;
import com.azhang.web.common.ResultUtils;
import com.azhang.web.exception.BusinessException;
import com.azhang.web.model.vo.EmailVo;
import com.azhang.web.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/reCaptcha")
@Slf4j
public class ReCaptchaController {



    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private MailService mailService;


    @PostMapping("sendEmailCode")
    public BaseResponse<Boolean> sendVerificationCode(@RequestBody EmailVo emailVo) {
        if(ObjectUtil.isEmpty(emailVo)|| !Validator.isEmail(emailVo.getUserEmail())){
             throw new BusinessException(ErrorCode.PARAMS_ERROR, "邮箱格式不正确");
        }
        String userEmail = emailVo.getUserEmail();
        String emailCode = stringRedisTemplate.opsForValue().get(userEmail);
        if (!StrUtil.isEmpty(emailCode)) {
            return ResultUtils.success(true);
        }
        // 生成6位随机数字验证码
        String code = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
        //放到redis中规定时间内有效
        stringRedisTemplate.opsForValue().set(userEmail,code,3, TimeUnit.MINUTES);
        mailService.sendMail(userEmail,"账号注册验证", "你的验证码如下:"+code+",该验证码主要是用户登录CodeXpress后台管理系统,请尽快输入,你的验证码将于1分钟之后失效啦! 千万不要告诉别人哦！");
        String emailCode2 = stringRedisTemplate.opsForValue().get(userEmail);
        log.warn("{}",emailCode2);
        return ResultUtils.success(true);
    }


}
