package com.baofu.cbpayservice.service.impl;

import com.baofu.cbpayservice.biz.CbPayOrderNotifyBiz;
import com.baofu.cbpayservice.biz.MqSendService;
import com.baofu.cbpayservice.common.enums.MqSendQueueNameEnum;
import com.baofu.cbpayservice.facade.CbPayNotifyMerchantFacade;
import com.baofu.cbpayservice.facade.models.CbPayOrderNotifyReqDto;
import com.system.commons.result.Result;
import com.system.commons.utils.ExceptionUtils;
import com.system.commons.utils.ParamValidate;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.slf4j.helpers.SystemMarker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 跨境人民币通知商户接口
 * <p>
 * 1、交易通知商户接口
 * 2、批量通知商户
 * 3、发送通知商户MQ接口信息
 * </p>
 * User: wanght Date:2016/10/25 ProjectName: asias-icpaygate Version: 1.0
 */
@Slf4j
@Service
public class CbPayNotifyMerchantServiceImpl implements CbPayNotifyMerchantFacade {

    /**
     * 通知商户处理服务Service
     */
    @Autowired
    private CbPayOrderNotifyBiz cbPayOrderNotifyBiz;

    /**
     * 发送MQ服务
     */
    @Autowired
    private MqSendService mqSendService;

    /**
     * 通知商户接口
     *
     * @param orderId    订单号
     * @param operator   操作员
     * @param traceLogId 全局id
     * @return 返回是否成功
     */
    @Override
    public Result<Boolean> notifyMerchant(Long orderId, String operator, String traceLogId) {
        Result<Boolean> result;
        try {
            MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);

            log.info("call 通知商户请求参数,orderId:{},operator:{}", orderId, operator);
            boolean isSuccess = cbPayOrderNotifyBiz.notifyMember(orderId, operator);
            log.info("通知商户结果,orderId:{},是否成功:{}", orderId, isSuccess);

            result = new Result<>(isSuccess);
        } catch (Exception e) {
            log.error("call 通知商户 Exception:", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("call 通知商户 result:{}", result);
        return result;
    }

    /**
     * 批量通知商户
     *
     * @param cbPayOrderNotifyReqDto 通知商户参数信息
     * @param traceLogId             日志id
     * @return 返回通知失败的宝付订单号
     */
    @Override
    public Result<List<Long>> notifyMember(CbPayOrderNotifyReqDto cbPayOrderNotifyReqDto, String traceLogId) {
        Result<List<Long>> result;
        try {
            MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
            log.info("call 批量通知商户 PARAM:{}", cbPayOrderNotifyReqDto);

            ParamValidate.validateParams(cbPayOrderNotifyReqDto);
            List<Long> failList = new ArrayList<>();
            for (Long orderId : cbPayOrderNotifyReqDto.getOrderId()) {
                if (!notifyMember(orderId, cbPayOrderNotifyReqDto.getOperationBy())) {
                    failList.add(orderId);
                }
            }
            result = new Result<>(failList);
        } catch (Exception e) {
            log.error("call 批量通知商户 Exception:", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("call 批量通知商户 RESULT:{}", result);
        return result;
    }

    /**
     * 发送通知商户MQ接口信息
     *
     * @param orderId    宝付订单号
     * @param traceLogId 日志ID
     * @return 返回结果
     */
    @Override
    public Result<Boolean> sendNotifyMemberMq(Long orderId, String traceLogId) {

        Result<Boolean> result;
        try {
            MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
            log.info("call 发送通知商户MQ接口信息 PARAM:{}", orderId);
            mqSendService.sendMessage(MqSendQueueNameEnum.CBPAY_NOTIFY_MEMBER_QUEUE_NAME, orderId);
            log.info("call 发送通知商户MQ接口信息 orderId:{} 发送MQ消息成功", orderId);

            result = new Result<>(Boolean.TRUE);
        } catch (Exception e) {
            result = ExceptionUtils.getResponse(e);
            log.error("call 发送通知商户MQ接口信息 Exception:", e);
        }
        log.info("call 发送通知商户MQ接口信息 RESULT:{}", result);
        return result;
    }

    /**
     * 通知商户
     *
     * @param orderId    宝付订单号
     * @param operatorBy 操作人
     * @return 返回结果
     */
    private Boolean notifyMember(Long orderId, String operatorBy) {
        try {
            log.info("call 批量通知商户宝付订单号:{}开始通知", orderId);
            return cbPayOrderNotifyBiz.notifyMember(orderId, operatorBy);
        } catch (Exception e) {
            log.error("call 批量通知商户宝付订单号：{}通知失败", orderId);
            return Boolean.FALSE;
        }
    }
}
