package com.baofu.cbpayservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 申请状态
 * User: wanght Date:2016/11/11 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@AllArgsConstructor
public enum SettleAmlStatus {

    /**
     * 不通过
     */
    NO_PASS(0, "不通过"),

    /**
     * 通过
     */
    PASS(1, "通过");

    /**
     * code
     */
    private int code;

    /**
     * 描述
     */
    private String desc;
}
