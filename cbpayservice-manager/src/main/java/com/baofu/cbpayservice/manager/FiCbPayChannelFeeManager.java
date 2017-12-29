package com.baofu.cbpayservice.manager;

import com.baofu.cbpayservice.dal.models.CbPaySelectChannelFeePageDo;
import com.baofu.cbpayservice.dal.models.FiCbPayChannelFeeDo;

import java.util.List;

/**
 * 渠道成本配置操作Manager服务
 * <p>
 * 1、根据商户号查询商户缓存信息
 * </p>
 * User: wanght Date:2016/11/30 ProjectName: asias-icpservice Version: 1.0
 */
public interface FiCbPayChannelFeeManager {

    /**
     * 根据商户号查询商户缓存信息
     *
     * @param channelId 渠道号
     * @param status    状态
     * @return 返回商户缓存信息
     */
    FiCbPayChannelFeeDo queryChannelFee(Long channelId, String status);

    /**
     * 根据商户号查询商户缓存信息
     *
     * @param recordId 渠道号
     * @return 返回商户缓存信息
     */
    FiCbPayChannelFeeDo queryChannelFeeByKey(Long recordId);

    /**
     * 汇款渠道管理查询总数
     *
     * @param channelId  渠道id
     * @param beginTime  开始时间
     * @param endTime    结束时间
     * @return 总记录数
     */
    Integer selectCount(Long channelId, String beginTime, String endTime);

    /**
     * 汇款订单商户审核
     *
     * @param cbPaySelectChannelFeePageDo 分页查询请求参数
     * @return 返回是否成功
     */
    List<FiCbPayChannelFeeDo> selectPageList(CbPaySelectChannelFeePageDo cbPaySelectChannelFeePageDo);

    /**
     * 汇款订单商户审核
     *
     * @param cbPayChannelFeeDo 新增请求参数
     */
    void addChannelFee(FiCbPayChannelFeeDo cbPayChannelFeeDo);

    /**
     * 汇款订单商户审核
     *
     * @param cbPayChannelFeeDo 修改请求参数
     */
    void editChannelFee(FiCbPayChannelFeeDo cbPayChannelFeeDo);
}
