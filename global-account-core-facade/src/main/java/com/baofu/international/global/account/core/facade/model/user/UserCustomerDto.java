package com.baofu.international.global.account.core.facade.model.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@ToString(callSuper = true)
public class UserCustomerDto implements Serializable {
    /**
     * 用户号
     */
    private Long userNo;

    /**
     * 用户类型: 1-个人 2-企业
     */
    private int userType;
    /**
     * 用户状态：0-冻结 1-正常 2-锁定
     */
    private int userState;
    /**
     * 客户号
     */
    private Long customerNo;

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

    /**
     * 审核失败原因
     */
    private String remarks;

    /**
     * 创建日期
     */
    private Date createAt;
}