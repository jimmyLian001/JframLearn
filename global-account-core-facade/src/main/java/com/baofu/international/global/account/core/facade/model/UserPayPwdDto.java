package com.baofu.international.global.account.core.facade.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@ToString
public class UserPayPwdDto implements Serializable {
    /**
     * 用户号
     */
    private Long userNo;

    /**
     * 支付密码
     */
    private String payPwd;

    /**
     * 支付密码状态：1-正常，2-锁定，3-失效
     */
    private Integer payState;

    /**
     * 支付密码错误次数 默认为0
     */
    private Integer payPwdErrorNum;

    /**
     * 密码错误次数锁定时间
     */
    private Date payPwdLockAt;

    /**
     * 最后一次修改密码时间
     */
    private Date payPwdModfiyAt;

    /**
     * 密码版本，默认版本1
     */
    private Integer payPwdVersion;
}