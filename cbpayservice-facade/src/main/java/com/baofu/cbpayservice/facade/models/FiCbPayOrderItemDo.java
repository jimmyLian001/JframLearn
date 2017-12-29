package com.baofu.cbpayservice.facade.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 跨境人民币网关商品信息表
 * User: 香克斯 Date:2016/9/23 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@Setter
@ToString
public class FiCbPayOrderItemDo implements Serializable {

    /**
     * 宝付订单号
     */
    private Long orderId;

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