package com.baofu.cbpayservice.facade;

import com.baofu.cbpayservice.facade.models.CbPayChannelFeeDto;
import com.baofu.cbpayservice.facade.models.CbPaySelectChannelFeePageDto;
import com.baofu.cbpayservice.facade.models.res.CbPayChannelFeeRespDto;
import com.system.commons.result.Result;

import java.util.List;

/**
 * 跨境人民币渠道费用配置操作接口
 * <p>
 * 1、创建汇款订单
 * 2、汇款订单审核
 * 3、汇款订单状态更新
 * </p>
 * User: wanght Date:2016/11/10 ProjectName: cbpay-service Version: 1.0
 */
public interface CbPayChannelFeeFacade {

    /**
     * 查询渠道成本配置总记录数
     *
     * @param channelId  渠道id
     * @param beginTime  开始时间
     * @param endTime    结束时间
     * @param traceLogId 日志id
     * @return 总记录数
     */
    Result<Integer> selectCount(Long channelId, String beginTime, String endTime, String traceLogId);

    /**
     * 分页查询
     *
     * @param cbPaySelectChannelFeePageDto 分页查询请求参数
     * @param traceLogId                   日志ID
     * @return 返回是否成功
     */
    Result<List<CbPayChannelFeeRespDto>> selectPageList(CbPaySelectChannelFeePageDto cbPaySelectChannelFeePageDto, String traceLogId);

    /**
     * 新增渠道成本配置
     *
     * @param cbPayChannelFeeDto 新增请求参数
     * @param traceLogId         日志ID
     * @return 返回是否成功
     */
    Result<Boolean> addChannelFee(CbPayChannelFeeDto cbPayChannelFeeDto, String traceLogId);

    /**
     * 修改渠道成本配置
     *
     * @param cbPayChannelFeeDto 修改请求参数
     * @param traceLogId         日志ID
     * @return 返回是否成功
     */
    Result<Boolean> editChannelFee(CbPayChannelFeeDto cbPayChannelFeeDto, String traceLogId);

    /**
     * 查询渠道成本配置
     *
     * @param recordId   记录编号
     * @param traceLogId 日志ID
     * @return 返回是否成功
     */
    Result<CbPayChannelFeeRespDto> queryChannelFeeByKey(Long recordId, String traceLogId);

    /**
     * 查询渠道成本配置
     *
     * @param chanelId   渠道id
     * @param status     状态
     * @param traceLogId 日志ID
     * @return 返回是否成功
     */
    Result<CbPayChannelFeeRespDto> queryChannelFee(Long chanelId, String status, String traceLogId);
}
