package com.baofu.cbpayservice.dal.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 上送商品参数
 * User: 白玉京 Date:2017/8/8 ProjectName: cbpaygate Version: 1.0
 */
@Setter
@Getter
@ToString
public class OrderItemBo {

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品单价
     */
    private BigDecimal goodsPrice;

    /**
     * 商品数量
     */
    private Integer goodsNum;

}
