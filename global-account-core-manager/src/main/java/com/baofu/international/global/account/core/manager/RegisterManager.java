package com.baofu.international.global.account.core.manager;

import com.baofu.international.global.account.core.dal.model.*;

import java.util.List;

/**
 * description:注册Manager
 * <p/>
 *
 * @author : liy on 2017/12/5
 * @version : 1.0.0
 */
public interface RegisterManager {

    /**
     * 根据loginNo判断登录账户是否存在
     *
     * @param loginNo 登录账户号(手机号或邮箱)
     * @return 结果集
     */
    int selectUseLoginInfoNum(String loginNo);

    /**
     * 查询系统安全问题
     *
     * @return 结果集
     */
    List<SysSecrueqaInfoDo> selectSysSecrueqaInfoList();

    /**
     * 新增用户登录信息
     *
     * @param userLoginInfoDo 请求参数
     */
    void insertUserLoginInfo(UserLoginInfoDo userLoginInfoDo);

    /**
     * 新增用户基本信息
     *
     * @param userInfoDo 请求参数
     */
    void insertUserInfo(UserInfoDo userInfoDo);

    /**
     * 新增用户登录密码
     *
     * @param userPwdDo 请求参数
     */
    void insertUserPwdInfo(UserPwdDo userPwdDo);

    /**
     * 根据问题编号查询系统安全问题信息
     *
     * @param questionNo 问题编号
     * @return 结果集
     */
    SysSecrueqaInfoDo selectSysSecrueqaInfoByQuestionNo(long questionNo);

    /**
     * 新增用户安全问题
     *
     * @param userSecrueqaInfoDo 请求参数
     */
    void insertUserSecrueqaInfo(UserSecrueqaInfoDo userSecrueqaInfoDo);

    /**
     * 修改企业用户信息
     *
     * @param userOrgDo 请求参数
     * @return 结果集
     */
    int updateUserOrgPhone(UserOrgDo userOrgDo);
}
