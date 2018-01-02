package com.baofu.international.global.account.core.service.impl;

import com.baofu.global.cross.border.logback.ex.constant.MDCPropertyConsts;
import com.baofu.international.global.account.core.biz.UserInfoCheckBiz;
import com.baofu.international.global.account.core.facade.UserInfoCheckFacade;
import com.system.commons.result.Result;
import com.system.commons.utils.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 资质信息校验接口
 * <p>
 * 1、邮箱校验
 * </p>
 *
 * @author : hetao  Date: 2017/11/04 ProjectName: globalaccount Version: 1.0
 */
@Service
@Slf4j
public class UserInfoCheckFacadeImpl implements UserInfoCheckFacade {

    /**
     * 资质校验服务
     */
    @Autowired
    private UserInfoCheckBiz userInfoCheckBiz;

    /**
     * 用户注册校验
     * 用户实名基本信息校验
     * 主要校验邮箱是否已经存在
     *
     * @param email      邮箱
     * @param traceLogId 日志ID
     * @return 校验结果
     */
    @Override
    public Result<Boolean> userInfoAddCheck(String email, String traceLogId) {
        Result<Boolean> result;
        try {
            MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
            log.info("邮箱校验,param:{}", email);

            userInfoCheckBiz.addCheckEmail(email);
            result = new Result<>(Boolean.TRUE);
        } catch (Exception e) {
            log.error("邮箱校验异常", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("邮箱校验结果:{}", result);
        return result;
    }

    /**
     * 1、校验邮箱是否已经存在
     * 2、校验认证状态是否为待认证或认证失败，其他情况返回失败
     *
     * @param email      邮箱
     * @param userInfoNo 用户信息编号
     * @param userType   用户类型
     * @param traceLogId 日志ID
     * @return 校验结果
     */
    @Override
    public Result<Boolean> userInfoModifyCheck(String email, Long userInfoNo, Integer userType, String traceLogId) {
        Result<Boolean> result;
        try {
            MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
            log.info("邮箱校验,param:{}", email);

            userInfoCheckBiz.updateCheckEmail(email, userInfoNo);

            userInfoCheckBiz.updateCheckRealNameStatus(userInfoNo, userType);
            result = new Result<>(Boolean.TRUE);
        } catch (Exception e) {
            log.error("邮箱校验异常", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("邮箱校验结果:{}", result);
        return result;
    }
}
