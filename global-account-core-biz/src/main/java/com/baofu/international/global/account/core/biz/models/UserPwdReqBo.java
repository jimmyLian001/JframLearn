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
public class UserPwdReqBo {

    /**
     * 用户号
     */
    private Long userNo;

    /**
     * 用户名（登录号）
     */
    private String loginNo;

    /**
     * 老密码
     */
    private String oldPwd;

    /**
     * 第一次输入密码
     */
    private String firstPwd;

    /**
     * 第二次输入密码
     */
    private String secondPwd;

    /**
     * 密码类型
     */
    private Integer pwdType;

    /**
     * 操作人
     */
    private String operator;

}