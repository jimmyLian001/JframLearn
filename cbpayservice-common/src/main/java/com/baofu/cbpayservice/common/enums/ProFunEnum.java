package com.baofu.cbpayservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 产品功能关联关系
 * User: 香克斯 Date:2016/12/19 ProjectName: asias-icpaygate Version: 1.0
 */
@Getter
@AllArgsConstructor
public enum ProFunEnum {

    /**
     * 跨境结汇功能
     */
    PRO_FUN_10180001(1018, 10180001, "跨境结汇功能"),
    /**
     * 跨境结汇垫资-自动功能
     */
    PRO_FUN_10180003(1018, 10180003, "跨境结汇垫资-自动功能"),
    /**
     * 跨境收款账户-自动功能
     */
    PRO_FUN_10190001(1019, 10190001, "跨境收款账户-自动功能"),

    /**
     * 跨境人民币汇款
     */
    PRO_FUN_10160001(1016, 10160001, "跨境人民币汇款"),

    /**
     * 跨境外汇汇款
     */
    PRO_FUN_10160002(1016, 10160002, "跨境外汇汇款"),;

    /**
     * 产品编号
     */
    private int productId;

    /**
     * 功能编号
     */
    private int functionId;

    /**
     * 功能描述
     */
    private String desc;
}
