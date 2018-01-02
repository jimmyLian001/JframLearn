package com.baofu.international.global.account.core.manager;

import com.baofu.international.global.account.core.dal.model.UserWithdrawRelationDo;

import java.util.List;

/**
 * <p>
 * 1、新增提现关系信息记录
 * 2、根据批次号获取明细编号
 * </p>
 * User: 香克斯  Date: 2017/11/13 ProjectName:account-core  Version: 1.0
 */
public interface UserWithdrawRelationManager {

    /**
     * 新增提现关系信息记录
     *
     * @param userWithdrawRelationDo 提现关系信息记录
     */
    void addUserWithdrawRelation(UserWithdrawRelationDo userWithdrawRelationDo);

    /**
     * 根据批次号获取明细编号
     *
     * @param batchNo 批次号
     * @return 明细编号
     */
    List<Long> queryWithdrawIdByBatchNo(Long batchNo);
}
