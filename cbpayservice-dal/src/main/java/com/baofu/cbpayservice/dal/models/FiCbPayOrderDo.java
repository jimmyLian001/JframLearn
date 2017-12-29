package com.baofu.cbpayservice.dal.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 跨境人民币订单信息
 * User: 香克斯 Date:2016/10/25 ProjectName: asias-icpaygate Version: 1.0
 */
@Setter
@Getter
@ToString(callSuper = true)
public class FiCbPayOrderDo extends BaseDo {

    /**
     * 宝付订单号
     */
    private Long orderId;

    /**
     * 商户编号
     */
    private Long memberId;

    /**
     * 商户订单号
     */
    private String memberTransId;

    /**
     * 订单金额
     */
    private BigDecimal orderMoney;

    /**
     * 订单币种
     */
    private String orderCcy;

    /**
     * 交易金额
     */
    private BigDecimal transMoney;

    /**
     * 交易币种
     */
    private String transCcy;

    /**
     * 产品编号
     */
    private Integer productId;

    /**
     * 功能编号
     */
    private Integer functionId;

    /**
     * 支付编号
     */
    private Integer payId;

    /**
     * 渠道编号
     */
    private Integer channelId;

    /**
     * 支付状态：INIT:未支付，TRUE:支付成功，FALSE:支付失败
     */
    private String payStatus;

    /**
     * 订单完成时间
     */
    private Date orderCompleteTime;

    /**
     * 订单汇率
     */
    private BigDecimal transRate;

    /**
     * 终端编号
     */
    private Integer terminalId;

    /**
     * 交易时间
     */
    private Date transTime;

    /**
     * 交易手续费
     */
    private BigDecimal transFee;

    /**
     * 订单类型：B2C:b2c订单，WEIXI:微信订单，ALIPAY:支付宝订单,PROXY-其他
     */
    private String orderType;

    /**
     * 通知状态：INIT:未通知，TRUE:通知成功，FALSE:通知失败
     */
    private String notifyStatus;

    /**
     * 汇款状态：INIT:未汇款，TRUE:已汇款，FALSE:汇款失败
     */
    private String remittanceStatus;

    /**
     * 文件批次号
     */
    private Long batchFileId;

    /**
     * 总署报关状态
     */
    private String adminState;

    /**
     * 业务流水号
     */
    private String businessNo;

    /**
     * 汇款批次号
     */
    private String batchNo;

    /**
     * 确认状态：1-是、2否
     */
    private Integer ackStatus;

    /**
     * 行业类型
     */
    private String careerType;

    /**
     * 反洗钱审核状态
     * 0：初始
     * 1：审核通过
     * 2：审核未通过
     */
    private Integer amlStatus;
}