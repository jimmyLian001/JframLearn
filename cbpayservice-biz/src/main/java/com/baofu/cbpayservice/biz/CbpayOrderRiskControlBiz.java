package com.baofu.cbpayservice.biz;

import java.util.List;

/**
 * 跨境订单风控
 * <p>
 * 1、判断是否有redis标识
 * 2、创建redis标识
 * </p>
 * User: wdj Date:2017/04/27 ProjectName: cbpayservice Version: 1.0
 */
public interface CbpayOrderRiskControlBiz {

    /**
     * 判断是否有redis标识
     *
     * @param key        redisKey
     * @param traceLogId 日志ID
     * @return 返回处理结果
     */
    Boolean isComplete(String key, String traceLogId);

    /**
     * 跨境订单风控定时任务接口
     *
     * @param traceLogId 日志ID
     * @param date       指定特定风控时间
     */
    void doCbPayOrderRiskControl(String traceLogId, String date);

    /**
     * 风控MQ消费处理方法
     *
     * @param batchNos 汇款批次号
     */
    void handelOrderRiskControl(List<String> batchNos);

    /**
     * 执行结汇订单风控任务
     *
     * @param traceLogId 日志ID
     * @param date       指定特定风控时间
     */
    void doCbPayOrderRiskControlOfSettleV2(String traceLogId, String date);

    /**
     * 结汇风控MQ处理
     *
     * @param batchNos 文件批次号
     */
    void handelSettleOrderRiskControl(List<String> batchNos);
}
