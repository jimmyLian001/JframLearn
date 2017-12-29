package com.baofu.cbpayservice.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * User: yangjian  Date: 2017-05-23 ProjectName:  Version: 1.0
 */
@Getter
@Setter
@ToString
public class ClearRequestBo {

    /**
     * 会员号
     */
    private Long memberId;

    /**
     * 业务流水号
     */
    private String businessNo;

    /**
     * 交易编号
     */
    private String tradeNo;

    /**
     * 商户订单号
     */
    private String memberTransId;

    /**
     * 交易码
     */
    private Integer dealCode;

    /**
     * 渠道ID
     */
    private Long channelId;

    /**
     * 付款方账号
     */
    private String payerAcctCode;

    /**
     * 收款方账号
     */
    private String payeeAcctCode;
    /**
     * 功能ID
     */
    private Integer functionId;

    /**
     * 订单号
     */
    private Long orderId;

    /**
     * 交易金额
     */
    private BigDecimal dealAmount;

    /**
     * 付款方费用
     */
    private BigDecimal payerFee;

    /**
     * 收款方费用
     */
    private BigDecimal payeeFee;

    /**
     * 费用作用方
     */
    private int feeAcctId;

    /**
     * 产品ID
     */
    private Integer productId;

    /**
     * 是否计费
     */
    private int hasCalculatePrice;

    /**
     * 付款方币种
     */
    private String payerCurrencyCode;

    /**
     * 收款方币种
     */
    private String payeeCurrencyCode;

    /**
     * 支付会员号
     */
    private Long payMemberId;

    /**
     * 成功时间
     */
    private Date successTime;

    /**
     * 是否通知会员
     */
    private boolean synNoticeMa = false;
}
