package com.baofu.international.global.account.core.manager.impl;

import com.baofu.international.global.account.core.dal.mapper.UserWithdrawRelationMapper;
import com.baofu.international.global.account.core.dal.model.UserWithdrawRelationDo;
import com.baofu.international.global.account.core.manager.UserWithdrawRelationManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 1、新增提现关系信息记录
 * 2、根据批次号获取明细编号
 * </p>
 * User: 香克斯  Date: 2017/11/13 ProjectName:account-core  Version: 1.0
 */
@Slf4j
@Repository
public class UserWithdrawRelationManagerImpl implements UserWithdrawRelationManager {


    /**
     * 汇总和批次关系记录
     */
    @Autowired
    private UserWithdrawRelationMapper userWithdrawRelationMapper;

    /**
     * 新增提现关系信息记录
     *
     * @param userWithdrawRelationDo 提现关系信息记录
     */
    @Override
    public void addUserWithdrawRelation(UserWithdrawRelationDo userWithdrawRelationDo) {
        userWithdrawRelationMapper.insertWithdrawRelation(userWithdrawRelationDo);
    }

    /**
     * 根据批次号获取明细编号
     *
     * @param batchNo 批次号
     * @return 明细编号
     */
    @Override
    public List<Long> queryWithdrawIdByBatchNo(Long batchNo) {
        return userWithdrawRelationMapper.selectWithdrawIdByBatchNo(batchNo);
    }
}
