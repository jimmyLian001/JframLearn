package com.baofu.international.global.account.core.manager;

import com.baofu.international.global.account.core.dal.model.*;

/**
 * 用户绑定手机号维护数据层操作接口
 * <p/>
 * User: lian zd Date:2017/11/4 ProjectName: account-core Version:1.0
 */
public interface UserTelInfoManager {

    /**
     * 更新用户登录信息表
     *
     * @param userLoginInfoDo 更新对象
     */
    void updateUserLoginInfo(UserLoginInfoDo userLoginInfoDo);

    /**
     * 根据用户名查询用户密保问题
     *
     * @param userNo 用户号
     * @return userSecrueqaInfoDo
     */
    UserSecrueqaInfoDo queryByUserNo(Long userNo);

    /**
     * 更新用户登录日志记录表
     *
     * @param userLoginLogDo 更新对象
     */
    void updateUserLoginLog(UserLoginLogDo userLoginLogDo);

    /**
     * 根据用户名查询用户登录
     *
     * @param userNo 用户号
     * @return userSecrueqaInfoDo
     */
    UserLoginInfoDo queryUserInfoByUserNo(Long userNo);

    /**
     * 更新企业用户信息表 手机号
     *
     * @param userOrgDo userOrgDo
     * @return
     */
    void updateOrgTelByUserNo(UserOrgDo userOrgDo);

    /**
     * 更新个人用户信息表 手机号
     *
     * @param userPersonalDo userPersonalDo
     * @return
     */
    void updatePersonalTelByUserNo(UserPersonalDo userPersonalDo);

    /**
     * 根据用户名查询用户登录
     *
     * @param userNo 用户号
     * @return userSecrueqaInfoDo
     */
    UserLoginInfoDo queryUserInfoByUserNo(Long userNo, Integer loginType, String loginNo);

    /**
     * 新增登录信息
     *
     * @param tUserLoginLogDo UserLoginInfoDo
     */
    void insertLoginInfo(UserLoginInfoDo tUserLoginLogDo);
}
