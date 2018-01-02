package com.baofu.international.global.account.core.manager.impl;

import com.baofu.international.global.account.core.dal.mapper.UserAccountBalMapper;
import com.baofu.international.global.account.core.dal.model.UserAccountBalDo;
import com.baofu.international.global.account.core.manager.UserAccountBalManager;
import com.system.commons.utils.ParamValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 账户余额信息模型
 * <p>
 * 1、用户账余额信息
 * </p>
 * User: 陶伟超 Date: 2017/11/7 ProjectName: account-core Version: 1.0.0
 */
@Repository
public class UserAccountBalManagerImpl implements UserAccountBalManager {

    /**
     * 账户余额 mapper
     */
    @Autowired
    private UserAccountBalMapper tUserAccountBalMapper;

    /**
     * 根据用户银行卡号，会员号查询余额信息
     *
     * @param userNo    用户号
     * @param accountNo 账户号
     * @return 余额Do
     */
    @Override
    public UserAccountBalDo queryBalInfoByKeys(Long userNo, Long accountNo) {

        return tUserAccountBalMapper.selectBalByAccNo(accountNo, userNo);
    }

    /**
     * 更新账户余额
     *
     * @param userAccountBalDo 账户余额信息
     */
    @Override
    public void modifyAccountBal(UserAccountBalDo userAccountBalDo) {

        ParamValidate.checkUpdate(tUserAccountBalMapper.updateAccountBal(userAccountBalDo), "更新账户余额失败");
    }

}
