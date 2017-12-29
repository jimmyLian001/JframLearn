package com.baofu.cbpayservice.manager;

import com.baofu.cbpayservice.dal.models.FiCbpayOrderLogisticsDo;

/**
 * 跨境订单物流信息
 * <p>
 * 1、跨境人民订单新增
 * </p>
 * User: 莫小阳 Date:2017/04/27 ProjectName: asias-icpaygate Version: 1.0
 */
public interface CbpayLogisticsManager {

    /**
     * 根据宝付订单号查询订单物流信息
     *
     * @param orderId 宝付订单号
     * @return 返回物流信息
     */
    FiCbpayOrderLogisticsDo queryOrderLogisticsByOrderId(Long orderId);
}
