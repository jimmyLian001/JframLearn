package com.baofu.cbpayservice.facade;

import com.baofu.cbpayservice.facade.models.ChannelGroupAddReqDto;
import com.baofu.cbpayservice.facade.models.ChannelGroupModifyReqDto;
import com.system.commons.result.Result;

/**
 * 渠道分组对外提供接口信息
 * <p>
 * 1、渠道分组信息新增
 * 2、渠道分组信息更新
 * </p>
 * User: 香克斯 Date:2017/4/24 ProjectName: cbpay-service Version: 1.0
 */
public interface ChannelGroupFacade {

    /**
     * 渠道分组信息新增
     *
     * @param channelGroupAddReqDto 渠道分组信息
     * @param traceLogId            日志ID编号
     * @return 返回是否成功标识
     */
    Result<Boolean> addChannelGroup(ChannelGroupAddReqDto channelGroupAddReqDto, String traceLogId);

    /**
     * 渠道分组信息更新
     *
     * @param channelGroupModifyReqDto 渠道分组信息
     * @param traceLogId               日志ID编号
     * @return 返回是否成功标识
     */
    Result<Boolean> modifyChannelGroup(ChannelGroupModifyReqDto channelGroupModifyReqDto, String traceLogId);
}
