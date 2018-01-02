package com.baofu.international.global.account.core.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户登录密码修改，重置BO
 * <p>
 * User: 康志光 Date: 2017/11/04 Version: 1.0
 * </p>
 */
@Setter
@Getter
@ToString
public class UserLoginInfoReqBo {

    /**
     * 用户名（登录号）
     */
    private Long loginNo;

    /**
     * 页面验证码
     */
    private String pageVerifyCode;

}