package com.baofu.international.global.account.core.dal.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Setter
@Getter
@ToString(callSuper = true)
public class UserLoginInfoDo extends BaseDo {
    /**
     * 登录账户号 手机号或邮箱
     */
    private String loginNo;

    /**
     * 用户号
     */
    private Long userNo;

    /**
     * 登录账户类型：1-邮箱，2-手机号码
     */
    private int loginType;

    /**
     * 用户类型: 1-个人 2-企业
     */
    private int userType;

    /**
     * 登录账户状态：1-正常
     */
    private int loginState;

    /**
     * 最后登录时间
     */
    private Date lastLoginAt;
}