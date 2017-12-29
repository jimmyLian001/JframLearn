package com.baofu.cbpayservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 商户多币种结算状态：1-不可结算；2-可结算
 * <p>
 * User: 不良人 Date:2017/4/26 ProjectName: feature-BFO-793-170426-zwb Version: 1.0
 */
@Getter
@AllArgsConstructor
public enum BankSettleStatus {

    /**
     * 1-不可结算
     */
    NO_SETTLE(1, "不可结算"),

    /**
     * 2-可结算
     */
    YES_SETTLE(2, "可结算"),;

    /**
     * code
     */
    private int code;

    /**
     * 描述
     */
    private String desc;
}
