package com.baofu.international.global.account.core.biz;

/**
 * 用户客户信息BIZ
 * <p>
 * <p>
 * User: 康志光 Date: 2017/11/04 Version: 1.0
 * </p>
 */
public interface UserPayPwdBiz {
    /**
     * 用户支付密码校验
     *
     * @param userNo  用户号
     * @param payPwd  支付密码
     * @param pwdType 密码类型
     */
    void payPwdCheck(Long userNo, String payPwd, Integer pwdType);
}