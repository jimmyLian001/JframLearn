package com.baofu.international.global.account.core.facade;

import com.baofu.international.global.account.core.facade.model.UserSettleApplyDto;
import com.baofu.international.global.account.core.facade.model.UserDistributeApiDto;
import com.system.commons.result.Result;

/**
 * 功能：用户汇入汇款申请处理接口
 * User: feng_jiang Date:2017/11/23 ProjectName: globalaccount-core Version: 1.0
 **/
public interface UserWithdrawApiFacade {

    /**
     * 功能：处理用户结汇申请
     *
     * @param userSettleApplyDto 用户用户结汇申请返回参数
     * @param traceLogId         日志IID
     */
    Result<Boolean> dealUserSettleApply(UserSettleApplyDto userSettleApplyDto, String traceLogId);

    /**
     * 功能：处理内卡下发结果
     *
     * @param userDistributeApiDto 内卡下发结果返回参数
     * @param traceLogId         日志IID
     */
    Result<Boolean> dealWithdrawDistributeApply(UserDistributeApiDto userDistributeApiDto, String traceLogId);

}
