package com.baofu.international.global.account.core.facade.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 用户登录信息
 * <p>
 * 1,用户登录信息
 * </p>
 *
 * @author : wuyazi
 * @date: 2017/11/06
 * @version: 1.0.0
 */
@Setter
@Getter
@ToString
public class UserCustomerRespDTO extends BaseDTO {

    /**
     * 用户号
     */
    private Long userNo;

    /**
     * 客户号
     */
    private Long customerNo;

    /**
     * 手机号(登录手机号)
     */
    private String phoneNumber;

    /**
     * 邮箱(登录邮箱)
     */
    private String email;

    /**
     * 用户类型: 1-个人 2-企业
     */
    private Integer userType;

    /**
     * 用户状态：0-冻结 1-正常 2-锁定
     */
    private Integer userState;

    /**
     * 登录密码
     */
    private String loginPwd;

    /**
     * 密码类型：1-静态密码
     */
    private Integer loginPwdType;

    /**
     * 登陆密码错误次数 默认为0
     */
    private Integer loginPwdErrorNum;

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
    private Integer loginPwdVersion;

    /**
     * 认证状态：0-未认证；1-认证中 2-已认证 默认为未认证
     */
    private Integer realnameStatus;

}
