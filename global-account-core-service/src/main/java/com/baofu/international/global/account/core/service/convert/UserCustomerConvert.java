package com.baofu.international.global.account.core.service.convert;

import com.baofu.international.global.account.core.biz.models.UserCustomerReqBo;
import com.baofu.international.global.account.core.biz.models.UserCustomerRespBo;
import com.baofu.international.global.account.core.biz.models.UserPwdReqBo;
import com.baofu.international.global.account.core.facade.model.UserCustomerReqDTO;
import com.baofu.international.global.account.core.facade.model.UserCustomerRespDTO;
import com.baofu.international.global.account.core.facade.model.UserPwdReqDto;

/**
 * 用户客户转换
 * <p>
 * <p>
 * User: 康志光 Date: 2017/11/04 Version: 1.0
 * </p>
 */
public final class UserCustomerConvert {

    private UserCustomerConvert() {
    }

    public static UserPwdReqBo convert(UserPwdReqDto loginPwdReqDTO) {
        if (loginPwdReqDTO == null) {
            return null;
        }
        UserPwdReqBo userPwdReqBo = new UserPwdReqBo();
        userPwdReqBo.setUserNo(loginPwdReqDTO.getUserNo());
        userPwdReqBo.setLoginNo(loginPwdReqDTO.getLoginNo());
        userPwdReqBo.setOldPwd(loginPwdReqDTO.getOldPwd());
        userPwdReqBo.setFirstPwd(loginPwdReqDTO.getFirstPwd());
        userPwdReqBo.setSecondPwd(loginPwdReqDTO.getSecondPwd());
        userPwdReqBo.setPwdType(loginPwdReqDTO.getPwdType());
        userPwdReqBo.setOperator(loginPwdReqDTO.getOperator());
        return userPwdReqBo;
    }

    public static UserCustomerReqBo convert(UserCustomerReqDTO userCustomerReqDTO) {
        if (userCustomerReqDTO == null) {
            return null;
        }
        UserCustomerReqBo userCustomerReqBo = new UserCustomerReqBo();
        userCustomerReqBo.setCustomerNo(userCustomerReqDTO.getCustomerNo());
        userCustomerReqBo.setUserNo(userCustomerReqDTO.getUserNo());
        userCustomerReqBo.setLoginNo(userCustomerReqDTO.getLoginNo());
        return userCustomerReqBo;
    }

    public static UserCustomerRespDTO convert(UserCustomerRespBo userCustomerRespBo) {
        if (userCustomerRespBo == null) {
            return null;
        }
        UserCustomerRespDTO userCustomerRespDTO = new UserCustomerRespDTO();
        userCustomerRespDTO.setUserNo(userCustomerRespBo.getUserNo());
        userCustomerRespDTO.setCustomerNo(userCustomerRespBo.getCustomerNo());
        userCustomerRespDTO.setUserType(userCustomerRespBo.getUserType());
        userCustomerRespDTO.setUserState(userCustomerRespBo.getUserState());
        userCustomerRespDTO.setLoginPwd(userCustomerRespBo.getLoginPwd());
        userCustomerRespDTO.setLoginPwdType(userCustomerRespBo.getLoginPwdType());
        userCustomerRespDTO.setLoginPwdErrorNum(userCustomerRespBo.getLoginPwdErrorNum());
        userCustomerRespDTO.setLoginPwdLockAt(userCustomerRespBo.getLoginPwdLockAt());
        userCustomerRespDTO.setLoginPwdModfiyAt(userCustomerRespBo.getLoginPwdModfiyAt());
        userCustomerRespDTO.setLoginPwdVersion(userCustomerRespBo.getLoginPwdVersion());
        userCustomerRespDTO.setRealnameStatus(userCustomerRespBo.getRealnameStatus());
        userCustomerRespDTO.setPhoneNumber(userCustomerRespBo.getPhoneNumber());
        userCustomerRespDTO.setEmail(userCustomerRespBo.getEmail());
        return userCustomerRespDTO;
    }


}
