package com.baofu.international.global.account.core.biz.convert;

import com.baofu.international.global.account.core.biz.models.UserPwdReqBo;
import com.baofu.international.global.account.core.dal.model.UserPwdDo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;


/**
 * 用户客户信息转换
 * <p>
 * 1,用户客户信息转换
 * <p>
 *
 * @author : wuyazi
 * @version : 1.0.0
 * </p>
 * @date : 2017/11/04
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserCustomerConvert {

    public static UserPwdDo convert(UserPwdReqBo userPwdReqBo) {
        if (userPwdReqBo == null) {
            return null;
        }
        UserPwdDo userPwdDo = new UserPwdDo();
        userPwdDo.setUserNo(userPwdReqBo.getUserNo());
        userPwdDo.setPwd(userPwdReqBo.getSecondPwd());
        userPwdDo.setPwdType(userPwdReqBo.getPwdType());
        userPwdDo.setUpdateBy(userPwdReqBo.getOperator());
        return userPwdDo;
    }


}