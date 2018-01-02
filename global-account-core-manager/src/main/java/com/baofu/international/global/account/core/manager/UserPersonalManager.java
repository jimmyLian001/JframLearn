package com.baofu.international.global.account.core.manager;

import com.baofu.international.global.account.core.dal.model.UserPersonalDo;

/**
 * 个人信息服务
 * <p>
 * 1、添加认证个人信息
 * </p>
 *
 * @author : hetao  Date: 2017/11/06 ProjectName: globalaccount Version: 1.0
 */
public interface UserPersonalManager {

    /**
     * 添加认证个人信息
     *
     * @param userPersonalDo 个人信息
     */
    void updateUserPersonal(UserPersonalDo userPersonalDo);

    /**
     * 根据userNo查询
     *
     * @param userNo 用户编号
     * @return 用户信息
     */
    UserPersonalDo selectInfoByUserNo(Long userNo);

    /**
     * 根据userNo查询
     *
     * @param userInfoNo 申请编号
     * @return 用户信息
     */
    UserPersonalDo queryByUserInfoNo(Long userInfoNo);

    /**
     * 根据qualifiedNo查询
     *
     * @param qualifiedNo 用户编号
     * @return 用户信息
     */
    UserPersonalDo selectInfoByQualifiedNo(Long qualifiedNo);

    /**
     * 新增个人用户基本信息
     *
     * @param userPersonalDo 请求参数
     */
    void insertUserPersonalInfo(UserPersonalDo userPersonalDo);

    /**
     * 根据email查询
     *
     * @param email 邮箱
     * @return 用户信息
     */
    UserPersonalDo queryByEmail(String email);
}
