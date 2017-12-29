package com.baofu.cbpayservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 汇款订单发起类型
 * User: wanght Date:2016/11/23 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@AllArgsConstructor
public enum TransType {

    /**
     * 系统自动
     */
    SYSTEM("SYSTEM", "系统自动"),

    /**
     * 手动提现
     */
    CASH("CASH", "手动提现");

    /**
     * code
     */
    private String code;

    /**
     * 描述
     */
    private String desc;
}
