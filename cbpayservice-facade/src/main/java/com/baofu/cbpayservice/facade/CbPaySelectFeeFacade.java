package com.baofu.cbpayservice.facade;

import com.baofu.cbpayservice.facade.models.CbPayCalculateFeeDto;
import com.baofu.cbpayservice.facade.models.CbPaySelectFeeDto;
import com.baofu.cbpayservice.facade.models.res.CbPayFeeRespDto;
import com.baofu.cbpayservice.facade.models.res.ExchangeRateQueryRespDto;
import com.system.commons.result.Result;

/**
 * 跨境人民币订单查询接口
 * <p>
 * </p>
 * User: 莫小阳 Date:2017/03/28 ProjectName: cbpay-service Version: 1.0
 */
public interface CbPaySelectFeeFacade {

    /**
     * @param cbPaySelectFeeDto 批次号组合
     * @param traceLogId        日志ID
     * @return 试算结果
     */
    Result<CbPayFeeRespDto> selectFee(CbPaySelectFeeDto cbPaySelectFeeDto, String traceLogId);

    /**
     * 汇率查询API接口
     *
     * @param memberId   商户号
     * @param ccy        币种
     * @param traceLogId 日志ID
     * @return 查汇结果
     */
    Result<ExchangeRateQueryRespDto> exchangeRateQuery(Long memberId, String ccy, String traceLogId);

    /**
     * 跨境汇款汇率试算
     * @param cbPayCalculateFeeDto 批次号组合
     * @param traceLogId        日志ID
     * @return 试算结果
     */
    Result<CbPayFeeRespDto> preCalculateFee(CbPayCalculateFeeDto cbPayCalculateFeeDto, String traceLogId);
}
