package com.baofu.international.global.account.core.manager;

import com.baofu.international.global.account.core.dal.model.UserBankCardInfoDo;
import com.baofu.international.global.account.core.dal.model.UserLoginInfoDo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户银行卡描述信息
 * <p>
 * 1、用户银行卡 manager
 * </p>
 * User: 陶伟超 Date: 2017/11/6 ProjectName: account-core Version: 1.0.0
 */
public interface UserBankCardManager {

    /**
     * 根据用户号获取银行卡信息
     *
     * @param userNo 会员号
     * @return 用户银行卡list
     */
    List<UserBankCardInfoDo> selectUserBankCardByUserNo(Long userNo);

    /**
     * 根据用户号和记录编号获取银行卡信息
     *
     * @param bankCardRecordNo 银行卡记录编号
     * @param userNo           会员号
     * @return 用户银行卡list
     */
    UserBankCardInfoDo selectUserBankCardByUserNo(Long userNo, Long bankCardRecordNo);


    /**
     * 更新银行卡信息(若人工审核失败更新银行卡信息为失效)
     *
     * @param userNo 会员号
     * @return 更新记录数
     */
    Integer updateBankCartState(@Param("userNo") Long userNo, @Param("state") int state,
                                @Param("updateBy") String updateBy);

    /**
     * 添加用户银行信息
     *
     * @param userBankCardInfoDo 用户银行卡信息
     */
    void addUserBankCard(UserBankCardInfoDo userBankCardInfoDo);

    /**
     * 删除银行卡信息，逻辑删除
     *
     * @param userNo   用户号
     * @param recordNo 记录编号
     */
    void removeUserBankCard(Long userNo, Long recordNo);

    /**
     * 根据用户号查询用户登录信息
     *
     * @param userNo 用户号
     * @return UserLoginInfoDo
     */
    List<UserLoginInfoDo> queryUserInfoListByUserNo(Long userNo);
}
