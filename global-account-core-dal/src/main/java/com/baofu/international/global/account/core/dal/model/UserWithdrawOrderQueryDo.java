package com.baofu.international.global.account.core.dal.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(callSuper = true)
public class UserWithdrawOrderQueryDo extends UserWithdrawOrderDo {

    /**
     * 商品名称,多个商品名称以，分开
     */
    private String commodityName;

    /**
     * 商品数量,多个商品数量以，分开
     */
    private String commodityAmount;

    /**
     * 商品价格,多个商品数量以，分开
     */
    private String commodityPrice;
}