package com.baofu.international.global.account.core.dal.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
@ToString(callSuper = true)
public class UserWithdrawDistributeDo extends BaseDo {
    /**
     * 用户号
     */
    private Long userNo;

    /**
     * 下发批次号
     */
    private Long orderId;

    /**
     * 汇款流水号（汇总批次号）
     */
    private Long withdrawBatchId;

    /**
     * 提现申请编号
     */
    private Long withdrawId;

    /**
     * 账户号
     */
    private Long accountNo;

    /**
     * 银行名称
     */
    private String bankName;

    /**
     * 提现银行卡持有人
     */
    private String bankCardHolder;

    /**
     * 提现银行卡帐号
     */
    private String bankCardNo;

    /**
     * 提现金额
     */
    private BigDecimal withdrawAmt;

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
    private BigDecimal transferRate;

    /**
     * 实际扣款金额，提现金额 + 提现手续费
     */
    private BigDecimal deductAmt;

    /**
     * 归集状态，0-待转账；1-转账处理中，2-转账成功，3-转账失败
     */
    private Integer transferState;

    /**
     * 提现状态，0-待提现；1-提现处理中，2-提现成功，3-提现失败(宝付转账)
     */
    private Integer withdrawState;

    /**
     * 用户结算手续费
     */
    private BigDecimal settleFee;

    /**
     * 提现完成时间
     */
    private Date withdrawAt;

    /**
     * 下发审核状态：0-待审核，1-审核通过，2-审核失败
     */
    private Integer status;
}