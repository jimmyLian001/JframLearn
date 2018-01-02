package com.baofu.international.global.account.core.biz.impl;

import com.baofu.international.global.account.core.biz.UserLoginBiz;
import com.baofu.international.global.account.core.biz.convert.UserLoginBizConvert;
import com.baofu.international.global.account.core.biz.models.UserLoginReqBo;
import com.baofu.international.global.account.core.biz.models.UserLoginRespBo;
import com.baofu.international.global.account.core.common.constant.NumberDict;
import com.baofu.international.global.account.core.common.enums.ErrorCodeEnum;
import com.baofu.international.global.account.core.dal.model.UserLoginInfoDo;
import com.baofu.international.global.account.core.dal.model.UserPwdDo;
import com.baofu.international.global.account.core.manager.UserLoginManager;
import com.system.commons.exception.BizServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 用户登录服务
 *
 * @author: 不良人 Date:2017/11/5 ProjectName: account-core Version: 1.0
 */
@Slf4j
@Service
public class UserLoginBizImpl implements UserLoginBiz {

    /**
     * 用户登录服务
     */
    @Autowired
    private UserLoginManager userLoginManager;

    /**
     * 用户登录
     *
     * @param reqBo 登录请求信息
     * @return 用户信息
     */
    @Override
    public UserLoginRespBo userLogin(UserLoginReqBo reqBo) {

        UserLoginInfoDo infoDo = userLoginManager.queryLoginInfo(reqBo.getLoginNo());
        UserPwdDo serPwdDo = userLoginManager.queryCustomer(infoDo.getUserNo(), NumberDict.ONE);
        //当前时间 - 密码错误次数锁定时间 < 30分钟
        Boolean timeFlag = serPwdDo.getLockAt() != null
                && System.currentTimeMillis() - serPwdDo.getLockAt().getTime() < NumberDict.THIRTY_MINUTES;
        //判断是否锁定：密码错误次数大于4 && 时间判断
        if (serPwdDo.getErrorNum() > NumberDict.FOUR && timeFlag) {
            log.info("[用户登录] {}账号已被锁定", reqBo.getLoginPwd());
            addLoginLog(infoDo, serPwdDo, reqBo, "账号已被锁定");
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_200002, "您的账号已被锁定，请在30分钟后重试");
        }
        //判断用户状态
        if (NumberDict.ZERO == serPwdDo.getState()) {
            log.info("[用户登录] 用户状态不正确，状态值：{}", serPwdDo.getState());
            addLoginLog(infoDo, serPwdDo, reqBo, "用户状态不正确");
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_200003, "用户状态不正确");
        }
        //密码判断
        pwdCheck(infoDo, serPwdDo, reqBo);
        //登录成功更新密码错误次数为0
        if (serPwdDo.getErrorNum() > NumberDict.ZERO) {
            userLoginManager.updateByUserNo(serPwdDo.getUserNo(), NumberDict.ZERO, NumberDict.ONE, NumberDict.ONE);
        }
        UserLoginRespBo respDTO = UserLoginBizConvert.toUserLoginRespDTO(infoDo);
        addLoginLog(infoDo, serPwdDo, reqBo, "登录成功");

        log.info("[用户登录] 返回登录信息：{}", respDTO);
        return respDTO;
    }

    /**
     * 插入登录日志
     *
     * @param infoDo     用户信息
     * @param customerDo 账户信息
     * @param reqBo      登录信息
     * @param remarks    备注
     */
    private void addLoginLog(UserLoginInfoDo infoDo, UserPwdDo customerDo, UserLoginReqBo reqBo, String remarks) {
        userLoginManager.addLoginLog(UserLoginBizConvert.toLoginLogDo(infoDo, customerDo, reqBo.getLoginIp(), remarks));
    }

    /**
     * 密码校验
     *
     * @param infoDo    用户信息
     * @param userPwdDo 账户信息
     * @param reqBo     登录信息
     */
    private void pwdCheck(UserLoginInfoDo infoDo, UserPwdDo userPwdDo, UserLoginReqBo reqBo) {

        Integer errorNum = userPwdDo.getErrorNum() + NumberDict.ONE;
        String errorMsg = "用户名不存在或者密码错误，还剩" + (NumberDict.FIVE - errorNum) + "次机会";
        Date lastLoginAt = userPwdDo.getLockAt() != null ? userPwdDo.getLockAt() : new Date();
        Boolean timeFlag = System.currentTimeMillis() - lastLoginAt.getTime() > NumberDict.THIRTY_MINUTES;

        if (!reqBo.getLoginPwd().equals(userPwdDo.getPwd())) {
            log.info("[用户登录] 密码不一致，用户输入：{}，查询出：{}", reqBo.getLoginPwd(), userPwdDo.getPwd());
            //30分钟后再次输入错误密码
            if (userPwdDo.getErrorNum() > NumberDict.FOUR && timeFlag) {
                errorNum = NumberDict.FIVE;
            }
            //最后一次提示账户锁定
            if (NumberDict.FIVE == errorNum) {
                errorMsg = "您的账号已被锁定，请在30分钟后重试";
            }
            addLoginLog(infoDo, userPwdDo, reqBo, "密码不一致");
            userLoginManager.updateByUserNo(userPwdDo.getUserNo(), errorNum, null, NumberDict.ONE);
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_200002, errorMsg);
        }
    }

    /**
     * 查询用户信息
     *
     * @param loginNo 登录号
     * @return 用户信息
     */
    @Override
    public UserLoginRespBo queryLoginInfo(String loginNo) {
        UserLoginInfoDo infoDo = userLoginManager.queryLoginInfo(loginNo);
        return UserLoginBizConvert.toUserLoginRespDTO(infoDo);
    }
}
