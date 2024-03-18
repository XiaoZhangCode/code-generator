package com.azhang.web.model.dto.user;

import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;

/**
 * 用户注册请求体
 *
 * @author codeZhang
 */
@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    private String userAccount;

    private String userPassword;

    private String checkPassword;

    private String userEmail;
    /**
     * 验证码
     */
    private String code;
}
