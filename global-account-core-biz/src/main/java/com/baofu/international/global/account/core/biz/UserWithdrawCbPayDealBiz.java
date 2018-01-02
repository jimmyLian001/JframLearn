package com.baofu.international.global.account.core.biz;

import com.baofu.international.global.account.core.biz.models.UserDistributeApplyRespBo;
import com.baofu.international.global.account.core.biz.models.UserSettleApplyRespBo;

/**
 * 功能：用户提现与跨境API交互服务
 * User: feng_jiang Date:2017/11/5 ProjectName: globalaccount-core Version: 1.0
 **/
public interface UserWithdrawCbPayDealBiz {

    /**
     * 功能：发起结汇申请API请求
     *
     * @param withdrawBatchId 提现汇总批次号
     * @param fileName        文件名
     */
    void processSettleAPI(Long withdrawBatchId, String fileName);

    /**
     * 功能：处理结汇申请API请求
     *
     * @param userSettleApplyRespBo 结汇处理结果
     */
    void dealSettleAPIResult(UserSettleApplyRespBo userSettleApplyRespBo);

    /**
     * 功能：处理内卡下发处理结果
     * @return 提现订单
     */
    void dealWithdrawDistributeAPIResult(UserDistributeApplyRespBo userDistributeApplyRespBo);
}
