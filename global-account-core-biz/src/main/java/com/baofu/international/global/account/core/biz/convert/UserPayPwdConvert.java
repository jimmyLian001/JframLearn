package com.baofu.international.global.account.core.biz.convert;

import com.baofu.international.global.account.core.biz.models.UserPayPwdBo;
import com.baofu.international.global.account.core.dal.model.UserPayPwdDo;
import org.springframework.beans.BeanUtils;


/**
 * 用户支付信息转换
 * <p>
 * 1,用户支付信息转换
 * <p>
 *
 * @author : wuyazi
 * @date: 2017/11/04
 * @version: 1.0.0
 * </p>
 */
public final class UserPayPwdConvert {

    private UserPayPwdConvert() {
    }

    public static UserPayPwdDo convert(UserPayPwdBo userPayPwdBo) {
        if (userPayPwdBo == null) {
            return null;
        }
        UserPayPwdDo userPayPwdDo = new UserPayPwdDo();
        BeanUtils.copyProperties(userPayPwdBo, userPayPwdDo);
        userPayPwdDo.setUpdateBy("system");
        return userPayPwdDo;
    }

    public static UserPayPwdBo convert(UserPayPwdDo userPayPwdDo) {
        if (userPayPwdDo == null) {
            return null;
        }
        UserPayPwdBo userPayPwdBo = new UserPayPwdBo();
        BeanUtils.copyProperties(userPayPwdDo, userPayPwdBo);
        return userPayPwdBo;
    }

}