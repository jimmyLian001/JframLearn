package com.baofu.cbpayservice.facade.models.res;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 费率查询响应信息
 * User: wht Date:2017/7/23 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@Setter
@ToString
public class ExchangeRateQueryRespDto implements Serializable {

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

}
