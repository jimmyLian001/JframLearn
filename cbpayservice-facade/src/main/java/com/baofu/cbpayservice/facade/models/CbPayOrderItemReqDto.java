package com.baofu.cbpayservice.facade.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商品信息参数
 * User: 香克斯 Date:2016/10/28 ProjectName: cbpayservice Version: 1.0
 */
@Setter
@Getter
@ToString
public class CbPayOrderItemReqDto implements Serializable {

    /**
     * 商品名称
     */
    private String commodityName;

    /**
     * 商品单价
     */
    private BigDecimal commodityPrice;

    /**
     * 商品数量
     */
    private Integer commodityAmount;
}
