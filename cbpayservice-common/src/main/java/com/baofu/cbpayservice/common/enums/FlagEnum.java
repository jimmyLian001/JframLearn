package com.baofu.cbpayservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 成功状态标识
 * User: 香克斯 Date:2016/9/24 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@AllArgsConstructor
public enum FlagEnum {

    /**
     * 初始化
     */
    INIT("INIT", "初始化"),

    /**
     * 是
     */
    TRUE("TRUE", "是"),

    /**
     * 否
     */
    FALSE("FALSE", "否");

    /**
     * code
     */
    private String code;

    /**
     * 描述
     */
    private String desc;
}
