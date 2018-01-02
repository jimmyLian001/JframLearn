package com.baofu.international.global.account.core.manager.impl;

import com.baofu.international.global.account.core.dal.mapper.UserAccountMapper;
import com.baofu.international.global.account.core.dal.model.UserAccInfoReqDo;
import com.baofu.international.global.account.core.dal.model.UserPayeeAccountDo;
import com.baofu.international.global.account.core.dal.model.UserStoreInfoRespDo;
import com.baofu.international.global.account.core.manager.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 1、方法描述
 * </p>
 * User: 香克斯  Date: 2017/11/7 ProjectName:account-core  Version: 1.0
 */
@Repository
public class UserPayeeAccountManagerImpl implements UserAccountManager {

    /**
     * 用戶收款賬戶信息管理
     */
    @Autowired
    private UserAccountMapper userAccountMapper;

    /**
     * 根据用户号和银行账户号查询收款账户信息
     *
     * @param userNo    用户编号
     * @param bankAccNo 银行账户
     * @return 返回收款账户信息
     */
    @Override
    public List<UserPayeeAccountDo> queryPayeeAccount(Long userNo, String bankAccNo) {

        //查詢用戶收款賬戶信息
        return userAccountMapper.selectPayeeAccount(userNo, bankAccNo);
    }

    /**
     * 根据用户号和银行账户号币种查询收款账户信息
     *
     * @param bankAccNo 银行账户
     * @param userNo    用户编号
     * @return 返回收款账户信息
     * @parm ccy 币种
     */
    @Override
    public List<UserPayeeAccountDo> queryPayeeAccount(Long userNo, String bankAccNo, String ccy) {

        //查詢用戶收款賬戶信息
        return userAccountMapper.selectPayeeAccountByCcy(userNo, bankAccNo, ccy);
    }

    /**
     * 根据账户号和币种信息查询账户wyreId
     *
     * @param userAccInfoReqDo 币种
     * @return BankAccNo集合
     */
    @Override
    public List<UserStoreInfoRespDo> queryUserStoreByCcy(UserAccInfoReqDo userAccInfoReqDo) {
        return userAccountMapper.queryUserStoreByCcy(userAccInfoReqDo);
    }

    /**
     * 用户账户信息查询
     *
     * @param userNo    用户号
     * @param accountNo 账户号
     * @return
     */
    @Override
    public UserPayeeAccountDo queryUserAccount(Long userNo, Long accountNo) {

        return userAccountMapper.selectUserAccountAccNo(accountNo);
    }


}
