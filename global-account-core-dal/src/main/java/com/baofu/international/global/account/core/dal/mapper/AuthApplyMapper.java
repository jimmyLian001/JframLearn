package com.baofu.international.global.account.core.dal.mapper;

import com.baofu.international.global.account.core.dal.model.AuthApplyDo;
import org.apache.ibatis.annotations.Param;

/**
 * 实名申请
 *
 * @author wanght 2017.11.29 account-core version: 1.0.0
 */
public interface AuthApplyMapper {

    /**
     * 新增实名申请
     *
     * @param tAuthApplyDo 申请信息
     */
    int insert(AuthApplyDo tAuthApplyDo);

    /**
     * 更新认证申请记录
     *
     * @param authApplyNo 认证申请编号
     * @param userInfoNo  资质信息编号
     */
    int updateAuthApply(@Param("authApplyNo") Long authApplyNo, @Param("userInfoNo") Long userInfoNo);
}