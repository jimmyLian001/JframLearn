package com.baofu.international.global.account.core.facade.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * description:注册用户信息
 * <p/>
 * Created by liy on 2017/11/24 ProjectName：account
 */
@Getter
@Setter
@ToString
public class RegisterUserReqDto implements Serializable {

    private static final long serialVersionUID = 920735084575848178L;

    /**
     * 用户名
     */
    @NotNull(message = "用户名格式不正确")
    private String loginNo;

    /**
     * 登录密码
     */
    @NotNull(message = "登录密码不能为空")
    private String loginPwd;

    /**
     * 登录密码
     */
    @NotNull(message = "登录密码不能为空")
    private String loginPwdAgain;
    /**
     * 验证码
     */
    @NotNull(message = "验证码不能为空")
    private String captcha;
}
