package com.baofu.international.global.account.core.manager.impl;

import com.baofu.international.global.account.core.common.constant.Constants;
import com.baofu.international.global.account.core.common.util.SecurityUtil;
import com.baofu.international.global.account.core.dal.mapper.UserOrgMapper;
import com.baofu.international.global.account.core.dal.model.UserOrgDo;
import com.baofu.international.global.account.core.dal.model.user.EditUserReqDo;
import com.baofu.international.global.account.core.manager.UserOrgManager;
import com.system.commons.utils.ParamValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 企业用户信息 ManagerImpl
 * <p>
 * 企业用户信息 Manager
 * </p>
 * Created by 陶伟超 on 2017/11/4
 */
@Repository
public class UserOrgManagerImpl implements UserOrgManager {

    /**
     * 企业用户查询服务
     */
    @Autowired
    private UserOrgMapper userOrgMapper;

    /**
     * 根据userNo查询
     *
     * @param userInfoNo 用户编号
     * @return 企业信息
     */
    @Override
    public UserOrgDo queryByUserInfoNo(Long userInfoNo) {

        UserOrgDo userOrgDo = userOrgMapper.selectByUserInfoNo(userInfoNo);
        if (userOrgDo != null && userOrgDo.getLegalIdNo() != null) {
            userOrgDo.setLegalIdNo(SecurityUtil.desDecrypt(userOrgDo.getLegalIdNo(), Constants.CARD_DES_KEY));
        }
        return userOrgDo;
    }

    /**
     * 更新企业用户信息
     *
     * @param userOrgDo 企业信息
     */
    @Override
    public void updateUserOrg(UserOrgDo userOrgDo) {
        userOrgMapper.updateUserOrg(userOrgDo);
    }

    /**
     * 根据条件查询机构信息记录笔数
     *
     * @param tOrgInfoDo 机构DO
     * @return 记录笔数
     */
    @Override
    public int countAllByCondition(UserOrgDo tOrgInfoDo) {
        return userOrgMapper.countAllByCondition(tOrgInfoDo);
    }

    /**
     * 根据条件查询机构列表
     *
     * @param tOrgInfoDo 机构对象
     * @return 机构列表
     */
    @Override
    public List<UserOrgDo> selectAllByCondition(UserOrgDo tOrgInfoDo) {
        return userOrgMapper.selectAllByCondition(tOrgInfoDo);
    }

    /**
     * 根据用户号更新企业信息
     *
     * @param editUserReqDo 企业DO
     * @return 返回更新记录数
     */
    @Override
    public Integer modifyAuthStatus(EditUserReqDo editUserReqDo) {
        return userOrgMapper.updateAuthStatus(editUserReqDo);
    }

    /**
     * 根据用户号查询企业用户Do
     *
     * @param userNo 会员号
     * @return 企业用户Do
     */
    @Override
    public UserOrgDo selectInfoByUserNo(Long userNo) {
        return userOrgMapper.selectInfoByUserNo(userNo);
    }

    /**
     * 根据qualifiedNo查询
     *
     * @param qualifiedNo 用户编号
     * @return 企业用户Do
     */
    @Override
    public UserOrgDo selectInfoByQualifiedNo(Long qualifiedNo) {
        return userOrgMapper.selectInfoByQualifiedNo(qualifiedNo);
    }


    /**
     * 新增企业信息
     *
     * @param userOrgDo 企业信息
     */
    @Override
    public void insert(UserOrgDo userOrgDo) {
        ParamValidate.checkUpdate(userOrgMapper.insert(userOrgDo), "新增企业用户基本信息异常");
    }

    /**
     * 根据email查询
     *
     * @param email 邮箱
     * @return 企业信息
     */
    @Override
    public UserOrgDo queryByEmail(String email) {
        return userOrgMapper.queryByEmail(email);
    }
}
