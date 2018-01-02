package com.baofu.international.global.account.core.service.convert;

import com.baofu.international.global.account.core.biz.models.UserLoginReqBo;
import com.baofu.international.global.account.core.biz.models.UserLoginRespBo;
import com.baofu.international.global.account.core.facade.model.UserLoginReqDTO;
import com.baofu.international.global.account.core.facade.model.UserLoginRespDTO;

/**
 * 用户登录服务参数转换
 *
 * @author: 不良人 Date:2017/11/6 ProjectName: globalaccount-core Version: 1.0
 */
public final class UserLoginFacadeConvert {

    private UserLoginFacadeConvert() {
    }

    /**
     * 用户登录参数转换
     *
     * @param userLoginReqDTO 登录请求信息
     * @return 登录信息
     */
    public static UserLoginReqBo toUserLoginReqBo(UserLoginReqDTO userLoginReqDTO) {
        UserLoginReqBo userLoginReqBo = new UserLoginReqBo();
        userLoginReqBo.setLoginNo(userLoginReqDTO.getLoginNo());
        userLoginReqBo.setLoginPwd(userLoginReqDTO.getLoginPwd());
        userLoginReqBo.setLoginIp(userLoginReqDTO.getLoginIp());
        return userLoginReqBo;
    }

    /**
     * 查询信息返回参数
     *
     * @param respBo 登录查询信息
     * @return Session信息
     */
    public static UserLoginRespDTO toUserLoginRespBo(UserLoginRespBo respBo) {

        UserLoginRespDTO userLoginRespDTO = new UserLoginRespDTO();
        userLoginRespDTO.setLoginNo(respBo.getLoginNo());
        userLoginRespDTO.setUserNo(respBo.getUserNo());
        userLoginRespDTO.setCustomerNo(respBo.getCustomerNo());
        userLoginRespDTO.setUserType(respBo.getUserType());
        userLoginRespDTO.setLoginType(respBo.getLoginType());
        return userLoginRespDTO;
    }
}
