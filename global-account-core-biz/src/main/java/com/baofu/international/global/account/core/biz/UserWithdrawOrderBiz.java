package com.baofu.international.global.account.core.biz;

import com.baofu.international.global.account.core.biz.models.BatchWithdrawBo;

/**
 * 功能：用户自主注册平台提现订单生成服务
 * User: feng_jiang Date:2017/11/20 ProjectName: globalaccount-core Version: 1.0
 **/
public interface UserWithdrawOrderBiz {

    /**
     * 功能：创建批量提现文件批次
     *
     * @param batchWithdrawBo 提现文件批次参数
     */
    void createWithdrawFileBatch(BatchWithdrawBo batchWithdrawBo);

    /**
     * 功能：根据提现批次号更新外部订单为可用（转账失败调用）
     * @param withdrawId 提现文件批次号
     */
    void updateExternalOrder(Long withdrawId);
}
