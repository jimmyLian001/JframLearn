package com.baofu.international.global.account.core.facade;

import com.system.commons.result.Result;


/**
 * 功能：用户定时任务
 * User: feng_jiang Date:2017/11/5 ProjectName: globalaccount-core Version: 1.0
 **/
public interface UserJobFacade {

    /**
     * 功能：用户前台提现归集
     *
     * @param channelId  渠道编号
     * @param traceLogId 日志ID
     * @return 汇总归集是否成功
     */
    Result<Boolean> execUserWithdrawMerge(Long channelId, String traceLogId);

    /**
     * 功能：定时获取中行汇率
     *
     * @param traceLogId 日志ID
     * @return 获取中行汇率是否成功
     */
    Result<Boolean> loadCgwBocRate(String traceLogId);
}
