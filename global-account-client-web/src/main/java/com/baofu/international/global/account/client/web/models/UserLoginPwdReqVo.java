package com.baofu.international.global.account.client.web.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *  * 修改登录密码信息vo
 * <p>
 * User: kangzhiguang Date:2017/11/04 ProjectName: account-client Version: 1.0
 */
@Getter
@Setter
@ToString
public class UserLoginPwdReqVo {

    /**
     * 老密码
     */
    private String oldPassword;

    /**
     * 第一次输入密码
     */
    private String firstPwd;

    /**
     * 第二次输入密码
     */
    private String secondPwd;
}
