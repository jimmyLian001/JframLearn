package com.baofu.international.global.account.core.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * 1、方法描述
 * </p>
 * User: 香克斯  Date: 2017/11/3 ProjectName:account-core  Version: 1.0
 */
@Getter
@AllArgsConstructor
public enum SystemEnum {

    /**
     * 国际收款账户核心
     */
    SYSTEM_NAME("GLOBAL-ACCOUNT-CORE", "国际收款账户核心"),;

    /**
     * code
     */
    private String code;

    /**
     * 描述
     */
    private String desc;
}
