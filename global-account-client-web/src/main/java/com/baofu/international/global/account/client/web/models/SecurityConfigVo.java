package com.baofu.international.global.account.client.web.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 安全设置首页VO
 *
 * Created by kangzhiguang on 2017/11/5 0005.
 */
@Setter
@Getter
@ToString
public class SecurityConfigVo {

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
    private String loginPassStatus;

    /**
     * 支付密码状态
     */
    private String payPassStatus;

    /**
     * 手机绑定状态
     */
    private String mobileBandStatus;

    /**
     * 密保问题状态
     */
    private String questionsStatus;

}
