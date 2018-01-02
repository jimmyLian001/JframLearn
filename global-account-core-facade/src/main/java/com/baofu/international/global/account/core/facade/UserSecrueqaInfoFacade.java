package com.baofu.international.global.account.core.facade;

import com.baofu.international.global.account.core.facade.model.UserAnswerReqDTO;
import com.baofu.international.global.account.core.facade.model.UserAnswerRespDTO;
import com.system.commons.result.Result;

import java.util.List;

/**
 * 用户安全问题facade
 * <p>
 * 1,重置登录密码
 * 2，修改登录密码
 * </p>
 * User: 康志光  Date: 2017/11/04 ProjectName: globalaccount Version: 1.0
 */
public interface UserSecrueqaInfoFacade {

    /**
     * 查询用户安全问题
     *
     * @param userAnswerReqDTO 用户安全问题请求参数
     * @param traceLogId       日志ID
     */
    Result<List<UserAnswerRespDTO>> findUserAnswer(UserAnswerReqDTO userAnswerReqDTO, String traceLogId);

    /**
     * 修改用户安全问题
     *
     * @param userAnswerReqDTO 用户安全问题请求参数
     * @param traceLogId       日志ID
     */
    Result<Boolean> modifyUserAnswer(UserAnswerReqDTO userAnswerReqDTO, String traceLogId);

    /**
     * 修改用户安全问题
     *
     * @param userAnswerReqDTO 用户安全问题请求参数
     * @param traceLogId       日志ID
     */
    Result<Boolean> verifyUserAnswer(UserAnswerReqDTO userAnswerReqDTO, String traceLogId);

    /**
     * 新增用户安全问题
     *
     * @param userAnswerReqDtos 用户安全问题请求参数
     * @param traceLogId        日志ID
     */
    Result<Boolean> createUserAnswer(List<UserAnswerReqDTO> userAnswerReqDtos, String traceLogId);
}
