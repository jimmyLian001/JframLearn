package com.baofu.cbpayservice.biz;

/**
 * 跨境人民币通知清算处理
 * <p>
 * 1、通知清算系统
 * </p>
 * User: wanght Date:2017/02/27 ProjectName: cbpay-service Version: 1.0
 */
public interface CbPayNotifySettleBiz {

    /**
     * 通知清算系统
     *
     * @param batchNo    宝付订单号
     * @param operatorBy 操作用户
     * @return 通知结果
     */
    Boolean notifySettle(String batchNo, String operatorBy);
}
