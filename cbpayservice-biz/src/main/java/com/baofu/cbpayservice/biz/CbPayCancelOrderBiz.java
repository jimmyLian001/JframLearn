package com.baofu.cbpayservice.biz;

import com.baofu.cbpayservice.biz.models.CbPayCancelOrderBo;

/**
 * 取消订单biz服务
 * User: yangjian  Date: 2017-06-07 ProjectName:cbpay-service  Version: 1.0
 */
public interface CbPayCancelOrderBiz {

    /**
     * 取消订单
     *
     * @param cbPayCancelOrderBo 文件批次号
     */
    void cancelOrder(CbPayCancelOrderBo cbPayCancelOrderBo);
}
