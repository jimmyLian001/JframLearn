package com.baofu.international.global.account.core.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 描述：计算手续费返回结果
 * User: feng_jiang Date:2017/11/5 ProjectName: globalaccount-core Version: 1.0
 */
@Setter
@Getter
@ToString
public class WithdrawCashFeeRespBo implements Serializable {

    /**
     * 提现金额
     */
    private BigDecimal withdrawAmt;

    /**
     * 提现币种
     */
    private String withdrawCcy;

    /**
     * 手续费金额
     */
    private BigDecimal withdrawFee;

    /**
     * 提现费率
     */
    private BigDecimal transferRate;

    /**
     * 到账金额
     */
    private BigDecimal destAmt;

    /**
     * 账户余额
     */
    private BigDecimal balanceAmt;

    /**
     * 提现账户
     */
    private Long accountNo;

    /**
     * 店铺编号
     */
    private String storeNo;
}
