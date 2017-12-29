package com.baofu.cbpayservice.dal.mapper;

import com.baofu.cbpayservice.dal.models.FiCbPayChannelCostDo;

/**
 * <p>
 * 1、添加汇款订单渠道成本
 * </p>
 * User: wanght Date:2016/11/30 ProjectName: asias-icpservice Version: 1.0
 */
public interface FiCbPayChannelCostMapper {

    /**
     * 添加汇款订单渠道成本
     *
     * @param fiCbPayChannelCostDo 渠道成本信息参数
     */
    int insert(FiCbPayChannelCostDo fiCbPayChannelCostDo);
}
