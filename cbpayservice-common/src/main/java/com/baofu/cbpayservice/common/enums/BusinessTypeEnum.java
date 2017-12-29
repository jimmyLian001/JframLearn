package com.baofu.cbpayservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 业务类型
 * User: feng_jiang
 */
@Getter
@AllArgsConstructor
public enum BusinessTypeEnum {

    /**
     * 1-购汇
     */
    REMITTANCE(1, "购汇"),

    /**
     * 2-结汇
     */
    SETTLE(2, "结汇"),

    /**
     * 3-垫资结汇
     */
    ADVANCE_SETTLE(3, "垫资结汇");

    /**
     * 值
     */
    private Integer code;

    /**
     * 描述
     */
    private String desc;
}
