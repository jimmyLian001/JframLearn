package com.baofu.cbpayservice.facade;

import com.baofu.cbpayservice.facade.models.CbPayOrderNotifyReqDto;
import com.system.commons.result.Result;

import java.util.List;

/**
 * 网关通知商户相关服务
 * <p>
 * 1、交易通知商户接口
 * 2、批量通知商户
 * 3、发送通知商户MQ接口信息
 * </p>
 * User: wanght Date:2016/11/03 ProjectName: cbpay-service Version: 1.0
 */
public interface CbPayNotifyMerchantFacade {

    /**
     * 通知商户接口
     *
     * @param orderId    订单号
     * @param operator   操作员
     * @param traceLogId 全局id
     * @return 返回是否成功
     */
    Result<Boolean> notifyMerchant(Long orderId, String operator, String traceLogId);

    /**
     * 批量通知商户
     *
     * @param cbPayOrderNotifyReqDto 通知商户参数信息
     * @param traceLogId             日志id
     * @return 返回通知失败的宝付订单号
     */
    Result<List<Long>> notifyMember(CbPayOrderNotifyReqDto cbPayOrderNotifyReqDto, String traceLogId);

    /**
     * 发送通知商户MQ接口信息
     *
     * @param orderId    宝付订单号
     * @param traceLogId 日志ID
     * @return 返回结果
     */
    Result<Boolean> sendNotifyMemberMq(Long orderId, String traceLogId);
}
