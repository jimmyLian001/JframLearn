package com.baofu.international.global.account.core.manager;

import com.baofu.international.global.account.core.dal.model.UserWithdrawApplyDo;
import com.baofu.international.global.account.core.dal.model.UserWithdrawSumDo;

import java.util.List;

/**
 * description:用户提现金额归集操作类
 * <p/>
 * User: feng_jiang Date:2017/11/5 ProjectName: globalaccount-core Version: 1.0
 */
public interface PayeeTransferDepositManager {

    /**
     * 根据时间查询批次
     *
     * @param channelId 渠道编号
     * @return 结果集
     */
    List<UserWithdrawApplyDo> queryUserWithdrawApplyList(Long channelId);

    /**
     * 新增汇总信息
     *
     * @param userWithdrawSumDo 参数
     * @return 影响条数
     */
    int addUserWithdrawSum(UserWithdrawSumDo userWithdrawSumDo);

    /**
     * 根据平台流水号修改汇总信息的转账状态
     *
     * @param withdrawBatchId 平台流水号
     * @param transferState   转账状态
     * @param remarks         备注
     * @return 影响条数
     */
    int updateWithdrawSumState(Long withdrawBatchId, Integer transferState, String remarks);

    /**
     * 根据平台流水号批量修改用户提现申请的转账状态
     *
     * @param orderId       平台流水号
     * @param withdrawState 提现状态
     * @return 影响条数
     */
    int updateUserWithdrawApplyState(List<Long> orderId, Integer withdrawState);


    /**
     * 根据汇总批号查询汇总转账信息
     *
     * @param orderId 汇总流水号
     * @return 影响条数
     */
    UserWithdrawSumDo queryTUserWithdrawSumDoByBatch(Long orderId);
}
