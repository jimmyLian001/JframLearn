package com.baofu.cbpayservice.facade.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * User: yangjian  Date: 2017-05-17 ProjectName:  Version: 1.0
 */
@Getter
@Setter
@ToString
public class ExchangeRateResultDto implements Serializable {

    private static final long serialVersionUID = -1814705856490402500L;

    private String sourceCurrency;

    private String targetCurrency;

    private BigDecimal buyRateOfCcy;

    private BigDecimal sellRateOfCcy;

    private BigDecimal buyRateOfCash;

    private BigDecimal sellRateOfCash;
    /**
     * add by feng_jiang 2017/08/11
     */
    private Date updateDate;
}
