package com.baofu.cbpayservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 产品对应的产品ID和功能ID
 * <p>
 * User: 不良人 Date:2017/4/14 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ProductAndFunctionEnum {

    /**
     * 跨境结汇产品
     */
    FUNCTION_SETTLE(1018, 10180001),

    /**
     * 跨境人民币汇款
     */
    PRO_1016(1016, 10160001),;

    /**
     * 产品ID
     */
    private Integer productId;

    /**
     * 功能ID
     */
    private Integer FunctionId;
}
