package com.baofu.cbpayservice.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 结汇垫资BIZ对象
 * <p>
 * </p>
 * User: 康志光 Date: 2017/8/17 ProjectName: cbpay-customs-service Version: 1.0
 */
@Getter
@Setter
@ToString
public class CbPaySettlePrepaymentBo {

    /**
     * 商户号
     */
    private Long memberId;

    /**
     * 商户名称
     */
    private String memberName;

    /**
     * 垫资申请编号
     */
    private Long applyId;

    /**
     * 银行汇入汇款编号
     */
    private String incomeNo;

    /**
     * 汇入金额
     */
    private BigDecimal incomeAmt;

    /**
     * 汇入币种
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
     * 垫资状态
     */
    private Integer preStatus;

    /**
     * 垫资备注
     */
    private String remarks;

    /**
     * 创建人
     */
    private String createBy;

}
