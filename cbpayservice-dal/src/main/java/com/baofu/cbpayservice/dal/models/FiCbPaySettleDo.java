package com.baofu.cbpayservice.dal.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
@ToString
public class FiCbPaySettleDo extends BaseDo {

    /**
     * 商户编号
     */
    private Long memberId;

    /**
     * 宝付内部订单号，数据生成时产生
     */
    private Long orderId;

    /**
     * 宝付渠道编号
     */
    private Long channelId;

    /**
     * 商户外币汇入编号,由银行通知时填入
     */
    private String incomeNo;

    /**
     * 汇入金额
     */
    private BigDecimal incomeAmt;


    /**
     * 真实汇入金额
     */
    private BigDecimal realIncomeAmt;

    /**
     * 汇入币种
     */
    private String incomeCcy;

    /**
     * 汇入时间
     */
    private Date incomeAt;

    /**
     * 解付时间
     */
    private Date relieveAt;

    /**
     * 汇入状态：1-待解付，2-已解付
     */
    private Integer incomeStatus;

    /**
     * 前汇入状态：1-待解付，2-已解付
     */
    private Integer oldIncomeStatus;

    /**
     * 是否需要解付：2-否 、1-是
     */
    private Integer isIncome;

    /**
     * 结算金额，用外币结汇完成之后的人民币金额
     */
    private BigDecimal settleAmt;

    /**
     * 结算币种，用外币结汇完成之后的人民币币种
     */
    private String settleCcy;

    /**
     * 结算汇率，用外币结汇完成之后的人民币汇率
     */
    private BigDecimal settleRate;

    /**
     * 结算状态，1-待结汇，2-结汇处理中，3-结汇完成，4-结汇失败
     */
    private Integer settleStatus;

    /**
     * 前结算状态，1-待结汇，2-结汇处理中，3-结汇完成，4-结汇失败
     */
    private Integer oldSettleStatus;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 错误描述
     */
    private String errorMsg;

    /**
     * 汇款人账户
     */
    private String incomeAccountNo;

    /**
     * 汇款人名称
     */
    private String incomeAccountName;

    /**
     * 汇款人地址
     */
    private String incomeAddress;

    /**
     * 汇入手续费
     */
    private BigDecimal incomeFee;

    /**
     * 结算申请时间
     */
    private Date settleAt;

    /**
     * 结算完成时间
     */
    private Date settleCompleteAt;

    /**
     * 清算状态，1-未清算，2-清算失败，3-清算成功
     */
    private int settleFlag;

    /**
     * 清算手续费，指订单的人民币手续费
     */
    private BigDecimal settleFee;

    /**
     * 文件状态：1-待上传，2-上传成功，3-上传失败
     */
    private int fileStatus;

    /**
     * 银行名称
     */
    private String bankName;


    /**
     * 文件批次号
     */
    private Long fileBatchNo;

    /**
     * 商户结汇金额
     */
    private BigDecimal memberSettleAmt;

    /**
     * 商户结算汇率
     */
    private BigDecimal memberSettleRate;

    /**
     * 宝付损益
     */
    private BigDecimal profitAndLoss;

    /**
     * 清算收汇到账通知清算审核
     */
    private Integer cmAuditState;
}