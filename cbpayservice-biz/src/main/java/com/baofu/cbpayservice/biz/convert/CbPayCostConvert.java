package com.baofu.cbpayservice.biz.convert;

import com.baofu.cbpayservice.biz.models.ChannelCostCalBo;
import com.baofu.cbpayservice.dal.models.FiCbPayChannelCostDo;
import com.baofu.cbpayservice.dal.models.FiCbPayRemittanceDo;

import java.math.BigDecimal;

/**
 * Service层参数转换
 * <p>
 * 1、跨境人民币渠道成本请求参数信息转换
 * </p>
 * User: wanght Date:2016/11/30 ProjectName: cbpayservice Version: 1.0
 */
public final class CbPayCostConvert {

    private CbPayCostConvert() {

    }

    /**
     * 跨境人民币渠道成本请求参数信息转换
     *
     * @param fiCbPayRemittanceDo 汇款订单信息
     * @param updateBy            创建人
     * @param fee                 费用
     * @return 转换信息
     */
    public static FiCbPayChannelCostDo paramConvert(FiCbPayRemittanceDo fiCbPayRemittanceDo, String updateBy, BigDecimal fee) {
        FiCbPayChannelCostDo fiCbPayChannelCostDo = new FiCbPayChannelCostDo();
        fiCbPayChannelCostDo.setBatchNo(fiCbPayRemittanceDo.getBatchNo());
        fiCbPayChannelCostDo.setChannelFee(fee);
        fiCbPayChannelCostDo.setChannelId(fiCbPayRemittanceDo.getChannelId());
        fiCbPayChannelCostDo.setCurrency("CNY");
        fiCbPayChannelCostDo.setMemberNo(fiCbPayRemittanceDo.getMemberNo());
        fiCbPayChannelCostDo.setCreateBy(updateBy);
        fiCbPayChannelCostDo.setUpdateBy(updateBy);
        return fiCbPayChannelCostDo;
    }

    /**
     * 渠道成本数据信息转换
     *
     * @param channelCostCalBo 渠道成本
     * @param channelFee       渠道成本
     * @return 返回渠道成本数据库层次对象信息
     */
    public static FiCbPayChannelCostDo paramConvert(ChannelCostCalBo channelCostCalBo, BigDecimal channelFee) {

        FiCbPayChannelCostDo fiCbPayChannelCostDo = new FiCbPayChannelCostDo();
        fiCbPayChannelCostDo.setChannelId(channelCostCalBo.getChannelId());
        fiCbPayChannelCostDo.setMemberNo(channelCostCalBo.getMemberId());
        fiCbPayChannelCostDo.setCurrency(channelCostCalBo.getCcy());
        fiCbPayChannelCostDo.setChannelFee(channelFee);
        fiCbPayChannelCostDo.setBatchNo(channelCostCalBo.getBatchNo());

        return fiCbPayChannelCostDo;
    }
}
