package com.baofu.cbpayservice.facade;

import com.baofu.cbpayservice.facade.models.RemitOrderApiQueryDto;
import com.baofu.cbpayservice.facade.models.res.RemitDetailsQueryRespDto;
import com.baofu.cbpayservice.facade.models.res.RemitOrderQueryRespDto;
import com.system.commons.result.Result;

/**
 * API汇款信息查询接口
 * <p>
 * User: 不良人 Date:2017/9/22 ProjectName: cbpayservice Version: 1.0
 */
public interface RemittanceApiQueryFacade {

    /**
     * API汇款订单信息查询接口
     *
     * @param remitOrderApiQueryDto 汇款订单信息查询请求参数
     * @param traceLogId         日志ID
     * @return 查询返回参数
     */
    Result<RemitOrderQueryRespDto> remittanceOrderQuery(RemitOrderApiQueryDto remitOrderApiQueryDto, String traceLogId);

    /**
     * API订单明细上传查询
     *
     * @param remitOrderApiQueryDto 汇款订单信息查询请求参数
     * @param traceLogId 日志ID
     * @return 查询返回参数
     */
    Result<RemitDetailsQueryRespDto> orderDetailsUploadQuery(RemitOrderApiQueryDto remitOrderApiQueryDto, String traceLogId);
}
