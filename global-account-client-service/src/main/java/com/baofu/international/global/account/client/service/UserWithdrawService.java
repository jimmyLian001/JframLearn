package com.baofu.international.global.account.client.service;

import com.baofu.international.global.account.client.service.models.UserWithdrawReqDto;
import com.baofu.international.global.account.core.facade.model.res.WithdrawAccountRespDto;
import com.baofu.international.global.account.core.facade.model.res.WithdrawCashFeeRespDto;

import java.util.List;

/**
 * <p>
 * 1、用户提现确认校验
 * </p>
 * User: 香克斯  Date: 2017/11/8 ProjectName:account-client  Version: 1.0
 */
public interface UserWithdrawService {

    /**
     * 查询提现店铺账户余额信息
     * @param userNo 用户号
     */
    List<WithdrawAccountRespDto> queryWithdrawAccountInfo(Long userNo);

    /**
     * 提现手续费、费率试算
     * @param userNo 用户号
     * @param list   用户提现信息
     */
    List<WithdrawCashFeeRespDto> withdrawCashFee(Long userNo, List<UserWithdrawReqDto> list);
}
