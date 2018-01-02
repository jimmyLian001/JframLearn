package com.baofu.international.global.account.core.service.impl;

import com.baofu.global.cross.border.logback.ex.constant.MDCPropertyConsts;
import com.baofu.international.global.account.core.biz.AccountAuthBiz;
import com.baofu.international.global.account.core.facade.AccountAuthFacade;
import com.baofu.international.global.account.core.facade.model.OrgAuthReqDto;
import com.baofu.international.global.account.core.facade.model.PersonalAuthReqDto;
import com.baofu.international.global.account.core.service.convert.AccountAuthConvert;
import com.system.commons.result.Result;
import com.system.commons.utils.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 认证操作接口
 * <p>
 * 1、个人信息实名认证
 * 2、企业信息实名认证
 * </p>
 *
 * @author : hetao  Date: 2017/11/04 ProjectName: globalaccount Version: 1.0
 */
@Service
@Slf4j
public class AccountAuthFacadeImpl implements AccountAuthFacade {

    /**
     * 认证操作服务
     */
    @Autowired
    private AccountAuthBiz accountAuthBiz;

    /**
     * 个人信息实名认证
     *
     * @param personAuthReqDto 个人信息
     * @param traceLogId       日志ID
     * @return 响应码
     */
    @Override
    public Result<Long> personalAuthApply(PersonalAuthReqDto personAuthReqDto, String traceLogId) {
        Result<Long> result;
        try {
            MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
            log.info("个人信息实名认证,param:{}", personAuthReqDto);

            Long userInfoNo = accountAuthBiz.personalAuthApply(AccountAuthConvert.personalAuthConvert(personAuthReqDto));
            result = new Result<>(userInfoNo);
        } catch (Exception e) {
            log.error("个人信息实名认证异常", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("个人信息实名认证结果:{}", result);
        return result;
    }

    /**
     * 企业信息实名认证
     *
     * @param orgAuthReqDto 企业信息
     * @param traceLogId    日志ID
     * @return 响应码
     */
    @Override
    public Result<Long> orgAuthApply(OrgAuthReqDto orgAuthReqDto, String traceLogId) {
        Result<Long> result;
        try {
            MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
            log.info("企业信息实名认证,param:{}", orgAuthReqDto);

            Long authResult = accountAuthBiz.orgAuthApply(AccountAuthConvert.orgAuthConvert(orgAuthReqDto));
            result = new Result<>(authResult);
        } catch (Exception e) {
            log.error("企业信息实名认证异常", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("企业信息实名认证结果:{}", result);
        return result;
    }
}
