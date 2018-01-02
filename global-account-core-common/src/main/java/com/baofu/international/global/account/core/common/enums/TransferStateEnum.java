package com.baofu.international.global.account.core.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 转账状态，0-待转账；1-转账处理中，2-转账成功，3-转账失败
 * User: feng_jiang Date:2017/11/5 ProjectName: globalaccount-core Version: 1.0
 **/
@Getter
@AllArgsConstructor
public enum TransferStateEnum {

    /**
     * 0-待转账
     */
    TRANSFER_ACCOUNTS_WATTING(0, "待转账"),

    /**
     * 1-转账处理中
     */
    TRANSFER_ACCOUNTS_PROCESS(1, "转账处理中"),

    /**
     * 2-转账成功
     */
    TRANSFER_ACCOUNTS_SUCCESS(2, "转账成功"),

    /**
     * 3-转账失败
     */
    TRANSFER_ACCOUNTS_FAIL(3, "转账失败"),;

    /**
     * code
     */
    private Integer code;

    /**
     * 描述
     */
    private String desc;
}
