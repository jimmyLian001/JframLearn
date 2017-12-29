package com.baofu.cbpayservice.facade;

import com.baofu.cbpayservice.facade.models.CbPayOrderQueryReqDto;
import com.baofu.cbpayservice.facade.models.res.CbPayOrderRespDto;
import com.system.commons.result.Result;

/**
 * 跨境人民币订单查询接口
 * <p>
 * </p>
 * User: 香克斯 Date:2016/10/25 ProjectName: asias-icpaygate Version: 1.0
 */
public interface CbPayOrderQueryFacade {

    /**
     * 查询订单
     *
     * @param cbPayOrderReqDto 请求参数信息
     * @param traceLogId       日志ID
     * @return 返回订单信息
     */
    Result<CbPayOrderRespDto> queryOrder(CbPayOrderQueryReqDto cbPayOrderReqDto, String traceLogId);
}
