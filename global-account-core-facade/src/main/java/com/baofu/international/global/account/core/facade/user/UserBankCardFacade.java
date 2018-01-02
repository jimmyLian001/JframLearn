package com.baofu.international.global.account.core.facade.user;

import com.baofu.international.global.account.core.facade.model.user.UserBankCardInfoDto;
import com.system.commons.result.Result;

import java.util.List;

/**
 * 用户银行卡服务类
 * <p>
 * 1、根据用户号查询用户银行卡信息
 * 2、根据用户号、银行卡记录编号后查询用户银行卡信息
 * </p>
 * User: 陶伟超 Date: 2017/11/4 ProjectName: account-core Version: 1.0.0
 */
public interface UserBankCardFacade {

    /**
     * 根据用户号查询用户银行卡信息
     *
     * @param userNo 会员号
     * @param logId  日志ID
     * @return List
     */
    Result<List<UserBankCardInfoDto>> findUserBankCardInfo(Long userNo, String logId);

    /**
     * 根据用户号、银行卡记录编号后查询用户银行卡信息
     *
     * @param userNo           用户号
     * @param bankCardRecordNo 银行卡记录编号
     * @param traceLogId       日志ID
     * @return 返回银行卡信息
     */
    Result<UserBankCardInfoDto> findUserBankCard(Long userNo, Long bankCardRecordNo, String traceLogId);

}
