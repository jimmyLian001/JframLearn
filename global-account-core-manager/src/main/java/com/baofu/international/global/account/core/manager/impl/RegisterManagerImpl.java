package com.baofu.international.global.account.core.manager.impl;

import com.baofu.international.global.account.core.dal.mapper.*;
import com.baofu.international.global.account.core.dal.model.*;
import com.baofu.international.global.account.core.manager.RegisterManager;
import com.system.commons.utils.ParamValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * description:注册 ManagerImpl
 * <p/>
 *
 * @author : liy on 2017/11/6
 * @version : 1.0.0
 */
@Repository
public class RegisterManagerImpl implements RegisterManager {

    /**
     * 用户登录信息
     */
    @Autowired
    private UserLoginInfoMapper tUserLoginInfoMapper;

    /**
     * 用户基本信息
     */
    @Autowired
    private UserInfoMapper tUserInfoMapper;

    /**
     * 用户密码
     */
    @Autowired
    private UserPwdMapper tUserPwdMapper;

    /**
     * 用户安全问题
     */
    @Autowired
    private UserSecrueqaInfoMapper tUserSecrueqaInfoMapper;

    /**
     * 系统安全问题
     */
    @Autowired
    private SysSecrueqaInfoMapper tSysSecrueqaInfoMapper;

    /**
     * 新增用户登录信息异常
     */
    private static final String INSERT_USER_LOGIN_INFO_ERROR = "新增用户登录信息异常";

    /**
     * 新增用户基本信息异常
     */
    private static final String INSERT_USER_INFO_ERROR = "新增用户基本信息异常";

    /**
     * 新增用户登录密码异常
     */
    private static final String INSERT_USER_PW_INFO_ERROR = "新增用户登录密码异常";

    /**
     * 新增用户安全问题异常
     */
    private static final String INSERT_USER_SECRUEQA_INFO_ERROR = "新增用户安全问题异常";

    /**
     * 根据loginNo判断登录账户是否存在
     *
     * @param loginNo 登录账户号(手机号或邮箱)
     */
    @Override
    public int selectUseLoginInfoNum(String loginNo) {

        return tUserLoginInfoMapper.selectUseLoginInfoNum(loginNo);
    }

    /**
     * 查询系统安全问题
     *
     * @return 结果集
     */
    @Override
    public List<SysSecrueqaInfoDo> selectSysSecrueqaInfoList() {

        return tSysSecrueqaInfoMapper.selectSysSecrueqaInfoList();
    }

    /**
     * 新增用户登录信息
     *
     * @param userLoginInfoDo 请求参数
     */
    @Override
    public void insertUserLoginInfo(UserLoginInfoDo userLoginInfoDo) {

        ParamValidate.checkUpdate(tUserLoginInfoMapper.insertInfo(userLoginInfoDo), INSERT_USER_LOGIN_INFO_ERROR);
    }

    /**
     * 新增用户基本信息
     *
     * @param userInfoDo 请求参数
     */
    @Override
    public void insertUserInfo(UserInfoDo userInfoDo) {

        ParamValidate.checkUpdate(tUserInfoMapper.insert(userInfoDo), INSERT_USER_INFO_ERROR);
    }

    /**
     * 新增用户登录密码
     *
     * @param userPwdDo 请求参数
     */
    @Override
    public void insertUserPwdInfo(UserPwdDo userPwdDo) {

        ParamValidate.checkUpdate(tUserPwdMapper.insert(userPwdDo), INSERT_USER_PW_INFO_ERROR);
    }

    /**
     * 根据问题编号查询系统安全问题信息
     *
     * @param questionNo 问题编号
     * @return 结果集
     */
    @Override
    public SysSecrueqaInfoDo selectSysSecrueqaInfoByQuestionNo(long questionNo) {

        return tSysSecrueqaInfoMapper.selectSysSecrueqaInfoByQuestionNo(questionNo);
    }

    /**
     * 新增用户安全问题
     *
     * @param userSecrueqaInfoDo 请求参数
     */
    @Override
    public void insertUserSecrueqaInfo(UserSecrueqaInfoDo userSecrueqaInfoDo) {

        ParamValidate.checkUpdate(tUserSecrueqaInfoMapper.insertInfo(userSecrueqaInfoDo), INSERT_USER_SECRUEQA_INFO_ERROR);
    }

    /**
     * 修改企业用户信息
     *
     * @param userOrgDo 请求参数
     * @return 结果集
     */
    @Override
    public int updateUserOrgPhone(UserOrgDo userOrgDo) {
        return 0;
    }
}
