package com.baofu.international.global.account.core.service.impl;

import com.baofu.global.cross.border.logback.ex.constant.MDCPropertyConsts;
import com.baofu.international.global.account.core.biz.UserPwdBiz;
import com.baofu.international.global.account.core.biz.models.UserPwdRespBo;
import com.baofu.international.global.account.core.facade.UserPwdFacade;
import com.baofu.international.global.account.core.facade.model.UserPwdReqDto;
import com.baofu.international.global.account.core.facade.model.UserPwdRespDto;
import com.baofu.international.global.account.core.service.convert.UserCustomerConvert;
import com.system.commons.result.Result;
import com.system.commons.utils.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户客户信息facade
 * <p>
 * <p>
 * 1、重置登录密码
 * 2、修改登录密码
 * 3、查询用户登录信息
 * 4、更新客户认证状态
 * </p>
 * User: 康志光  Date: 2017/11/04 ProjectName: globalaccount Version: 1.0
 */
@Slf4j
@Service
public class UserPwdFacadeImpl implements UserPwdFacade {

    /**
     * 用户客户信息BIZ
     */
    @Autowired
    private UserPwdBiz userPwdBiz;

    /**
     * 重置登录密码
     *
     * @param userPwdReqDto 用户密码重置请求参数
     * @param traceLogId    日志ID
     */
    @Override
    public Result<Boolean> resetUserPwd(UserPwdReqDto userPwdReqDto, String traceLogId) {

        Result<Boolean> results;
        log.info("call 重置登录密码参数 ：{}", userPwdReqDto);
        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
        try {
            userPwdBiz.resetLoginPwd(UserCustomerConvert.convert(userPwdReqDto));
            results = new Result<>(Boolean.TRUE);
        } catch (Exception e) {
            log.error("call 重置登录密码异常:{}", e);
            results = ExceptionUtils.getResponse(e);
        }
        log.info("call重置登录密码返回结果：{}", results);
        return results;

    }

    /**
     * 修改登录密码
     *
     * @param userPwdReqDto 用户密码修改请求参数
     * @param traceLogId    日志ID
     */
    @Override
    public Result<Boolean> modifyUserPwd(UserPwdReqDto userPwdReqDto, String traceLogId) {
        Result<Boolean> results;
        log.info("call 修改登录密码参数 ：{}", userPwdReqDto);
        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
        try {
            userPwdBiz.modifyLoginPwd(UserCustomerConvert.convert(userPwdReqDto));
            results = new Result<>(Boolean.TRUE);
        } catch (Exception e) {
            log.error("call 修改登录密码异常:{}", e);
            results = ExceptionUtils.getResponse(e);
        }
        log.info("call 修改登录密码返回结果：{}", results);
        return results;
    }

    /**
     * 查询用户登录信息
     *
     * @param loginNo    参数
     * @param traceLogId 日志ID
     * @return Result<UserCustomerReqDTO> 结果
     */
    @Override
    public Result<Long> find(String loginNo, String traceLogId) {
        Result<Long> results;
        log.info("call 查询用户登录参数 ：{}", loginNo);
        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
        try {
            Long userNo = userPwdBiz.find(loginNo);
            results = new Result<>(userNo);
        } catch (Exception e) {
            log.error("call 查询用户登录异常:{}", e);
            results = ExceptionUtils.getResponse(e);
        }
        log.info("call 查询用户登录返回结果：{}", results);
        return results;
    }

    /**
     * 查询用户密码信息
     *
     * @param userNo     参数
     * @param pwdType    密码类型
     * @param traceLogId 日志ID
     * @return Result<UserCustomerReqDTO> 结果
     */
    @Override
    public Result<UserPwdRespDto> findPwdInfo(Long userNo, Integer pwdType, String traceLogId) {
        Result<UserPwdRespDto> results;
        log.info("call 查询用户登录参数 ：{}，{}", userNo, pwdType);
        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
        try {
            UserPwdRespBo userPwdRespBo = userPwdBiz.findPwdInfo(userNo, pwdType);
            UserPwdRespDto userPwdRespDto = new UserPwdRespDto();
            BeanUtils.copyProperties(userPwdRespBo, userPwdRespDto);
            results = new Result<>(userPwdRespDto);
        } catch (Exception e) {
            log.error("call 查询用户登录异常:{}", e);
            results = ExceptionUtils.getResponse(e);
        }
        log.info("call 查询用户登录返回结果：{}", results);
        return results;
    }

    /**
     * 支付密码验证
     *
     * @param payPwd     用户密码修改请求参数
     * @param userNo     用户号
     * @param traceLogId 日志ID
     * @return 成功或失败
     */
    @Override
    public Result<Boolean> checkPayPwd(String payPwd, Long userNo, String traceLogId) {

        log.info("[支付密码验证] 参数 ：{}", payPwd);
        Result<Boolean> result;
        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
        try {
            userPwdBiz.checkPayPwd(payPwd, userNo);
            result = new Result<>(Boolean.TRUE);
        } catch (Exception e) {
            log.error("[支付密码验证] 异常", e);
            result = ExceptionUtils.getResponse(e);
        }

        log.info("[支付密码验证] 返回结果：{}", result);
        return result;
    }

    /**
     * 设置支付密码
     *
     * @param userPwdReqDto 支付密码请求参数
     * @param traceLogId    日志ID
     * @return f失败或成功
     */
    @Override
    public Result<Boolean> setPayPwd(UserPwdReqDto userPwdReqDto, String traceLogId) {
        log.info("[支付密码验证] 参数 ：{}", userPwdReqDto);
        Result<Boolean> result;
        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
        try {
            userPwdBiz.setPayPwd(UserCustomerConvert.convert(userPwdReqDto));
            result = new Result<>(Boolean.TRUE);
        } catch (Exception e) {
            log.error("[支付密码验证] 异常", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("[支付密码验证] 返回结果：{}", result);
        return result;
    }
}
