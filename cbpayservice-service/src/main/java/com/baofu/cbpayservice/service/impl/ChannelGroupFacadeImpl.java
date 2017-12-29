package com.baofu.cbpayservice.service.impl;

import com.baofu.cbpayservice.facade.ChannelGroupFacade;
import com.baofu.cbpayservice.facade.models.ChannelGroupAddReqDto;
import com.baofu.cbpayservice.facade.models.ChannelGroupModifyReqDto;
import com.baofu.cbpayservice.manager.ChannelGroupManager;
import com.baofu.cbpayservice.manager.OrderIdManager;
import com.baofu.cbpayservice.service.convert.ChannelGroupConvert;
import com.system.commons.result.Result;
import com.system.commons.utils.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.slf4j.helpers.SystemMarker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 渠道分组对外提供接口信息
 * <p>
 * 1、渠道分组信息新增
 * 2、渠道分组信息更新
 * </p>
 * User: 香克斯 Date:2017/4/24 ProjectName: cbpay-service Version: 1.0
 */
@Slf4j
@Service
public class ChannelGroupFacadeImpl implements ChannelGroupFacade {

    /**
     * 渠道分组信息Manager接口服务
     */
    @Autowired
    private ChannelGroupManager channelGroupManager;

    /**
     * 创建宝付订单号
     */
    @Autowired
    private OrderIdManager orderIdManager;

    /**
     * 渠道分组信息新增
     *
     * @param channelGroupAddReqDto 渠道分组信息
     * @param traceLogId            日志ID编号
     * @return 返回是否成功标识
     */
    @Override
    public Result<Boolean> addChannelGroup(ChannelGroupAddReqDto channelGroupAddReqDto, String traceLogId) {

        Result<Boolean> result;
        MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
        try {
            log.info("渠道分组信息新增请求参数信息：{}", channelGroupAddReqDto);
            channelGroupManager.addChannelGroup(ChannelGroupConvert.paramConvert(channelGroupAddReqDto, orderIdManager.orderIdCreate()));

            result = new Result<>(Boolean.TRUE);
        } catch (Exception e) {
            log.error("渠道分组信息新增失败，异常信息：", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("渠道分组信息新增完成，返回参数信息：{}", result);
        return result;
    }

    /**
     * 渠道分组信息更新
     *
     * @param channelGroupModifyReqDto 渠道分组信息
     * @param traceLogId               日志ID编号
     * @return 返回是否成功标识
     */
    @Override
    public Result<Boolean> modifyChannelGroup(ChannelGroupModifyReqDto channelGroupModifyReqDto, String traceLogId) {

        Result<Boolean> result;
        MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
        try {
            log.info("渠道分组信息更新请求参数信息：{}", channelGroupModifyReqDto);

            channelGroupManager.modifyChannelGroup(ChannelGroupConvert.paramConvert(channelGroupModifyReqDto));

            result = new Result<>(Boolean.TRUE);
        } catch (Exception e) {
            log.error("渠道分组信息更新失败，异常信息：", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("渠道分组信息更新完成，返回参数信息：{}", result);
        return result;
    }
}
