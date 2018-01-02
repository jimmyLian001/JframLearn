package com.baofu.international.global.account.core.facade;

import com.baofu.international.global.account.core.facade.model.UserPwdReqDto;
import com.baofu.international.global.account.core.facade.model.UserPwdRespDto;
import com.system.commons.result.Result;

/**
 * 用户客户信息facade
 * <p>
 * 1、重置登录密码
 * 2、修改登录密码
 * 3、查询用户登录信息
 * 4、更新客户认证状态
 * </p>
 * User: 康志光  Date: 2017/11/04 ProjectName: globalaccount Version: 1.0
 */
public interface UserPwdFacade {

    /**
     * 重置登录密码
     *
     * @param userPwdReqDto 用户密码重置请求参数
     * @param traceLogId    日志ID
     */
    Result<Boolean> resetUserPwd(UserPwdReqDto userPwdReqDto, String traceLogId);

    /**
     * 修改登录密码
     *
     * @param userPwdReqDto 用户密码修改请求参数
     * @param traceLogId    日志ID
     */
    Result<Boolean> modifyUserPwd(UserPwdReqDto userPwdReqDto, String traceLogId);

    /**
     * 查询用户登录信息
     *
     * @param loginNo    参数
     * @param traceLogId 日志ID
     * @return Result<UserCustomerReqDTO> 结果
     */
    Result<Long> find(String loginNo, String traceLogId);

    /**
     * 查询用户密码信息
     *
     * @param userNo     参数
     * @param pwdType    密码类型
     * @param traceLogId 日志ID
     * @return Result<UserCustomerReqDTO> 结果
     */
    Result<UserPwdRespDto> findPwdInfo(Long userNo, Integer pwdType, String traceLogId);

    /**
     * 支付密码验证
     *
     * @param payPwd     用户密码修改请求参数
     * @param userNo     用户号
     * @param traceLogId 日志ID
     * @return 成功或失败
     */
    Result<Boolean> checkPayPwd(String payPwd, Long userNo, String traceLogId);

    /**
     * 设置支付密码
     *
     * @param userPwdReqDto 支付密码请求参数
     * @param traceLogId    日志ID
     * @return f失败或成功
     */
    Result<Boolean> setPayPwd(UserPwdReqDto userPwdReqDto, String traceLogId);
}
