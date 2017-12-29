package com.baofu.cbpayservice.dal.mapper;

import com.baofu.cbpayservice.dal.models.FiCbPayOrderTicketsDo;

import java.util.List;

public interface FiCbPayOrderTicketsMapper {

    /**
     * 批量添加航旅信息
     *
     * @param fiCbPayOrderTicketsDoList 航旅信息集合
     */
    int batchInsert(List<FiCbPayOrderTicketsDo> fiCbPayOrderTicketsDoList);

}