package com.baofu.cbpayservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 清算状态
 * <p>
 * User: 康志光 Date:2017/4/13 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@AllArgsConstructor
public enum SettleFlagEnum {

    /**
     * 1-未清算
     */
    WAIT_SETTLEMENT(1, "未清算"),

    /**
     * 2-清算失败
     */
    SETTLE_FAILED(2, "清算失败"),

    /**
     * 3-清算成功
     */
    SETTLE_SUCCESS(3, "清算成功");

    /**
     * code
     */
    private int code;

    /**
     * 描述
     */
    private String desc;

}
