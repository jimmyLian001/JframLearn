package com.baofu.international.global.account.core.manager;

import com.baofu.international.global.account.core.dal.model.PaymentDetailDo;

/**
 * 功能：收款账户收支明细
 * User: feng_jiang Date:2017/11/5 ProjectName: globalaccount-core Version: 1.0
 */
public interface TPayeePaymentDetailManager {

    /**
     * 查询收款账户收支明细
     *
     * @param bankRespId 返回流水号
     * @param userNo     用户号
     * @return 收支明细信息
     */
    PaymentDetailDo queryPaymentDetail(String bankRespId, Long userNo);

    /**
     * 创建收款账户收支明细
     *
     * @param paymentDetailDo 收款账户收支明细
     */
    void addPaymentDetail(PaymentDetailDo paymentDetailDo);
}
