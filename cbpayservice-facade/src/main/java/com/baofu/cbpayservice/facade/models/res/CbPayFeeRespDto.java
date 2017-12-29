package com.baofu.cbpayservice.facade.models.res;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Administrator on 2017/3/24.
 */
@Setter
@Getter
@ToString
public class CbPayFeeRespDto implements Serializable {

    /**
     * 源币种
     */
    private String sourceCurrency;

    /**
     * 目标币种
     */
    private String targetCurrency;

    /**
     * 现汇买入价
     */
    private BigDecimal buyRateOfCcy;

    /**
     * 现汇卖出价
     */
    private BigDecimal sellRateOfCcy;

    /**
     * 现钞买入价
     */
    private BigDecimal buyRateOfCash;

    /**
     * 现钞卖出价
     */
    private BigDecimal sellRateOfCash;


    /**
     * 商户号
     */
    private Integer memberNo;

    /**
     * 交易金额
     */
    private BigDecimal transMoney;

    /**
     * 交易手续费
     */
    private BigDecimal transFee;

    /**
     * 交易总金额
     */
    private BigDecimal transTotalMoney;

    /**
     * 结算金额
     */
    private BigDecimal balanceMoney;

    /**
     * 账户余额  格式化后的账户余额
     */
    private BigDecimal accountMoney;


}
