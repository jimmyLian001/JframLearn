package com.baofu.international.global.account.core.dal.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Setter
@Getter
@ToString(callSuper = true)
public class UserWithdrawSumDo extends BaseDo {
    /**
     * 提现批次号
     */
    private Long withdrawBatchId;

    /**
     * 商户编号(目前是固定商户)
     */
    private Long memberId;

    /**
     * 转出账户号
     */
    private String sourceAccNo;

    /**
     * 目标账户号
     */
    private String destAccNo;

    /**
     * 提现金额
     */
    private BigDecimal withdrawAmt = BigDecimal.ZERO;

    /**
     * 提现币种
     */
    private String withdrawCcy;

    /**
     * 提现手续费
     */
    private BigDecimal withdrawFee;

    /**
     * 目标金额
     */
    private BigDecimal destAmt;

    /**
     * 目标币种
     */
    private String destCcy;

    /**
     * 提现汇率
     */
    private BigDecimal withdrawRate;

    /**
     * 实际扣款金额，提现金额 + 提现手续费
     */
    private BigDecimal deductAmt;

    /**
     * 转账状态，0-待转账；1-转账处理中，2-转账成功，3-转账失败
     */
    private Integer transferState;

    /**
     * 海外渠道
     */
    private String channelName;
}