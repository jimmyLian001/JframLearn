package com.baofu.cbpayservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 渠道状态
 * User: wanght Date:2016/11/11 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@AllArgsConstructor
public enum ChannelStatus {

    /**
     * 初始化
     */
    INIT("INIT", "初始化"),

    /**
     * 银行处理中
     */
    PROCESSING("PROCESSING", "银行处理中"),

    /**
     * 返回成功
     */
    TRUE("TRUE", "返回成功"),

    /**
     * 返回失败
     */
    FALSE("FALSE", "返回失败");

    /**
     * code
     */
    private String code;

    /**
     * 描述
     */
    private String desc;
}
