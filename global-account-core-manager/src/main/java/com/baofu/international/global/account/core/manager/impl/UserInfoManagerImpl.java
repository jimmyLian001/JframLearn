package com.baofu.international.global.account.core.manager.impl;

import com.baofu.international.global.account.core.dal.mapper.UserInfoMapper;
import com.baofu.international.global.account.core.dal.mapper.UserPersonalMapper;
import com.baofu.international.global.account.core.dal.model.UserInfoDo;
import com.baofu.international.global.account.core.dal.model.UserPersonalDo;
import com.baofu.international.global.account.core.dal.model.user.EditUserReqDo;
import com.baofu.international.global.account.core.manager.UserInfoManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 个人用户 ManagerImpl
 * <p>
 * 个人用户信息提取 Manager
 * </p>
 * Created by 陶伟超 on 2017/11/4
 */
@Repository
public class UserInfoManagerImpl implements UserInfoManager {

    /**
     * 个人用户查询服务
     */
    @Autowired
    private UserPersonalMapper mapper;

    /**
     * 用户信息
     */
    @Autowired
    private UserInfoMapper userInfoMapper;

    /**
     * 根据userNo查询
     *
     * @param userNo 用户编号
     * @return 用户信息
     */
    @Override
    public UserInfoDo selectUserInfoByUserNo(Long userNo) {
        return userInfoMapper.selectByUserNo(userNo);
    }

    /**
     * 根据用户选择的信息筛选个人用户信息
     *
     * @param userPersonalDo 个人用户对象
     * @return 用户信息
     */
    @Override
    public List<UserPersonalDo> selectAllByCondition(UserPersonalDo userPersonalDo) {
        return mapper.selectAllByCondition(userPersonalDo);
    }

    /**
     * 根据用户选择的信息筛选个人用户信息记录数
     *
     * @param userPersonalDo 个人用户对象
     * @return 返回笔数
     */
    @Override
    public int countAllByCondition(UserPersonalDo userPersonalDo) {
        return mapper.countAllByCondition(userPersonalDo);
    }

    /**
     * 修改认证状态
     *
     * @param editUserReqDo 更新请求对象
     * @return >0 更新成功
     */
    @Override
    public int updateAuthStatus(EditUserReqDo editUserReqDo) {
        return mapper.updateAuthStatus(editUserReqDo);
    }
}
