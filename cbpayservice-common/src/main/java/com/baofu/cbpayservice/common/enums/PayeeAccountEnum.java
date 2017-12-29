package com.baofu.cbpayservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 收款账户状态枚举类
 * User: wanght Date:2017/9/12 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@AllArgsConstructor
public enum PayeeAccountEnum {

    /**
     * 待处理
     */
    WAIT(0, "待处理"),

    /**
     * 处理中
     */
    PROCESSING(1, "处理中"),

    /**
     * 成功
     */
    SUCCESS(2, "成功"),

    /**
     * 失败
     */
    FAIL(3, "失败"),

    /**
     * 关闭
     */
    CLOSED(4, "关闭");

    /**
     * code
     */
    private int code;

    /**
     * 描述
     */
    private String desc;
}
