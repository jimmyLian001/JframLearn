package com.baofu.cbpayservice.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 结汇结果通知商户信息
 * User: 香克斯 Date:2017/7/25 ProjectName: cbpayservice Version: 1.0
 */
@Setter
@Getter
@ToString(callSuper = true)
public class SettleNotifyMemberBo extends SettleNotifyBo {

    /**
     * 订单金额
     */
    private BigDecimal orderAmt;

    /**
     * 订单币种
     */
    private String orderCcy;

    /**
     * 原实际汇款金额
     */
    private BigDecimal remitAmt;

    /**
     * 汇款手续费
     */
    private BigDecimal remitFee;

    /**
     * 处理状态
     */
    private int processStatus;

    /**
     * 真实结汇金额(外币)
     */
    private BigDecimal realSettleAmt;

    /**
     * 结汇汇率
     */
    private BigDecimal exchangeRate;

    /**
     * 结汇金额(人民币)
     */
    private BigDecimal exchangeAmt;

    /**
     * 手续费(人民币)
     */
    private BigDecimal settleFee;

    /**
     * 结算余额(人民币)(结汇金额-手续费)
     */
    private BigDecimal settleAmt;

    /**
     * 错误文件名称
     */
    private String errorFileName;
}
