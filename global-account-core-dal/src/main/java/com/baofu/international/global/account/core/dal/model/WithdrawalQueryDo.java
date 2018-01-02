package com.baofu.international.global.account.core.dal.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 交易提现查询
 *
 * @author 莫小阳  on 2017/11/7.
 */
@Getter
@Setter
@ToString
public class WithdrawalQueryDo {

    /**
     * 流水号
     */
    private Long serialNumber;


    /**
     * 提现金额
     */
    private BigDecimal withdrawalMoney;

    /**
     * 提现手续费
     */
    private BigDecimal withdrawalFee;


    /**
     * 成交汇率
     */
    private BigDecimal tradeRate;

    /**
     * 结算金额
     */
    private BigDecimal settleMoney;

    /**
     * 结算手续费
     */
    private BigDecimal settleFee;

    /**
     * 到账金额
     */
    private BigDecimal sucMoney;

    /**
     * 申请时间
     */
    private Date applyTime;


    /**
     * 完成时间
     */
    private Date finishTime;

    /**
     * 状态
     */
    private String state;

    /**
     * 店铺名称
     */
    private String storeName;

    /**
     * 收款账户
     */
    private String bankCard;

    /**
     * 提现状态
     */
    private String transferState;

}
