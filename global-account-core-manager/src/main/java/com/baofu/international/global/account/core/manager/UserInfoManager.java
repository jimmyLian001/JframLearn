package com.baofu.international.global.account.core.manager;

import com.baofu.international.global.account.core.dal.model.UserInfoDo;
import com.baofu.international.global.account.core.dal.model.UserPersonalDo;
import com.baofu.international.global.account.core.dal.model.user.EditUserReqDo;

import java.util.List;

/**
 * 个人用户基本信息接口
 * <p>
 * 1、提供用户查询服务供控台使用
 * </p>
 * User: 陶伟超 Date: 2017/11/9 ProjectName: account-core Version: 1.0.0
 */
public interface UserInfoManager {

    /**
     * 根据userNo查询用户信息
     *
     * @param userNo 用户编号
     * @return 用户信息
     */
    UserInfoDo selectUserInfoByUserNo(Long userNo);

    /**
     * 根据用户选择的信息筛选个人用户信息
     *
     * @param userPersonalDo 个人用户对象
     * @return 用户信息
     */
    List<UserPersonalDo> selectAllByCondition(UserPersonalDo userPersonalDo);

    /**
     * 根据用户选择的信息筛选个人用户信息记录数
     *
     * @param userPersonalDo 个人用户对象
     * @return 返回笔数
     */
    int countAllByCondition(UserPersonalDo userPersonalDo);

    /**
     * 修改认证状态
     *
     * @param editUserReqDo 更新请求对象
     * @return >0 更新成功
     */
    int updateAuthStatus(EditUserReqDo editUserReqDo);
}
