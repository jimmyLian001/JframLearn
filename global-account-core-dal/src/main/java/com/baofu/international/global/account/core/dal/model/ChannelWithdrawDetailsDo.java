package com.baofu.international.global.account.core.dal.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户提现明细Do
 *
 * @author dxy  on 2017/11/21.
 */
@Getter
@Setter
@ToString
public class ChannelWithdrawDetailsDo {

    /**
     * 用户号
     */
    private String userNo;

    /**
     * 商户名称
     */
    private String userName;

    /**
     * 提现金额
     */
    private BigDecimal withdrawAmount;

    /**
     * 提现币种
     */
    private String withdrawCCY;

    /**
     * 渠道
     */
    private String channel;

    /**
     * 宝付费率
     */
    private BigDecimal baoFuFeeRate;

    /**
     * 手续费
     */
    private BigDecimal baoFuFee;

    /**
     * 汇率
     */
    private BigDecimal rate;

    /**
     * 到账金额
     */
    private BigDecimal arriveAmount;

    /**
     * 提现状态　：0-待提现；1-提现处理中，2-提现成功，3-提现失败(宝付转账)
     */
    private String withdrawStatus;

    /**
     * 转账状态
     */
    private String transferStatus;

    /**
     * 汇款流水号
     */
    private String batchNo;

    /**
     * 提现申请号
     */
    private String withdrawId;

    /**
     * 申请时间
     */
    private Date applyTime;

    /**
     * 完成时间
     */
    private Date finishTime;
}
