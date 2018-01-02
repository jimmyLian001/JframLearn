package com.baofu.international.global.account.core.dal.mapper;

import com.baofu.international.global.account.core.dal.model.AccQualifiedDo;

import java.util.List;

public interface AccQualifiedMapper {

    /**
     * 新增资质关系信息
     *
     * @param accQualifiedDo 资质关系信息
     * @return 新增数量
     */
    int insert(AccQualifiedDo accQualifiedDo);

    /**
     * 根据用户号查询用户
     *
     * @param userNo 　用户号申请编号
     * @return TUserInfoDo
     */
    List<AccQualifiedDo> selectByUserNo(Long userNo);

    /**
     * 根据用户号查询用户
     *
     * @param qualifiedNo 　资质编号
     * @return AccQualifiedDo
     */
    AccQualifiedDo selectByQualified(Long qualifiedNo);

    /**
     * 根据申请编号查询
     *
     * @param userInfoNo 用户编号
     * @return AccQualifiedDo
     */
    AccQualifiedDo selectByApplyNo(Long userInfoNo);

}