package com.baofu.international.global.account.core.manager.impl;

import com.baofu.international.global.account.core.dal.mapper.AuthApplyMapper;
import com.baofu.international.global.account.core.dal.model.AuthApplyDo;
import com.baofu.international.global.account.core.manager.AuthApplyManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 认证申请操作接口
 * <p>
 * 1,新增认证申请记录
 * </p>
 *
 * @author : hetao  Date: 2017/11/06 ProjectName: globalaccount Version: 1.0
 */
@Component
public class AuthApplyManagerImpl implements AuthApplyManager {

    /**
     * 认证申请操作mapper
     */
    @Autowired
    private AuthApplyMapper tAuthApplyMapper;

    /**
     * 新增认证申请记录
     *
     * @param tAuthApplyDo 认证申请信息
     */
    @Override
    public void addAuthApply(AuthApplyDo tAuthApplyDo) {
        tAuthApplyMapper.insert(tAuthApplyDo);
    }

    /**
     * 更新认证申请记录
     *
     * @param authApplyNo 认证申请编号
     * @param userInfoNo  资质信息编号
     */
    @Override
    public void updateAuthApply(Long authApplyNo, Long userInfoNo) {
        tAuthApplyMapper.updateAuthApply(authApplyNo, userInfoNo);
    }

}
