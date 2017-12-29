package com.baofu.cbpayservice.manager.impl;

import com.baofu.cbpayservice.dal.mapper.FiCbPayRiskOrderMapper;
import com.baofu.cbpayservice.dal.models.CbPayOrderRiskControlDo;
import com.baofu.cbpayservice.dal.models.CbPayRiskOrderDo;
import com.baofu.cbpayservice.manager.CbPayRiskOrderManager;
import com.system.commons.utils.ParamValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 风险订单信息Manager
 * <p>
 * 1、跨境风险订单新增
 * </p>
 * User: wdj Date:2017/04/27 ProjectName: cbpay-service Version: 1.0
 */
@Repository
public class CbPayRiskOrderManagerImpl implements CbPayRiskOrderManager {

    /**
     * 跨境订单信息服务
     */
    @Autowired
    private FiCbPayRiskOrderMapper fiCbPayRiskOrderMapper;

    /**
     * 跨境风险订单新增
     *
     * @param cbPayRiskOrderDo 风险订单信息
     */
    @Override
    public void addRiskOrder(CbPayRiskOrderDo cbPayRiskOrderDo) {
        ParamValidate.checkUpdate(fiCbPayRiskOrderMapper.insertRiskOrder(cbPayRiskOrderDo), "跨境风险订单新增异常");
    }

    /**
     * 人工审核修改订单信息
     *
     * @param cbPayOrderRiskControlDo 订单信息
     */
    @Override
    public void modifyRiskControlOrder(CbPayOrderRiskControlDo cbPayOrderRiskControlDo) {
        ParamValidate.checkUpdate(fiCbPayRiskOrderMapper.updateRiskOrderByOrderId(cbPayOrderRiskControlDo), "更新风控订单失败");
    }
}
