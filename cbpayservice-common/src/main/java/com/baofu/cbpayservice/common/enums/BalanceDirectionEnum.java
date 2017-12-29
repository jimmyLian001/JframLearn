package com.baofu.cbpayservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * description:收款账户收支明细余额方向
 * <p/>
 * Created by liy on 2017/9/13
 */
@Getter
@AllArgsConstructor
public enum BalanceDirectionEnum {

    /**
     * 1-收入
     */
    INCOME(1, "收入"),

    /**
     * 2-支出
     */
    EXPENDITURE(2, "支出"),;

    /**
     * code
     */
    private int code;

    /**
     * 描述
     */
    private String desc;
}
