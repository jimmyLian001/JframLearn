package com.baofu.international.global.account.core.manager.impl;

import com.baofu.international.global.account.core.dal.mapper.*;
import com.baofu.international.global.account.core.dal.model.*;
import com.baofu.international.global.account.core.manager.UserTelInfoManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户手机号数据操作
 * <p/>
 *
 * @author : lian zd
 * @date :2017/11/4 ProjectName: account-core Version:1.0
 */
@Service
public class UserTelInfoManagerImpl implements UserTelInfoManager {

    /**
     * 用户登录信息表数据操作
     */
    @Autowired
    private UserLoginInfoMapper userLoginInfoMapper;
    /**
     * 用户安全问题表数据操作
     */
    @Autowired
    private UserSecrueqaInfoMapper userSecrueqaInfoMapper;
    /**
     * 用户登录日志记录表数据操作
     */
    @Autowired
    private UserLoginLogMapper userLoginLogMapper;

    /**
     * 更新企业用户信息
     */
    @Autowired
    private UserOrgMapper userOrgMapper;

    /**
     * 更新企业用户信息
     */
    @Autowired
    private UserPersonalMapper userPersonalMapper;

    /**
     * 更新状态
     *
     * @param userLoginInfoDo 更新对象
     */
    @Override
    public void updateUserLoginInfo(UserLoginInfoDo userLoginInfoDo) {
        userLoginInfoMapper.updateByUserNo(userLoginInfoDo);
    }

    /**
     * 根据用户名查询用户密保问题
     *
     * @param userNo 用户号
     * @return userSecrueqaInfoDo
     */
    @Override
    public UserSecrueqaInfoDo queryByUserNo(Long userNo) {
        return userSecrueqaInfoMapper.selectByUserNo(userNo);
    }

    /**
     * 更新用户登录日志记录表
     *
     * @param userLoginLogDo 更新对象
     */
    @Override
    public void updateUserLoginLog(UserLoginLogDo userLoginLogDo) {
        userLoginLogMapper.updateByUserNo(userLoginLogDo);
    }

    /**
     * 根据用户名查询用户登录信息
     *
     * @param userNo 用户号
     * @return userLoginInfoDo
     */
    @Override
    public UserLoginInfoDo queryUserInfoByUserNo(Long userNo) {
        return userLoginInfoMapper.queryUserInfoByUserNo(userNo);
    }

    /**
     * 更新企业用户信息表 手机号
     *
     * @param userOrgDo userOrgDo
     * @return
     */
    @Override
    public void updateOrgTelByUserNo(UserOrgDo userOrgDo) {
        userOrgMapper.updateTelByUserNo(userOrgDo);
    }

    /**
     * 更新个人用户信息表 手机号
     *
     * @param userPersonalDo Personal
     * @return
     */
    @Override
    public void updatePersonalTelByUserNo(UserPersonalDo userPersonalDo) {
        userPersonalMapper.updateTelByUserNo(userPersonalDo);
    }

    /**
     * 根据用户名查询用户登录信息
     *
     * @param userNo 用户号
     * @return userLoginInfoDo
     */
    @Override
    public UserLoginInfoDo queryUserInfoByUserNo(Long userNo, Integer loginType, String loginNo) {
        UserLoginInfoDo userLoginInfoDo = new UserLoginInfoDo();
        userLoginInfoDo.setUserNo(userNo);
        userLoginInfoDo.setLoginType(loginType);
        userLoginInfoDo.setLoginNo(loginNo);
        return userLoginInfoMapper.queryUserInfo(userLoginInfoDo);
    }

    /**
     * 新增登录信息
     *
     * @param userLoginInfoDo tUserLoginLogDo
     */
    @Override
    public void insertLoginInfo(UserLoginInfoDo userLoginInfoDo) {
        userLoginInfoMapper.insertInfo(userLoginInfoDo);
    }
}
