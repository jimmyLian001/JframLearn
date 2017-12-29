package com.baofu.cbpayservice.dal.mapper;

import com.baofu.cbpayservice.dal.models.FiCbPayOrderHotelDo;

import java.util.List;

public interface FiCbPayOrderHotelMapper {

    /**
     * 批量添加酒店信息
     *
     * @param fiCbPayOrderHotelDoList 酒店信息集合
     */
    int batchInsert(List<FiCbPayOrderHotelDo> fiCbPayOrderHotelDoList);
}