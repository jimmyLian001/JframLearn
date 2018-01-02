package com.baofu.international.global.account.core.service.impl;

import com.baofu.global.cross.border.logback.ex.constant.MDCPropertyConsts;
import com.baofu.international.global.account.core.biz.UserPayPwdBiz;
import com.baofu.international.global.account.core.facade.UserPayPwdFacade;
import com.system.commons.result.Result;
import com.system.commons.utils.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户客户信息facade
 * <p>
 * 1,重置登录密码
 * 2，次修改登录密码
 * </p>
 *
 * @author : wuyazi
 * @version : 1.0.0
 * @date : 2017/11/04
 */
@Slf4j
@Service
public class UserPayPwdFacadeImpl implements UserPayPwdFacade {

    /**
     * 用户客户信息BIZ
     */
    @Autowired
    private UserPayPwdBiz userPayPwdBiz;

    /**
     * 用户支付密码校验
     *
     * @param userNo 用户号
     * @param payPwd 支付密码
     * @param logId  日志ID
     * @return 返回是否校验通过
     */
    @Override
    public Result<Boolean> userPwdCheck(Long userNo, String payPwd, Integer pwdType, String logId) {

        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, logId);
        log.info("用户支付密码校验,用户号：{}", userNo);
        Result<Boolean> result;
        try {
            userPayPwdBiz.payPwdCheck(userNo, payPwd, pwdType);
            result = new Result<>(Boolean.TRUE);
        } catch (Exception e) {
            result = ExceptionUtils.getResponse(e);
            log.error("用户支付密码校验异常{}", e);
        }
        log.error("用户支付密码校验{}", result);
        return result;
    }
}
