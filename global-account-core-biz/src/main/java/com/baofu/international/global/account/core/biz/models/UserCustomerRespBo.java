package com.baofu.international.global.account.core.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 用户登录信息
 * <p>
 * User: 康志光 Date: 2017/11/06 Version: 1.0
 * </p>
 */
@Setter
@Getter
@ToString
public class UserCustomerRespBo {

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
    private int userType;

    /**
     * 用户状态：0-冻结 1-正常 2-锁定
     */
    private int userState;

    /**
     * 登录密码
     */
    private String loginPwd;

    /**
     * 密码类型：1-静态密码
     */
    private int loginPwdType;

    /**
     * 登陆密码错误次数 默认为0
     */
    private int loginPwdErrorNum;

    /**
     * 密码错误次数锁定时间
     */
    private Date loginPwdLockAt;

    /**
     * 最后一次修改密码时间
     */
    private Date loginPwdModfiyAt;

    /**
     * 密码版本：默认版本1
     */
    private int loginPwdVersion;

    /**
     * 认证状态：0-未认证；1-认证中 2-已认证 默认为未认证
     */
    private int realnameStatus;

    /**
     * 手机号(登录手机号)
     */
    private String phoneNumber;

    /**
     * 邮箱(登录邮箱)
     */
    private String email;


}
