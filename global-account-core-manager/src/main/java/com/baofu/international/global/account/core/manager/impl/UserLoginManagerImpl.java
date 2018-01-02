package com.baofu.international.global.account.core.manager.impl;

import com.baofu.international.global.account.core.common.constant.NumberDict;
import com.baofu.international.global.account.core.common.enums.ErrorCodeEnum;
import com.baofu.international.global.account.core.dal.mapper.UserLoginInfoMapper;
import com.baofu.international.global.account.core.dal.mapper.UserLoginLogMapper;
import com.baofu.international.global.account.core.dal.mapper.UserPwdMapper;
import com.baofu.international.global.account.core.dal.model.UserLoginInfoDo;
import com.baofu.international.global.account.core.dal.model.UserLoginLogDo;
import com.baofu.international.global.account.core.dal.model.UserPwdDo;
import com.baofu.international.global.account.core.manager.UserLoginManager;
import com.system.commons.exception.BizServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * 用户登录服务
 *
 * @author: 不良人 Date:2017/11/5 ProjectName: account-core Version: 1.0
 */
@Slf4j
@Repository
public class UserLoginManagerImpl implements UserLoginManager {


    /**
     * 用户登录信息操作
     */
    @Autowired
    private UserLoginInfoMapper userLoginInfoMapper;

    /**
     * 用户客户操作
     */
    @Autowired
    private UserPwdMapper userPwdMapper;

    /**
     * 登录日志操作
     */
    @Autowired
    private UserLoginLogMapper loginLogMapper;

    /**
     * 查询用户信息
     *
     * @param loginNo 登录号
     * @return 用户信息
     */
    @Override
    public UserLoginInfoDo queryLoginInfo(String loginNo) {

        UserLoginInfoDo infoDo = userLoginInfoMapper.findByLoginNo(loginNo);
        if (infoDo == null) {
            log.info("[用户登录] 用户信息不存在");
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_200001, "账户或密码输入不正确");
        }
        return infoDo;
    }

    /**
     * 查询用户客户信息
     *
     * @param userNo 用户号
     * @return 用户信息
     */
    @Override
    public UserPwdDo queryCustomer(Long userNo, Integer type) {
        UserPwdDo userPwdDo = userPwdMapper.selectByUserNo(userNo, type);
        if (userPwdDo == null && NumberDict.ONE == type) {
            log.info("[用户登录] 用户客户信息不存在");
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_200001, "账户或密码输入不正确");
        }
        if (userPwdDo == null && NumberDict.TWO == type) {
            log.info("[查询支付密码] 支付密码不存在");
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_200001, "支付密码输入不正确");
        }
        return userPwdDo;
    }

    /**
     * 更新登录密码错误次数
     *
     * @param userNo           用户号
     * @param loginPwdErrorNum 登录错误次数
     * @param userState        用户状态
     * @param pwdType          密码类型
     */
    @Override
    public void updateByUserNo(Long userNo, Integer loginPwdErrorNum, Integer userState, Integer pwdType) {
        Date date = null;
        if (loginPwdErrorNum == NumberDict.FIVE) {
            date = new Date();
            userState = NumberDict.TWO;
        }

        userPwdMapper.updateByUserNo(userNo, loginPwdErrorNum, date, userState, pwdType);

    }

    /**
     * 添加登录日志
     *
     * @param userLoginLogDo 登录日志
     */
    @Override
    public void addLoginLog(UserLoginLogDo userLoginLogDo) {
        loginLogMapper.insert(userLoginLogDo);
    }
}
