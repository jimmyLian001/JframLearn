package com.baofu.cbpayservice.dal.mapper;

import com.baofu.cbpayservice.dal.models.FiCbPayOrderTourismDo;

import java.util.List;

public interface FiCbPayOrderTourismMapper {
    /**
     * 批量添加旅游信息
     *
     * @param fiCbPayOrderTourismDoList 旅游信息集合
     */
    int batchInsert(List<FiCbPayOrderTourismDo> fiCbPayOrderTourismDoList);
}