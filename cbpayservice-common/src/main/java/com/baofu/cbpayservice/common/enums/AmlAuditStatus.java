package com.baofu.cbpayservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 反洗钱审核状态
 * User: wanght Date:2017/05/21 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@AllArgsConstructor
public enum AmlAuditStatus {

    /**
     * 状态，INIT:初始
     */
    INIT(0, "初始"),

    /**
     * 审核通过
     */
    SUCCESS(1, "审核通过"),

    /**
     * 审核不通过
     */
    FAIL(2, "审核不通过");

    /**
     * 值
     */
    private Integer code;

    /**
     * 描述
     */
    private String desc;
}
