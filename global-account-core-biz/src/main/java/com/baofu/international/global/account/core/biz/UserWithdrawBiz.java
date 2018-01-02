package com.baofu.international.global.account.core.biz;

import com.baofu.international.global.account.core.biz.models.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * 功能：用户自主注册平台提现
 * User: feng_jiang Date:2017/11/5 ProjectName: globalaccount-core Version: 1.0
 **/
public interface UserWithdrawBiz {

    /**
     * 功能：用户提现
     *
     * @param userWithdrawCashBo 提现参数
     * @return 提现订单
     */
    void userWithdrawCash(UserWithdrawCashBo userWithdrawCashBo);

    /**
     * 功能：发送渠道转账申请
     * @param userNo     用户号
     * @param accountNo  账户号
     * @param withdrawId 提现订单号
     */
    void sendCgwTransferApply(Long userNo, Long accountNo, Long withdrawId);

    /**
     * 功能：宝付转账至备付金(归集)
     */
    void execTransferDeposit(Long channelId);

    /**
     * 功能：用户转账分发
     *
     * @param userWithdrawDistributeBo 用户转账分发参数
     */
    void userWithdrawDistribute(UserWithdrawDistributeBo userWithdrawDistributeBo);

    /**
     * 功能：获取中行锁定汇率
     *
     * @param withdrawCcy 提现币种
     * @return 锁定中行汇率
     */
    BigDecimal queryWithdrawRate(String withdrawCcy);

    /**
     * 功能：查询提现店铺账户余额信息
     *
     * @param userNo 用户号
     * @return 可提现店铺账户信息
     */
    List<WithdrawAccountRespBo> queryWithdrawAccountInfo(Long userNo);

    /**
     * 功能：计算用户提现手续费
     *
     * @param userWithdrawFeeReqBo 计算用户提现手续费参数
     * @return 计算用户提现手续费
     */
    WithdrawCashFeeRespBo withdrawCashFee(UserWithdrawFeeReqBo userWithdrawFeeReqBo);
}
