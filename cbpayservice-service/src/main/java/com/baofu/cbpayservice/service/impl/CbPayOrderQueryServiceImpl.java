package com.baofu.cbpayservice.service.impl;

import com.baofu.cbpayservice.common.enums.ErrorCodeEnum;
import com.baofu.cbpayservice.dal.models.FiCbPayOrderAdditionDo;
import com.baofu.cbpayservice.dal.models.FiCbPayOrderDo;
import com.baofu.cbpayservice.facade.CbPayOrderQueryFacade;
import com.baofu.cbpayservice.facade.models.CbPayOrderQueryReqDto;
import com.baofu.cbpayservice.facade.models.res.CbPayOrderRespDto;
import com.baofu.cbpayservice.manager.CbPayOrderManager;
import com.baofu.cbpayservice.manager.OrderAdditionManager;
import com.baofu.cbpayservice.service.convert.CbPayOrderConvert;
import com.system.commons.exception.BizServiceException;
import com.system.commons.exception.CommonErrorCode;
import com.system.commons.result.Result;
import com.system.commons.utils.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.MDC;
import org.slf4j.helpers.SystemMarker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: suqier Date:2016/10/26 ProjectName: asias-icpaygate Version: 1.0
 **/
@Slf4j
@Service
public class CbPayOrderQueryServiceImpl implements CbPayOrderQueryFacade {

    /**
     * 跨境订单Biz层相关操作
     */
    @Autowired
    private CbPayOrderManager cbPayOrderManager;

    /**
     * 订单附加信息
     */
    @Autowired
    private OrderAdditionManager orderAdditionManager;

    /**
     * 查询订单
     *
     * @param cbPayOrderQueryReqDto 请求参数信息
     * @param traceLogId            日志ID
     * @return 返回订单信息
     */
    @Override
    public Result<CbPayOrderRespDto> queryOrder(CbPayOrderQueryReqDto cbPayOrderQueryReqDto, String traceLogId) {

        Result<CbPayOrderRespDto> result;
        try {
            MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
            log.info("订单接口查询开始 cbPayOrderReqDto:{}", cbPayOrderQueryReqDto);
            if (cbPayOrderQueryReqDto.getOrderId() == null
                    && StringUtils.isBlank(cbPayOrderQueryReqDto.getMemberTransId())) {
                throw new BizServiceException(CommonErrorCode.PARAMETER_VALID_NOT_PASS, "宝付订单号或商户订单号不能同时为空");
            }
            if (StringUtils.isNotBlank(cbPayOrderQueryReqDto.getMemberTransId())
                    && cbPayOrderQueryReqDto.getMemberId() == null
                    && cbPayOrderQueryReqDto.getOrderId() == null) {
                throw new BizServiceException(CommonErrorCode.PARAMETER_VALID_NOT_PASS, "查询订单信息商户编号不能为空");
            }
            FiCbPayOrderDo fiCbPayOrderDo;
            if (cbPayOrderQueryReqDto.getOrderId() != null) {
                fiCbPayOrderDo = cbPayOrderManager.queryOrder(cbPayOrderQueryReqDto.getOrderId());
            } else {
                fiCbPayOrderDo = cbPayOrderManager.queryOrderByMemberAndTransId(
                        cbPayOrderQueryReqDto.getMemberId(), cbPayOrderQueryReqDto.getMemberTransId());
            }
            if (fiCbPayOrderDo == null) {
                log.error("查询订单信息为空，查询请求参数信息:{}", cbPayOrderQueryReqDto);
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0094);
            }
            FiCbPayOrderAdditionDo orderAddition = orderAdditionManager.queryOrderAddition(fiCbPayOrderDo.getOrderId());
            result = new Result<>(CbPayOrderConvert.paramConvert(fiCbPayOrderDo, orderAddition));
        } catch (Exception e) {
            log.error("查询订单发生异常，Exception：{}", e);
            result = ExceptionUtils.getResponse(e);
        }

        log.info("订单接口查询结束 result:{}", result);
        return result;
    }
}

