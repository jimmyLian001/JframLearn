package com.baofu.cbpayservice.biz;

import java.math.BigDecimal;

/**
 * 结汇流程发送邮件通知
 * <p>
 * 1、收到银行发送到账通知后发送邮件给清算人员和结汇相关人员提示可以进行人工匹配
 * 2、收到商户前台录入的汇入汇款申请后提示清算人员和结汇相关人员可以进行人工匹配
 * 3、人工匹配之后发送邮件给结汇相关人员提示商户可以上传明细和商户
 * 4、结汇完成之后发送结汇成功邮件通知结汇相关人员和商户
 * </p>
 * <p>
 * User: 不良人 Date:2017/7/14 ProjectName: cbpayservice Version: 1.0
 */
public interface SettleEmailBiz {

    /**
     * 收到银行发送到账通知后发送邮件给清算人员和结汇相关人员提示可以进行人工匹配
     *
     * @param incomeNo  汇入汇款编号
     * @param incomeAmt 汇入实际收到金额
     * @param incomeCcy 汇入币种
     */
    void exchangeEarningsNotify(String incomeNo, BigDecimal incomeAmt, String incomeCcy);

    /**
     * 收到商户前台录入的汇入汇款申请后提示清算人员和结汇相关人员可以进行人工匹配
     *
     * @param memberId 商户号
     * @param TtNo     商户填写TT编号
     * @param orderAmt 商户填写汇款金额
     * @param orderCcy 商户填写汇款币总
     */
    void importApplyNotify(Long memberId, String TtNo, BigDecimal orderAmt, String orderCcy);

    /**
     * 结汇完成之后发送结汇成功邮件通知结汇相关人员和商户
     *
     * @param settleOrderId 结汇订单ID
     */
    void settleSuccessNotify( Long settleOrderId);
}
