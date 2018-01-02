package com.baofu.international.global.account.core.biz;

import com.baofu.international.global.account.core.biz.models.OrgInfoReqBo;

/**
 * 企业信息服务
 * <p>
 * 1、更新企业用户信息
 * </p>
 *
 * @author : hetao  Date: 2017/11/07 ProjectName: globalaccount Version: 1.0
 */
public interface UserOrgBiz {

    /**
     * 更新企业用户信息
     *
     * @param orgInfoReqBo 企业用户信息
     */
    void updateUserOrg(OrgInfoReqBo orgInfoReqBo);

    /**
     * 更新企业用户信息
     *
     * @param orgInfoReqBo 企业用户信息
     */
    void addUserOrg(OrgInfoReqBo orgInfoReqBo);
}
