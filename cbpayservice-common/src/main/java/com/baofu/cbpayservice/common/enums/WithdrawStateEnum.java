package com.baofu.cbpayservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * description:宝付转账备付金 提现状态
 * <p/>
 * Created by liy on 2017/9/11
 */
@Getter
@AllArgsConstructor
public enum WithdrawStateEnum {

    /**
     * 0-待提现
     */
    WITHDRAW_WAIT(0, "待提现"),

    /**
     * 1-提现处理中
     */
    WITHDRAW_PROCESS(1, "提现处理中"),

    /**
     * 2-提现成功
     */
    WITHDRAW_SUCCESS(2, "提现成功"),

    /**
     * 3-提现失败
     */
    WITHDRAW_FAIL(3, "提现失败"),;

    /**
     * code
     */
    private Integer code;

    /**
     * 描述
     */
    private String desc;
}
