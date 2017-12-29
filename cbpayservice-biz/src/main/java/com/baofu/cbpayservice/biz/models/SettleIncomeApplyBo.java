package com.baofu.cbpayservice.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 外汇汇入申请参数
 * <p>
 * User: 不良人 Date:2017/4/15 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@Setter
@ToString
public class SettleIncomeApplyBo implements Serializable {

    /**
     * 商户编号
     */
    private Long memberId;

    /**
     * 终端号
     */
    private Integer terminalId;

    /**
     * 商户外币汇入编号,由商户填入（TT编号）
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
     * 汇入账户（宝付备付金账户）
     */
    private String incomeAccount;

    /**
     * 汇款凭证dfs文件Id
     */
    private Long paymentFileId;

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
     * 收款行
     */
    private String incomeBankName;

    /**
     * 通知地址
     */
    private String notifyUrl;

    /**
     * 结汇订单明细文件ID
     */
    private Long settleDFSId;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 商户名称
     */
    private String memberName;
}
