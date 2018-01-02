package com.baofu.international.global.account.core.manager;

import com.baofu.international.global.account.core.dal.model.UserLoginInfoDo;
import com.baofu.international.global.account.core.dal.model.UserLoginLogDo;
import com.baofu.international.global.account.core.dal.model.UserPwdDo;

/**
 * 用户登录服务
 *
 * @author: 不良人 Date:2017/11/5 ProjectName: account-core Version: 1.0
 */
public interface UserLoginManager {

    /**
     * 查询用户信息
     *
     * @param loginNo 登录号
     * @return 用户信息
     */
    UserLoginInfoDo queryLoginInfo(String loginNo);

    /**
     * 查询用户客户信息
     *
     * @param userNo 用户号
     * @return 用户信息
     */
    UserPwdDo queryCustomer(Long userNo, Integer type);

    /**
     * 更新登录密码错误次数
     *
     * @param userNo           用户号
     * @param loginPwdErrorNum 登录错误次数
     * @param userState        用户状态
     * @param pwdType          密码类型
     */
    void updateByUserNo(Long userNo, Integer loginPwdErrorNum, Integer userState, Integer pwdType);

    /**
     * 添加登录日志
     *
     * @param userLoginLogDo 登录日志
     */
    void addLoginLog(UserLoginLogDo userLoginLogDo);
}
