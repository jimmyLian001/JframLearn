package com.baofu.cbpayservice.biz;

/**
 * 异步通知服务
 * <p>
 * User: 不良人 Date:2017/9/22 ProjectName: cbpayservice Version: 1.0
 */
public interface NotifyBiz {

    /**
     * 通知商户
     *
     * @param memberId     商户号
     * @param terminalId   终端号
     * @param notifyUrl    通知地址
     * @param t            通知内容
     * @param memberReqId  商户请求流水号
     * @param businessType 业务类型
     */
    <T> void notifyMember(String memberId, String terminalId, String notifyUrl, T t, String memberReqId, String businessType);

    /**
     * 通知商户
     *
     * @param memberId   商户号
     * @param terminalId 终端号
     * @param notifyUrl  通知地址
     * @param t          通知内容
     */
    <T> void notifyMember(String memberId, String terminalId, String notifyUrl, T t);
}
