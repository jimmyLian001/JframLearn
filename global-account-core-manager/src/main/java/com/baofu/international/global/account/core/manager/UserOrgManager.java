package com.baofu.international.global.account.core.manager;

import com.baofu.international.global.account.core.dal.model.UserOrgDo;
import com.baofu.international.global.account.core.dal.model.user.EditUserReqDo;

import java.util.List;

/**
 * 类的描述信息
 * <p>
 * 1、此处填写方法描述
 * 2、新增认证企业信息
 * </p>
 * User: 陶伟超 Date: 2017/11/4 ProjectName: account-core Version: 1.0.0
 */
public interface UserOrgManager {

    /**
     * 根据userNo查询
     *
     * @param userInfoNo 用户编号
     * @return 企业信息
     */
    UserOrgDo queryByUserInfoNo(Long userInfoNo);

    /**
     * 新增认证企业信息
     *
     * @param userOrgDo 企业信息
     */
    void updateUserOrg(UserOrgDo userOrgDo);

    /**
     * 根据条件查询机构信息记录笔数
     *
     * @param tOrgInfoDo 机构DO
     * @return 记录笔数
     */
    int countAllByCondition(UserOrgDo tOrgInfoDo);

    /**
     * 根据条件查询机构列表
     *
     * @param tOrgInfoDo 机构对象
     * @return 机构列表
     */
    List<UserOrgDo> selectAllByCondition(UserOrgDo tOrgInfoDo);

    /**
     * 更新企业运营认证状态
     *
     * @param editUserReqDo 更新机构请求对象
     * @return 更新结果
     */
    Integer modifyAuthStatus(EditUserReqDo editUserReqDo);

    /**
     * 根据用户号查询企业用户Do
     *
     * @param userNo 会员号
     * @return 企业用户Do
     */
    UserOrgDo selectInfoByUserNo(Long userNo);

    /**
     * 根据qualifiedNo查询
     *
     * @param qualifiedNo 用户编号
     * @return 企业用户Do
     */
    UserOrgDo selectInfoByQualifiedNo(Long qualifiedNo);

    /**
     * 新增企业信息
     *
     * @param userOrgDo 企业信息
     */
    void insert(UserOrgDo userOrgDo);

    /**
     * 根据email查询
     *
     * @param email 邮箱
     * @return 企业信息
     */
    UserOrgDo queryByEmail(String email);
}
