package com.baofu.cbpayservice.manager;

import com.baofu.cbpayservice.dal.models.FiCbPayOrderItemDo;
import com.baofu.cbpayservice.dal.models.FiCbPayOrderItemInfoDo;
import com.baofu.cbpayservice.dal.models.FiCbPayOrderItemsInfoDo;

import java.util.List;

/**
 * 跨境订单商品信息操作
 * <p>
 * 1、批量添加跨境订单商品信息
 * </p>
 * User: 香克斯 Date:2016/10/28 ProjectName: cbpayservice Version: 1.0
 */
public interface OrderItemManager {

    /**
     * 批量添加跨境订单商品信息
     *
     * @param fiCbPayOrderItemDoList 跨境订单商品信息集合
     */
    void addOrderItem(List<FiCbPayOrderItemDo> fiCbPayOrderItemDoList);

    /**
     * 根据宝付订单号查询网关订单商品信息
     *
     * @param orderId 宝付订单号
     * @return 返回网关订单商品信息
     */
    List<FiCbPayOrderItemInfoDo> queryItemInfo(Long orderId);

    /**
     * 批量添加跨境订单商品信息
     *
     * @param fiCbPayOrderItemDoList 跨境订单商品信息集合
     */
    void addBatchOrderItem(List<FiCbPayOrderItemDo> fiCbPayOrderItemDoList);

    /**
     * 根据宝付订单号查询订单商品信息  wdj
     *
     * @param orderId 宝付订单号
     * @return 商品组合信息
     */
    FiCbPayOrderItemsInfoDo queryItemsInfo(Long orderId);

}
