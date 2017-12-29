package com.baofu.cbpayservice.dal.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class FiCbPaySettleApplyDo extends BaseDo {

    /**
     * 宝付内部流水号
     */
    private Long orderId;

    /**
     * 商户编号
     */
    private Long memberId;

    /**
     * 商户外币汇入编号,由商户填入
     */
    private String incomeNo;

    /**
     * 订单金额
     */
    private BigDecimal orderAmt;

    /**
     * 订单币种
     */
    private String orderCcy;

    /**
     * 汇款人名称
     */
    private String incomeAccountName;

    /**
     * 匹配状态，10-未匹配，11-匹配成功，12-待设置商户编号
     */
    private Integer matchingStatus;

    /**
     * 审核之前的状态
     */
    private int beforeMatchingStatus;

    /**
     * 匹配成功单号，结汇表中宝付订单号
     */
    private Long matchingOrderId;

    /**
     * 汇入账户（宝付备付金账户）
     */
    private String incomeAccount;

    /**
     * 汇款凭证dfs文件Id
     */
    private Long paymentFileId;

    /**
     * 收款行
     */
    private String incomeBankName;

    /**
     * 审核之前的状态
     */
    private Integer beforeCmStatus;

    /**
     * 文件批次号
     */
    private Long fileBatchNo;

    /**
     * 汇款人国别
     */
    private String remittanceCountry;

    /**
     * 汇款人帐号
     */
    private String remittanceAcc;

    /**
     * 汇款人名称
     */
    private String remittanceName;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 通知地址
     */
    private String notifyUrl;

    /**
     * 终端编号
     */
    private Integer terminalId;

    /**
     * 汇款流水号
     */
    private String inwardRemittanceId;
}