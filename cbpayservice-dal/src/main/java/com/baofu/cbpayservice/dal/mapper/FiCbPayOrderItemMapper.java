package com.baofu.cbpayservice.dal.mapper;


import com.baofu.cbpayservice.dal.models.FiCbPayOrderItemDo;
import com.baofu.cbpayservice.dal.models.FiCbPayOrderItemInfoDo;
import com.baofu.cbpayservice.dal.models.FiCbPayOrderItemsInfoDo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 网关订单商品信息相关操作数据服务
 * <p>
 * 1、添加网关订单商品信息
 * 2、根据宝付订单号查询网关订单商品信息
 * </p>
 * User: 香克斯 Date:2016/9/23 ProjectName: cbpayservice Version: 1.0
 */
public interface FiCbPayOrderItemMapper {

    /**
     * 添加网关订单商品信息
     *
     * @param payOrderItem 网关订单商品信息
     * @return 返回受影响行数
     */
    int insert(FiCbPayOrderItemDo payOrderItem);

    /**
     * 根据宝付订单号查询网关订单商品信息
     *
     * @param orderId 宝付订单号
     * @return 返回网关订单商品信息
     */
    List<FiCbPayOrderItemDo> select(Long orderId);

    /**
     * 根据宝付订单号查询网关订单商品信息
     *
     * @param orderId 宝付订单号
     * @return 返回网关订单商品信息
     */
    List<FiCbPayOrderItemInfoDo> queryItemInfo(Long orderId);

    /**
     * 批量添加网关订单商品信息
     *
     * @param fiCbPayOrderItemDoList 网关订单商品信息
     * @return 返回受影响行数
     */
    int batchInsert(List<FiCbPayOrderItemDo> fiCbPayOrderItemDoList);

    /**
     * 根据宝付订单号查询订单商品信息   wdj  0503
     *
     * @param orderId 宝付订单号
     * @return FiCbPayOrderItemsInfoDo
     */
    FiCbPayOrderItemsInfoDo queryItemsInfo(@Param("orderId") Long orderId);

}