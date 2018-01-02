package com.baofu.international.global.account.core.biz;


import com.baofu.international.global.account.core.biz.models.UserLoginReqBo;
import com.baofu.international.global.account.core.biz.models.UserLoginRespBo;

/**
 * 用户登录服务
 *
 * @author: 不良人 Date:2017/11/5 ProjectName: account-core Version: 1.0
 */
public interface UserLoginBiz {

    /**
     * 用户登录
     *
     * @param userLoginReqBo 登录请求信息
     * @return 用户信息
     */
    UserLoginRespBo userLogin(UserLoginReqBo userLoginReqBo);

    /**
     * 查询用户信息
     *
     * @param loginNo 登录号
     * @return 用户信息
     */
    UserLoginRespBo queryLoginInfo(String loginNo);

}
