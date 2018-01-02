package com.baofu.international.global.account.core.manager.impl;

import com.baofu.international.global.account.core.dal.mapper.UserWithdrawApplyMapper;
import com.baofu.international.global.account.core.dal.model.UserWithdrawApplyDo;
import com.baofu.international.global.account.core.manager.UserWithdrawApplyManager;
import com.system.commons.utils.ParamValidate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 功能：用户前台提现服务
 * User: feng_jiang Date:2017/11/5 ProjectName: globalaccount-core Version: 1.0
 **/
@Slf4j
@Repository
public class UserWithdrawApplyManagerImpl implements UserWithdrawApplyManager {

    /**
     * 用户提现申请数据操作
     */
    @Autowired
    private UserWithdrawApplyMapper userWithdrawApplyMapper;

    /**
     * 功能：查询所有明细文件批次号
     *
     * @param orderId 转账汇总订单号
     * @return
     */
    @Override
    public List<UserWithdrawApplyDo> queryUserWithdrawByOrderId(Long orderId) {

        return userWithdrawApplyMapper.queryUserWithdrawByOrderId(orderId);
    }

    /**
     * 功能：提现明细更新
     *
     * @param userWithdrawApplyDo 提现明细
     */
    @Override
    public void modifyWithdrawApplyDo(UserWithdrawApplyDo userWithdrawApplyDo) {
        ParamValidate.checkUpdate(userWithdrawApplyMapper.modifyWithdrawApplyDo(userWithdrawApplyDo), "更新用户提现申请信息异常");
    }
}
