package com.baofu.international.global.account.core.dal.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Setter
@Getter
@ToString(callSuper = true)
public class ExternalOrderItemDo extends BaseDo {

    /**
     * 宝付订单号
     */
    private Long orderId;

    /**
     * 商品名称
     */
    private String commodityName;

    /**
     * 商品数量
     */
    private Integer commodityAmount;

    /**
     * 商品价格
     */
    private BigDecimal commodityPrice;

    /**
     *
     */
    private Integer state;
}