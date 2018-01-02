package com.baofu.international.global.account.core.facade;

import com.system.commons.result.Result;

/**
 * 用户支付密码信息facade
 * <p>
 * 1,重置支付密码
 * </p>
 * User: 康志光  Date: 2017/11/04 ProjectName: globalaccount Version: 1.0
 */
public interface UserPayPwdFacade {

    /**
     * 用户密码校验
     *
     * @param userNo  用户号
     * @param payPwd  支付密码
     * @param pwdType 密码类型
     * @param logId   日志ID
     * @return 返回是否校验通过
     */
    Result<Boolean> userPwdCheck(Long userNo, String payPwd, Integer pwdType, String logId);
}
