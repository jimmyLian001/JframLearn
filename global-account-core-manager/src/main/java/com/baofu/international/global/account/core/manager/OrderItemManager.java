package com.baofu.international.global.account.core.manager;

import com.baofu.international.global.account.core.dal.model.WithdrawOrderItemDo;

import java.util.List;

/**
 * 描述：提现订单商品信息操作
 * <p>
 * 1、批量添加提现订单商品信息
 * </p>
 * User: feng_jiang Date:2017/11/7 ProjectName: globalaccount-core Version: 1.0
 */
public interface OrderItemManager {

    /**
     * 批量添加提现订单商品信息
     *
     * @param withdrawOrderItemDoList 提现订单商品信息集合
     */
    void addBatchOrderItem(List<WithdrawOrderItemDo> withdrawOrderItemDoList);

}
