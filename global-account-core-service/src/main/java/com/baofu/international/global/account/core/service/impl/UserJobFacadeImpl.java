package com.baofu.international.global.account.core.service.impl;

import com.baofu.global.cross.border.logback.ex.constant.MDCPropertyConsts;
import com.baofu.international.global.account.core.biz.UserWithdrawBiz;
import com.baofu.international.global.account.core.biz.UserWithdrawCgwDealBiz;
import com.baofu.international.global.account.core.facade.UserJobFacade;
import com.system.commons.result.Result;
import com.system.commons.utils.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 功能：用户定时任务
 * User: feng_jiang Date:2017/11/13 ProjectName: globalaccount-core Version: 1.0
 **/
@Slf4j
@Service
public class UserJobFacadeImpl implements UserJobFacade {

    /**
     * 提现操作业务逻辑实现接口
     */
    @Autowired
    private UserWithdrawBiz userWithdrawBiz;

    /**
     * 用户提现处理渠道通知服务
     */
    @Autowired
    private UserWithdrawCgwDealBiz userWithdrawCgwDealBiz;

    /**
     * 功能：用户前台提现归集
     *
     * @param channelId  渠道编号
     * @param traceLogId 日志ID
     * @return 汇总归集是否成功
     */
    @Override
    public Result<Boolean> execUserWithdrawMerge(Long channelId, String traceLogId) {
        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
        log.info("call 用户前台提现归集");
        Result<Boolean> result;
        try {
            long startTime = System.currentTimeMillis();
            log.info("call wyre 转账第二步汇总处理开始");
            userWithdrawBiz.execTransferDeposit(channelId);
            log.info("call wyre 转账第二步汇总处理结束 总处理时间：{}", System.currentTimeMillis() - startTime);
            result = new Result<>(Boolean.TRUE);
        } catch (Exception e) {
            log.error("call 转账第二步汇总处理结束", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("call 转账第二步汇总处理结束,返回参数:{}", result);
        return result;
    }

    /**
     * 功能：定时获取中行汇率
     *
     * @param traceLogId 日志ID
     * @return 获取中行汇率是否成功
     */
    @Override
    public Result<Boolean> loadCgwBocRate(String traceLogId) {
        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
        log.info("call 定时获取中行汇率");
        Result<Boolean> result;
        try {
            long startTime = System.currentTimeMillis();
            log.info("call 定时获取中行汇率开始");
            userWithdrawCgwDealBiz.loadCgwBocRate();
            log.info("call 定时获取中行汇率结束 总处理时间：{}", System.currentTimeMillis() - startTime);
            result = new Result<>(Boolean.TRUE);
        } catch (Exception e) {
            log.error("call 定时获取中行汇率结束", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("call 定时获取中行汇率处理结束,返回参数:{}", result);
        return result;
    }
}
