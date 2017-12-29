package com.baofu.cbpayservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 清算处理码
 * User: wanght Date:2016/11/30 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@AllArgsConstructor
public enum DealCodeEnums {

    /**
     * 人民币汇款处理第一步
     */
    RMB_REMIT_ONE(130, "人民币汇款处理第一步"),

    /**
     * 人民币汇款处理成功
     */
    RMB_REMIT_TWO(131, "人民币汇款处理成功"),

    /**
     * 人民币汇款处理失败
     */
    RMB_REMIT_THREE(132, "人民币汇款处理失败"),

    /**
     * 外币汇款处理第一步
     */
    FC_REMIT_ONE(146, "外币汇款处理第一步"),

    /**
     * 外币汇款处理成功
     */
    FC_REMIT_TWO(147, "外币汇款处理成功"),

    /**
     * 外币汇款处理失败
     */
    FC_REMIT_THREE(148, "外币汇款处理失败"),


    /**
     * 结汇成功
     */
    SETTLE_CODE(306, "结汇成功"),

    /**
     * 跨境汇款汇率损失
     */
    REMIT_LOSS(142, "跨境汇款汇率损失"),

    /**
     * 跨境汇款汇率收益
     */
    REMIT_PROFIT(143, "跨境汇款汇率收益"),

    /**
     * 结汇汇款汇率损失
     */
    SETTLE_LOSS(144, "结汇汇率损失"),

    /**
     * 结汇汇率收益
     */
    SETTLE_PROFIT(145, "结汇汇率收益"),

    /**
     * 结汇垫资
     */
    SETTLE_PREPAYMENT(204, "结汇垫资"),

    /**
     * 收款账户
     */
    ACCOUNT_PREPAYMENT(205, "收款账户"),;

    /**
     * code
     */
    private Integer code;

    /**
     * 描述
     */
    private String desc;

    /**
     * 汇款第一步
     *
     * @param ccy 币种信息
     * @return 返回dealCode
     */
    public static Integer remitDealCodeOne(String ccy) {

        return CcyEnum.CNY.getKey().equals(ccy) ? RMB_REMIT_ONE.code : FC_REMIT_ONE.code;
    }

    /**
     * 汇款成功
     *
     * @param ccy 币种信息
     * @return 返回dealCode
     */
    public static Integer remitDealCodeTwo(String ccy) {

        return CcyEnum.CNY.getKey().equals(ccy) ? RMB_REMIT_TWO.code : FC_REMIT_TWO.code;
    }

    /**
     * 汇款失败
     *
     * @param ccy 币种信息
     * @return 返回dealCode
     */
    public static Integer remitDealCodeThree(String ccy) {

        return CcyEnum.CNY.getKey().equals(ccy) ? RMB_REMIT_THREE.code : FC_REMIT_THREE.code;
    }
}
