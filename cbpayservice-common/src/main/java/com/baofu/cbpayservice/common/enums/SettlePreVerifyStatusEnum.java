package com.baofu.cbpayservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 结汇垫资审核状态
 * <p>
 * </p>
 * User: 康志光 Date: 2017/8/17 ProjectName: cbpay-customs-service Version: 1.0
 */
@Getter
@AllArgsConstructor
public enum SettlePreVerifyStatusEnum {

    FIRST_VERIFY(0, "待初审"),

    SECOND_VERIFY(1, "待复审"),

    SECOND_PASS(2, "复审通过"),

    FIRST_VERIFY_FAILED(3, "初审审核失败"),

    SECOND_VERIFY_FAILED(4, "复审审核失败"),;

    private int code;

    private String desc;

}
