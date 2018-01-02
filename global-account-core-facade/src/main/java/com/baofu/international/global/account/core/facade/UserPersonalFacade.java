package com.baofu.international.global.account.core.facade;

import com.baofu.international.global.account.core.facade.model.UserPersonalReqDto;
import com.baofu.international.global.account.core.facade.model.user.UserPersonalDto;
import com.system.commons.result.Result;

/**
 * 个人用户信息接口
 * <p>
 * 1、更新个人用户信息
 * </p>
 *
 * @author : hetao  Date: 2017/11/06 ProjectName: globalaccount Version: 1.0
 */
public interface UserPersonalFacade {

    /**
     * 更新个人用户信息
     *
     * @param personInfoReqDto 个人认证信息
     * @param traceLogId       日志ID
     */
    Result<Boolean> addUserPersonal(UserPersonalReqDto personInfoReqDto, String traceLogId);

    /**
     * 更新个人用户信息
     *
     * @param personInfoReqDto 个人认证信息
     * @param traceLogId       日志ID
     */
    Result<Boolean> updateUserPersonal(UserPersonalReqDto personInfoReqDto, String traceLogId);

    /**
     * 根据userNo查询
     *
     * @param userNo     用户编号
     * @param traceLogId 日志ID
     * @return 用户信息
     */
    Result<UserPersonalDto> findByUserNo(Long userNo, String traceLogId);

    /**
     * 根据qualifiedNo查询
     *
     * @param qualifiedNo 资质编号
     * @param traceLogId  日志ID
     * @return 用户信息
     */
    Result<UserPersonalDto> selectInfoByQualifiedNo(Long qualifiedNo, String traceLogId);

    /**
     * 根据userInfoNo查询
     *
     * @param userInfoNo 资质编号
     * @param traceLogId 日志ID
     * @return 用户信息
     */
    Result<UserPersonalDto> selectInfoByUserInfoNo(Long userInfoNo, String traceLogId);
}
