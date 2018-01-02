package com.baofu.international.global.account.core.facade;

import com.system.commons.result.Result;

/**
 * 资质信息校验接口
 * <p>
 * 1、邮箱校验
 * </p>
 *
 * @author : hetao  Date: 2017/11/04 ProjectName: globalaccount Version: 1.0
 */
public interface UserInfoCheckFacade {

    /**
     * 用户注册校验
     * 用户实名基本信息校验
     * 主要校验邮箱是否已经存在
     *
     * @param email      邮箱
     * @param traceLogId 日志ID
     * @return 校验结果
     */
    Result<Boolean> userInfoAddCheck(String email, String traceLogId);

    /**
     * 1、校验邮箱是否已经存在
     * 2、校验认证状态是否为待认证或认证失败，其他情况返回失败
     *
     * @param email      邮箱
     * @param userInfoNo 用户信息编号
     * @param traceLogId 日志ID
     * @param userType   用户类型
     * @return 校验结果
     */
    Result<Boolean> userInfoModifyCheck(String email, Long userInfoNo, Integer userType, String traceLogId);
}
