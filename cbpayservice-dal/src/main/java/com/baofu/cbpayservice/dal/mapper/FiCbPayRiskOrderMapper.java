package com.baofu.cbpayservice.dal.mapper;

import com.baofu.cbpayservice.dal.models.CbPayOrderRiskControlDo;
import com.baofu.cbpayservice.dal.models.CbPayRiskOrderDo;

/**
 * 跨境风险订单
 * <p>
 * 1、新增跨境风险订单信息
 * </p>
 * User: wdj Date:2017/04/27 ProjectName: asias-icpaygate Version: 1.0
 */
public interface FiCbPayRiskOrderMapper {

    /**
     * 新增跨境风险订单信息
     *
     * @param cbPayRiskOrderDo 风险订单信息
     */
    int insertRiskOrder(CbPayRiskOrderDo cbPayRiskOrderDo);

    int updateRiskOrderByOrderId(CbPayOrderRiskControlDo cbPayOrderRiskControlDo);
}
