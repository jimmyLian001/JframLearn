package com.baofu.cbpayservice.facade;

import com.system.commons.result.Result;

/**
 * 跨境人民币人工通知清算接口
 * <p>
 * 1、人工通知清算
 * </p>
 * User: wanght Date:2017/02/27 ProjectName: cbpay-service Version: 1.0
 */
public interface CbPayNotifySettleFacade {

    /**
     * 人工通知清算
     *
     * @param batchNo    批次号
     * @param operator   操作员
     * @param traceLogId 全局id
     * @return 返回是否成功
     */
    Result<Boolean> notifySettle(String batchNo, String operator, String traceLogId);
}
