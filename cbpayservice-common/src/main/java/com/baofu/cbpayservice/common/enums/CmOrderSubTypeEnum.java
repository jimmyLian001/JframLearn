package com.baofu.cbpayservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 账户操作类型
 * <p>
 * User: wanght Date:2016/12/26 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@AllArgsConstructor
public enum CmOrderSubTypeEnum {

    /**
     * 账户充值
     */
    ACCOUNT_RECHARGE(301, "账户充值"),

    /**
     * 账户转账
     */
    ACCOUNT_TRANSFER(302, "账户转账"),

    /**
     * 账户提现
     */
    ACCOUNT_WITHDRAW(303, "账户提现");

    /**
     * 类型code
     */
    private Integer code;

    /**
     * 描述
     */
    private String desc;
}
