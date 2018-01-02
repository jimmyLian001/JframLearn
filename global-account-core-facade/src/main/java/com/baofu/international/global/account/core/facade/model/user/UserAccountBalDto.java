package com.baofu.international.global.account.core.facade.model.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

@Setter
@Getter
@ToString(callSuper = true)
public class UserAccountBalDto implements Serializable {

    /**
     * 用户号
     */
    private Long userNo;

    /**
     * 币种信息
     */
    private String ccy;

    /**
     * 渠道编号
     */
    private Long channelId;

    /**
     * 账户余额
     */
    private BigDecimal accountBal;

    /**
     * 可提现金额
     */
    private BigDecimal availableAmt;

    /**
     * 提现中金额
     */
    private BigDecimal withdrawProcessAmt;

    /**
     * 待入账金额
     */
    private BigDecimal waitAmt;
}