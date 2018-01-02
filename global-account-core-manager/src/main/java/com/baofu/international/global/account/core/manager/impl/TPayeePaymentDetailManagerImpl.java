package com.baofu.international.global.account.core.manager.impl;

import com.baofu.international.global.account.core.dal.mapper.PaymentDetailMapper;
import com.baofu.international.global.account.core.dal.model.PaymentDetailDo;
import com.baofu.international.global.account.core.manager.TPayeePaymentDetailManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * description:收款账户收支明细 ManagerImpl
 * <p/>
 * User: feng_jiang Date:2017/11/5 ProjectName: globalaccount-core Version: 1.0
 */
@Slf4j
@Repository
public class TPayeePaymentDetailManagerImpl implements TPayeePaymentDetailManager {

    /**
     * 收款账户收支明细 Mapper
     */
    @Autowired
    private PaymentDetailMapper paymentDetailMapper;

    /**
     * 查询收支明细信息
     *
     * @param bankRespId 请求参数
     * @param userNo     用户号
     * @return 收支明细信息
     */
    @Override
    public PaymentDetailDo queryPaymentDetail(String bankRespId, Long userNo) {

        PaymentDetailDo paymentDetailDo = new PaymentDetailDo();
        paymentDetailDo.setBankRespId(bankRespId);
        paymentDetailDo.setUserNo(userNo);
        return paymentDetailMapper.selectPaymentDetail(paymentDetailDo);
    }

    /**
     * 新增收款账户收支明细
     *
     * @param tPayeePaymentDetailDo 收款账户收支明细
     */
    @Override
    public void addPaymentDetail(PaymentDetailDo tPayeePaymentDetailDo) {
        paymentDetailMapper.insertPaymentDetail(tPayeePaymentDetailDo);
    }
}
