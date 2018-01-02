package com.baofu.international.global.account.core.manager;

import com.baofu.international.global.account.core.dal.model.UserWithdrawApplyDo;

import java.util.List;

/**
 * 功能：用户提前申请表操作类
 * User: feng_jiang Date:2017/11/5 ProjectName: globalaccount-core Version: 1.0
 **/
public interface UserWithdrawApplyManager {

    /**
     * 功能：查询所有明细文件批次号
     *
     * @param orderId 转账汇总订单号
     * @return 提现申请明细
     */
    List<UserWithdrawApplyDo> queryUserWithdrawByOrderId(Long orderId);

    /**
     * 功能：提现明细更新
     *
     * @param userWithdrawApplyDo 提现明细
     */
    void modifyWithdrawApplyDo(UserWithdrawApplyDo userWithdrawApplyDo);
}
