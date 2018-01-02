package com.baofu.international.global.account.core.manager.impl;

import com.baofu.international.global.account.core.common.constant.Constants;
import com.baofu.international.global.account.core.common.util.SecurityUtil;
import com.baofu.international.global.account.core.dal.mapper.UserPersonalMapper;
import com.baofu.international.global.account.core.dal.model.UserPersonalDo;
import com.baofu.international.global.account.core.manager.UserPersonalManager;
import com.system.commons.utils.ParamValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 个人信息服务
 * <p>
 * 1、更新个人用户信息
 * </p>
 *
 * @author : hetao  Date: 2017/11/06 ProjectName: globalaccount Version: 1.0
 */
@Component
public class UserPersonalManagerImpl implements UserPersonalManager {

    /**
     * 个人用户信息操作mapper
     */
    @Autowired
    private UserPersonalMapper userPersonalMapper;

    /**
     * 更新个人用户信息
     *
     * @param userPersonalDo 个人用户信息
     */
    @Override
    public void updateUserPersonal(UserPersonalDo userPersonalDo) {
        userPersonalMapper.updateUserPersonal(userPersonalDo);
    }

    /**
     * 根据userNo查询
     *
     * @param userNo 用户编号
     * @return 用户信息
     */
    @Override
    public UserPersonalDo selectInfoByUserNo(Long userNo) {
        return userPersonalMapper.selectInfoByUserNo(userNo);
    }

    /**
     * 根据userNo查询
     *
     * @param userInfoNo 申请编号
     * @return 用户信息
     */
    @Override
    public UserPersonalDo queryByUserInfoNo(Long userInfoNo) {
        UserPersonalDo userPersonalDo = userPersonalMapper.selectByUserInfoNo(userInfoNo);
        if (userPersonalDo != null && userPersonalDo.getIdNo() != null) {
            userPersonalDo.setIdNo(SecurityUtil.desDecrypt(userPersonalDo.getIdNo(), Constants.CARD_DES_KEY));
        }
        return userPersonalDo;
    }


    /**
     * 根据qualifiedNo查询
     *
     * @param qualifiedNo 用户编号
     * @return 用户信息
     */
    @Override
    public UserPersonalDo selectInfoByQualifiedNo(Long qualifiedNo) {
        return userPersonalMapper.selectInfoByQualifiedNo(qualifiedNo);
    }

    /**
     * 新增个人用户基本信息
     *
     * @param userPersonalDo 请求参数
     */
    @Override
    public void insertUserPersonalInfo(UserPersonalDo userPersonalDo) {
        ParamValidate.checkUpdate(userPersonalMapper.insert(userPersonalDo), "新增个人用户基本信息异常");
    }

    /**
     * 根据email查询
     *
     * @param email 邮箱
     * @return 用户信息
     */
    @Override
    public UserPersonalDo queryByEmail(String email) {
        return userPersonalMapper.queryByEmail(email);
    }
}
