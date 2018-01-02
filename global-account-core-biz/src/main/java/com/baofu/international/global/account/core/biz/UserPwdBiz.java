package com.baofu.international.global.account.core.biz;

import com.baofu.international.global.account.core.biz.models.UserPwdReqBo;
import com.baofu.international.global.account.core.biz.models.UserPwdRespBo;

/**
 * 用户客户信息BIZ
 * <p>
 * <p>
 * User: 康志光 Date: 2017/11/04 Version: 1.0
 * </p>
 */
public interface UserPwdBiz {

    /**
     * 重置密码
     *
     * @param userPwdReqBo 用户密码修改，重置请求参数
     */
    void resetLoginPwd(UserPwdReqBo userPwdReqBo);

    /**
     * 修改密码
     *
     * @param userPwdReqBo 用户密码修改，重置请求参数
     */
    void modifyLoginPwd(UserPwdReqBo userPwdReqBo);

    /**
     * 查询用户登录信息
     *
     * @param loginNo 参数
     * @return long 用户号
     */
    Long find(String loginNo);

    /**
     * 查询用户密码信息
     *
     * @param userNo  用户号
     * @param pwdType 密码类型
     * @return UserPwdRespBo 密码信息响应
     */
    UserPwdRespBo findPwdInfo(Long userNo, Integer pwdType);

    /**
     * 支付密码验证
     *
     * @param payPwd 用户密码修改请求参数
     * @param userNo 用户号
     */
    void checkPayPwd(String payPwd, Long userNo);

    /**
     * 设置支付密码
     *
     * @param payPwd 设置支付密码 请求参数
     */
    void setPayPwd(UserPwdReqBo payPwd);
}