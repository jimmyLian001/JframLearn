package com.baofu.international.global.account.core.service.impl;

import com.baofu.global.cross.border.logback.ex.constant.MDCPropertyConsts;
import com.baofu.international.global.account.core.biz.UserSecrueqaInfoBiz;
import com.baofu.international.global.account.core.biz.models.UserAnswerRespBo;
import com.baofu.international.global.account.core.facade.UserSecrueqaInfoFacade;
import com.baofu.international.global.account.core.facade.model.UserAnswerReqDTO;
import com.baofu.international.global.account.core.facade.model.UserAnswerRespDTO;
import com.baofu.international.global.account.core.service.convert.UserSecrueqaInfoConvert;
import com.system.commons.result.Result;
import com.system.commons.utils.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户客户信息facade
 * <p>
 * <p>
 * 1,重置登录密码
 * 2，次修改登录密码
 * </p>
 * User: 康志光  Date: 2017/11/04 ProjectName: globalaccount Version: 1.0
 */
@Slf4j
@Service
public class UserSecrueqaInfoFacadeImpl implements UserSecrueqaInfoFacade {

    /**
     * 用户安全问题BIZ
     */
    @Autowired
    private UserSecrueqaInfoBiz userSecrueqaInfoBiz;

    /**
     * 查询用户安全问题
     *
     * @param userAnswerReqDTO 用户密码修改，重置请求参数
     * @param traceLogId       日志ID
     */
    @Override
    public Result<List<UserAnswerRespDTO>> findUserAnswer(UserAnswerReqDTO userAnswerReqDTO, String traceLogId) {
        Result<List<UserAnswerRespDTO>> results;
        log.info("call 查询用户安全问题参数 ：{}", userAnswerReqDTO);
        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
        try {
            List<UserAnswerRespBo> userAnswerRespBos = userSecrueqaInfoBiz.findUserAnswer(
                    UserSecrueqaInfoConvert.convert(userAnswerReqDTO));
            results = new Result<>(UserSecrueqaInfoConvert.convert(userAnswerRespBos));
        } catch (Exception e) {
            log.error("call 查询用户安全问题异常:{}", e);
            results = ExceptionUtils.getResponse(e);
        }
        log.info("call 查询用户安全问题返回结果：{}", results);
        return results;
    }

    /**
     * 修改用户安全问题
     *
     * @param userAnswerReqDTO 用户密码修改，重置请求参数
     * @param traceLogId       日志ID
     */
    @Override
    public Result<Boolean> modifyUserAnswer(UserAnswerReqDTO userAnswerReqDTO, String traceLogId) {
        Result<Boolean> results;
        log.info("call 修改用户安全问题参数 ：{}", userAnswerReqDTO);
        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
        try {
            userSecrueqaInfoBiz.modifyUserAnswer(UserSecrueqaInfoConvert.convert(userAnswerReqDTO));
            results = new Result<>(Boolean.TRUE);
        } catch (Exception e) {
            log.error("call 修改用户安全问题异常:{}", e);
            results = ExceptionUtils.getResponse(e);
        }
        log.info("call 修改用户安全问题返回结果：{}", results);
        return results;
    }

    /**
     * 验证用户安全问题
     *
     * @param userAnswerReqDTO 用户密码修改，重置请求参数
     * @param traceLogId       日志ID
     */
    @Override
    public Result<Boolean> verifyUserAnswer(UserAnswerReqDTO userAnswerReqDTO, String traceLogId) {
        Result<Boolean> results;
        log.info("call 验证用户安全问题参数 ：{}", userAnswerReqDTO);
        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
        try {
            Boolean isSuccess = userSecrueqaInfoBiz.verifyUserAnswer(UserSecrueqaInfoConvert.convert(userAnswerReqDTO));
            results = new Result<>(isSuccess);
        } catch (Exception e) {
            log.error("call 验证用户安全问题异常:{}", e);
            results = ExceptionUtils.getResponse(e);
        }
        log.info("call 验证用户安全问题返回结果：{}", results);
        return results;
    }


    /**
     * 新增用户安全问题
     *
     * @param userAnswerReqDtos 用户安全问题请求参数
     * @param traceLogId        日志ID
     */
    @Override
    public Result<Boolean> createUserAnswer(List<UserAnswerReqDTO> userAnswerReqDtos, String traceLogId) {
        Result<Boolean> results;
        log.info("call 新增用户安全问题参数 ：{}", userAnswerReqDtos);
        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
        try {
            Boolean successFlag = userSecrueqaInfoBiz.createQuestions(UserSecrueqaInfoConvert.convertToDtos(userAnswerReqDtos));
            results = new Result<>(successFlag);
        } catch (Exception e) {
            log.error("call 新增用户安全问题异常:{}", e);
            results = ExceptionUtils.getResponse(e);
        }
        log.info("call 新增用户安全问题返回结果：{}", results);
        return results;
    }
}
