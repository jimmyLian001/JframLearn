package com.baofu.international.global.account.core.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 用户登录密码响应BO
 * <p>
 * User: 康志光 Date: 2017/11/04 Version: 1.0
 * </p>
 */
@Setter
@Getter
@ToString
public class UserPwdRespBo {

    /**
     * 用户号
     */
    private Long userNo;

    /**
     * 支付密码
     */
    private String pwd;

    /**
     * 密码类型：1-登录密码，2-支付密码
     */
    private Integer pwdType;

    /**
     * 支付密码状态：1-正常，2-锁定，3-失效
     */
    private Integer state;

    /**
     * 支付密码错误次数 默认为0
     */
    private Integer errorNum;

    /**
     * 密码错误次数锁定时间
     */
    private Date lockAt;

    /**
     * 最后一次修改密码时间
     */
    private Date modifyAt;

    /**
     * 密码版本，默认版本1
     */
    private Integer version;

}