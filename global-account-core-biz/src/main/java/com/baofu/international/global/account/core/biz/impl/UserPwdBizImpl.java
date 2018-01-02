package com.baofu.international.global.account.core.biz.impl;

import com.baofu.international.global.account.core.biz.UserPwdBiz;
import com.baofu.international.global.account.core.biz.convert.UserCustomerConvert;
import com.baofu.international.global.account.core.biz.models.UserPwdReqBo;
import com.baofu.international.global.account.core.biz.models.UserPwdRespBo;
import com.baofu.international.global.account.core.common.constant.NumberDict;
import com.baofu.international.global.account.core.common.enums.ErrorCodeEnum;
import com.baofu.international.global.account.core.dal.model.UserLoginInfoDo;
import com.baofu.international.global.account.core.dal.model.UserPwdDo;
import com.baofu.international.global.account.core.manager.UserLoginManager;
import com.baofu.international.global.account.core.manager.UserPwdManager;
import com.system.commons.exception.BizServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 用户客户信息BIZ
 * <p>
 * <p>
 * User: 康志光 Date: 2017/11/04 Version: 1.0
 * </p>
 */
@Slf4j
@Service
public class UserPwdBizImpl implements UserPwdBiz {

    /**
     * 用户客户信息服务
     */
    @Autowired
    private UserPwdManager userPwdManager;

    /**
     * 登录信息服务
     */
    @Autowired
    private UserLoginManager userLoginManager;

    /**
     * 重置密码
     *
     * @param userPwdReqBo 用户密码修改，重置请求参数
     */
    @Override
    public void resetLoginPwd(UserPwdReqBo userPwdReqBo) {
        //验证新密码和二次密码是否一致和规范
        if (!userPwdReqBo.getFirstPwd().equals(userPwdReqBo.getSecondPwd())) {
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190100);
        }
        //更新用户登陆密码为新密码
        userPwdManager.update(UserCustomerConvert.convert(userPwdReqBo));
    }

    /**
     * 修改密码
     *
     * @param userPwdReqBo 用户密码修改，重置请求参数
     */
    @Override
    public void modifyLoginPwd(UserPwdReqBo userPwdReqBo) {
        //验证用户是否被锁定并旧密码是否正确
        UserPwdDo userPwdDo = userPwdManager.query(userPwdReqBo.getUserNo(), userPwdReqBo.getPwdType());
        if (userPwdDo == null || !userPwdDo.getPwd().equals(userPwdReqBo.getOldPwd())) {
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190101);
        }
        //验证新密码和二次密码是否一致和规范
        if (!userPwdReqBo.getFirstPwd().equals(userPwdReqBo.getSecondPwd())) {
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190100);
        }
        //更新用户登陆密码为新密码
        userPwdManager.update(UserCustomerConvert.convert(userPwdReqBo));
    }

    /**
     * 查询用户登录信息
     *
     * @param loginNo 参数
     * @return Long 结果
     */
    @Override
    public Long find(String loginNo) {
        UserLoginInfoDo userLoginInfoDo = userLoginManager.queryLoginInfo(loginNo);
        if (userLoginInfoDo == null) {
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190104);
        }
        return userLoginInfoDo.getUserNo();
    }

    /**
     * 查询用户密码信息
     *
     * @param userNo  用户号
     * @param pwdType 密码类型
     * @return UserPwdRespBo 密码信息响应
     */
    @Override
    public UserPwdRespBo findPwdInfo(Long userNo, Integer pwdType) {
        UserPwdDo userPwdDo = userPwdManager.query(userNo, pwdType);
        if (userPwdDo == null) {
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190213);
        }
        UserPwdRespBo userPwdRespBo = new UserPwdRespBo();
        BeanUtils.copyProperties(userPwdDo, userPwdRespBo);
        return userPwdRespBo;
    }

    /**
     * 支付密码验证
     *
     * @param payPwd 用户密码修改请求参数
     * @param userNo 登录名
     */
    @Override
    public void checkPayPwd(String payPwd, Long userNo) {

        UserPwdDo serPwdDo = userLoginManager.queryCustomer(userNo, NumberDict.TWO);
        //当前时间 - 密码错误次数锁定时间 < 30分钟
        Boolean timeFlag = serPwdDo.getLockAt() != null
                && System.currentTimeMillis() - serPwdDo.getLockAt().getTime() < NumberDict.THIRTY_MINUTES;
        //判断是否锁定：密码错误次数大于4 && 时间判断
        if (serPwdDo.getErrorNum() > NumberDict.FOUR && timeFlag) {
            log.info("[支付密码验证] {}账号支付密码已被锁定", userNo);
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_200002, "您的支付密码已被锁定，请在30分钟后重试");
        }
        //判断用户状态
        if (NumberDict.ZERO == serPwdDo.getState()) {
            log.info("[支付密码验证] 用户状态不正确，状态值：{}", serPwdDo.getState());
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_200003, "用户状态不正确");
        }
        //密码判断
        pwdCheck(serPwdDo, payPwd);
        //登录成功更新密码错误次数为0
        if (serPwdDo.getErrorNum() > NumberDict.ZERO) {
            userLoginManager.updateByUserNo(serPwdDo.getUserNo(), NumberDict.ZERO, NumberDict.ONE, NumberDict.TWO);
        }

    }

    /**
     * 密码校验
     *
     * @param userPwdDo 账户信息
     * @param payPwd    支付密码
     */
    private void pwdCheck(UserPwdDo userPwdDo, String payPwd) {

        Integer errorNum = userPwdDo.getErrorNum() + NumberDict.ONE;
        String errorMsg = "支付密码不正确，请重新输入";
        Date lastLoginAt = userPwdDo.getLockAt() != null ? userPwdDo.getLockAt() : new Date();
        Boolean timeFlag = System.currentTimeMillis() - lastLoginAt.getTime() > NumberDict.THIRTY_MINUTES;

        if (!payPwd.equals(userPwdDo.getPwd())) {
            log.info("[支付密码验证] 密码不一致，用户输入：{}，查询出：{}", payPwd, userPwdDo.getPwd());
            //30分钟后再次输入错误密码
            if (userPwdDo.getErrorNum() > NumberDict.FOUR && timeFlag) {
                errorNum = NumberDict.FIVE;
            }
            //最后一次提示账户锁定
            if (NumberDict.FIVE == errorNum) {
                errorMsg = "您的支付密码已被锁定，请在30分钟后重试";
            }
            userLoginManager.updateByUserNo(userPwdDo.getUserNo(), errorNum, null, NumberDict.TWO);
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_200002, errorMsg);
        }
    }

    /**
     * 设置支付密码
     *
     * @param payPwd 设置支付密码 请求参数
     */
    @Override
    public void setPayPwd(UserPwdReqBo payPwd) {
        //验证新密码和二次密码是否一致和规范
        if (!payPwd.getFirstPwd().equals(payPwd.getSecondPwd())) {
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190100);
        }
        //设置用户支付密码
        UserPwdDo userPwdDo = UserCustomerConvert.convert(payPwd);
        userPwdDo.setState(NumberDict.ONE);
        userPwdDo.setErrorNum(NumberDict.ZERO);
        userPwdDo.setVersion(NumberDict.ONE);
        userPwdManager.insert(userPwdDo);
    }
}