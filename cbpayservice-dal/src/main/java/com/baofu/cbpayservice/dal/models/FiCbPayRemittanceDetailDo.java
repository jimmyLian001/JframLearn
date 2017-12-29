package com.baofu.cbpayservice.dal.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 跨境人民币汇款明细信息
 * User: wanght Date:2016/11/10 ProjectName: cbpay-service Version: 1.0
 */
@Setter
@Getter
@ToString(callSuper = true)
public class FiCbPayRemittanceDetailDo extends BaseDo {

    /**
     * 商户编号
     */
    private Long memberId;

    /**
     * 宝付订单号
     */
    private Long orderId;

    /**
     * 交易时间
     */
    private Date transDate;

    /**
     * 付款人名称
     */
    private String paymentName;

    /**
     * 付款人证件类型，默认身份证
     */
    private String paymentIdType;

    /**
     * 付款人证件号码，加密
     */
    private String paymentIdCardNo;

    /**
     * 付款人银行卡号，加密
     */
    private String paymentBankCardNo;

    /**
     * 购汇方式
     */
    private String buySellFlag;

    /**
     * 订单金额
     */
    private BigDecimal orderMoney;

    /**
     * 订单币种
     */
    private String orderCcy;

    /**
     * 商品信息
     */
    private String commodityInfo;

    /**
     * 申请人，商户联系人
     */
    private String applyBy;

    /**
     * 申请人联系方式
     */
    private String applyTel;

    /**
     * 付款类型：A－预付货款，P－货到付款，R－退款，O-其它
     */
    private String payType;

    /**
     * 订单类型
     */
    private String orderAttribute;

    /**
     * 系统金额
     */
    private BigDecimal systemMoney;

    /**
     * 系统币种
     */
    private String systemCcy;

    /**
     * 系统汇率
     */
    private BigDecimal systemRate;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 汇款批次号
     */
    private String remittanceBatchNo;

    /**
     * 请求业务流水号
     */
    private String requestNo;
}
