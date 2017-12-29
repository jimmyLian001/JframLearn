package com.baofu.cbpayservice.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 返回汇率查询结果
 * <p>
 * User: 不良人 Date:2017/1/5 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@Setter
@ToString
public class ExchangeRateQueryBo {

    /**
     * 源币种
     */
    private String sourceCurrency;

    /**
     * 目标币种
     */
    private String targetCurrency;

    /**
     * 现汇买入价（结汇汇率）
     */
    private BigDecimal buyRateOfCcy;

    /**
     * 现汇卖出价（购汇汇率）
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
     * 数据更新时间
     */
    private Date updateDate;

    /**
     * 垫资现汇买入价（垫资结汇汇率）
     */
    private BigDecimal buyAdvanceRateOfCcy;
}
