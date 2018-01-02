package com.baofu.international.global.account.core.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 卡状态枚举类
 * <p>
 *
 * @author : hetao Date: 2017/11/06 account-core version: 1.0.0
 * </p>
 */
@Getter
@AllArgsConstructor
public enum CardStateEnum {
    /**
     * 0-冻结
     */
    FROZEN(0, "冻结"),

    /**
     * 1-待激活
     */
    WAIT_ACTIVE(1, "待激活"),

    /**
     * 2-已激活
     */
    ACTIVATED(2, "已激活"),

    /**
     * 3-失效
     */
    INVALID(3, "失效"),;

    /**
     * code
     */
    private int code;

    /**
     * 描述
     */
    private String desc;
}
