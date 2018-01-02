package com.baofu.international.global.account.core.facade;

import com.baofu.international.global.account.core.facade.model.UserOrgReqDto;
import com.baofu.international.global.account.core.facade.model.user.OrgInfoRespDto;
import com.system.commons.result.Result;

/**
 * 企业信息API服务
 * <p>
 * 1、更新企业用户信息
 * </p>
 *
 * @author : hetao  Date: 2017/11/06 ProjectName: globalaccount Version: 1.0
 */
public interface UserOrgFacade {

    /**
     * 新增企业用户信息
     *
     * @param userOrgReqDto 企业用户信息
     * @param traceLogId    日志ID
     * @return 新增结果
     */
    Result<Boolean> addUserOrg(UserOrgReqDto userOrgReqDto, String traceLogId);

    /**
     * 更新企业用户信息
     *
     * @param userOrgReqDto 企业用户信息
     * @param traceLogId    日志ID
     * @return 新增结果
     */
    Result<Boolean> updateUserOrg(UserOrgReqDto userOrgReqDto, String traceLogId);

    /**
     * 根据用户号查询企业用户Do
     *
     * @param userNo     会员号
     * @param traceLogId 日志ID
     * @return 企业用户Dto
     */
    Result<OrgInfoRespDto> findByUserNo(Long userNo, String traceLogId);

    /**
     * 根据qualifiedNo查询
     *
     * @param qualifiedNo 用户编号
     * @param traceLogId  日志ID
     * @return 企业用户Do
     */
    Result<OrgInfoRespDto> selectInfoByQualifiedNo(Long qualifiedNo, String traceLogId);

    /**
     * 根据userInfoNo查询
     *
     * @param userInfoNo 信息编号
     * @param traceLogId 日志ID
     * @return 企业用户Do
     */
    Result<OrgInfoRespDto> selectInfoByUserInfoNo(Long userInfoNo, String traceLogId);
}
