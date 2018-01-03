package com.baofu.international.global.account.client.service;

import com.baofu.international.global.account.client.service.models.OrgAuthReq;
import com.baofu.international.global.account.client.service.models.PersonAuthReq;
import com.baofu.international.global.account.core.facade.model.user.OrgInfoRespDto;
import com.baofu.international.global.account.core.facade.model.user.UserPersonalDto;

import java.io.IOException;
import java.util.Map;

/**
 * 实名认证
 * <p>
 * 1、个人实名认证
 * 2、企业实名认证
 * 3、查询个人用户信息
 * 4、查询企业用户信息
 * </p>
 *
 * @author : hetao Date:2017/11/05 ProjectName: account-client Version: 1.0
 */
public interface RealNameAuthService {

    /**
     * 个人实名认证
     *
     * @param personAuthReq 个人实名请求信息
     */
    void personAuth(PersonAuthReq personAuthReq) throws IOException;

    /**
     * 个人实名认证更新
     *
     * @param personAuthReq 个人实名请求信息
     */
    void personAuthUpdate(PersonAuthReq personAuthReq) throws IOException;

    /**
     * 企业实名认证
     *
     * @param orgAuthReq 企业实名请求信息
     */
   void orgAuth(OrgAuthReq orgAuthReq)  throws IOException;

    /**
     * 企业实名认证更新
     *
     * @param orgAuthReq 企业实名请求信息
     */
    void orgAuthUpdate(OrgAuthReq orgAuthReq)  throws IOException;

    /**
     * 查询个人用户信息
     *
     * @param userInfoNo 信息编号
     * @return 处理结果
     */
    UserPersonalDto queryUserPersonal(Long userInfoNo);

    /**
     * 查询企业用户信息
     *
     * @param userInfoNo 信息编号
     * @return 处理结果
     */
    OrgInfoRespDto queryUserOrg(Long userInfoNo);

    /**
     * 查询个人用户信息
     *
     * @param userNo 用户编号
     * @return 处理结果
     */
    UserPersonalDto queryUserPersonalByUserNo(Long userNo);

    /**
     * 查询企业用户信息
     *
     * @param userNo 用户编号
     * @return 处理结果
     */
    OrgInfoRespDto queryUserOrgByUserNo(Long userNo);

    /**
     * 查询用户实名认证状态
     *
     * @param userNo   用户号
     * @param userType 用户类型
     * @return 返回认证状态
     */
    int userAuthStatus(Long userNo, int userType);
}
