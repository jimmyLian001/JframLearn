package com.baofu.international.global.account.core.biz.impl;

import com.baofu.international.global.account.core.biz.UserOrgBiz;
import com.baofu.international.global.account.core.biz.convert.AccQualifiedConvert;
import com.baofu.international.global.account.core.biz.convert.UserOrgConvert;
import com.baofu.international.global.account.core.biz.models.OrgInfoReqBo;
import com.baofu.international.global.account.core.common.constant.NumberDict;
import com.baofu.international.global.account.core.dal.mapper.AccQualifiedMapper;
import com.baofu.international.global.account.core.dal.model.UserInfoDo;
import com.baofu.international.global.account.core.manager.AuthApplyManager;
import com.baofu.international.global.account.core.manager.OrderIdManager;
import com.baofu.international.global.account.core.manager.UserInfoManager;
import com.baofu.international.global.account.core.manager.UserOrgManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 企业信息服务
 * <p>
 * 1、新增企业基本信息
 * 2、更新企业基本信息
 * </p>
 *
 * @author : hetao  Date: 2017/11/06 ProjectName: globalaccount Version: 1.0
 */
@Service
@Slf4j
public class UserOrgBizImpl implements UserOrgBiz {

    /**
     * 企业认证信息操作manager
     */
    @Autowired
    private UserOrgManager userOrgManager;

    /**
     * 用户信息操作服务
     */
    @Autowired
    private UserInfoManager userInfoManager;

    /**
     * 订单号服务
     */
    @Autowired
    private OrderIdManager orderIdManager;

    /**
     * 资质关系信息操作服务
     */
    @Autowired
    private AccQualifiedMapper accQualifiedMapper;

    /**
     * 认证申请服务
     */
    @Autowired
    private AuthApplyManager authApplyManager;

    /**
     * 新增企业用户信息
     *
     * @param orgInfoReqBo 企业用户信息
     */
    @Override
    public void addUserOrg(OrgInfoReqBo orgInfoReqBo) {
        log.info("新增企业信息：{}", orgInfoReqBo);

        orgInfoReqBo.setUserInfoNo(orderIdManager.orderIdCreate());
        // 新增资质
        if (orgInfoReqBo.getRequestType() == NumberDict.THREE || orgInfoReqBo.getRequestType() == NumberDict.FOUR) {
            // 新增企业信息基本信息
            userOrgManager.insert(UserOrgConvert.addUserOrgConvert(orgInfoReqBo));
            log.info("新增企业信息基本信息成功");
        } else {
            // 实名认证
            UserInfoDo userInfoDo = userInfoManager.selectUserInfoByUserNo(orgInfoReqBo.getUserNo());
            orgInfoReqBo.setUserInfoNo(userInfoDo.getUserInfoNo());

            // 新增企业信息基本信息
            userOrgManager.updateUserOrg(UserOrgConvert.addUserOrgConvert(orgInfoReqBo));
            log.info("新增企业信息基本信息成功");
        }

        // 更新认证申请信息表
        authApplyManager.updateAuthApply(orgInfoReqBo.getAuthApplyNo(), orgInfoReqBo.getUserInfoNo());
        log.info("更新认证申请信息成功");

        // 新增资质关系信息
        accQualifiedMapper.insert(AccQualifiedConvert.addAccQualifiedConvert(orgInfoReqBo.getUserInfoNo(), orgInfoReqBo.getUserNo(),
                orgInfoReqBo.getLoginNo(), orderIdManager.orderIdCreate()));
        log.info("新增资质关系信息成功");
    }

    /**
     * 更新企业用户信息
     *
     * @param orgInfoReqBo 企业用户信息
     */
    @Override
    public void updateUserOrg(OrgInfoReqBo orgInfoReqBo) {
        log.info("更新企业信息：{}", orgInfoReqBo);

        // 新增企业信息基本信息
        userOrgManager.updateUserOrg(UserOrgConvert.updateUserOrgConvert(orgInfoReqBo));
        log.info("更新企业信息成功");
    }
}
