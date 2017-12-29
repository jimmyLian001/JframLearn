package com.baofu.cbpayservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 产品对应功能集合
 * User: 不良人 Date:2017/02/22 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@AllArgsConstructor
public enum RemittanceOrderType {

    /**
     * 代理跨境批次
     */
    PROXY_REMITTANCE_ORDER("0", "代理跨境批次"),

    /**
     * 跨境批次
     */
    REMITTANCE_ORDER("1", "跨境批次"),;

    /**
     * 产品ID
     */
    private String code;

    /**
     * 功能ID
     */
    private String desc;
}
