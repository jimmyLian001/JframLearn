package com.baofu.international.global.account.core.manager.impl;

import com.baofu.international.global.account.core.common.enums.ErrorCodeEnum;
import com.baofu.international.global.account.core.dal.mapper.UserAccountMapper;
import com.baofu.international.global.account.core.dal.mapper.UserWithdrawApplyMapper;
import com.baofu.international.global.account.core.dal.mapper.UserWithdrawFileUploadMapper;
import com.baofu.international.global.account.core.dal.model.UserPayeeAccountDo;
import com.baofu.international.global.account.core.dal.model.UserWithdrawApplyDo;
import com.baofu.international.global.account.core.dal.model.UserWithdrawFileUploadDo;
import com.baofu.international.global.account.core.manager.UserWithdrawCashManager;
import com.system.commons.exception.BizServiceException;
import com.system.commons.utils.ParamValidate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 功能：用户前台提现服务
 * User: feng_jiang Date:2017/11/5 ProjectName: globalaccount-core Version: 1.0
 **/
@Slf4j
@Repository
public class UserWithdrawCashManagerImpl implements UserWithdrawCashManager {

    /**
     * 收款账户业务数据库操作接口
     */
    @Autowired
    private UserAccountMapper userPayeeAccountMapper;

    /**
     * 商户提现申请数据操作
     */
    @Autowired
    private UserWithdrawApplyMapper userWithdrawApplyMapper;

    /**
     * 文件批次操作服务
     */
    @Autowired
    private UserWithdrawFileUploadMapper userWithdrawFileUploadMapper;

    /**
     * 查询用户有效的子账户信息
     *
     * @param userPayeeAccountDo 用户账户信息
     * @return 子账户信息
     */
    @Override
    public UserPayeeAccountDo selectUserAccountInfo(UserPayeeAccountDo userPayeeAccountDo) {
        UserPayeeAccountDo tPayeeAccountDo = userPayeeAccountMapper.selectUserAccountInfo(userPayeeAccountDo);
        if (tPayeeAccountDo == null) {
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190023);
        }
        return tPayeeAccountDo;
    }

    /**
     * 添加提现申请订单
     *
     * @param userWithdrawApplyDo 申请订单信息
     */
    @Override
    public void addTUserWithdrawApply(UserWithdrawApplyDo userWithdrawApplyDo) {
        ParamValidate.checkUpdate(userWithdrawApplyMapper.insert(userWithdrawApplyDo));
    }

    /**
     * 根据主键更新提现申请订单
     *
     * @param userWithdrawApplyDo 申请订单信息
     */
    @Override
    public void updateTUserWithdrawApply(UserWithdrawApplyDo userWithdrawApplyDo) {
        ParamValidate.checkUpdate(userWithdrawApplyMapper.updateByOrderId(userWithdrawApplyDo));
    }

    /**
     * 根据批次号查询
     *
     * @param batch 订单批次号
     */
    @Override
    public List<UserWithdrawFileUploadDo> selectByBatch(String batch) {
        List<UserWithdrawFileUploadDo> fiCbPayFileUploadDoList = userWithdrawFileUploadMapper.queryByBatchNo(batch);
        if (CollectionUtils.isEmpty(fiCbPayFileUploadDoList)) {
            log.info("call 查询提现文件批次为空，提现订单号:{}", batch);
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190024);
        }
        return fiCbPayFileUploadDoList;
    }

    /**
     * 根据订单号查询提现订单信息
     *
     * @param orderId 提现订单号
     * @return 提现订单信息
     */
    @Override
    public UserWithdrawApplyDo selectTransferDetailByOrder(Long orderId) {

        UserWithdrawApplyDo userWithdrawApplyDo = userWithdrawApplyMapper.selectByOrderId(orderId);
        if (userWithdrawApplyDo == null) {
            log.info("call 查询提现订单信息不存在，提现订单号:{}", orderId);
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190024);
        }
        return userWithdrawApplyDo;
    }

    /**
     * 根据提现订单批次更新文件信息
     *
     * @param fileUploadDo 文件信息
     */
    @Override
    public void updateFileByBatch(UserWithdrawFileUploadDo fileUploadDo) {
        ParamValidate.checkUpdate(userWithdrawFileUploadMapper.updateFileByBatch(fileUploadDo));
    }

    /**
     * 根据用户号和银行账户号币种查询收款账户信息
     *
     * @param userNo    用户编号
     * @param bankAccNo 银行账户
     * @param ccy       币种
     * @return 返回收款账户信息
     */
    @Override
    public List<UserPayeeAccountDo> selectPayeeAccountByCcy(Long userNo, String bankAccNo, String ccy) {
        List<UserPayeeAccountDo> tPayeeAccountDos = userPayeeAccountMapper.selectPayeeAccountByCcy(userNo, bankAccNo, ccy);
        if (tPayeeAccountDos == null || tPayeeAccountDos.isEmpty()) {
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190023);
        }
        return tPayeeAccountDos;
    }

}
