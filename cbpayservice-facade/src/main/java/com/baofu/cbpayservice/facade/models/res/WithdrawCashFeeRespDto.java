package com.baofu.cbpayservice.facade.models.res;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 计算手续费返回结果
 * <p>
 * User: 不良人 Date:2017/9/12 ProjectName: cbpayservice Version: 1.0
 **/
@Setter
@Getter
@ToString
public class WithdrawCashFeeRespDto implements Serializable {

    private static final long serialVersionUID = 5962679522164338102L;

    /**
     * 转账金额
     */
    private BigDecimal transferAmt;

    /**
     * 手续费金额
     */
    private BigDecimal transferFee;

    /**
     * 账户余额
     */
    private BigDecimal balanceAmt;
}
