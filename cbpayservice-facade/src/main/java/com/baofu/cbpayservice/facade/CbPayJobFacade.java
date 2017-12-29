package com.baofu.cbpayservice.facade;

/**
 * 跨境人民币定时任务接口
 * <p>
 * 1、中行自动汇款审核任务
 * 2、中行自动查询结算订单任务
 * 3、平安自动汇款审核任务
 * 4、平安自动查询结算订单任务
 * </p>
 * User: wanght Date:2016/12/29 ProjectName: cbpay-service Version: 1.0
 */
public interface CbPayJobFacade {

    /**
     * 中行自动汇款审核任务
     */
    void bocAutoAuditJob();

    /**
     * 自动购汇
     *
     * @param channelId  渠道id
     * @param traceLogId 日志ID
     */
    void autoPurchase(Long channelId, String traceLogId);

    /**
     * 自动查询汇率
     *
     * @param channelId  渠道id
     * @param traceLogId 日志ID
     */
    void autoQueryExchange(Long channelId, String traceLogId);

    /**
     * 跨境订单风控定时任务  wdj
     * date 需要重新风控的日期
     *
     * @param traceLogId 日志ID
     * @param date       日期
     */
    void cbPayOrderRiskControl(String traceLogId, String date);

    /**
     * 结汇订单风控
     *
     * @param traceLogId 日志ID
     * @param date       日期
     */
    void cbPaySettleOrderRiskControl(String traceLogId, String date);

    /**
     * 海关实名认证统计发送邮件  定时任务  每天早晨9:00发送邮件
     * 邮件统计数据时间段是：昨天09:00~18:00
     *
     * @param traceLogId 日志ID
     */
    void customsVerifyStatisticsEmail(String traceLogId);

}
