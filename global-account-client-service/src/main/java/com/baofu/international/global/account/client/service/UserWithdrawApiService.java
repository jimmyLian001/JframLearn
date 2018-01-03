package com.baofu.international.global.account.client.service;

import com.baofu.international.global.account.client.service.models.UserWithdrawDistributeReqBo;

/**
 * <p>
 * 1、用户结汇申请处理服务
 * </p>
 * User: feng_jiang  Date: 2017/11/24 ProjectName:account-client  Version: 1.0
 */
public interface UserWithdrawApiService {

    /**
     * 功能：用户结汇申请处理
     * @param withdrawDistributeReqBo
     */
    void dealUserSettleApply(UserWithdrawDistributeReqBo withdrawDistributeReqBo);

    /**
     * 功能：内卡下发处理
     * @param respDecrypt 内卡反馈内容
     */
    void dealUserWithdraw(String respDecrypt);
}
