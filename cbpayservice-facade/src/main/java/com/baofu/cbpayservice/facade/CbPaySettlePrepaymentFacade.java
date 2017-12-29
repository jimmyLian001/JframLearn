package com.baofu.cbpayservice.facade;

import com.baofu.cbpayservice.facade.models.CbPaySettlePrepaymentDto;
import com.system.commons.result.Result;

/**
 * 结汇垫资操作接口服务
 * <p>
 * User: 康志光 Date:2017/08/18 ProjectName: cbpayservice Version: 1.0
 */
public interface CbPaySettlePrepaymentFacade {

    /**
     * 结汇垫资申请
     *
     * @param incomeNo   银行汇入汇款编号
     * @param traceLogId 日志ID
     * @return 同步响应结果
     */
    Result<Boolean> prepaymentApply(String incomeNo, String traceLogId);

    /**
     * 结汇垫资审核
     *
     * @param applyId    垫资申请编号
     * @param status     垫资状态
     * @param remarks    备注
     * @param traceLogId 日志ID
     * @return 同步响应结果
     */
    Result<Boolean> prepaymentVerify(Long applyId, Integer status, String remarks, String traceLogId);

    /**
     * 计算结汇垫资金额
     *
     * @param incomeNo   汇入汇款编号
     * @param traceLogId 日志ID
     * @return Result<CbPaySettlePrepaymentDto>
     */
    Result<CbPaySettlePrepaymentDto> calculateSettleAmt(String incomeNo, String traceLogId);

}
