package com.baofu.cbpayservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 入账标识，30-未入账，31-入账成功
 * <p>
 * User: 不良人 Date:2017/4/13 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@AllArgsConstructor
public enum SettleAccStatusEnum {

    /**
     * 1-未入账
     */
    NOT_RECORDED(30, "未入账"),

    /**
     * 2-结汇处理中
     */
    RECORDED_SUCCESS(31, "入账成功"),;

    /**
     * code
     */
    private int code;

    /**
     * 描述
     */
    private String desc;

}
