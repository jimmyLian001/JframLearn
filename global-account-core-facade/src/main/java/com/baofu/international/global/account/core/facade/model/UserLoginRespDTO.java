package com.baofu.international.global.account.core.facade.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 用户登录接口返回参数
 *
 * @author: 不良人 Date:2017/11/5 ProjectName: account-core Version: 1.0
 */
@Setter
@Getter
@ToString
public class UserLoginRespDTO implements Serializable {

    private static final long serialVersionUID = -7627872391472416648L;
    /**
     * 登录账户号 手机号或邮箱
     */
    private String loginNo;

    /**
     * 用户号
     */
    private Long userNo;

    /**
     * 客户号
     */
    private Long customerNo;

    /**
     * 用户类型: 1-个人 2-企业
     */
    private Integer userType;

    /**
     * 登录类型 1-邮箱，2-手机号码
     */
    private Integer loginType;
}
