package com.baofu.international.global.account.core.biz;

import com.baofu.international.global.account.core.biz.models.AccBalanceBo;
import com.baofu.international.global.account.core.biz.models.UserAccountBalBo;

/**
 * 用户余额相关服务
 * <p>
 * 1、查询账户余额
 * </p>
 * User: 香克斯  Date: 2017/11/14 ProjectName:account-core  Version: 1.0
 */
public interface UserBalBiz {

    /**
     * 功能：查询账户余额
     *
     * @param userNo    用户号
     * @param accountNo 账户号
     * @return 返回账户信息
     */
    UserAccountBalBo queryUserAccountBal(Long userNo, Long accountNo);

    /**
     * 功能：查询账户币种余额(含约人民币余额)
     *
     * @param userNo 用户号
     * @param ccy    币种
     * @return AccBalanceBo 账户余额信息
     */
    AccBalanceBo queryCcyBalance(Long userNo, String ccy);

    /**
     * 功能：根据虚拟账号查询账户余额
     *
     * @param walletId  虚拟账号
     * @param channelId 渠道
     * @return 返回账户信息
     */
    UserAccountBalBo queryAccountBalByWalletId(String walletId, Long channelId);
}
