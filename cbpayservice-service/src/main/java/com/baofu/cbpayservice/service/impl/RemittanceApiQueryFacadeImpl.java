package com.baofu.cbpayservice.service.impl;

import com.baofu.cbpayservice.biz.RemittanceApiQueryBiz;
import com.baofu.cbpayservice.biz.models.RemitDetailsQueryResultBo;
import com.baofu.cbpayservice.biz.models.RemitOrderApiQueryBo;
import com.baofu.cbpayservice.biz.models.RemitOrderApiQueryResultBo;
import com.baofu.cbpayservice.facade.RemittanceApiQueryFacade;
import com.baofu.cbpayservice.facade.models.RemitOrderApiQueryDto;
import com.baofu.cbpayservice.facade.models.res.RemitDetailsQueryRespDto;
import com.baofu.cbpayservice.facade.models.res.RemitOrderQueryRespDto;
import com.baofu.cbpayservice.service.convert.RemitOrderApiQueryConvert;
import com.system.commons.result.Result;
import com.system.commons.utils.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.slf4j.helpers.SystemMarker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * API汇款信息查询接口
 * <p>
 * User: 不良人 Date:2017/9/22 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
@Service
public class RemittanceApiQueryFacadeImpl implements RemittanceApiQueryFacade {

    /**
     * API汇款信息查询服务
     */
    @Autowired
    private RemittanceApiQueryBiz remittanceApiQueryBiz;

    /**
     * API汇款订单信息查询接口
     *
     * @param queryDto   汇款订单信息查询请求参数
     * @param traceLogId 日志ID
     * @return 查询返回参数
     */
    @Override
    public Result<RemitOrderQueryRespDto> remittanceOrderQuery(RemitOrderApiQueryDto queryDto, String traceLogId) {

        log.info("call API汇款订单信息查询接口,请求参数：{}", queryDto);
        Result<RemitOrderQueryRespDto> result;
        MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);

        try {
            //查询汇款订单信息
            RemitOrderApiQueryBo remitOrderApiQueryBo = new RemitOrderApiQueryBo();
            remitOrderApiQueryBo.setMemberId(queryDto.getMemberId());
            remitOrderApiQueryBo.setBatchNo(queryDto.getBatchNo());
            RemitOrderApiQueryResultBo queryResultBo = remittanceApiQueryBiz.remittanceOrderQuery(remitOrderApiQueryBo);
            //参数转换
            RemitOrderQueryRespDto respDto = RemitOrderApiQueryConvert.toRemitOrderQueryRespDto(queryResultBo);
            result = new Result<>(respDto);
        } catch (Exception e) {
            log.error("call API汇款订单信息查询接口发生异常：{}", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("call API汇款订单信息查询接口返回参数:{}", result);

        return result;
    }

    /**
     * API订单明细上传查询
     *
     * @param queryDto 汇款订单信息查询请求参数
     * @param traceLogId            日志ID
     * @return 查询返回参数
     */
    @Override
    public Result<RemitDetailsQueryRespDto> orderDetailsUploadQuery(RemitOrderApiQueryDto queryDto, String traceLogId) {

        log.info("call API汇款订单信息查询接口,请求参数：{}", queryDto);
        Result<RemitDetailsQueryRespDto> result;
        MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);

        try {
            //查询汇款订单信息
            RemitOrderApiQueryBo remitOrderApiQueryBo = new RemitOrderApiQueryBo();
            remitOrderApiQueryBo.setMemberId(queryDto.getMemberId());
            remitOrderApiQueryBo.setBatchNo(queryDto.getBatchNo());
            RemitDetailsQueryResultBo queryResultBo = remittanceApiQueryBiz.orderDetailsUploadQuery(remitOrderApiQueryBo);
            //参数转换

            RemitDetailsQueryRespDto respDto = RemitOrderApiQueryConvert.toRemitDetailsQueryRespDto(queryResultBo);

            result = new Result<>(respDto);
        } catch (Exception e) {
            log.error("call API汇款订单信息查询接口发生异常：{}", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("call API汇款订单信息查询接口返回参数:{}", result);

        return result;
    }
}
