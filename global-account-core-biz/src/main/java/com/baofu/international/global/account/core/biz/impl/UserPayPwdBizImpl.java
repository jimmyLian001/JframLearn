package com.baofu.international.global.account.core.biz.impl;

import com.baofu.international.global.account.core.biz.UserPayPwdBiz;
import com.baofu.international.global.account.core.common.constant.NumberDict;
import com.baofu.international.global.account.core.common.enums.ErrorCodeEnum;
import com.baofu.international.global.account.core.common.enums.PayPwdStateEnum;
import com.baofu.international.global.account.core.dal.model.UserPwdDo;
import com.baofu.international.global.account.core.manager.UserPwdManager;
import com.system.commons.exception.BizServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 用户支付信息BIZ
 * <p>
 * <p>
 * User: 康志光 Date: 2017/11/04 Version: 1.0
 * </p>
 */
@Slf4j
@Service
public class UserPayPwdBizImpl implements UserPayPwdBiz {


    /**
     * 用户密码服务manager
     */
    @Autowired
    private UserPwdManager userPwdManager;

    /**
     * 用户支付密码校验
     *
     * @param userNo 用户号
     * @param payPwd 支付密码
     */
    @Override
    public void payPwdCheck(Long userNo, String payPwd, Integer pwdType) {

        UserPwdDo userPwdDo = userPwdManager.query(userNo, pwdType);
        UserPwdDo errorUserPayPwd = new UserPwdDo();
        errorUserPayPwd.setUserNo(userNo);
        errorUserPayPwd.setPwdType(pwdType);
        //校验支付密码是否失效
        if (PayPwdStateEnum.INVALID.getCode() == userPwdDo.getState()) {
            log.warn("用户：{}支付密码状态异常，状态：{}", userNo, userPwdDo.getState());
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190210, "用户支付密码已失效，请联系客服处理");
        }
        //支付密码锁定校验
        if (PayPwdStateEnum.LOCK.getCode() == userPwdDo.getState()) {
            //当前时间 - 支付密码锁定时间
            Long dateDifference = System.currentTimeMillis() - userPwdDo.getLockAt().getTime();
            if (dateDifference < NumberDict.THIRTY_MINUTES) {
                log.warn("用户：{}支付密码已锁定，状态：{}", userNo, userPwdDo.getState());
                throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190210, "用户支付密码已锁定，请稍后重试");
            }
        }
        //校验支付密码是否正确
        if (!StringUtils.equals(userPwdDo.getPwd(), payPwd)) {
            log.warn("用户：{}支付密码校验失败", userNo);
            errorUserPayPwd.setErrorNum(userPwdDo.getErrorNum() == null ? 0 : userPwdDo.getErrorNum() + NumberDict.ONE);
            //判断是否需要修改锁定状态
            if (errorUserPayPwd.getErrorNum() > NumberDict.FOUR) {
                errorUserPayPwd.setLockAt(new Date());
                errorUserPayPwd.setState(PayPwdStateEnum.LOCK.getCode());
            }
            userPwdManager.update(errorUserPayPwd);
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190210, "用户支付密码输入错误，请重试");
        }
        errorUserPayPwd.setLockAt(null);
        errorUserPayPwd.setState(PayPwdStateEnum.NORMAL.getCode());
        errorUserPayPwd.setErrorNum(NumberDict.ZERO);
        userPwdManager.update(errorUserPayPwd);
    }
}