package com.baofu.cbpayservice.dal.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 跨境风控订单表  wdj
 */
@Getter
@Setter
@ToString
public class CbPayRiskOrderDo extends BaseDo {

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
    private BigDecimal orderAmt;

    /**
     * 订单币种
     */
    private String orderCcy;

    /**
     * 风险等级：1-低风险；2-中风险；3-高风险
     */
    private Integer riskLevel;

    /**
     * 伪造订单标识：0-真实订单；1-伪造订单  2：待人工审核
     */
    private Integer fakeFlag;

    /**
     * 运单订单标识：0-真实运单；1-伪造运单；2-无运单信息  3.待人工审核
     */
    private Integer wayBillFlag;

    /**
     * 风控规则描述
     */
    private String riskRule;

    /**
     * 人工处理状态：0-待处理；1-伪造订单；2-真实订单
     */
    private Integer artifiStatus;

    /**
     * 订单交易时间
     */
    private Date memberTransDate;

    /**
     * 备注
     */
    private String remarks;


    /**
     * 文件批次号
     */
    private Long batchFileId;

    /**
     * 汇款
     */
    private String batchNo;


    /**
     * 订单类型   1：购付汇   2 结汇
     */
    private int orderType;


}
