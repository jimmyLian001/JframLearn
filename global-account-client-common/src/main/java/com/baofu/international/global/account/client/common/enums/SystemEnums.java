package com.baofu.international.global.account.client.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * 1、系统描述
 * </p>
 * User: 香克斯  Date: 2017/11/8 ProjectName:account-client  Version: 1.0
 */
@Getter
@AllArgsConstructor
public enum SystemEnums {

    /**
     * 系统名称
     */
    SYSTEM_NAME("GLOBAL_CLIENT", "收款账户页面前端系统");

    /**
     * code
     */
    private String code;

    /**
     * 描述
     */
    private String desc;
}
