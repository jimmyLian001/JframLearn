package com.baofu.cbpayservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 浮动汇率浮动值设置方式
 * User: feng_jiang
 */
@Getter
@AllArgsConstructor
public enum RateSetTypeEnum {

    /**
     * 1-bp
     */
    BP(1, "bp"),

    /**
     * 2-百分比
     */
    PERCENTAGE(2, "百分比");

    /**
     * 值
     */
    private Integer code;

    /**
     * 描述
     */
    private String desc;
}
