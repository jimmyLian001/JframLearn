package com.baofu.international.global.account.core.dal.mapper;

import com.baofu.international.global.account.core.dal.model.UserOrgDo;
import com.baofu.international.global.account.core.dal.model.user.EditUserReqDo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 */
public interface UserOrgMapper {

    /**
     * 根据userNo查询
     *
     * @param userInfoNo 用户编号
     * @return 企业信息
     */
    UserOrgDo selectByUserInfoNo(Long userInfoNo);

    /**
     * 新增企业信息
     *
     * @param record 企业信息
     * @return 影响行数
     */
    int insert(UserOrgDo record);

    /**
     * 更新企业用户信息表 手机号
     *
     * @param userOrgDo userOrgDo
     */
    int updateTelByUserNo(UserOrgDo userOrgDo);

    /**
     * 根据用户号更新企业信息
     *
     * @param editUserReqDo 企业DO
     * @return 返回更新记录数
     */
    int updateAuthStatus(EditUserReqDo editUserReqDo);

    /**
     * 查询企业用户记录笔数
     *
     * @param userOrgDo 企业用户DO
     * @return 企业用户查询笔数
     */
    int countAllByCondition(UserOrgDo userOrgDo);

    /**
     * 查询企业用户记录
     *
     * @param userOrgDo 企业用户DO
     * @return 企业用户list
     */
    List<UserOrgDo> selectAllByCondition(UserOrgDo userOrgDo);

    /**
     * 更新企业用户信息
     *
     * @param userOrgDo 企业用户信息
     */
    void updateUserOrg(UserOrgDo userOrgDo);

    /**
     * 根据userNo查询
     *
     * @param userNo 用户编号
     * @return 企业信息
     */
    UserOrgDo selectInfoByUserNo(Long userNo);

    /**
     * 根据qualifiedNo查询
     *
     * @param qualifiedNo 资质编号
     * @return 企业信息
     */
    UserOrgDo selectInfoByQualifiedNo(Long qualifiedNo);


    /**
     * 根据userNo查询
     *
     * @param userNo 用户编号
     * @return 企业信息
     */
    List<UserOrgDo> selectInfoByUserNoList(Long userNo);

    /**
     * 根据email查询
     *
     * @param email 邮箱
     * @return 企业信息
     */
    UserOrgDo queryByEmail(@Param("email") String email);
}