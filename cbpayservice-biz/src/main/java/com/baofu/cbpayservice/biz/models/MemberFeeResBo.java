package com.baofu.cbpayservice.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * User: yangjian  Date: 2017-05-25 ProjectName:cbpay-service  Version: 1.0
 */
@Getter
@Setter
@ToString
public class MemberFeeResBo {

    /**
     * 计费规则
     */
    private String feeRuleId;

    /**
     * 计费策略ID
     */
    private String feeStrategyId;

    /**
     * 费用
     */
    private BigDecimal feeAmount;

    /**
     * 计费模式ID
     */
    private int feeModelId;

    /**
     * 费率
     */
    private BigDecimal feeRate;

    /**
     * 计费作用方
     */
    private int feeAccId;

    /**
     * 最小目标
     */
    private BigDecimal minTarget;

    /**
     * 最大目标
     */
    private BigDecimal maxTarget;

    /**
     * 最小费用
     */
    private BigDecimal minFee;

    /**
     * 最大费用
     */
    private BigDecimal maxFee;

    /**
     * 基本费用
     */
    private BigDecimal baseFee;
}

