package com.baofu.cbpayservice.dal.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 跨境人民币网关商品信息表
 * User: wanght Date:2016/9/23 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@Setter
@ToString
public class FiCbPayOrderItemInfoDo {

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