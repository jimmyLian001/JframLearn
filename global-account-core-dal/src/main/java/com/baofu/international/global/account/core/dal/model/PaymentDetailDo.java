package com.baofu.international.global.account.core.dal.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Setter
@Getter
@ToString(callSuper = true)
public class PaymentDetailDo extends BaseDo {
    /**
     * 明细编号
     */
    private Long detailId;

    /**
     * 用户号
     */
    private Long userNo;

    /**
     * 渠道方虚拟账户
     */
    private String walletId;

    /**
     * 渠道返回流水号
     */
    private String bankRespId;

    /**
     * 金额
     */
    private BigDecimal orderAmt;

    /**
     * 币种
     */
    private String orderCcy;

    /**
     * 业务类型：1-海外收款
     */
    private int businessType;

    /**
     * 余额方向:1-收入；2-支出
     */
    private int balanceDirection;

    /**
     * 账户编号
     */
    private Long accountNo;
}