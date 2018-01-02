package com.baofu.international.global.account.core.service.impl;

import com.baofu.global.cross.border.logback.ex.constant.MDCPropertyConsts;
import com.baofu.international.global.account.core.biz.UserWithdrawCbPayDealBiz;
import com.baofu.international.global.account.core.biz.models.UserDistributeApplyRespBo;
import com.baofu.international.global.account.core.biz.models.UserSettleApplyRespBo;
import com.baofu.international.global.account.core.facade.UserWithdrawApiFacade;
import com.baofu.international.global.account.core.facade.model.UserDistributeApiDto;
import com.baofu.international.global.account.core.facade.model.UserSettleApplyDto;
import com.system.commons.result.Result;
import com.system.commons.utils.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 功能：用户汇入汇款申请处理接口
 * User: feng_jiang Date:2017/11/23 ProjectName: globalaccount-core Version: 1.0
 **/
@Slf4j
@Service
public class UserWithdrawApiFacadeImpl implements UserWithdrawApiFacade {

    /**
     * 用户提现与跨境API交互服务
     */
    @Autowired
    private UserWithdrawCbPayDealBiz userWithdrawCbPayDealBiz;

    /**
     * 功能：处理用户结汇申请
     *
     * @param userSettleApplyDto 用户结汇申请返回参数
     * @param traceLogId         日志IID
     */
    @Override
    public Result<Boolean> dealUserSettleApply(UserSettleApplyDto userSettleApplyDto, String traceLogId) {
        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
        log.info("call 处理用户结汇申请结果,请求参数:{}", userSettleApplyDto);
        Result<Boolean> result;
        try {
            UserSettleApplyRespBo userSettleApplyRespBo = new UserSettleApplyRespBo();
            BeanUtils.copyProperties(userSettleApplyDto, userSettleApplyRespBo);
            userWithdrawCbPayDealBiz.dealSettleAPIResult(userSettleApplyRespBo);
            result = new Result<>(Boolean.TRUE);
        } catch (Exception e) {
            log.error("call 处理用户结汇申请结果 {}", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("call 处理用户结汇申请结果,返回参数:{}", result);
        return result;
    }

    /**
     * 功能：处理内卡下发结果
     *
     * @param userDistributeApiDto 内卡下发结果返回参数
     * @param traceLogId         日志IID
     */
    @Override
    public Result<Boolean> dealWithdrawDistributeApply(UserDistributeApiDto userDistributeApiDto, String traceLogId){
        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
        log.info("call 处理内卡反馈下发结果,请求参数:{}", userDistributeApiDto);
        Result<Boolean> result;
        try {
            UserDistributeApplyRespBo userDistributeApplyRespBo = new UserDistributeApplyRespBo();
            BeanUtils.copyProperties(userDistributeApiDto, userDistributeApplyRespBo);
            userWithdrawCbPayDealBiz.dealWithdrawDistributeAPIResult(userDistributeApplyRespBo);
            result = new Result<>(Boolean.TRUE);
        } catch (Exception e) {
            log.error("call 处理内卡反馈下发结果 {}", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("call 处理内卡反馈下发结果,返回参数:{}", result);
        return result;
    }
}
