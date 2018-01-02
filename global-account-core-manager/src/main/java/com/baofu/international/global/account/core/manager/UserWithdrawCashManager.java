package com.baofu.international.global.account.core.manager;

import com.baofu.international.global.account.core.dal.model.UserPayeeAccountDo;
import com.baofu.international.global.account.core.dal.model.UserWithdrawApplyDo;
import com.baofu.international.global.account.core.dal.model.UserWithdrawFileUploadDo;

import java.util.List;

/**
 * 功能：用户前台提现服务
 * User: feng_jiang Date:2017/11/5 ProjectName: globalaccount-core Version: 1.0
 **/
public interface UserWithdrawCashManager {

    /**
     * 查询用户有效的子账户信息
     *
     * @param userPayeeAccountDo 用户账户信息
     * @return 子账户信息
     */
    UserPayeeAccountDo selectUserAccountInfo(UserPayeeAccountDo userPayeeAccountDo);

    /**
     * 添加提现申请订单
     *
     * @param userWithdrawApplyDo 申请订单信息
     */
    void addTUserWithdrawApply(UserWithdrawApplyDo userWithdrawApplyDo);

    /**
     * 根据主键更新提现申请订单
     *
     * @param userWithdrawApplyDo 申请订单信息
     */
    void updateTUserWithdrawApply(UserWithdrawApplyDo userWithdrawApplyDo);

    /**
     * 根据批次号查询
     *
     * @param batch 订单批次号
     * @return 文件信息集合
     */
    List<UserWithdrawFileUploadDo> selectByBatch(String batch);

    /**
     * 根据订单号查询提现订单信息
     *
     * @param orderId 提现订单号
     * @return 提现订单信息
     */
    UserWithdrawApplyDo selectTransferDetailByOrder(Long orderId);

    /**
     * 根据提现订单批次更新文件信息
     *
     * @param fileUploadDo 文件信息
     */
    void updateFileByBatch(UserWithdrawFileUploadDo fileUploadDo);

    /**
     * 根据用户号和银行账户号币种查询收款账户信息
     *
     * @param bankAccNo 银行账户
     * @param userNo    用户编号
     * @param ccy       币种
     * @return 返回收款账户信息
     */
    List<UserPayeeAccountDo> selectPayeeAccountByCcy(Long userNo, String bankAccNo, String ccy);
}
