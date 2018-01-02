package com.baofu.international.global.account.core.biz;

import com.baofu.international.global.account.core.biz.models.AccountDetailBo;

/**
 * 功能：用户收支明细处理
 * User: feng_jiang Date:2017/11/5 ProjectName: globalaccount-core Version: 1.0
 **/
public interface UserAccountPaymentDetailBiz {

    /**
     * 功能：处理收支明细
     *
     * @param accountDetailBo 收款账户收支明细
     */
    void dealPayeePaymentDetail(AccountDetailBo accountDetailBo);
}
