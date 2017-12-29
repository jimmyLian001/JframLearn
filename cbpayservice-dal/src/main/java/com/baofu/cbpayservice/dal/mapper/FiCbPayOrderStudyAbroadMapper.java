package com.baofu.cbpayservice.dal.mapper;

import com.baofu.cbpayservice.dal.models.FiCbPayOrderStudyAbroadDo;

import java.util.List;

public interface FiCbPayOrderStudyAbroadMapper {
    /**
     * 批量添加留学信息
     *
     * @param fiCbPayOrderStudyAbroadDoList 留学信息集合
     */
    int batchInsert(List<FiCbPayOrderStudyAbroadDo> fiCbPayOrderStudyAbroadDoList);
}