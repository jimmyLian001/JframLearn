package com.baofu.cbpayservice.dal.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 跨境人民币汇款明细信息
 * User: wanght Date:2017/04/01 ProjectName: cbpay-service Version: 1.0
 */
@Setter
@Getter
@ToString
public class FiCbPayEmailDetailDo {

    /**
     * 宝付订单号
     */
    private String memberTransId;

    /**
     * 交易时间
     */
    private Date transTime;

    /**
     * 交易金额
     */
    private BigDecimal transMoney;

    /**
     * 付款人名称
     */
    private String idHolder;

    /**
     * 付款人证件号码，加密
     */
    private String idCardNo;

    /**
     * 付款人银行卡号，加密
     */
    private String bankCardNo;

    /**
     * 商品名称
     */
    private String commodityName;

    /**
     * 商品价格
     */
    private String commodityPrice;

    /**
     * 商品数量
     */
    private String commodityAmount;

    /**
     * 交易币种
     */
    private String transCcy;

    /**
     * 手机号
     */
    private String mobile;
}
