package com.baofu.international.global.account.core.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * 1、店铺状态枚举
 * </p>
 * User: 香克斯  Date: 2017/11/7 ProjectName:account-core  Version: 1.0
 */
@Getter
@AllArgsConstructor
public enum StoreStateEnum {

    /**
     * 店铺冻结
     */
    FROZEN(0, "店铺冻结"),

    /**
     * 店铺正常
     */
    NORMAL(1, "店铺正常"),

    /**
     * 店铺失效
     */
    INVALID(2, "店铺失效");


    /**
     * code
     */
    private int code;

    /**
     * 描述
     */
    private String desc;
}
