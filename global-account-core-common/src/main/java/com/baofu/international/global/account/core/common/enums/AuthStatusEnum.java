package com.baofu.international.global.account.core.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 实名认证状态枚举类
 * <p>
 *
 * @author : hetao Date: 2017/11/06 account-core version: 1.0.0
 * </p>
 */
@Getter
@AllArgsConstructor
public enum AuthStatusEnum {
    /**
     * 认证成功
     */
    AUTH_SUCCESS(4, "认证成功"),

    /**
     * 认证失败
     */
    AUTH_FAIL(3, "认证失败"),;

    /**
     * code
     */
    private int code;

    /**
     * 描述
     */
    private String desc;
}
