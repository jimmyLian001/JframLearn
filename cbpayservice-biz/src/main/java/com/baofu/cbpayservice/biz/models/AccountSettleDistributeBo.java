package com.baofu.cbpayservice.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 商户转账分发参数
 * User: feng_jiang
 * Date:2017/9/9
 * ProjectName: cbPayService
 * Version: 1.0
 */
@Getter
@Setter
@ToString
public class AccountSettleDistributeBo {
    /**
     * 币种
     */
    private String ccy;
    /**
     * 商户编号
     */
    private Long memberId;

    /**
     * 订单号（结汇订单号）
     */
    private Long orderID;

    /**
     * 结汇汇率
     */
    private BigDecimal settleRate;
}
