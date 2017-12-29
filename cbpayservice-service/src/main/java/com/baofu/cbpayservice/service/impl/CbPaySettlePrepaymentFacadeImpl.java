package com.baofu.cbpayservice.service.impl;

import com.baofu.cbpayservice.biz.CbPaySettlePrepaymentBiz;
import com.baofu.cbpayservice.biz.models.CbPaySettlePrepaymentBo;
import com.baofu.cbpayservice.facade.CbPaySettlePrepaymentFacade;
import com.baofu.cbpayservice.facade.models.CbPaySettlePrepaymentDto;
import com.baofu.cbpayservice.service.convert.CbPaySettlePrepaymentConvert;
import com.system.commons.result.Result;
import com.system.commons.utils.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.slf4j.helpers.SystemMarker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 结汇垫资接口
 * <p>
 * 1，结汇垫资申请
 * 2，结汇垫资审核
 * </p>
 * User: 康志光 Date: 2017/8/18 ProjectName: cbpay-customs-service Version: 1.0
 */
@Slf4j
@Service
public class CbPaySettlePrepaymentFacadeImpl implements CbPaySettlePrepaymentFacade {

    /**
     * 结汇垫资biz服务
     */
    @Autowired
    private CbPaySettlePrepaymentBiz cbPaySettlePrepaymentBiz;

    /**
     * 结汇垫资申请
     *
     * @param incomeNo   银行汇入汇款编号
     * @param traceLogId 日志ID
     * @return 同步响应结果
     */
    @Override
    public Result<Boolean> prepaymentApply(String incomeNo, String traceLogId) {
        MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
        Result<Boolean> result;

        try {
            log.info("结汇垫资申请参数：{}", incomeNo);
            cbPaySettlePrepaymentBiz.prepaymentApply(incomeNo, 1);
            result = new Result<>(Boolean.TRUE);
        } catch (Exception e) {
            log.error("结汇垫资申请失败：{}", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("结汇垫资申请结汇：{}", result);
        return result;
    }

    /**
     * 结汇垫资审核
     *
     * @param applyId    垫资申请编号
     * @param status     垫资状态
     * @param remarks    备注
     * @param traceLogId 日志ID
     * @return 同步响应结果
     */
    @Override
    public Result<Boolean> prepaymentVerify(Long applyId, Integer status, String remarks, String traceLogId) {
        MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
        Result<Boolean> result;

        try {
            log.info("结汇垫资审核参数：applyId {}, status {}", applyId, status);
            cbPaySettlePrepaymentBiz.prepaymentVerify(applyId, status, remarks);
            result = new Result<>(Boolean.TRUE);
        } catch (Exception e) {
            log.error("结汇垫资审核失败：{}", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("结汇垫资审核结汇：{}", result);
        return result;
    }


    /**
     * 计算结汇垫资金额
     *
     * @param incomeNo   汇入汇款编号
     * @param traceLogId 日志ID
     * @return Result<CbPaySettlePrepaymentDto>
     */
    @Override
    public Result<CbPaySettlePrepaymentDto> calculateSettleAmt(String incomeNo, String traceLogId) {
        MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
        Result<CbPaySettlePrepaymentDto> result;

        try {
            log.info("结汇垫资计算参数：{}", incomeNo);
            CbPaySettlePrepaymentBo cbPcaySettlePrepaymentBo = cbPaySettlePrepaymentBiz.calculateSettleAmt(incomeNo);
            CbPaySettlePrepaymentDto cbPaySettlePrepaymentDto = CbPaySettlePrepaymentConvert.convertTo(cbPcaySettlePrepaymentBo);
            result = new Result<>(cbPaySettlePrepaymentDto);
        } catch (Exception e) {
            log.error("结汇垫资计算失败：{}", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("结汇垫资计算结果：{}", result);
        return result;
    }
}
