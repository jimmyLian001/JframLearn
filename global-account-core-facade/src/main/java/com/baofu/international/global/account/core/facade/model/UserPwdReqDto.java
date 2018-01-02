package com.baofu.international.global.account.core.facade.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 用户密码重置，修改DTO
 * User: 康志光  Date: 2017/11/04 ProjectName: globalaccount Version: 1.0
 */
@Setter
@Getter
@ToString
public class UserPwdReqDto implements Serializable {

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
