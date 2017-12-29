package com.baofu.cbpayservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 申请状态
 * User: wanght Date:2016/11/11 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@AllArgsConstructor
public enum CMStatus {

    /**
     * 初始化
     */
    INIT("INIT", "初始化"),

    /**
     * 结算成功
     */
    TRUE("TRUE", "结算成功"),

    /**
     * 结算失败
     */
    FALSE("FALSE", "结算失败");

    /**
     * code
     */
    private String code;

    /**
     * 描述
     */
    private String desc;
}
