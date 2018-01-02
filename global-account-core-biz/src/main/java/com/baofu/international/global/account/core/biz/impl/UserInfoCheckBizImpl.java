package com.baofu.international.global.account.core.biz.impl;

import com.baofu.international.global.account.core.biz.UserInfoCheckBiz;
import com.baofu.international.global.account.core.common.enums.ErrorCodeEnum;
import com.baofu.international.global.account.core.common.enums.RealNameStatusEnum;
import com.baofu.international.global.account.core.common.enums.UserTypeEnum;
import com.baofu.international.global.account.core.dal.model.UserOrgDo;
import com.baofu.international.global.account.core.dal.model.UserPersonalDo;
import com.baofu.international.global.account.core.manager.UserOrgManager;
import com.baofu.international.global.account.core.manager.UserPersonalManager;
import com.system.commons.exception.BizServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 资质校验接口
 * <p>
 * 1、新增资质时邮箱校验
 * 2、更新资质时邮箱校验
 * </p>
 *
 * @author : hetao  Date: 2017/11/06 ProjectName: globalaccount Version: 1.0
 */
@Service
@Slf4j
public class UserInfoCheckBizImpl implements UserInfoCheckBiz {

    /**
     * 个人认证信息操作manager
     */
    @Autowired
    private UserPersonalManager userPersonalManager;


    /**
     * 企业认证信息操作manager
     */
    @Autowired
    private UserOrgManager userOrgManager;

    /**
     * 新增资质时邮箱校验
     *
     * @param email 邮箱
     */
    @Override
    public void addCheckEmail(String email) {
        UserOrgDo userOrgDo = userOrgManager.queryByEmail(email);
        UserPersonalDo userPersonalDo = userPersonalManager.queryByEmail(email);

        if (userOrgDo != null) {
            log.error("新增资质时，该邮箱已被使用：{}", email);
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190406);
        }
        if (userPersonalDo != null) {
            log.error("新增资质时，该邮箱已被个人用户使用:{}", email);
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190406);
        }
    }

    /**
     * 更新资质时邮箱校验
     *
     * @param email 邮箱
     */
    @Override
    public void updateCheckEmail(String email, Long userInfoNo) {
        UserOrgDo userOrgDo = userOrgManager.queryByEmail(email);
        UserPersonalDo userPersonalDo = userPersonalManager.queryByEmail(email);

        if (userOrgDo != null && !userOrgDo.getUserInfoNo().equals(userInfoNo)) {
            log.error("更新资质时，该邮箱已被企业用户使用：{}", email);
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190406);
        }
        if (userPersonalDo != null && !userPersonalDo.getUserInfoNo().equals(userInfoNo)) {
            log.error("更新资质时，该邮箱已被个人用户使用:{}", email);
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190406);
        }
    }

    /**
     * 校验用户状态是否为未认证和认证失败
     *
     * @param userInfoNo 用户信息编号
     * @param userType   用户类型
     */
    @Override
    public void updateCheckRealNameStatus(Long userInfoNo, Integer userType) {

        Integer realNameStatus;
        //用户状态校验
        if (userType == UserTypeEnum.ORG.getType()) {
            realNameStatus = userOrgManager.queryByUserInfoNo(userInfoNo).getRealnameStatus();
        } else {
            realNameStatus = userPersonalManager.queryByUserInfoNo(userInfoNo).getRealnameStatus();
        }
        if (realNameStatus != RealNameStatusEnum.FAIL.getState() && realNameStatus != RealNameStatusEnum.NOT.getState()) {
            log.error("用户信息编号：{}，认证状态：{}", userInfoNo, realNameStatus);
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_290016);
        }
    }
}
