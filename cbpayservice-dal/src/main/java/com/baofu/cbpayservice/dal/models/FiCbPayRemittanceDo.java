package com.baofu.cbpayservice.dal.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 跨境人民币汇款信息
 * User: wanght Date:2016/11/10 ProjectName: cbpay-service Version: 1.0
 */
@Setter
@Getter
@ToString(callSuper = true)
public class FiCbPayRemittanceDo extends BaseDo {

    /**
     * 汇款批次号
     */
    private String batchNo;

    /**
     * 商户编号
     */
    private Long memberNo;

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
     * 商户开通时填写SWIFT_CODE
     */
    private String swiftCode;

    /**
     * 汇款订单类型：0:代理跨境批次，1:跨境批次
     */
    private String orderType;

    /**
     * 订单发起类型：SYSTEM:系统自动,CASH:手动提现
     */
    private String transType;

    /**
     * 汇款渠道
     */
    private Long channelId;

    /**
     * 审核状态：INIT:初始，TRUE:审核通过，FALSE:审核失败
     */
    private String auditStatus;

    /**
     * 渠道状态：INIT:初始，TRUE:返回成功，FALSE：返回失败
     */
    private String channelStatus;

    /**
     * 汇款账户
     */
    private String bankCardNo;

    /**
     * 审核时间
     */
    private Date auditDate;

    /**
     * 审核人员
     */
    private String auditBy;

    /**
     * 渠道返回时间
     */
    private Date channelDate;

    /**
     * 系统金额
     */
    private BigDecimal systemMoney;

    /**
     * 系统币种
     */
    private String systemCcy;

    /**
     * 银行实际汇率
     */
    private BigDecimal realRate;

    /**
     * 系统汇率
     */
    private BigDecimal systemRate;

    /**
     * 申请状态：INIT:初始，TRUE:申请成功，FALSE：申请失败
     */
    private String applyStatus;

    /**
     * 结算状态：INIT:初始，TRUE:结算成功，FALSE：结算失败
     */
    private String cmStatus;

    /**
     * 购汇状态 0:初始化，1:购汇成功，2 : 购汇中，3:购汇失败
     */
    private Integer purchaseStatus;

    /**
     * 审核动作的之前状态
     */
    private String beforeAuditStatus;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 业务流水号
     */
    private String businessNo;

    /**
     * 银行到账手续费
     */
    private BigDecimal bankFee;

    /**
     * 银行到账手续费审核状态
     */
    private int feeStatus;

    /**
     * 凭证dfs文件id
     */
    private Long receiptId;

    /**
     * 购汇成功时间
     */
    private Date exchangeSuccDate;

    /**
     * 付汇成功时间
     */
    private Date remitSuccDate;

    /**
     * 银行受理批次号
     */
    private String bankBatchNo;

    /**
     * 银行实际扣款金额
     */
    private BigDecimal realMoney;

    /**
     * 购汇方式
     */
    private Integer exchangeType;

    /**
     * 购汇成功后，付汇申请金额
     */
    private BigDecimal remitMoney;
}