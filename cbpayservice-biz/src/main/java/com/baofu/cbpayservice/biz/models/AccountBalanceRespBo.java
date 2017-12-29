package com.baofu.cbpayservice.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @author 莫小阳  on 2017/10/20.
 */

@Getter
@Setter
@ToString
public class AccountBalanceRespBo {

    public AccountBalanceRespBo() {
    }

    public AccountBalanceRespBo(BigDecimal orderAmount, BigDecimal accountAmount) {
        this.orderAmount = orderAmount == null ? BigDecimal.ZERO : orderAmount;
        this.accountAmount = accountAmount == null ? BigDecimal.ZERO : accountAmount;
    }

    /**
     * 剩余订单可用金额
     */
    private BigDecimal orderAmount;


    /**
     * 账户可用余额
     */
    private BigDecimal accountAmount;


}
