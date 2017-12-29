package com.baofu.cbpayservice.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 结汇申请回执请求对象
 * <p>
 * </p>
 * User: 康志光 Date: 2017/4/14 ProjectName: cbpay-customs-service Version: 1.0
 */
@Setter
@Getter
@ToString
public class CbPaySettleBo {

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
     * 汇入手续费
     */
    private BigDecimal IncomeFee;

    /**
     * 结汇汇率
     */
    private BigDecimal settleRate;

    /**
     * 结汇币种
     */
    private String settleCcy;

    /**
     * 结汇金额
     */
    private BigDecimal settleAmt;

    /**
     * 清算申请时间
     */
    private Date settleAt;

    /**
     * 清算完成时间
     */
    private Date settleCompleteAt;

    /**
     * 清算标识
     */
    private int settleFlag;

    /**
     * 清算手续费
     */
    private BigDecimal settleFee;


    /**
     * 结算状态
     */
    private Integer settleStatus;

    /**
     * 解付状态
     */
    private Integer incomeStatus;

    /**
     * 前结算状态
     */
    private Integer oldSettleStatus;

    /**
     * 前解付状态
     */
    private Integer oldIncomeStatus;

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
     * 文件状态：1-待上传，2-上传成功，3-上传失败
     */
    private int fileStatus;
}
