package com.baofu.cbpayservice.dal.mapper;

import com.baofu.cbpayservice.dal.models.CbPaySelectChannelFeePageDo;
import com.baofu.cbpayservice.dal.models.FiCbPayChannelFeeDo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 1、查询渠道成本配置信息
 * </p>
 * User: wanght Date:2016/11/30 ProjectName: asias-icpservice Version: 1.0
 */
public interface FiCbPayChannelFeeMapper {

    /**
     * 查询渠道成本配置信息
     *
     * @param channelId 渠道号
     * @param status    状态
     * @return 返回订单总表信息
     */
    FiCbPayChannelFeeDo selectChannelFee(@Param("channelId") Long channelId, @Param("status") String status);

    /**
     * 查询渠道成本配置信息
     *
     * @param recordId 记录编号
     * @return 返回订单总表信息
     */
    FiCbPayChannelFeeDo selectChannelFeeByKey(@Param("recordId") Long recordId);

    /**
     * 查询渠道成本配置信息
     *
     * @param channelId 渠道号
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @return 返回订单总表信息
     */
    Integer selectCount(@Param("channelId") Long channelId, @Param("beginTime") String beginTime, @Param("endTime") String endTime);

    /**
     * 渠道成本配置分页查询
     * @param cbPaySelectChannelFeePageDto 分页查询参数
     * @return 查询结果
     */
    List<FiCbPayChannelFeeDo> selectPageList(CbPaySelectChannelFeePageDo cbPaySelectChannelFeePageDto);

    /**
     * 汇款订单商户审核
     *
     * @param fiCbPayChannelFeeDo 新增请求参数
     * @return 返回是否成功
     */
    int addChannelFee(FiCbPayChannelFeeDo fiCbPayChannelFeeDo);

    /**
     * 汇款订单商户审核
     *
     * @param cbPayChannelFeeDto 修改请求参数
     * @return 返回是否成功
     */
     int editChannelFee(FiCbPayChannelFeeDo cbPayChannelFeeDto);
}
