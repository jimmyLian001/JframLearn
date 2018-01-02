package com.baofu.international.global.account.core.service.impl;

import com.baofu.global.cross.border.logback.ex.constant.MDCPropertyConsts;
import com.baofu.international.global.account.core.biz.UserLoginBiz;
import com.baofu.international.global.account.core.biz.models.UserLoginRespBo;
import com.baofu.international.global.account.core.facade.UserLoginFacade;
import com.baofu.international.global.account.core.facade.model.UserLoginReqDTO;
import com.baofu.international.global.account.core.facade.model.UserLoginRespDTO;
import com.baofu.international.global.account.core.service.convert.UserLoginFacadeConvert;
import com.system.commons.result.Result;
import com.system.commons.utils.ExceptionUtils;
import com.system.commons.utils.ParamValidate;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户登录服务
 *
 * @author: 不良人 Date:2017/11/5 ProjectName: account-core Version: 1.0
 */
@Slf4j
@Service
public class UserLoginFacadeImpl implements UserLoginFacade {

    /**
     * 用户登录服务
     */
    @Autowired
    private UserLoginBiz userLoginBiz;

    /**
     * 用户登录
     *
     * @param userLoginReqDTO 用户信息
     * @param traceLogId      日志ID
     */
    @Override
    public Result<UserLoginRespDTO> userLogin(UserLoginReqDTO userLoginReqDTO, String traceLogId) {

        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
        log.info("[用户登录] facade层请求参数：{}", userLoginReqDTO);
        Result<UserLoginRespDTO> result;
        try {
            ParamValidate.validateParams(userLoginReqDTO);
            UserLoginRespBo respBo = userLoginBiz.userLogin(UserLoginFacadeConvert.toUserLoginReqBo(userLoginReqDTO));
            UserLoginRespDTO respDTO = UserLoginFacadeConvert.toUserLoginRespBo(respBo);
            result = new Result<>(respDTO);
        } catch (Exception e) {
            log.error("[用户登录] 异常", e);
            result = ExceptionUtils.getResponse(e);
        }

        log.info("[用户登录] dubbo返回参数{}", result);
        return result;
    }

    /**
     * 查询用户信息
     *
     * @param loginNo 登录号
     * @return 用户信息
     */
    @Override
    public Result<UserLoginRespDTO> findLoginInfo(String loginNo, String traceLogId) {
        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
        log.info("[用户登录信息] facade层请求参数：{}", loginNo);
        Result<UserLoginRespDTO> result;
        try {
            UserLoginRespBo respBo = userLoginBiz.queryLoginInfo(loginNo);
            UserLoginRespDTO respDTO = UserLoginFacadeConvert.toUserLoginRespBo(respBo);
            result = new Result<>(respDTO);
        } catch (Exception e) {
            log.error("[用户登录信息] 异常", e);
            result = ExceptionUtils.getResponse(e);
        }

        log.info("[用户登录信息] dubbo返回参数{}", result);
        return result;
    }
}
