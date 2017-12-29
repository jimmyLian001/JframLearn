package com.baofu.cbpayservice.facade;

import com.baofu.cbpayservice.facade.models.CbPayCancelOrderDto;
import com.system.commons.result.Result;

/**
 * <p>
 * 取消上传订单
 * </p>
 * User: yangjian  Date: 2017-06-07 ProjectName:cbpay-service  Version: 1.0
 */
public interface CbPayCancelOrderFacade {

    /**
     * 取消上传订单
     *
     * @param cbPayCancelOrderDto 取消订单请求参数
     * @param traceLogId          日志ID
     * @return 返回成功结果
     */
    Result<Boolean> cancelRemittanceOrder(CbPayCancelOrderDto cbPayCancelOrderDto, String traceLogId);
}
