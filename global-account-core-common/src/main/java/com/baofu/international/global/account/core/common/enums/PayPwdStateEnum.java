package com.baofu.international.global.account.core.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * 1、支付密码状态枚举
 * </p>
 * User: 香克斯  Date: 2017/11/8 ProjectName:account-core  Version: 1.0
 */
@Getter
@AllArgsConstructor
public enum PayPwdStateEnum {


    /**
     * 正常
     */
    NORMAL(1, "正常"),

    /**
     * 锁定
     */
    LOCK(2, "锁定"),

    /**
     * 失效
     */
    INVALID(3, "失效");

    /**
     * code
     */
    private int code;

    /**
     * 描述
     */
    private String desc;

}
