package com.baofu.international.global.account.core.biz.impl;

import com.baofu.international.global.account.core.biz.AccountAuthBiz;
import com.baofu.international.global.account.core.biz.CardBinBiz;
import com.baofu.international.global.account.core.biz.convert.AuthConvert;
import com.baofu.international.global.account.core.biz.external.CompanyAuthBizImpl;
import com.baofu.international.global.account.core.biz.external.UserAuthBizImpl;
import com.baofu.international.global.account.core.biz.models.OrgAuthReqBo;
import com.baofu.international.global.account.core.biz.models.PersonAuthReqBo;
import com.baofu.international.global.account.core.common.enums.*;
import com.baofu.international.global.account.core.manager.AuthApplyManager;
import com.baofu.international.global.account.core.manager.AuthPersonManager;
import com.baofu.international.global.account.core.manager.OrderIdManager;
import com.system.commons.exception.BizServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 认证操作接口
 * <p>
 * 1、个人实名认证
 * 2、企业实名认证
 * </p>
 *
 * @author : hetao  Date: 2017/11/06 ProjectName: globalaccount Version: 1.0
 */
@Service
@Slf4j
public class AccountAuthBizImpl implements AccountAuthBiz {

    /**
     * 外部认证服务
     */
    @Autowired
    private UserAuthBizImpl userAuthBizImpl;

    /**
     * 企业外部认证服务
     */
    @Autowired
    private CompanyAuthBizImpl companyAuthBiz;

    /**
     * 个人认证服务
     */
    @Autowired
    private AuthPersonManager authPersonManager;

    /**
     * 认证申请服务
     */
    @Autowired
    private AuthApplyManager authApplyManager;

    /**
     * 记录编号生成服务
     */
    @Autowired
    private OrderIdManager orderIdManager;

    /**
     * 卡bin服务
     */
    @Autowired
    private CardBinBiz cardBinBiz;

    /**
     * 身份证二要素认证
     *
     * @param personAuthReqBo 个人信息
     * @return 响应结果
     */
    @Override
    public Long personalAuthApply(PersonAuthReqBo personAuthReqBo) {
        // 卡bin校验
        cardBinBiz.checkCardBin(personAuthReqBo.getBankCardNo());

        // 申请编号
        Long userInfoNo = orderIdManager.orderIdCreate();
        personAuthReqBo.setAuthApplyNo(userInfoNo);

        // 二要素认证
        int authStatus = idCardAuth(personAuthReqBo);

        // 三要素
        if (AuthApplyStatusEnum.AUTH_SUCCESS.getCode() == authStatus) {
            authStatus = bankCardAuth(personAuthReqBo);
        }

        // 新增申请信息
        personAuthReqBo.setAuthStatus(authStatus);

        authApplyManager.addAuthApply(AuthConvert.addPersonalAuthApplyConvert(personAuthReqBo, UserTypeEnum.PERSONAL.getType(),
                AuthMethodEnum.AUTH_PERSONAL.getCode()));
        log.info("新增个人认证申请记录成功");

        if (AuthStatusEnum.AUTH_SUCCESS.getCode() != authStatus) {
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190401, personAuthReqBo.getRemarks());
        }

        return userInfoNo;
    }

    /**
     * 企业实名认证
     *
     * @param orgAuthReqBo 企业信息
     * @return 响应结果
     */
    @Override
    public Long orgAuthApply(OrgAuthReqBo orgAuthReqBo) {
        Long userInfoNo = orderIdManager.orderIdCreate();
        orgAuthReqBo.setAuthApplyNo(userInfoNo);

        // 企业认证
        int authStatus = orgAuth(orgAuthReqBo);

        orgAuthReqBo.setAuthStatus(authStatus);

        // 新增认证记录
        authApplyManager.addAuthApply(AuthConvert.addOrgAuthApplyConvert(orgAuthReqBo, UserTypeEnum.ORG.getType(), AuthMethodEnum.AUTH_ORG.getCode()));
        log.info("新增企业实名认证申请记录成功");

        if (AuthStatusEnum.AUTH_SUCCESS.getCode() != authStatus) {
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190401, orgAuthReqBo.getRemarks());
        }
        return userInfoNo;
    }

    /**
     * 身份证二要素认证
     *
     * @param personAuthReqBo 个人信息
     * @return 响应结果
     */
    private int idCardAuth(PersonAuthReqBo personAuthReqBo) {

        personAuthReqBo.setAuthReqNo(orderIdManager.orderIdCreate());
        // 二要素认证
        IdCardAuthStateEnum result = userAuthBizImpl.idCardAuth(personAuthReqBo);
        log.info("二要素认证结果：{},{}", result.getCode(), result.getDesc());

        int authStatus = result.getCode() == 0 ? AuthStatusEnum.AUTH_SUCCESS.getCode() : AuthStatusEnum.AUTH_FAIL.getCode();
        personAuthReqBo.setAuthStatus(authStatus);
        personAuthReqBo.setRemarks(result.getDesc());

        // 新增二要素认证记录
        authPersonManager.addAuthPerson(AuthConvert.addAuthPersonConvert(personAuthReqBo));
        log.info("新增二要素认证记录成功");

        return result.getCode() == 0 ? AuthStatusEnum.AUTH_SUCCESS.getCode() : AuthApplyStatusEnum.ID_CARD_AUTH_FAIL.getCode();
    }

    /**
     * 银行卡三要素认证
     *
     * @param personAuthReqBo 个人信息
     * @return 响应结果
     */
    private int bankCardAuth(PersonAuthReqBo personAuthReqBo) {

        personAuthReqBo.setAuthReqNo(orderIdManager.orderIdCreate());
        // 三要素认证
        IdCardAuthStateEnum result = userAuthBizImpl.bankCardAuth(personAuthReqBo);
        log.info("银行卡三要素认证结果：{},{}", result.getCode(), result.getDesc());

        int authStatus = result.getCode() == 0 ? AuthStatusEnum.AUTH_SUCCESS.getCode() : AuthStatusEnum.AUTH_FAIL.getCode();
        personAuthReqBo.setAuthStatus(authStatus);
        personAuthReqBo.setRemarks(result.getDesc());
        personAuthReqBo.setBankCardType(CardTypeEnum.CARD_TYPE_1.getCode());

        // 新增银行卡三要素认证记录
        authPersonManager.addAuthBank(AuthConvert.addAuthBankConvert(personAuthReqBo));
        log.info("新增银行卡三要素认证记录成功");

        return result.getCode() == 0 ? AuthStatusEnum.AUTH_SUCCESS.getCode() : AuthApplyStatusEnum.BANK_CARD_AUTH_FAIL.getCode();
    }

    /**
     * 企业实名认证
     *
     * @param orgAuthReqBo 企业信息
     * @return 响应结果
     */
    private int orgAuth(OrgAuthReqBo orgAuthReqBo) {
        orgAuthReqBo.setAuthReqNo(orderIdManager.orderIdCreate());
        // 企业认证
        CompanyAuthStateEnum result = companyAuthBiz.companyAuth(AuthConvert.orgAuthConvert(orgAuthReqBo));
        log.info("企业实名认证认证结果：{},{}", result.getCode(), result.getDesc());

        int authStatus = result.getCode() == 0 ? AuthStatusEnum.AUTH_SUCCESS.getCode() : AuthStatusEnum.AUTH_FAIL.getCode();
        orgAuthReqBo.setAuthStatus(authStatus);
        orgAuthReqBo.setRemarks(result.getDesc());

        // 新增企业实名认证记录
        authPersonManager.addAuthOrg(AuthConvert.addAuthOrgConvert(orgAuthReqBo, authStatus));
        log.info("新增企业实名认证记录成功");

        return result.getCode() == 0 ? AuthStatusEnum.AUTH_SUCCESS.getCode() : AuthApplyStatusEnum.ORG_AUTH_FAIL.getCode();
    }
}
