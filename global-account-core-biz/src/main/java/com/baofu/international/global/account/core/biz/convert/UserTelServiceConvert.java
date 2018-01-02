package com.baofu.international.global.account.core.biz.convert;

import com.baofu.international.global.account.core.biz.models.FixPhoneNoApplyBo;
import com.baofu.international.global.account.core.biz.models.UserRegisterTelInfoBo;
import com.baofu.international.global.account.core.common.constant.NumberDict;
import com.baofu.international.global.account.core.dal.model.UserLoginInfoDo;
import com.baofu.international.global.account.core.dal.model.UserSecrueqaInfoDo;

import java.util.Date;

/**
 * 收款账户用户绑定手机号码维护数据操作对象转换
 * <p/>
 *
 * @author : lian zd
 * @date :2017/11/4 ProjectName: account-core Version:1.0
 */
public final class UserTelServiceConvert {

    private UserTelServiceConvert() {

    }

    /**
     * 转换用户登录信息表对象
     *
     * @param fixPhoneNoApplyBo
     * @return
     */
    public static UserLoginInfoDo toUserLoginInfoDo(FixPhoneNoApplyBo fixPhoneNoApplyBo) {
        UserLoginInfoDo userLoginInfoDo = new UserLoginInfoDo();
        userLoginInfoDo.setLoginNo(fixPhoneNoApplyBo.getAfterFixPhoneNumber());
        userLoginInfoDo.setUserNo(fixPhoneNoApplyBo.getUserNo());
        userLoginInfoDo.setUpdateBy(fixPhoneNoApplyBo.getLoginNo());
        return userLoginInfoDo;
    }

    /**
     * 转换成用户信息查询返回对象
     *
     * @param userSecrueqaInfoDo userSecrueqaInfoDo
     * @return userRegisterTelInfoVo
     */
    public static UserRegisterTelInfoBo toUserRegisterTelInfoVo(UserSecrueqaInfoDo userSecrueqaInfoDo, UserLoginInfoDo userLoginInfoDo) {
        UserRegisterTelInfoBo userRegisterTelInfoVo = new UserRegisterTelInfoBo();
        userRegisterTelInfoVo.setUserNo(userSecrueqaInfoDo.getUserNo().toString());
        userRegisterTelInfoVo.setAnswer(userSecrueqaInfoDo.getAnswer());
        userRegisterTelInfoVo.setQuestionSequence(String.valueOf(userSecrueqaInfoDo.getQuestionSequence()));
        userRegisterTelInfoVo.setQuestionNo(userSecrueqaInfoDo.getQuestionNo().toString());
        userRegisterTelInfoVo.setCurrentPhoneNumber(userLoginInfoDo.getLoginNo());
        return userRegisterTelInfoVo;
    }

    public static UserLoginInfoDo toTUserLoginInfoDo(UserLoginInfoDo infoDo, String loginNo) {
        UserLoginInfoDo userLoginInfoDo = new UserLoginInfoDo();
        userLoginInfoDo.setLoginType(NumberDict.TWO);
        userLoginInfoDo.setLoginNo(loginNo);
        userLoginInfoDo.setLoginState(infoDo.getLoginState());
        userLoginInfoDo.setRemarks(infoDo.getRemarks());
        userLoginInfoDo.setUserNo(infoDo.getUserNo());
        userLoginInfoDo.setCreateAt(new Date());
        userLoginInfoDo.setCreateBy(loginNo);
        userLoginInfoDo.setUpdateBy(loginNo);
        userLoginInfoDo.setUpdateAt(new Date());
        userLoginInfoDo.setUserType(infoDo.getUserType());
        userLoginInfoDo.setLastLoginAt(new Date());
        return userLoginInfoDo;
    }
}
