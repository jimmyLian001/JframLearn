package com.baofu.international.global.account.core.manager;

import com.baofu.international.global.account.core.dal.model.UserAccountBalDo;

/**
 * 账户余额信息模型
 * <p>
 * 1、用户账余额信息
 * </p>
 * User: 陶伟超 Date: 2017/11/7 ProjectName: account-core Version: 1.0.0
 */
public interface UserAccountBalManager {

    /**
     * 根据用户账户号和会员号查询余额信息
     *
     * @param userNo    用户号
     * @param accountNo 账户号
     * @return 余额Do对象
     */
    UserAccountBalDo queryBalInfoByKeys(Long userNo, Long accountNo);

    /**
     * 更新账户余额
     *
     * @param userAccountBalDo 账户余额信息
     */
    void modifyAccountBal(UserAccountBalDo userAccountBalDo);
}
