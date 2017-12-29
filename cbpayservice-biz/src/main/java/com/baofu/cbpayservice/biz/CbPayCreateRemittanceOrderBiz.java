package com.baofu.cbpayservice.biz;

import com.baofu.cbpayservice.biz.models.CbPayRemittanceReqBo;

/**
 * 跨境人民币创建汇款订单
 * <p>
 * 1、创建汇款订单
 * </p>
 * User: wanght Date:2017/03/14 ProjectName: cbpay-service Version: 1.0
 */
public interface CbPayCreateRemittanceOrderBiz {

    /**
     * 创建汇款订单
     *
     * @param cbPayRemittanceReqBo 请求参数
     * @return 汇款批次号
     */
    Long createRemittanceOrderV2(CbPayRemittanceReqBo cbPayRemittanceReqBo);
}
