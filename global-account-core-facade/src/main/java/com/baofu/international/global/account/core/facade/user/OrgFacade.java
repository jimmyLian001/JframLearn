package com.baofu.international.global.account.core.facade.user;

import com.baofu.international.global.account.core.facade.model.user.EditUserReqDto;
import com.baofu.international.global.account.core.facade.model.user.UserOrgReqDto;
import com.system.commons.result.PageRespDTO;
import com.system.commons.result.Result;

import java.util.List;

/**
 * 企业对象服务接口
 * <p>
 * 1、机构查询接口
 * </p>
 * User: 陶伟超 Date: 2017/11/4 ProjectName: account-core Version: 1.0.0
 */
public interface OrgFacade {

    /**
     * 根据信息编号查询机构信息
     *
     * @param userInfoNo 信息编号
     * @param logId      日志ID
     * @return 机构对象
     */
    Result<UserOrgReqDto> findOrgByApplyNo(Long userInfoNo, String logId);

    /**
     * 根据信息编号查询企业资质信息
     *
     * @param userInfoNo 信息编号
     * @param logId      日志ID
     * @return 机构对象
     */
    Result<UserOrgReqDto> findQualifiedByApplyNo(Long userInfoNo, String logId);

    /**
     * 查询企业用户分页信息
     *
     * @param userOrgReqDto 用户请求对象
     * @param logId         日志标记
     * @return 用户信息
     */
    Result<PageRespDTO> orgQueryForPage(UserOrgReqDto userOrgReqDto, String logId);


    /**
     * 修改认证状态
     *
     * @param editUserReqDto 更新用户请求对象
     * @return true：修改成功 false:修改失败
     */
    Result<Boolean> modifyAuthStatus(EditUserReqDto editUserReqDto, String logId);

    /**
     * 根据用户编号查询所有资质信息
     *
     * @param userNo 用户编号
     * @param logId  日志ID
     * @return 返回结果
     */
    Result<List<UserOrgReqDto>> queryUserQualified(Long userNo, String logId);

}
