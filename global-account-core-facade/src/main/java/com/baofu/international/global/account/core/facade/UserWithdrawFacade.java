package com.baofu.international.global.account.core.facade;

import com.baofu.international.global.account.core.facade.model.AccBalanceDto;
import com.baofu.international.global.account.core.facade.model.UserWithdrawDto;
import com.baofu.international.global.account.core.facade.model.UserWithdrawFeeDto;
import com.baofu.international.global.account.core.facade.model.res.WithdrawAccountRespDto;
import com.baofu.international.global.account.core.facade.model.res.WithdrawCashFeeRespDto;
import com.system.commons.result.Result;

import java.util.List;

/**
 * 功能：用户自主注册平台提现
 * User: feng_jiang Date:2017/11/5 ProjectName: globalaccount-core Version: 1.0
 **/
public interface UserWithdrawFacade {

    /**
     * 功能：查询提现店铺账户余额信息
     *
     * @param userNo     用户号
     * @param traceLogId 日志ID
     * @return 可提现店铺账户信息
     */
    Result<List<WithdrawAccountRespDto>> queryWithdrawAccountInfo(Long userNo, String traceLogId);

    /**
     * 功能：提现手续费、费率试算
     *
     * @param userWithdrawFeeDto 转账金额
     * @param traceLogId         日志ID
     * @return 手续费金额
     */
    Result<List<WithdrawCashFeeRespDto>> withdrawCashFee(UserWithdrawFeeDto userWithdrawFeeDto, String traceLogId);

    /**
     * 功能：用户前台提现
     *
     * @param userWithdrawDto 用户提现请求参数
     * @param traceLogId      日志ID
     * @return 提现订单
     */
    Result<Boolean> userWithdrawCash(UserWithdrawDto userWithdrawDto, String traceLogId);

    /**
     * 功能：查询账户币种账户余额，人民币余额，待入账金额
     *
     * @param userNo     用户号
     * @param ccy        币种
     * @param traceLogId 日志IID
     * @return AccBalanceDto 币种账户信息
     */
    Result<AccBalanceDto> queryCcyBalance(Long userNo, String ccy, String traceLogId);
}
