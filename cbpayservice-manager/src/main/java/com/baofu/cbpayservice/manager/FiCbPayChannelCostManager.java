package com.baofu.cbpayservice.manager;

import com.baofu.cbpayservice.dal.models.FiCbPayChannelCostDo;

/**
 * <p>
 * 1、跨境人民渠道成本新增
 * </p>
 * User: wanght Date:2016/11/30 ProjectName: asias-icpaygate Version: 1.0
 */
public interface FiCbPayChannelCostManager {

    /**
     * 跨境人民渠道成本新增
     *
     * @param fiCbPayChannelCostDo 渠道成本参数信息
     */
    void addChannelCost(FiCbPayChannelCostDo fiCbPayChannelCostDo);
}
