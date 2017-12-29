package com.baofu.cbpayservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 结汇信息状态
 * <p>
 * User: 不良人 Date:2017/4/13 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@AllArgsConstructor
public enum SettleStatusEnum {

    /**
     * 1-待结汇
     */
    WAIT_SETTLEMENT(1, "待结汇"),

    /**
     * 2-结汇处理中
     */
    SETTLEMENT_PROCESSING(2, "结汇处理中"),

    /**
     * 3-结汇完成
     */
    TURE(3, "结汇完成"),

    /**
     * 4-结汇失败
     */
    FAIL(4, "结汇失败"),

    /**
     * 5-部分结汇完成
     */
    PART_TRUE(5, "部分结汇完成"),;

    /**
     * code
     */
    private int code;

    /**
     * 描述
     */
    private String desc;

}
