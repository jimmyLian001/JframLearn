package com.baofu.cbpayservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baofu.cbpayservice.biz.MqSendService;
import com.baofu.cbpayservice.common.enums.MqSendQueueNameEnum;
import com.baofu.cbpayservice.facade.CbPayNotifySettleFacade;
import com.system.commons.result.Result;
import com.system.commons.utils.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.slf4j.helpers.SystemMarker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 跨境人民币人工通知清算接口
 * <p>
 * 1、人工通知清算接口
 * </p>
 * User: wanght Date:2017/02/27 ProjectName: cbpay-service Version: 1.0
 */
@Slf4j
@Service
public class CbPayNotifySettleServiceImpl implements CbPayNotifySettleFacade {

    /**
     * 发送MQ服务
     */
    @Autowired
    private MqSendService mqSendService;

    /**
     * 人工通知清算接口
     *
     * @param batchNo    批次号
     * @param operator   操作员
     * @param traceLogId 全局id
     * @return 返回是否成功
     */
    @Override
    public Result<Boolean> notifySettle(String batchNo, String operator, String traceLogId) {
        Result<Boolean> result;
        try {
            MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);

            log.info("call 发送人工通知清算MQ接口信息 batchNo:{}, operator:{}", batchNo, operator);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("batchNo", batchNo);
            jsonObject.put("updateBy", operator);

            mqSendService.sendMessage(MqSendQueueNameEnum.CBPAY_NOTIFY_SETTLE_QUEUE_NAME, jsonObject.toJSONString());
            log.info("call 发送人工通知清算MQ接口信息 batchNo:{} 发送MQ消息成功", batchNo);

            result = new Result<>(Boolean.TRUE);
        } catch (Exception e) {
            log.error("call 人工通知清算 Exception:", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("call 人工通知清算 result:{}", result);
        return result;
    }
}
