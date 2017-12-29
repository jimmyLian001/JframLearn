package com.baofu.cbpayservice.manager.impl;

import com.baofu.cbpayservice.dal.mapper.FiCbpayOrderLogisticsMapper;
import com.baofu.cbpayservice.dal.models.FiCbpayOrderLogisticsDo;
import com.baofu.cbpayservice.manager.CbpayLogisticsManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 跨境订单物流信息服务
 * <p>
 * 1、根据宝付订单号查询物流信息
 * </p>
 * User: wdj Date:2017/04/27 ProjectName: asias-icpaygate Version: 1.0
 */
@Slf4j
@Repository
public class CbpayLogisticsManagerImpl implements CbpayLogisticsManager {

    /**
     * 跨境订单物流Mapper
     */
    @Autowired
    private FiCbpayOrderLogisticsMapper fiCbpayOrderLogisticsMapper;

    /**
     * 根据宝付订单号查询订单物流信息
     *
     * @param orderId 宝付订单号
     * @return 返回查询结果
     */
    @Override
    public FiCbpayOrderLogisticsDo queryOrderLogisticsByOrderId(Long orderId) {
        return fiCbpayOrderLogisticsMapper.queryOrderLogisticsByOrderId(orderId);
    }
}
