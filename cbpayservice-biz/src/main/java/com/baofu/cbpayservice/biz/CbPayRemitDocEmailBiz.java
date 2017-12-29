package com.baofu.cbpayservice.biz;

/**
 * 汇款成功发送汇款凭证
 * <p>
 * Created by 莫小阳 on 2017/7/17.
 */
public interface CbPayRemitDocEmailBiz {

    /**
     * 发送汇款凭证
     *
     * @param batchNo 汇款批次号
     */
    void sendRemitDocEmail(String batchNo);
}
