package com.baofu.international.global.account.core.facade.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
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
public class WithdrawalQueryRespDto implements Serializable {
    private static final long serialVersionUID = 5788795300222765477L;


    /**
     * 提现手续费
     */
    private BigDecimal withdrawalFee;

    /**
     * 流水号
     */
    private Long serialNumber;


    /**
     * 提现金额
     */
    private BigDecimal withdrawalMoney;


    /**
     * 结算手续费
     */
    private BigDecimal settleFee;

    /**
     * 成交汇率
     */
    private BigDecimal tradeRate;

    /**
     * 结算金额
     */
    private BigDecimal settleMoney;


    /**
     * 申请时间
     */
    private Date applyTime;


    /**
     * 收款账户
     */
    private String bankCard;


    /**
     * 到账金额
     */
    private BigDecimal sucMoney;


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


}
