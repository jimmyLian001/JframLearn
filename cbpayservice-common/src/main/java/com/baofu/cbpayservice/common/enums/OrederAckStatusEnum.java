package com.baofu.cbpayservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 跨境订单确认状态处理
 * <p>
 * User: wanght Date:2016/12/26 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@AllArgsConstructor
public enum OrederAckStatusEnum {

    /**
     * 确认
     */
    YES(1, "确认"),

    /**
     * 未确认
     */
    NO(2, "未确认");

    /**
     * 值
     */
    private Integer code;

    /**
     * 描述
     */
    private String desc;
}
