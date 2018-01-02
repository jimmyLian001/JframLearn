package com.baofu.international.global.account.core.manager.impl;

import com.baofu.international.global.account.core.common.constant.NumberDict;
import com.baofu.international.global.account.core.common.enums.UserTypeEnum;
import com.baofu.international.global.account.core.dal.mapper.UserAccountApplyMapper;
import com.baofu.international.global.account.core.dal.mapper.UserAccountBalMapper;
import com.baofu.international.global.account.core.dal.mapper.UserAccountMapper;
import com.baofu.international.global.account.core.dal.model.*;
import com.baofu.international.global.account.core.manager.ApplyAccountManager;
import com.baofu.international.global.account.core.manager.UserInfoManager;
import com.baofu.international.global.account.core.manager.UserOrgManager;
import com.baofu.international.global.account.core.manager.UserPersonalManager;
import com.system.commons.utils.ParamValidate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * User: yangjian  Date: 2017-11-04 ProjectName:  Version: 1.0
 */
@Slf4j
@Component
public class ApplyAccountManagerImpl implements ApplyAccountManager {

    /**
     *
     */
    @Autowired
    private UserAccountMapper accountMapper;
    /**
     *
     */
    @Autowired
    private UserAccountApplyMapper accountApplyMapper;
    /**
     *
     */
    @Autowired
    private UserAccountBalMapper tUserAccountBalMapper;

    /**
     * 账户号生成工具类
     */
    @Autowired
    private AccountNoManagerImplBase accountNoManager;

    /**
     * 用户信息Manager
     */
    @Autowired
    private UserInfoManager userInfoManager;

    /**
     * 个人用户Manager
     */
    @Autowired
    private UserPersonalManager userPersonalManager;

    /**
     * 企业用户
     */
    @Autowired
    private UserOrgManager userOrgManager;


    /**
     * dddd
     * 新增申请记录
     *
     * @param accountApplyDo accountApplyDo
     */
    @Override
    public void addApplyRecourd(UserPayeeAccountApplyDo accountApplyDo) {
        accountApplyMapper.insert(accountApplyDo);
    }

    /**
     * 新增开户首次返回账号信息
     *
     * @param applyNo  申请编号
     * @param walletId walletId
     */
    @Override
    public Long addAccountInfo(Long applyNo, String walletId) {

        UserPayeeAccountApplyDo applyInfo = accountApplyMapper.selectByApplyId(applyNo);
        UserPayeeAccountDo userPayeeAccountDo = new UserPayeeAccountDo();
        userPayeeAccountDo.setWalletId(walletId);
        userPayeeAccountDo.setStatus(NumberDict.ONE);
        userPayeeAccountDo.setCcy(applyInfo.getCcy());
        userPayeeAccountDo.setUserNo(applyInfo.getUserNo());
        userPayeeAccountDo.setChannelId(applyInfo.getChannelId());
        userPayeeAccountDo.setQualifiedNo(applyInfo.getQualifiedNo());
        UserInfoDo userInfoDo = userInfoManager.selectUserInfoByUserNo(applyInfo.getUserNo());
        //根据用户类型判断查询用户信息，保存用户账户名称
        if (UserTypeEnum.PERSONAL.getType() == userInfoDo.getUserType()) {
            UserPersonalDo userPersonalDo = userPersonalManager.selectInfoByQualifiedNo(applyInfo.getQualifiedNo());
            userPayeeAccountDo.setBankAccName(userPersonalDo.getEnName());
        } else {
            UserOrgDo userOrgDo = userOrgManager.selectInfoByQualifiedNo(applyInfo.getQualifiedNo());
            userPayeeAccountDo.setBankAccName(userOrgDo.getEnName());
        }
        //生成账户号
        userPayeeAccountDo.setAccountNo(accountNoManager.generateLongAccountNo());
        ParamValidate.checkUpdate(accountMapper.insert(userPayeeAccountDo));

        return userPayeeAccountDo.getAccountNo();
    }

    @Override
    public void modifyAccountInfo(UserPayeeAccountDo userPayeeAccountDo) {
        accountMapper.updateAccountByAccountNo(userPayeeAccountDo);
    }

    /**
     * 根据账户号查询当前账户信息
     *
     * @param accountNo 账户号
     */
    @Override
    public UserPayeeAccountDo queryAccountInfo(Long accountNo) {
        return accountMapper.selectUserAccountAccNo(accountNo);
    }

    /**
     * 根据申请的acc_no更新申请状态
     *
     * @param accountApplyDo accountApplyDo
     */
    @Override
    public void modifyApplyStatus(UserPayeeAccountApplyDo accountApplyDo) {
        accountApplyMapper.updateStatusByApplyId(accountApplyDo);
    }

    /**
     * 新增账户余额表
     *
     * @param userAccountBalDo userAccountBalDo
     */
    @Override
    public void addAccountBal(UserAccountBalDo userAccountBalDo) {

        ParamValidate.checkUpdate(tUserAccountBalMapper.insert(userAccountBalDo));
    }

    /**
     * 根据申请号查询
     *
     * @param accountApplyDo accountApplyDo
     * @return list
     */
    @Override
    public List<UserPayeeAccountApplyDo> getApplyAccountByApplyNo(UserPayeeAccountApplyDo accountApplyDo) {
        return accountApplyMapper.selectByApplyInfo(accountApplyDo);
    }

    /**
     * 根据订单号去查询
     *
     * @param applyId applyId
     * @return 返回结果
     */
    @Override
    public UserPayeeAccountApplyDo queryApplyAccountByApplyId(Long applyId) {
        return accountApplyMapper.selectByApplyId(applyId);
    }

    /**
     * 店铺编号
     *
     * @param storeNo 店铺编号
     * @return 返回结果
     */
    @Override
    public UserPayeeAccountApplyDo queryApplyAccountByStoreNo(Long storeNo) {

        return accountApplyMapper.selectByStoreNo(storeNo);
    }

    /**
     * 根据订单号去查询
     *
     * @param qualifiedNo qualifiedNo
     * @return 返回结果
     */
    @Override
    public List<UserPayeeAccountApplyDo> queryApplyAccountByQualifiedNo(Long qualifiedNo) {
        return accountApplyMapper.selectByQualifiedNo(qualifiedNo);
    }

    /**
     * 查询用户申请币种信息
     *
     * @param userNo 用户号
     * @return 币种集合
     */
    @Override
    public List<String> queryAccApplyCcyByUserNo(Long userNo) {

        return accountApplyMapper.selectAccApplyCcyByUserNo(userNo);
    }
}
