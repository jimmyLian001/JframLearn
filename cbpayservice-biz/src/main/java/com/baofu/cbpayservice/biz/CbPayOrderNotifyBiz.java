package com.baofu.cbpayservice.biz;

/**
 * 人民币订单通知处理
 * <p>
 * 1、根据宝付订单号通知商户
 * </p>
 * User: 香克斯 Date:2016/10/28 ProjectName: cbpayservice Version: 1.0
 */
public interface CbPayOrderNotifyBiz {

    /**
     * 根据宝付订单号通知商户
     *
     * @param orderId    宝付订单号
     * @param operatorBy 操作用户
     */
    Boolean notifyMember(Long orderId, String operatorBy);

    /**
     * 根据宝付订单号修改报关状态  add  by  wdj
     *
     * @param orderId    宝付订单号
     * @param adminState 报关状态
     */
    void modifyStateByOrderId(Long orderId, String adminState);
}
