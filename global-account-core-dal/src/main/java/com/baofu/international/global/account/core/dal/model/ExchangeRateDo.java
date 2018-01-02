package com.baofu.international.global.account.core.dal.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Setter
@Getter
@ToString(callSuper = true)
public class ExchangeRateDo extends BaseDo {
    /**
     * 渠道编号
     */
    private Long channelId;

    /**
     * 币种
     */
    private String ccy;

    /**
     * 现汇买入价
     */
    private BigDecimal bidRateOfCcy;

    /**
     * 现汇卖出价
     */
    private BigDecimal ofrRateOfCcy;

    /**
     * 现钞买入价
     */
    private BigDecimal bidRateOfCash;

    /**
     * 现钞卖出价
     */
    private BigDecimal ofrRateOfCash;

    /**
     * 状态,0:失效,1:生效
     */
    private Integer status;
}