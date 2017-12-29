package com.baofu.cbpayservice.facade;

import com.baofu.cbpayservice.facade.models.CbPayRemittanceAuditReqDto;
import com.baofu.cbpayservice.facade.models.CbPayRemittanceBankFeeReqDto;
import com.baofu.cbpayservice.facade.models.CbPayRemittanceOrderReqV2Dto;
import com.baofu.cbpayservice.facade.models.CbPayRemtStatusChangeReqDto;
import com.system.commons.result.Result;

/**
 * 跨境人民币汇款操作接口
 * <p>
 * 1、创建汇款订单
 * 2、汇款订单审核
 * 3、汇款订单状态更新
 * </p>
 * User: wanght Date:2016/11/10 ProjectName: cbpay-service Version: 1.0
 */
public interface CbPayRemittanceFacade {

    /**
     * 创建汇款订单
     *
     * @param cbPayRemittanceOrderReqV2Dto 请求参数
     * @param traceLogId                   日志id
     * @return 返回是否成功
     */
    Result<Boolean> createRemittanceOrderV2(CbPayRemittanceOrderReqV2Dto cbPayRemittanceOrderReqV2Dto, String traceLogId);

    /**
     * 汇款订单商户审核
     *
     * @param cbPayRemittanceAuditReqDto 审核请求参数
     * @param traceLogId                 日志ID
     * @return 返回是否成功
     */
    Result<Boolean> auditRemittanceOrder(CbPayRemittanceAuditReqDto cbPayRemittanceAuditReqDto, String traceLogId);

    /**
     * 汇款订单状态更新
     *
     * @param cbPayRemtStatusChangeReqDto 状态更新请求参数
     * @param traceLogId                  日志ID
     * @return 返回是否成功
     */
    @Deprecated
    Result<Boolean> changeRemittanceOrderStatus(CbPayRemtStatusChangeReqDto cbPayRemtStatusChangeReqDto, String traceLogId);

    /**
     * 汇款订单线下，后台审核接口
     *
     * @param cbPayRemtStatusChangeReqDto 状态更新请求参数
     * @param traceLogId                  日志ID
     */
    Result<Boolean> backgroundAudit(CbPayRemtStatusChangeReqDto cbPayRemtStatusChangeReqDto, String traceLogId);

    /**
     * 付汇入账费用，管理后台更新接口
     *
     * @param cbPayRemittanceBankFeeReqDto 请求参数
     * @param traceLogId                   日志ID
     * @return 返回是否成功
     */
    Result<Boolean> updateBankFee(CbPayRemittanceBankFeeReqDto cbPayRemittanceBankFeeReqDto, String traceLogId);

    /**
     * 付汇入账费用，管理后台审核接口
     *
     * @param cbPayRemittanceBankFeeReqDto 请求参数
     * @param traceLogId                   日志ID
     * @return 返回是否成功
     */
    Result<Boolean> bankFeeAudit(CbPayRemittanceBankFeeReqDto cbPayRemittanceBankFeeReqDto, String traceLogId);

    /**
     * 汇款异常重发
     *
     * @param batchNo    批次号
     * @param memberId   商户号
     * @param updateBy   操作人
     * @param traceLogId 日志ID
     * @return 返回结果
     */
    Result<Boolean> remittanceResend(String batchNo, Long memberId, String updateBy, String traceLogId);


}
