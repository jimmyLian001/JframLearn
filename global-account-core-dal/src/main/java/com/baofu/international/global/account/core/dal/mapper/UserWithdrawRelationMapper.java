package com.baofu.international.global.account.core.dal.mapper;

import com.baofu.international.global.account.core.dal.model.UserWithdrawRelationDo;

import java.util.List;

public interface UserWithdrawRelationMapper {

    /**
     * 新增汇总和批次关系记录
     *
     * @param userWithdrawRelationDo 参数
     * @return 影响条数
     */
    int insertWithdrawRelation(UserWithdrawRelationDo userWithdrawRelationDo);

    /**
     * 根据批次号获取明细编号
     *
     * @param batchNo 批次号
     * @return 明细编号
     */
    List<Long> selectWithdrawIdByBatchNo(Long batchNo);
}