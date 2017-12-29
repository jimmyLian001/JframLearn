package com.baofu.cbpayservice.biz;

/**
 * 跨境订单购汇Biz层相关操作
 * <p>
 * 1、自动购汇
 * 2、手动购汇
 * </p>
 * User: wanght Date:2016/12/19 ProjectName: cbpay-service Version: 1.0
 */
public interface CbPayPurchaseBiz {

    /**
     * 自动购汇
     *
     * @param channelId 渠道ID
     * @param time      时间
     */
    void doAutoPurchase(Long channelId, String time);

}
