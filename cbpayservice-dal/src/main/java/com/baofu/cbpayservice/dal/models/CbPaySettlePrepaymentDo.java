package com.baofu.cbpayservice.dal.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 结汇垫资Do对象
 * <p>
 * </p>
 * User: 康志光 Date: 2017/8/17 ProjectName: cbpay-customs-service Version: 1.0
 */
@Getter
@Setter
@ToString
public class CbPaySettlePrepaymentDo extends BaseDo {

    /**
     * 商户号
     */
    private Long memberId;

    /**
     * 垫资申请编号
     */
    private Long applyId;

    /**
     * 银行汇入汇款编号
     */
    private String incomeNo;

    /**
     * 结汇金额
     */
    private BigDecimal incomeAmt;

    /**
     * 结汇币种
     */
    private String incomeCcy;

    /**
     * 垫资结汇汇率
     */
    private BigDecimal preSettleRate;

    /**
     * 垫资结汇金额
     */
    private BigDecimal preSettleAmt;

    /**
     * 垫资结汇手续费
     */
    private BigDecimal preSettleFee;

    /**
     * 结算账户
     */
    private String settleAcc;

    /**
     * 清算垫资审核
     */
    private Integer cmStatus;

    /**
     * 前置垫资状态
     */
    private Integer oldPreStatus;

    /**
     * 垫资状态
     */
    private Integer preStatus;

    /**
     * 结汇标识(垫资标志： 0-自动垫资   1-手工垫资)
     */
    private Integer autoFlag;

}
