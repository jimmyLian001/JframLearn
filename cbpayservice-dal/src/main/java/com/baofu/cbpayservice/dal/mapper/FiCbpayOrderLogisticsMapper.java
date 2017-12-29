package com.baofu.cbpayservice.dal.mapper;

import com.baofu.cbpayservice.dal.models.FiCbpayOrderLogisticsDo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FiCbpayOrderLogisticsMapper {

    /**
     * 插入数据库记录
     *
     * @param record 运单信息
     */
    int insert(FiCbpayOrderLogisticsDo record);

    /**
     * @param record 运单信息集合
     */
    int batchInsert(List<FiCbpayOrderLogisticsDo> record);

    /**
     * 根据宝付订单号查询物流信息
     *
     * @param orderId 宝付订单号
     * @return 返回物流信息
     */
    FiCbpayOrderLogisticsDo queryOrderLogisticsByOrderId(@Param("orderId") Long orderId);
}