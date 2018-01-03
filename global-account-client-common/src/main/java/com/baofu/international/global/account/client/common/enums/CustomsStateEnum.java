package com.baofu.international.global.account.client.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 客户状态
 * <p>
 * @author : hetao Date: 2017/11/06 account-core version: 1.0.0
 * </p>
 */
@Getter
@AllArgsConstructor
public enum CustomsStateEnum {
    /**
     * 冻结
     */
    FROZEN(0, "冻结"),

    /**
     * 正常
     */
    NORMAL(1, "正常"),

    /**
     * 失效
     */
    INVALID(2, "失效"),;

    /**
     * code
     */
    private int code;

    /**
     * 描述
     */
    private String desc;
}
