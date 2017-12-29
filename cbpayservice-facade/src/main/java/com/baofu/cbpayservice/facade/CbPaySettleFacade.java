package com.baofu.cbpayservice.facade;

import com.baofu.cbpayservice.facade.models.*;
import com.baofu.cbpayservice.facade.models.res.SettleNotifyRespDto;
import com.system.commons.result.Result;

/**
 * 结汇操作接口服务
 * <p>
 * User: 不良人 Date:2017/4/13 ProjectName: cbpayservice Version: 1.0
 */
public interface CbPaySettleFacade {

    /**
     * 外汇汇入申请(客户在商户前台发起)
     *
     * @param settleIncomeApplyDto 外汇汇入申请参数
     * @param traceLogId           日志ID
     * @return 申请单号
     */
    Result<Long> settleIncomeApply(SettleIncomeApplyDto settleIncomeApplyDto, String traceLogId);

    /**
     * 外汇汇入申请(客户API发起)
     *
     * @param settleIncomeApplyDto 外汇汇入申请参数
     * @param traceLogId           日志ID
     * @return 申请单号
     */
    Result<Long> settleIncomeApplyAPI(SettleIncomeApplyDto settleIncomeApplyDto, String traceLogId);

    /**
     * 商户汇入申请运营确认(后台运营人员操作)
     *
     * @param operateConfirmDto 外汇申请运营确认参数
     * @param traceLogId        日志ID
     * @return 申请单号
     */
    Result<Boolean> operateConfirm(OperateConfirmDto operateConfirmDto, String traceLogId);

    /**
     * 结汇上传订单明细文件服务
     * 由商户前台导入excel发起
     *
     * @param settleFileUploadDto 请求参数
     * @param traceLogId          日志id
     * @return 批次ID
     */
    Result<Long> settleFileUpload(SettleFileUploadDto settleFileUploadDto, String traceLogId);

    /**
     * 结汇上传订单明细文件服务(API)
     * 由商户前台导入excel发起
     *
     * @param settleFileUploadApiDto 请求参数
     * @param traceLogId             日志id
     * @return 批次ID
     */
    Result<Long> settleFileUploadFromApi(SettleFileUploadApiDto settleFileUploadApiDto, String traceLogId);

    /**
     * 结汇上传订单明细文件服务明细校验
     * 由商户前台导入excel发起
     * 此文件不入库，校验信息保存到redis
     *
     * @param settleFileVerifyDto 请求参数
     * @param traceLogId          日志id
     * @return 批次ID
     */
    Result<Long> settleFileVerify(SettleFileVerifyDto settleFileVerifyDto, String traceLogId);

    /**
     * 结汇上传订单明细文件服务明细校验结果返回
     * 由商户前台导入excel发起
     * 从缓存中查找
     *
     * @param fileBatchNo 请求参数
     * @param traceLogId  日志id
     * @return 批次ID
     */
    Result<Long> settleFileVerifyQuery(Long fileBatchNo, String traceLogId);

    /**
     * 收汇到账通知清算初审复审
     *
     * @param receiverAuditDto   审核对象
     * @param traceLogId 日志ID
     * @return 成功或失败
     */
    Result<Boolean> receiveAudit(ReceiverAuditDto receiverAuditDto, String traceLogId);

    /**
     * 运营设置匹配信息
     *
     * @param settleOperationSetDto 审核对象
     * @param traceLogId            日志ID
     * @return 成功或失败
     */
    @Deprecated
    Result<Boolean> operationSet(SettleOperationSetDto settleOperationSetDto, String traceLogId);

    /**
     * 手工重新清算
     *
     * @param orderId    宝付结汇批次号
     * @param traceLogId 日志ID
     * @return 成功或失败
     */
    Result<Boolean> manualSettlement(Long orderId, String traceLogId);

    /**
     * 收汇补录接口
     *
     * @param collectionMakeupDto 收汇补录参数
     * @param traceLogId          日志ID
     * @return 成功失败
     */
    Result<Boolean> collectionMakeup(CollectionMakeupDto collectionMakeupDto, String traceLogId);

    /**
     * 重发结汇申请
     *
     * @param batchNo       批次号
     * @param settleOrderId 结汇订单ID
     * @param traceLogId    日志ID
     * @return
     */
    Result<Boolean> reSendSettleApply(Long batchNo, Long settleOrderId, String traceLogId);

    /**
     * 文件处理，处理结果，匹配结果，结汇结果查询
     *
     * @param memberId   商户号
     * @param incomeNo   汇款流水号
     * @param traceLogId 日志id
     * @return SettleNotifyRespDto 查询结果
     */
    Result<SettleNotifyRespDto> settlementResultQuery(Long memberId, String incomeNo,  String traceLogId);

    /**
     * 运营后台上传结汇申请汇款凭证
     *
     * @param settlePaymentFileUploadDto 汇款凭证信息对象
     * @param traceLogId            日志ID
     * @return 成功或失败
     */
    Result<Boolean> operationSettlePaymentUpload(SettlePaymentFileUploadDto settlePaymentFileUploadDto, String traceLogId);
}
