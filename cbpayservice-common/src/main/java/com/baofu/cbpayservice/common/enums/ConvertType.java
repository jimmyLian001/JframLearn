package com.baofu.cbpayservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * User: yangjian  Date: 2017-06-16 ProjectName:cbpay-service Version: 1.0
 */
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public enum ConvertType {
    /**
     * 付汇类型
     */
    REMITTANCE_LIST("REMITTANCE_LIST"),
    /**
     * 结汇类型
     */
    SETTLE_LIST("SETTLE_LIST"),
    /**
     * 解付类型
     */
    RELIEVE_LIST("RELIEVE_LIST"),;

    /**
     * 类型
     */
    private String code;
}
