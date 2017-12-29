package com.baofu.cbpayservice.manager;

import com.baofu.cbpayservice.dal.models.CbPayOrderRiskControlDo;
import com.baofu.cbpayservice.dal.models.CbPayRiskOrderDo;

/**
 * 跨境风控订单Manager
 * <p>
 * 1、跨境人民汇款订单新增
 * </p>
 * User: wanght Date:2016/10/25 ProjectName: asias-icpaygate Version: 1.0
 */
public interface CbPayRiskOrderManager {

    /**
     * 将风险订单保存到数据库中
     *
     * @param cbPayRiskOrderDo 风险订单信息
     */
    void addRiskOrder(CbPayRiskOrderDo cbPayRiskOrderDo);

    /**
     * 人工修改审核结果
     *
     * @param cbPayOrderRiskControlDo 订单信息
     */
    void modifyRiskControlOrder(CbPayOrderRiskControlDo cbPayOrderRiskControlDo);
}
