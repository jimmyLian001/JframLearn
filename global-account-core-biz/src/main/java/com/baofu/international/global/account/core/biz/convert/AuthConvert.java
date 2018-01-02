package com.baofu.international.global.account.core.biz.convert;

import com.baofu.international.global.account.core.biz.models.CompanyAuthReqBo;
import com.baofu.international.global.account.core.biz.models.OrgAuthReqBo;
import com.baofu.international.global.account.core.biz.models.PersonAuthReqBo;
import com.baofu.international.global.account.core.common.constant.Constants;
import com.baofu.international.global.account.core.common.util.SecurityUtil;
import com.baofu.international.global.account.core.dal.model.AuthApplyDo;
import com.baofu.international.global.account.core.dal.model.AuthBankDo;
import com.baofu.international.global.account.core.dal.model.AuthOrgDo;
import com.baofu.international.global.account.core.dal.model.AuthPersonDo;

/**
 * 认证信息转换
 * <p>
 * 1、新增个人认证记录信息转换
 * 2、新增认证申请记录信息转换
 * </p>
 *
 * @author : hetao Date: 2017/11/06 ProjectName: account-core Version: 1.0.0
 */
public final class AuthConvert {

    private AuthConvert() {

    }

    /**
     * 新增个人认证记录信息转换
     *
     * @param personAuthReqBo 个人认证信息
     * @return 响应信息
     */
    public static AuthPersonDo addAuthPersonConvert(PersonAuthReqBo personAuthReqBo) {

        AuthPersonDo authPersonDo = new AuthPersonDo();
        authPersonDo.setAuthReqNo(personAuthReqBo.getAuthReqNo());
        authPersonDo.setAuthApplyNo(personAuthReqBo.getAuthApplyNo());
        authPersonDo.setIdNo(SecurityUtil.desEncrypt(personAuthReqBo.getIdCardNo(), Constants.CARD_DES_KEY));
        authPersonDo.setName(personAuthReqBo.getIdCardName());
        authPersonDo.setAuthStatus(personAuthReqBo.getAuthStatus());

        return authPersonDo;
    }

    /**
     * 新增个人认证申请记录信息转换
     *
     * @param personAuthReqBo 申请编号
     * @param type            用户类型
     * @param authMethod      认证类型
     * @return 响应信息
     */
    public static AuthApplyDo addPersonalAuthApplyConvert(PersonAuthReqBo personAuthReqBo, int type, int authMethod) {

        AuthApplyDo tAuthApplyDo = new AuthApplyDo();
        tAuthApplyDo.setAuthApplyNo(personAuthReqBo.getAuthApplyNo());
        tAuthApplyDo.setUserNo(personAuthReqBo.getUserNo());
        tAuthApplyDo.setUserType(type);
        tAuthApplyDo.setApplyType(1);
        tAuthApplyDo.setAuthMethod(authMethod);
        tAuthApplyDo.setAuthStatus(personAuthReqBo.getAuthStatus());
        tAuthApplyDo.setFailReason(personAuthReqBo.getRemarks());
        tAuthApplyDo.setCreateBy(personAuthReqBo.getLoginNo());
        tAuthApplyDo.setUpdateBy(personAuthReqBo.getLoginNo());

        return tAuthApplyDo;
    }

    /**
     * 新增企业认证申请记录信息转换
     *
     * @param orgAuthReqBo 企业信息
     * @param type         用户类型
     * @param authMethod   认证类型
     * @return 响应信息
     */
    public static AuthApplyDo addOrgAuthApplyConvert(OrgAuthReqBo orgAuthReqBo, int type, int authMethod) {

        AuthApplyDo tAuthApplyDo = new AuthApplyDo();
        tAuthApplyDo.setAuthApplyNo(orgAuthReqBo.getAuthApplyNo());
        tAuthApplyDo.setUserNo(orgAuthReqBo.getUserNo());
        tAuthApplyDo.setAuthStatus(orgAuthReqBo.getAuthStatus());
        tAuthApplyDo.setFailReason(orgAuthReqBo.getRemarks());
        tAuthApplyDo.setCreateBy(orgAuthReqBo.getLoginNo());
        tAuthApplyDo.setUpdateBy(orgAuthReqBo.getLoginNo());
        tAuthApplyDo.setUserType(type);
        tAuthApplyDo.setApplyType(1);
        tAuthApplyDo.setAuthMethod(authMethod);

        return tAuthApplyDo;
    }

    /**
     * 新增个人认证记录信息转换
     *
     * @param personAuthReqBo 个人认证信息
     * @return 响应信息
     */
    public static AuthBankDo addAuthBankConvert(PersonAuthReqBo personAuthReqBo) {

        AuthBankDo authBankDo = new AuthBankDo();
        authBankDo.setAuthReqNo(personAuthReqBo.getAuthReqNo());
        authBankDo.setAuthApplyNo(personAuthReqBo.getAuthApplyNo());
        authBankDo.setIdNo(SecurityUtil.desEncrypt(personAuthReqBo.getIdCardNo(), Constants.CARD_DES_KEY));
        authBankDo.setName(personAuthReqBo.getIdCardName());
        authBankDo.setAuthStatus(personAuthReqBo.getAuthStatus());
        authBankDo.setCardNo(SecurityUtil.desEncrypt(personAuthReqBo.getBankCardNo(), Constants.CARD_DES_KEY));
        authBankDo.setBankCardType(personAuthReqBo.getBankCardType());

        return authBankDo;
    }

    /**
     * 新增企业认证记录信息转换
     *
     * @param orgAuthReqBo 企业认证信息
     * @param status       认证状态
     * @return 响应信息
     */
    public static AuthOrgDo addAuthOrgConvert(OrgAuthReqBo orgAuthReqBo, int status) {

        AuthOrgDo authOrgDo = new AuthOrgDo();
        authOrgDo.setAuthReqNo(orgAuthReqBo.getAuthReqNo());
        authOrgDo.setAuthApplyNo(orgAuthReqBo.getAuthApplyNo());
        // 营业执照
        authOrgDo.setIdNo(orgAuthReqBo.getIdNo());
        authOrgDo.setName(orgAuthReqBo.getName());
        authOrgDo.setEnName(orgAuthReqBo.getEnName());
        authOrgDo.setIdType(orgAuthReqBo.getIdType());
        authOrgDo.setLegalName(orgAuthReqBo.getLegalName());
        authOrgDo.setLegalIdType(orgAuthReqBo.getLegalIdType());
        // 法人证件号
        authOrgDo.setLegalIdNo(SecurityUtil.desEncrypt(orgAuthReqBo.getLegalIdNo(), Constants.CARD_DES_KEY));
        authOrgDo.setAuthStatus(status);
        authOrgDo.setLegalIdType(1);

        return authOrgDo;
    }

    /**
     * 企业实名认证信息转换
     *
     * @param orgAuthReqBo 企业信息
     * @return 转换信息
     */
    public static CompanyAuthReqBo orgAuthConvert(OrgAuthReqBo orgAuthReqBo) {

        CompanyAuthReqBo companyAuthReqBo = new CompanyAuthReqBo();
        companyAuthReqBo.setAuthApplyNo(orgAuthReqBo.getAuthApplyNo());
        companyAuthReqBo.setEntName(orgAuthReqBo.getName());
        companyAuthReqBo.setFrCid(orgAuthReqBo.getLegalIdNo());
        companyAuthReqBo.setFrName(orgAuthReqBo.getLegalName());

        return companyAuthReqBo;
    }
}
