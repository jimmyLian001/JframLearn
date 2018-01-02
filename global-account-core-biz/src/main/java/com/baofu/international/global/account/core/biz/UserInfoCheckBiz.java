package com.baofu.international.global.account.core.biz;

/**
 * 资质校验接口
 * <p>
 * 1、新增资质时邮箱校验
 * 2、更新资质时邮箱校验
 * </p>
 *
 * @author : hetao  Date: 2017/11/06 ProjectName: globalaccount Version: 1.0
 */
public interface UserInfoCheckBiz {

    /**
     * 新增资质时邮箱校验
     *
     * @param email 邮箱
     */
    void addCheckEmail(String email);

    /**
     * 更新资质时邮箱校验
     *
     * @param email      邮箱
     * @param userInfoNo 用户信息编号
     */
    void updateCheckEmail(String email, Long userInfoNo);

    /**
     * 校验用户状态是否为未认证和认证失败
     *
     * @param userType   用户类型
     * @param userInfoNo 用户信息编号
     */
    void updateCheckRealNameStatus(Long userInfoNo, Integer userType);
}
