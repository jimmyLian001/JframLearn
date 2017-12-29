package com.baofu.cbpayservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 是否汇款
 * User: wanght Date:2016/11/11 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@AllArgsConstructor
public enum RemittanceStatus {

    /**
     * 未汇款
     */
    INIT("INIT", "未汇款"),

    /**
     * 处理中
     */
    PROCESSING("PROCESSING", "处理中"),

    /**
     * 汇款中
     */
    REMITTANCEING("REMITTANCEING", "汇款中"),

    /**
     * 已汇款
     */
    TRUE("TRUE", "已汇款"),

    /**
     * 汇款失败
     */
    FALSE("FALSE", "汇款失败");

    /**
     * code
     */
    private String code;

    /**
     * 描述
     */
    private String desc;
}
