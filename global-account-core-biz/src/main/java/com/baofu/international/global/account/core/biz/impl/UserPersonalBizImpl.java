package com.baofu.international.global.account.core.biz.impl;

import com.baofu.international.global.account.core.biz.UserPersonalBiz;
import com.baofu.international.global.account.core.biz.convert.AccQualifiedConvert;
import com.baofu.international.global.account.core.biz.convert.UserBankCardServiceConvert;
import com.baofu.international.global.account.core.biz.convert.UserPersonalConvert;
import com.baofu.international.global.account.core.biz.models.PersonInfoReqBo;
import com.baofu.international.global.account.core.dal.mapper.AccQualifiedMapper;
import com.baofu.international.global.account.core.dal.model.BankCardBinInfoDo;
import com.baofu.international.global.account.core.dal.model.UserBankCardInfoDo;
import com.baofu.international.global.account.core.dal.model.UserInfoDo;
import com.baofu.international.global.account.core.manager.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 个人信息服务
 * <p>
 * 1、添加认证个人信息
 * </p>
 *
 * @author : hetao  Date: 2017/11/06 ProjectName: globalaccount Version: 1.0
 */
@Service
@Slf4j
public class UserPersonalBizImpl implements UserPersonalBiz {

    /**
     * 个人认证信息操作manager
     */
    @Autowired
    private UserPersonalManager userPersonalManager;

    /**
     * 用户银行卡服务
     */
    @Autowired
    private UserBankCardManager userBankCardManager;

    /**
     * 卡bin相关服务
     */
    @Autowired
    private CardBinManager cardBinManager;

    /**
     * 记录编号生成服务
     */
    @Autowired
    private OrderIdManager orderIdManager;

    /**
     * 用户信息操作服务
     */
    @Autowired
    private UserInfoManager userInfoManager;

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
     * 添加认证个人信息
     *
     * @param personInfoReqBo 个人认证信息
     */
    @Override
    @Transactional
    public void addUserPersonal(PersonInfoReqBo personInfoReqBo) {
        log.info("添加实名信息：{}", personInfoReqBo);
        // 卡信息表主键
        Long recordId = orderIdManager.orderIdCreate();

        UserInfoDo userInfoDo = userInfoManager.selectUserInfoByUserNo(personInfoReqBo.getUserNo());
        personInfoReqBo.setUserInfoNo(userInfoDo.getUserInfoNo());

        // 新增个人信息基本信息
        userPersonalManager.updateUserPersonal(UserPersonalConvert.addUserPersonalConvert(personInfoReqBo, recordId));
        log.info("实名新增个人信息基本信息成功");

        // 更新认证申请信息表
        authApplyManager.updateAuthApply(personInfoReqBo.getAuthApplyNo(), personInfoReqBo.getUserInfoNo());
        log.info("更新认证申请信息成功");

        // 新增资质关系信息
        accQualifiedMapper.insert(AccQualifiedConvert.addAccQualifiedConvert(personInfoReqBo.getUserInfoNo(), personInfoReqBo.getUserNo(),
                personInfoReqBo.getLoginNo(), orderIdManager.orderIdCreate()));
        log.info("新增资质关系信息成功");

        // 新增卡信息表
        //卡bin查询
        BankCardBinInfoDo bankCardBinInfoDo = cardBinManager.queryCardBin(personInfoReqBo.getBankCardNo().substring(0, 6));
        UserBankCardInfoDo userBankCardInfoDo = UserBankCardServiceConvert.toTUserBankCardInfDo(personInfoReqBo, recordId);
        userBankCardInfoDo.setBankCode(bankCardBinInfoDo.getBankCode());
        userBankCardInfoDo.setBankName(bankCardBinInfoDo.getBankName());
        userBankCardInfoDo.setCardType(Integer.parseInt(bankCardBinInfoDo.getCardType()));

        log.info("新增用户绑卡信息：{}", userBankCardInfoDo);
        userBankCardManager.addUserBankCard(userBankCardInfoDo);
        log.info("新增用户绑卡信息成功");
    }

    /**
     * 添加认证个人信息
     *
     * @param personInfoReqBo 个人认证信息
     */
    @Override
    public void updateUserPersonal(PersonInfoReqBo personInfoReqBo) {
        // 新增个人信息基本信息
        userPersonalManager.updateUserPersonal(UserPersonalConvert.updateUserPersonalConvert(personInfoReqBo));
        log.info("更新个人信息基本信息成功");
    }
}
