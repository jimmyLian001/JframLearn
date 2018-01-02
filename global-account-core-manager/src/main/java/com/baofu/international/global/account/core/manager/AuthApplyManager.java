package com.baofu.international.global.account.core.manager;

import com.baofu.international.global.account.core.dal.model.AuthApplyDo;

/**
 * 认证申请操作接口
 * <p>
 * 1,新增认证申请记录
 * </p>
 *
 * @author : hetao  Date: 2017/11/06 ProjectName: globalaccount Version: 1.0
 */
public interface AuthApplyManager {

    /**
     * 新增认证申请记录
     *
     * @param tAuthApplyDo 卡bin
     */
    void addAuthApply(AuthApplyDo tAuthApplyDo);

    /**
     * 更新认证申请记录
     *
     * @param authApplyNo 认证申请编号
     * @param userInfoNo  资质信息编号
     */
    void updateAuthApply(Long authApplyNo, Long userInfoNo);
}
