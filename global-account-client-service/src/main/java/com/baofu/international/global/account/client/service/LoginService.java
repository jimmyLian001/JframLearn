package com.baofu.international.global.account.client.service;

import com.baofu.international.global.account.core.facade.model.UserLoginRespDTO;

/**
 * 用户登录
 * <p>
 * 1.用户登录服务
 * </p>
 *
 * @author : 不良人
 * @version : 1.0.0
 * @date : 2017/11/5
 */
public interface LoginService {

    /**
     * 用户登录服务
     *
     * @param loginNo  用户名
     * @param loginPwd 密码
     * @param loginIp  登录IP
     * @return 登录信息
     */
    UserLoginRespDTO loginService(String loginNo, String loginPwd, String loginIp);

    /**
     * 根据登录号查询登录信息
     *
     * @param loginNo 登录号
     * @return 返回结果
     */
    UserLoginRespDTO queryLoginInfo(String loginNo);
}
