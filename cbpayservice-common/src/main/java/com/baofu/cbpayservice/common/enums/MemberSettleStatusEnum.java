package com.baofu.cbpayservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * 1、方法描述
 * </p>
 * User: 香克斯  Date: 2017/10/25 ProjectName:cbpay-service  Version: 1.0
 */
@Getter
@AllArgsConstructor
public enum MemberSettleStatusEnum {

    /**
     * 申请处理中
     */
    APPLY_ING(1, "申请处理中"),

    /**
     * 申请处理失败
     */
    APPLY_FAIL(2, "申请处理失败"),

    /**
     * 申请处理成功
     */
    APPLY_SUCCESS(3, "申请处理成功"),

    /**
     * 结汇处理中
     */
    SETTLE_ING(4, "结汇处理中"),

    /**
     * 结汇失败
     */
    SETTLE_FAIL(5, "结汇失败"),

    /**
     * 结汇成功
     */
    SETTLE_SUCCESS(6, "结汇成功"),

    /**
     * 文件处理失败
     */
    FILE_PROCESS_FAIL(7, "文件处理失败"),;

    private int code;

    private String desc;
}
