package com.baofu.cbpayservice.biz;

import com.baofu.cbpayservice.biz.models.CbPayOrderReqBo;

/**
 * 跨境订单Biz层相关操作
 * <p>
 * 1、添加跨境订单信息和B2C网银订单信息
 * </p>
 * User: 香克斯 Date:2016/10/25 ProjectName: asias-icpaygate Version: 1.0
 */
public interface CbPayOrderBiz {

    /**
     * 添加跨境订单信息和B2C网银订单信息
     *
     * @param cbPayOrderReqBO 跨境订单信息
     * @return 返回宝付订单号
     */
    Long addCbPayOrder(CbPayOrderReqBo cbPayOrderReqBO);

    /**
     * 查询订单数量(定制)
     *
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param memberId  商户号
     * @return 返回订单数量
     */
    Long queryCbPayOrderCount(String beginTime, String endTime, Long memberId);
}
