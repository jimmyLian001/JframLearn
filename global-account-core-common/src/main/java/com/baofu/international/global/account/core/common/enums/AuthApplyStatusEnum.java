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
public enum AuthApplyStatusEnum {
    /**
     * 身份认证失败
     */
    ID_CARD_AUTH_FAIL(1, "身份认证失败"),
    /**
     * 银行卡认证失败
     */
    BANK_CARD_AUTH_FAIL(2, "银行卡认证失败"),
    /**
     * 企业实名认证失败
     */
    ORG_AUTH_FAIL(3, "企业实名认证失败"),

    /**
     * 认证成功
     */
    AUTH_SUCCESS(4, "认证成功"),;

    /**
     * code
     */
    private int code;

    /**
     * 描述
     */
    private String desc;
}
