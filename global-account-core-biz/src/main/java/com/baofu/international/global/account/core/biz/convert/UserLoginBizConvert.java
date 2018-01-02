package com.baofu.international.global.account.core.biz.convert;

import com.baofu.international.global.account.core.biz.models.UserLoginRespBo;
import com.baofu.international.global.account.core.dal.model.UserLoginInfoDo;
import com.baofu.international.global.account.core.dal.model.UserLoginLogDo;
import com.baofu.international.global.account.core.dal.model.UserPwdDo;

/**
 * 用户登录biz参数转换
 *
 * @author: 不良人 Date:2017/11/5 ProjectName: account-core Version: 1.0
 */
public final class UserLoginBizConvert {

    private UserLoginBizConvert() {
    }

    /**
     * 用户登录返回信息
     *
     * @param infoDo 用户信息
     * @return 用户登录接口返回参数
     */
    public static UserLoginRespBo toUserLoginRespDTO(UserLoginInfoDo infoDo) {

        UserLoginRespBo respDTO = new UserLoginRespBo();
        respDTO.setUserNo(infoDo.getUserNo());
        respDTO.setLoginNo(infoDo.getLoginNo());
        respDTO.setUserType(infoDo.getUserType());
        respDTO.setLoginType(infoDo.getLoginType());
        return respDTO;
    }

    /**
     * 登录日志转换
     *
     * @param infoDo     用户信息
     * @param customerDo 客户信息
     * @param loginIp    登录IP
     * @param remarks    备注
     * @return 日志对象
     */
    public static UserLoginLogDo toLoginLogDo(UserLoginInfoDo infoDo, UserPwdDo customerDo, String loginIp,
                                              String remarks) {
        UserLoginLogDo userLoginLogDo = new UserLoginLogDo();
        userLoginLogDo.setLoginIp(loginIp);
        userLoginLogDo.setRemarks(remarks);
        userLoginLogDo.setLoginNo(infoDo.getLoginNo());
        userLoginLogDo.setUserNo(customerDo.getUserNo());
        userLoginLogDo.setLoginType(infoDo.getLoginType());
        userLoginLogDo.setLoginState(customerDo.getState());
        return userLoginLogDo;
    }
}
