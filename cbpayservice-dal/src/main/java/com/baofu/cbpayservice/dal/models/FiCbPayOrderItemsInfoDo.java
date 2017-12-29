package com.baofu.cbpayservice.dal.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 跨境人民币网关商品组合信息表
 * User: wdj Date:2017/05/03 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@Setter
@ToString
public class FiCbPayOrderItemsInfoDo {

    /**
     * 商品名称
     */
    private String commodityName;

    /**
     * 商品数量
     */
    private String commodityAmount;

    /**
     * 商品组合价格
     */
    private String commodityPrice;

}
