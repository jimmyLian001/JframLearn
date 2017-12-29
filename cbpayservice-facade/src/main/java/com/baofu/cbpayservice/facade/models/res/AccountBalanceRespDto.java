package com.baofu.cbpayservice.facade.models.res;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 账户余额
 * <p>
 *
 * @author 莫小阳  on 2017/10/20.
 */
@Setter
@Getter
@ToString
public class AccountBalanceRespDto implements Serializable {

    private static final long serialVersionUID = -3319899974328276051L;

    /**
     * 剩余订单可用金额
     */
    private BigDecimal orderAmount;


    /**
     * 账户可用余额
     */
    private BigDecimal accountAmount;


}
