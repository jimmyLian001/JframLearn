package com.baofu.international.global.account.client.service.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 安全设置首页Bo
 *
 * Created by kangzhiguang on 2017/11/6 0005.
 */
@Setter
@Getter
@ToString
public class SecurityConfigBo {

    /**
     * 登陆号
     */
    private String loginNo;

    /**
     * 用户号
     */
    private Long userNo;

    /**
     * 手机号
     */
    private String mobileNo;

    /**
     * 实名状态
     */
    private String authStatus;

    /**
     * 登陆密码状态
     */
    private String loginPwdStatus;

    /**
     * 支付密码状态
     */
    private String payPwdStatus;

    /**
     * 手机绑定状态
     */
    private String mobileBandStatus;

    /**
     * 密保问题状态
     */
    private String questionsStatus;

}
