package com.baofu.international.global.account.core.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 转账类型枚举类：0-账户间转账，1-转账至银行
 * User: feng_jiang Date:2017/11/5 ProjectName: globalaccount-core Version: 1.0
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
