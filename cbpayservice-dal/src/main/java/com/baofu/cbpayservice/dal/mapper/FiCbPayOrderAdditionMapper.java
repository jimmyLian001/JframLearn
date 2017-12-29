package com.baofu.cbpayservice.dal.mapper;


import com.baofu.cbpayservice.dal.models.FiCbPayOrderAdditionDo;

import java.util.List;

/**
 * 网关订单附加信息服务
 * <p>
 * 1、新增数据至跨境人民币网关订单附加信息表
 * 2、根据宝付订单号查询网关订单附加信息
 * 3、根据商户订单号和商户号查询跨境人民币网关订单信息
 * </p>
 * User: 香克斯 Date:2016/9/23 ProjectName: cbpayservice Version: 1.0
 */
public interface FiCbPayOrderAdditionMapper {

    /**
     * 新增数据至网关订单附加信息表
     *
     * @param icPayOrderAddition 网关订单附加信息
     * @return 返回受影响行数
     */
    int insert(FiCbPayOrderAdditionDo icPayOrderAddition);

    /**
     * 根据宝付订单号查询网关订单附加信息
     *
     * @param orderId 宝付订单号
     * @return 返回网关订单附加信息
     */
    FiCbPayOrderAdditionDo selectOrderAdditionByOrderId(Long orderId);

    /**
     * 批量新增数据至网关订单附加信息表
     *
     * @param fiCbPayOrderAdditionDoList 网关订单附加信息
     * @return 返回受影响行数
     */
    int batchInsert(List<FiCbPayOrderAdditionDo> fiCbPayOrderAdditionDoList);
}