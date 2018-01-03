package com.baofu.international.global.account.client.web.convert;

import com.baofu.international.global.account.client.web.models.SessionVo;
import com.baofu.international.global.account.core.facade.model.UserLoginRespDTO;

/**
 * 用户登录转换
 *
 * @author: 不良人 Date:2017/11/6 ProjectName: account-client Version: 1.0
 */
public final class LoginControllerConvert {

    private LoginControllerConvert() {
    }

    /**
     * Session 对象转换
     *
     * @param respDTO 用户登录接口返回参数
     * @return Session对象
     */
    public static SessionVo toLoginInfoVo(UserLoginRespDTO respDTO) {
        SessionVo sessionVo = new SessionVo();
        sessionVo.setLoginNo(respDTO.getLoginNo());
        sessionVo.setUserNo(respDTO.getUserNo());
        sessionVo.setUserType(respDTO.getUserType());
        return sessionVo;
    }
}
