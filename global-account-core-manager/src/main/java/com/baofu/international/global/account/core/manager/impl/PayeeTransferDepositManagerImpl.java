package com.baofu.international.global.account.core.manager.impl;

import com.baofu.international.global.account.core.dal.mapper.UserWithdrawApplyMapper;
import com.baofu.international.global.account.core.dal.mapper.UserWithdrawSumMapper;
import com.baofu.international.global.account.core.dal.model.UserWithdrawApplyDo;
import com.baofu.international.global.account.core.dal.model.UserWithdrawSumDo;
import com.baofu.international.global.account.core.manager.PayeeTransferDepositManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * description:用户提现金额归集操作类
 * <p/>
 * User: feng_jiang Date:2017/11/5 ProjectName: globalaccount-core Version: 1.0
 */
@Slf4j
@Repository
public class PayeeTransferDepositManagerImpl implements PayeeTransferDepositManager {

    /**
     * 商户提现申请
     */
    @Autowired
    private UserWithdrawApplyMapper userWithdrawApplyMapper;

    /**
     * 汇总记录
     */
    @Autowired
    private UserWithdrawSumMapper userWithdrawSumMapper;


    /**
     * 根据时间查询批次
     *
     * @param channelId 渠道编号
     * @return 结果集
     */
    @Override
    public List<UserWithdrawApplyDo> queryUserWithdrawApplyList(Long channelId) {
        return userWithdrawApplyMapper.selectByTime(channelId);
    }

    /**
     * 新增汇总记录
     *
     * @param userWithdrawSumDo 参数
     * @return 影响条数
     */
    @Override
    public int addUserWithdrawSum(UserWithdrawSumDo userWithdrawSumDo) {
        return userWithdrawSumMapper.insertUserWithdrawSum(userWithdrawSumDo);
    }

    /**
     * 根据平台流水号修改汇总信息的转账状态
     *
     * @param withdrawBatchId 平台流水号
     * @param transferState   转账状态
     * @param remarks         备注
     * @return
     */
    @Override
    public int updateWithdrawSumState(Long withdrawBatchId, Integer transferState, String remarks) {
        return userWithdrawSumMapper.updateWithdrawSum(withdrawBatchId, transferState, remarks);
    }

    /**
     * 根据平台流水号批量修改用户提现申请的转账状态
     *
     * @param orderId       平台流水号
     * @param withdrawState 提现状态
     * @return 结果集
     */
    @Override
    public int updateUserWithdrawApplyState(List<Long> orderId, Integer withdrawState) {
        return userWithdrawApplyMapper.updateUserWithdrawApplyState(orderId, withdrawState);
    }

    /**
     * 根据汇总批号查询汇总转账信息
     *
     * @return 结果集
     */
    @Override
    public UserWithdrawSumDo queryTUserWithdrawSumDoByBatch(Long batch) {
        return userWithdrawSumMapper.queryTUserWithdrawSumDoByBatch(batch);
    }
}
