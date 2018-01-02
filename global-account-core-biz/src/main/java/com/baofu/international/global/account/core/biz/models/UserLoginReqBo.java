package com.baofu.international.global.account.core.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户登录接口请求参数
 *
 * @author: 不良人 Date:2017/11/5 ProjectName: account-core Version: 1.0
 */
@Setter
@Getter
@ToString
public class UserLoginReqBo {

    /**
     * 用户名（登录号）
     */
    private String loginNo;

    /**
     * 密码
     */
    private String loginPwd;

    /**
     * 登录IP
     */
    private String loginIp;

}