package com.baofu.cbpayservice.biz;

/**
 * 跨境订单自动审核Biz层相关操作
 * <p>
 * 1、汇款订单自动审核
 * </p>
 * User: wanght Date:2016/12/19 ProjectName: cbpay-service Version: 1.0
 */
public interface CbPayAutoAuditBiz {

    /**
     * 汇款订单自动审核
     *
     * @param channelId 渠道id
     * @param time      时间
     */
    void doAutoAudit(Long channelId, String time);
}
