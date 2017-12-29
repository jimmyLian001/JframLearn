package com.baofu.cbpayservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 渠道成本配置状态
 * User: wanght Date:2016/11/30 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@AllArgsConstructor
public enum ChannelFeeStatus {

    /**
     * 初始化
     */
    INIT("INIT", "初始化"),

    /**
     * 启用
     */
    TRUE("TRUE", "启用"),

    /**
     * 失效
     */
    FALSE("FALSE", "失效");

    /**
     * code
     */
    private String code;

    /**
     * 描述
     */
    private String desc;
}
