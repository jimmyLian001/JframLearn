package com.baofu.international.global.account.core.manager.impl;

import com.baofu.international.global.account.core.common.constant.Constants;
import com.baofu.international.global.account.core.common.enums.ErrorCodeEnum;
import com.baofu.international.global.account.core.common.util.SecurityUtil;
import com.baofu.international.global.account.core.dal.mapper.UserBankCardInfoMapper;
import com.baofu.international.global.account.core.dal.mapper.UserLoginInfoMapper;
import com.baofu.international.global.account.core.dal.model.UserBankCardInfoDo;
import com.baofu.international.global.account.core.dal.model.UserLoginInfoDo;
import com.baofu.international.global.account.core.manager.UserBankCardManager;
import com.system.commons.exception.BizServiceException;
import com.system.commons.utils.ParamValidate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户提现银行卡描述信息
 * <p>
 * 1、用户提现银行卡
 * </p>
 * User: 陶伟超 Date: 2017/11/6 ProjectName: account-core Version: 1.0.0
 */
@Slf4j
@Repository
public class UserBankCardManagerImpl implements UserBankCardManager {

    /**
     * 用户银行卡服务
     */
    @Autowired
    private UserBankCardInfoMapper userBankCardInfoMapper;

    /**
     * 用户登录信息服务
     */
    @Autowired
    private UserLoginInfoMapper userLoginInfoMapper;

    /**
     * 根据userNo查询出银行卡信息
     *
     * @param userNo 会员号
     * @return 用户银行卡list
     */
    @Override
    public List<UserBankCardInfoDo> selectUserBankCardByUserNo(Long userNo) {
        return userBankCardInfoMapper.selectUserBankCardByUserNo(userNo);
    }

    /**
     * 根据用户号和记录编号获取银行卡信息
     *
     * @param userNo           会员号
     * @param bankCardRecordNo 银行卡记录编号
     * @return 用户银行卡list
     */
    @Override
    public UserBankCardInfoDo selectUserBankCardByUserNo(Long userNo, Long bankCardRecordNo) {
        //查询银行卡信息
        UserBankCardInfoDo userBankCardInfoDo = userBankCardInfoMapper.selectUserBankCardNo(userNo, bankCardRecordNo);
        if (userBankCardInfoDo == null) {
            log.warn("根据用户号和记录编号获取银行卡信息为空，用户号：{},记录编号：{}", userNo, bankCardRecordNo);
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190106);
        }
        userBankCardInfoDo.setCardNo(SecurityUtil.desDecrypt(userBankCardInfoDo.getCardNo(), Constants.CARD_DES_KEY));
        return userBankCardInfoDo;
    }

    /**
     * 根据会员号更新银行卡状态
     *
     * @param userNo   会员号
     * @param state    银行卡状态
     * @param updateBy 更新人
     * @return 更新成功记录数
     */
    @Override
    public Integer updateBankCartState(Long userNo, int state, String updateBy) {
        return userBankCardInfoMapper.updateBankCartState(userNo, state, updateBy);
    }

    /**
     * 添加用户银行信息
     *
     * @param userBankCardInfoDo 用户银行卡信息
     */
    @Override
    public void addUserBankCard(UserBankCardInfoDo userBankCardInfoDo) {

        userBankCardInfoDo.setCardNo(SecurityUtil.desEncrypt(userBankCardInfoDo.getCardNo(), Constants.CARD_DES_KEY));
        ParamValidate.checkUpdate(userBankCardInfoMapper.insert(userBankCardInfoDo));
    }

    /**
     * 删除银行卡信息，逻辑删除
     *
     * @param userNo   用户号
     * @param recordNo 记录编号
     */
    @Override
    public void removeUserBankCard(Long userNo, Long recordNo) {

        ParamValidate.checkUpdate(userBankCardInfoMapper.delByUserBankCardNo(userNo, recordNo), "用户银行卡解绑失败");
    }

    /**
     * 根据用户号查询用户登录信息
     *
     * @param userNo 用户号
     * @return UserLoginInfoDo
     */
    @Override
    public List<UserLoginInfoDo> queryUserInfoListByUserNo(Long userNo) {
        return userLoginInfoMapper.queryUserInfoListByUserNo(userNo);
    }
}

