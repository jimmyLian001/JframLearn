package com.baofu.international.global.account.core.manager;

import com.baofu.international.global.account.core.dal.model.UserWithdrawOrderQueryDo;

import java.util.List;

/**
 * <p>
 * 1、用户提现订单明细信息
 * </p>
 * User: 香克斯  Date: 2017/11/14 ProjectName:account-core  Version: 1.0
 */
public interface UserWithdrawOrderManager {

    /**
     * 根据提现订单明细编号查询订单明细信息
     *
     * @param userNo      用户编号
     * @param fileBatchNo 文件编号
     * @return 返回订单明细集合
     */
    List<UserWithdrawOrderQueryDo> queryOrderByFileBatchNo(Long userNo, Long fileBatchNo);
}
