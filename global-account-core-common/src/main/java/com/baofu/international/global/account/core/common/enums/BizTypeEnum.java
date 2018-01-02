package com.baofu.international.global.account.core.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * description:
 * <p/>
 * User: feng_jiang Date:2017/11/5 ProjectName: globalaccount-core Version: 1.0
 */
@Getter
@AllArgsConstructor
public enum BizTypeEnum {

    /**
     * 1-批量转账
     */
    ONE_TRANSFER(1, "批量转账"),

    /**
     * 2-批量结汇
     */
    TWO_SETTLEMENT(2, "批量结汇"),

    /**
     * 3-收汇对应转账关系
     */
    THREE_EXCHANGE(3, "收汇对应转账关系"),;

    /**
     * code
     */
    private int code;

    /**
     * 描述
     */
    private String desc;
}
