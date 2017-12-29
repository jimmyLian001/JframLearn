package com.baofu.cbpayservice.manager;


import com.baofu.cbpayservice.dal.models.FiCbPayOrderAdditionDo;

import java.util.List;

/**
 * 订单附加信息Manager服务
 * <p>
 * 1、根据宝付订单号查询网关订单附加信息
 * 2、新增数据至网关订单附加信息表
 * </p>
 * User: 香克斯 Date:2016/9/24 ProjectName: cbpayservice Version: 1.0
 */
public interface OrderAdditionManager {

    /**
     * 根据宝付订单号查询网关订单附加信息
     *
     * @param orderId 宝付订单号
     * @return 返回网关订单附加信息
     */
    FiCbPayOrderAdditionDo queryOrderAddition(Long orderId);

    /**
     * 新增数据至网关订单附加信息表
     *
     * @param fiCbPayOrderAdditionDo 网关订单附加信息
     */
    void addOrderAddition(FiCbPayOrderAdditionDo fiCbPayOrderAdditionDo);

    /**
     * 批量新增数据至网关订单附加信息表
     *
     * @param fiCbPayOrderAdditionDoList 网关订单附加信息集合
     */
    void addBatchOrderAddition(List<FiCbPayOrderAdditionDo> fiCbPayOrderAdditionDoList);
}
