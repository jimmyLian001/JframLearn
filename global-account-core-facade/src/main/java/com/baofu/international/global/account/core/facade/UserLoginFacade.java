package com.baofu.international.global.account.core.facade;

import com.baofu.international.global.account.core.facade.model.UserLoginReqDTO;
import com.baofu.international.global.account.core.facade.model.UserLoginRespDTO;
import com.system.commons.result.Result;

/**
 * 用户登录接口
 *
 * @author: 不良人 Date:2017/11/5 ProjectName: account-core Version: 1.0
 */
public interface UserLoginFacade {

    /**
     * 用户登录
     *
     * @param userLoginReqDTO 用户信息
     * @param traceLogId      日志ID
     * @return 登录信息
     */
    Result<UserLoginRespDTO> userLogin(UserLoginReqDTO userLoginReqDTO, String traceLogId);

    /**
     * 查询用户信息
     *
     * @param loginNo    登录号
     * @param traceLogId 日志ID
     * @return 用户信息
     */
    Result<UserLoginRespDTO> findLoginInfo(String loginNo, String traceLogId);
}
