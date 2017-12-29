package com.baofu.cbpayservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 解付状态 : 1-待解付，2-已解付,3-解付申请中（发往渠道），4-渠道通知发送银行，5-解付失败
 * <p>
 * User: 康志光 Date:2017/4/13 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@AllArgsConstructor
public enum InComeStatusEnum {

    /**
     * 1-待解付
     */
    WAIT_INCOME(1, "待解付"),

    /**
     * 2-已解付
     */
    COMPELTED_INCOME(2, "已解付"),

    /**
     * 3-解付申请中
     */
    INCOMING(3, "解付申请中"),

    /**
     * 4-渠道通知发送银行
     */
    CHANNEL_INCOME(4, "渠道通知发送银行"),

    /**
     * 5-解付失败
     */
    FAIL(5, "解付失败"),;

    /**
     * code
     */
    private int code;

    /**
     * 描述
     */
    private String desc;

}
