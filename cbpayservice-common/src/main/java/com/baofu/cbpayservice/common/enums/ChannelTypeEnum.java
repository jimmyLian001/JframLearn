package com.baofu.cbpayservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 渠道类型：0-购付汇渠道，2-收款渠道
 * <p>
 * User: 不良人 Date:2017/9/13 ProjectName: cbpayservice Version: 1.0
 **/
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public enum  ChannelTypeEnum {

    /**
     * 0-购付汇渠道
     */

    PURCHASE_PAYMENT(0, "购付汇渠道"),

    /**
     * 1-收款渠道
     */
    RECEIVABLES(1, "收款渠道"),

    ;

    /**
     * 值
     */
    private Integer code;

    /**
     * 描述
     */
    private String desc;
}
