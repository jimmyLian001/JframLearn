package com.baofu.cbpayservice.dal.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
public class FiCbPaySettleOrderDo extends BaseDo {

    /**
     * 商户编号
     */
    private Long memberId;

    /**
     * 宝付订单号
     */
    private Long orderId;

    /**
     * 商户订单号
     */
    private String memberTransId;

    /**
     * 商户交易时间
     */
    private Date memberTransDate;

    /**
     * 订单金额
     */
    private BigDecimal orderAmt;

    /**
     * 订单币种，一般是外币
     */
    private String orderCcy;

    /**
     * 收款人证件类型：1-身份证
     */
    private Integer payeeIdType;

    /**
     * 收款人证件号码
     */
    private String payeeIdNo;

    /**
     * 收款人证件姓名
     */
    private String payeeName;

    /**
     * 收款人帐号
     */
    private String payeeAccNo;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 汇总批次号
     */
    private Long batchNo;
    /**
     * 购汇风控标识
     */
    private int risk_flag;

    /**
     * 文件批次号
     */
    private Long fileBatchNo;

    /**
     * TT编号
     */
    private String incomeNo;

    /**
     * 反洗钱状态 0-不通过 2-通过
     */
    private int amlStatus;

    /**
     * 商品信息字符串
     */
    private String commodityInfo;
}