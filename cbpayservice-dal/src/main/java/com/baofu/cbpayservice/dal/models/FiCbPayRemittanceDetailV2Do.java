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
public class FiCbPayRemittanceDetailV2Do {

    /**
     * 宝付订单号
     */
    private Long orderId;

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
     * 商品信息
     */
    private String commodityInfo;

    /**
     * 行业类型
     */
    private String careerType;

    /**
     * 付款人类型
     */
    private int payeeIdType;

    /**
     * 反洗钱状态
     */
    private Integer amlStatus;

}
