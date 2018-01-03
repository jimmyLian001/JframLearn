package com.baofu.international.global.account.client.web.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @author 莫小阳 on 2017/11/13.
 */

@Getter
@Setter
@ToString
public class WithdrawalQueryVo {

    /**
     * 流水号
     */
    private String serialNumber;


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
    private String applyTime;


    /**
     * 完成时间
     */
    private String finishTime;

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
     * 提现：前端样式
     */
    private String clazz;


}
