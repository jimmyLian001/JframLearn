package com.baofu.international.global.account.core.manager.impl;

import com.baofu.international.global.account.core.common.constant.Constants;
import com.baofu.international.global.account.core.common.util.SecurityUtil;
import com.baofu.international.global.account.core.dal.mapper.AuthApplyMapper;
import com.baofu.international.global.account.core.dal.mapper.AuthBankMapper;
import com.baofu.international.global.account.core.dal.model.AuthApplyDo;
import com.baofu.international.global.account.core.dal.model.AuthBankDo;
import com.baofu.international.global.account.core.manager.BankAuthApplyManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 描述
 * <p>
 * User: lian zd Date:2017/11/9 ProjectName: account-core Version:1.0
 */
@Repository
public class BankAuthApplyManagerImpl implements BankAuthApplyManager {
    /**
     * 认证申请操作mapper
     */
    @Autowired
    private AuthApplyMapper tAuthApplyMapper;

    /**
     * 银行卡认证操作mapper
     */
    @Autowired
    private AuthBankMapper tAuthBankMapper;

    /**
     * 新增认证申请记录
     *
     * @param tAuthApplyDo 认证申请信息
     */
    @Override
    public void addAuthApply(AuthApplyDo tAuthApplyDo) {
        tAuthApplyMapper.insert(tAuthApplyDo);
    }

    /**
     * 新增银行卡认证记录
     *
     * @param authBankDo 银行卡信息
     */
    @Override
    public void addAuthBank(AuthBankDo authBankDo) {
        authBankDo.setCardNo(SecurityUtil.desEncrypt(authBankDo.getCardNo(), Constants.CARD_DES_KEY));
        authBankDo.setIdNo(SecurityUtil.desEncrypt(authBankDo.getIdNo(), Constants.CARD_DES_KEY));
        tAuthBankMapper.insert(authBankDo);
    }
}
