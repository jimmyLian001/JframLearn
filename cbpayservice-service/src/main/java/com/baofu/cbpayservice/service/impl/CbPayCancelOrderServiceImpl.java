package com.baofu.cbpayservice.service.impl;

import com.baofu.cbpayservice.biz.CbPayCancelOrderBiz;
import com.baofu.cbpayservice.facade.CbPayCancelOrderFacade;
import com.baofu.cbpayservice.facade.models.CbPayCancelOrderDto;
import com.baofu.cbpayservice.service.convert.CbPayOrderConvert;
import com.system.commons.result.Result;
import com.system.commons.utils.ExceptionUtils;
import com.system.commons.utils.ParamValidate;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.slf4j.helpers.SystemMarker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 取消上传订单
 * </p>
 * <p>
 * User: yangjian  Date: 2017-06-07 ProjectName:cbpay-service   Version: 1.0
 */
@Slf4j
@Service
public class CbPayCancelOrderServiceImpl implements CbPayCancelOrderFacade {

    /**
     * 取消订单biz服务
     */
    @Autowired
    private CbPayCancelOrderBiz cbPayCancelOrderBiz;

    /**
     * @param cbPayCancelOrderDto 取消订单请求参数
     * @param traceLogId          日志ID
     * @return 返回成功结果
     */
    @Override
    public Result<Boolean> cancelRemittanceOrder(CbPayCancelOrderDto cbPayCancelOrderDto, String traceLogId) {

        long startTime = System.currentTimeMillis();
        Result<Boolean> result;
        MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
        log.info("取消订单请求参数：{}", cbPayCancelOrderDto);
        try {
            ParamValidate.validateParams(cbPayCancelOrderDto);
            cbPayCancelOrderBiz.cancelOrder(CbPayOrderConvert.paramConvert(cbPayCancelOrderDto));
            result = new Result<>(Boolean.TRUE);
        } catch (Exception e) {
            log.error("取消订单 Exception:", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("取消订单状态 RESULT:{}，执行时间：{}ms", result, System.currentTimeMillis() - startTime);
        return result;
    }
}
