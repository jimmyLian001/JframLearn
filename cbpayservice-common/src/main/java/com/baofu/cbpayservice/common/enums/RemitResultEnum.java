package com.baofu.cbpayservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 描述01-成功、02-失败、03-汇款中/处理中、04-待汇款
 * <p>
 * User: 不良人 Date:2017/9/22 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public enum RemitResultEnum {

    /**
     * 01-购付汇渠道
     */
    SUCCESS("01", "成功"),

    /**
     * 02-失败
     */
    FAIL("02", "失败"),

    /**
     * 03-汇款中/处理中
     */
    PROCESSING("03", "收款渠道"),

    /**
     * 04-待汇款
     */
    WAITING("04", "待汇款"),

    ;

    /**
     * 值
     */
    private String code;

    /**
     * 描述
     */
    private String desc;
}
