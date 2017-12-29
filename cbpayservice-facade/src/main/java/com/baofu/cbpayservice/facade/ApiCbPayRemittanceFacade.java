package com.baofu.cbpayservice.facade;

import com.baofu.cbpayservice.facade.models.ApiCbPayRemittanceOrderReqDto;
import com.system.commons.result.Result;

/**
 * 跨境人民币汇款操作接口
 * <p>
 * 1、创建汇款订单
 * User: 莫小阳 Date:2017/09/28 ProjectName: cbpay-service Version: 1.0
 */
public interface ApiCbPayRemittanceFacade {

    /**
     * @param apiCbPayRemittanceOrderReqDto api请求对象
     * @param traceLogId                    日志ID
     * @return 处理结果
     */
    Result<Boolean> apiCreateRemittanceOrder(ApiCbPayRemittanceOrderReqDto apiCbPayRemittanceOrderReqDto, String traceLogId);

}
