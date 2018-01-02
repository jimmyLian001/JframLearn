package com.baofu.international.global.account.core.manager;

import com.baofu.international.global.account.core.dal.model.AuthApplyDo;
import com.baofu.international.global.account.core.dal.model.AuthBankDo;

/**
 * 银行卡认证申请操作接口
 * <p>
 * 1,新增认证申请记录
 * </p>
 * User: lian zd  Date: 2017/11/06 ProjectName: globalaccount Version: 1.0
 */
public interface BankAuthApplyManager {

    /**
     * 新增认证申请记录
     *
     * @param tAuthApplyDo 卡bin
     */
    void addAuthApply(AuthApplyDo tAuthApplyDo);

    /**
     * 新增银行卡认证记录
     *
     * @param authBankDo 银行卡信息
     */
    void addAuthBank(AuthBankDo authBankDo);
}
