package com.baofu.international.global.account.core.manager.impl;

import com.baofu.international.global.account.core.dal.mapper.UserWithdrawOrderMapper;
import com.baofu.international.global.account.core.dal.model.UserWithdrawOrderQueryDo;
import com.baofu.international.global.account.core.manager.UserWithdrawOrderManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 1、方法描述
 * </p>
 * User: 香克斯  Date: 2017/11/14 ProjectName:account-core  Version: 1.0
 */
@Repository
public class UserWithdrawOrderManagerImpl implements UserWithdrawOrderManager {

    /**
     * 提现订单明细相关信息
     */
    @Autowired
    private UserWithdrawOrderMapper userWithdrawOrderMapper;

    /**
     * 根据提现订单明细编号查询订单明细信息
     *
     * @param userNo      用户编号
     * @param fileBatchNo 文件编号
     * @return 返回订单明细集合
     */
    @Override
    public List<UserWithdrawOrderQueryDo> queryOrderByFileBatchNo(Long userNo, Long fileBatchNo) {

        return userWithdrawOrderMapper.selectOrderByUserNoAndFileNo(userNo, fileBatchNo);
    }
}
