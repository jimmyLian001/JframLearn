package com.baofu.cbpayservice.facade.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 结汇垫资Dto对象
 * <p>
 * </p>
 * User: 康志光 Date: 2017/8/17 ProjectName: cbpay-customs-service Version: 1.0
 */
@Getter
@Setter
@ToString
public class CbPaySettlePrepaymentDto implements Serializable {

    /**
     * 商户号
     */
    private Long memberId;

    /**
     * 商户名称
     */
    private String memberName;

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
     * 结算账户
     */
    private String settleAcc;

    /**
     * 备注
     */
    private String remarks;

}
