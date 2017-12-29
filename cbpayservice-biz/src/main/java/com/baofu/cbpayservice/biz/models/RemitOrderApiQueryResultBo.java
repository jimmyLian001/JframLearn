package com.baofu.cbpayservice.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 跨境汇款结果查询返回参数
 * <p>
 * User: 不良人 Date:2017/9/22 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@Setter
@ToString
public class RemitOrderApiQueryResultBo {

    /**
     * 商户编号
     */
    private Long memberId;

    /**
     * 汇款批次号
     */
    private String batchNo;

    /**
     * 汇款结果：01-成功、02-失败、03-汇款中、04-待汇款
     */
    private String remitResult;

    /**
     * 交易金额
     */
    private BigDecimal transMoney;

    /**
     * 交易币种
     */
    private String transCcy;

    /**
     * 交易手续费
     */
    private BigDecimal transFee;

    /**
     * 系统金额
     */
    private BigDecimal systemMoney;

    /**
     * 系统币种
     */
    private String systemCcy;

    /**
     * 账号
     */
    private String accountNumber;

    /**
     * 账号名称
     */
    private String accountName;

    /**
     * 成交汇率
     */
    private BigDecimal systemRate;

    /**
     * 付汇成功时间
     */
    private Date remitSuccDate;

    /**
     * 失败信息
     */
    private String errorMsg;

    /**
     * 商户申请流水号
     */
    private String memberReqId;

    /**
     * 银行名称
     */
    private String bankName;

    /**
     * 商户终端号
     */
    private Long terminalId;

    /**
     * 购汇状态
     */
    private int purchaseStatus;
}
