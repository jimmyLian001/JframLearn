package com.baofu.cbpayservice.manager.impl;

import com.baofu.cbpayservice.dal.mapper.FiCbPayChannelCostMapper;
import com.baofu.cbpayservice.dal.models.FiCbPayChannelCostDo;
import com.baofu.cbpayservice.manager.FiCbPayChannelCostManager;
import com.system.commons.utils.ParamValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 渠道成本操作Manager服务实现
 * <p>
 * 1、新增渠道成本
 * </p>
 * User: wanght Date:2016/11/30 ProjectName: asias-icpservice Version: 1.0
 */
@Repository
public class FiCbPayChannelCostManagerImpl implements FiCbPayChannelCostManager {

    /**
     * 渠道成本操作服务
     */
    @Autowired
    private FiCbPayChannelCostMapper fiCbPayChannelCostMapper;

    /**
     * 新增渠道成本
     *
     * @param fiCbPayChannelCostDo 成本信息
     */
    @Override
    public void addChannelCost(FiCbPayChannelCostDo fiCbPayChannelCostDo) {

        ParamValidate.checkUpdate(fiCbPayChannelCostMapper.insert(fiCbPayChannelCostDo), "跨境人民渠道成本新增异常");
    }
}
