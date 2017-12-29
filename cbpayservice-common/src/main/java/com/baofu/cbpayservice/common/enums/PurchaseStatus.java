package com.baofu.cbpayservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 申请状态
 * User: kangzhiguang Date:2017/03/23 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@AllArgsConstructor
public enum PurchaseStatus {

    /**
     * 初始化
     */
    INIT(0, "初始化"),

    /**
     * 购汇成功
     */
    TRUE(1, "购汇成功"),

    /**
     * 购汇中
     */
    PROCESSING(2, "购汇中"),

    /**
     * 购汇失败
     */
    FALSE(3, "购汇失败");

    /**
     * code
     */
    private Integer code;

    /**
     * 描述
     */
    private String desc;
}
