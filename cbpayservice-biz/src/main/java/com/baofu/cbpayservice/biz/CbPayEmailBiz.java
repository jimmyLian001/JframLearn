package com.baofu.cbpayservice.biz;

/**
 * 跨境人民币邮件处理Biz层相关操作
 * <p>
 * 1、付汇成功发送明细邮件
 * </p>
 * User: wanght Date:2017/04/06 ProjectName: cbpay-service Version: 1.0
 */
public interface CbPayEmailBiz {

    /**
     * 付汇成功发送明细邮件
     *
     * @param batchNo         批次号
     * @param downloadDetail  下载地址
     * @param emailToReceiver 收件人
     * @param emailCcReceiver 抄送人
     * @return 发送结果
     */
    Boolean sendUploadFile(String batchNo, String downloadDetail, String emailToReceiver, String emailCcReceiver);

    /**
     * 邮件通知清算
     *
     * @param batchNo          批次号
     * @param emailToReceiver  收件人
     * @param emailCcReceiver  抄送人
     * @param emailBCCReceiver 密送人
     * @return 通知结果
     */
    Boolean sendEmailNotifySettle(String batchNo, String emailToReceiver, String emailCcReceiver, String emailBCCReceiver);
}
