package com.baofu.cbpayservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 转账类型枚举类：0-账户间转账，1-转账至银行
 *
 * User: 不良人 Date:2017/9/8 ProjectName: cbpayservice Version: 1.0
 **/
@Getter
@AllArgsConstructor
public enum TransferTypeEnum {

    /**
     * 0-账户间转账
     */
    ACCOUNT_TO_ACCOUNT(0, "账户间转账"),

    /**
     * 1-转账至银行
     */
    ACCOUNT_TO_BANK(1, "转账至银行");

    /**
     * code
     */
    private Integer code;

    /**
     * 描述
     */
    private String desc;
}
