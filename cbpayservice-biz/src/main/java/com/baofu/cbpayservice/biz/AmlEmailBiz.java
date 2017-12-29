package com.baofu.cbpayservice.biz;

import com.baofu.cbpayservice.dal.models.FiCbPayRemittanceDo;

/**
 * 发洗钱流程发送邮件通知
 * <p>
 * User: 不良人 Date:2017/7/14 ProjectName: cbpayservice Version: 1.0
 */
public interface AmlEmailBiz {

    /**
     * 反洗钱失败通知商户
     *
     * @param fiCbPayRemittanceDo 汇款批次信息
     */
    void amlFailToMemberEmail(FiCbPayRemittanceDo fiCbPayRemittanceDo);

    /**
     * 反洗钱失败通知业务人员
     *
     * @param fiCbPayRemittanceDo 汇款批次信息
     */
    void amlFailToBusinessEmail(FiCbPayRemittanceDo fiCbPayRemittanceDo);

    /**
     * 反洗钱部分成功通知商户
     *
     * @param fiCbPayRemittanceDo 汇款批次信息
     */
    void amlPortionSuccessToMemberEmail(FiCbPayRemittanceDo fiCbPayRemittanceDo);

    /**
     * 反洗钱失败通知业务人员
     *
     * @param fiCbPayRemittanceDo 汇款批次信息
     */
    void amlPortionSuccessToBusinessEmail(FiCbPayRemittanceDo fiCbPayRemittanceDo);
}
