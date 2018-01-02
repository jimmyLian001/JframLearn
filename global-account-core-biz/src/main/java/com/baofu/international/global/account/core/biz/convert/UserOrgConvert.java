package com.baofu.international.global.account.core.biz.convert;

import com.baofu.international.global.account.core.biz.models.OrgInfoReqBo;
import com.baofu.international.global.account.core.common.constant.Constants;
import com.baofu.international.global.account.core.common.enums.RealNameStatusEnum;
import com.baofu.international.global.account.core.common.util.SecurityUtil;
import com.baofu.international.global.account.core.dal.model.UserOrgDo;

/**
 * 企业用户信息转换
 * <p>
 * 1、更新企业用户信息转换
 * </p>
 *
 * @author : hetao Date: 2017/11/06 ProjectName: account-core Version: 1.0.0
 */
public final class UserOrgConvert {

    private UserOrgConvert() {

    }

    /**
     * 更新企业用户信息转换
     *
     * @param orgInfoReqBo 企业用户信息
     * @return 转换信息
     */
    public static UserOrgDo addUserOrgConvert(OrgInfoReqBo orgInfoReqBo) {
        UserOrgDo userOrgDo = new UserOrgDo();
        userOrgDo.setUserInfoNo(orgInfoReqBo.getUserInfoNo());
        userOrgDo.setName(orgInfoReqBo.getCompanyName());
        userOrgDo.setEnName(orgInfoReqBo.getEnName());
        userOrgDo.setIdType(orgInfoReqBo.getIdType());
        userOrgDo.setIdDfsId(orgInfoReqBo.getIdDfsId());
        userOrgDo.setIdNo(orgInfoReqBo.getIdNo());
        userOrgDo.setLicenseBeginDate(orgInfoReqBo.getLicenseBeginDate());
        userOrgDo.setLicenseEndDate(orgInfoReqBo.getLicenseEndDate());
        userOrgDo.setTaxRegistrationCertDfsId(orgInfoReqBo.getTaxRegistrationCertificateDfsId());
        userOrgDo.setOrgCodeCertDfsId(orgInfoReqBo.getOrgCodeCertificateDfsId());
        userOrgDo.setLegalName(orgInfoReqBo.getLegalName());
        userOrgDo.setLegalIdNo(SecurityUtil.desEncrypt(orgInfoReqBo.getLegalIdNo(), Constants.CARD_DES_KEY));
        userOrgDo.setLegalIdFrontDfsId(orgInfoReqBo.getLegalIdFrontDfsId());
        userOrgDo.setLegalIdReverseDfsId(orgInfoReqBo.getLegalIdReverseDfsId());
        userOrgDo.setLegalCertStartDate(orgInfoReqBo.getLegalCertStartDate());
        userOrgDo.setLegalCertExpiryDate(orgInfoReqBo.getLegalCertExpiryDate());
        userOrgDo.setAddress(orgInfoReqBo.getAddress());
        userOrgDo.setPostCode(orgInfoReqBo.getPostCode());
        userOrgDo.setRealnameStatus(RealNameStatusEnum.WAIT.getState());
        userOrgDo.setProvince(orgInfoReqBo.getProvince());
        userOrgDo.setCity(orgInfoReqBo.getCity());
        userOrgDo.setArea(orgInfoReqBo.getArea());
        userOrgDo.setEmail(orgInfoReqBo.getLoginNo());
        userOrgDo.setLegalIdType(1);
        userOrgDo.setEmail(orgInfoReqBo.getEmail());
        userOrgDo.setCreateBy(orgInfoReqBo.getLoginNo());
        userOrgDo.setUpdateBy(orgInfoReqBo.getLoginNo());

        return userOrgDo;
    }

    /**
     * 更新企业用户信息转换
     *
     * @param orgInfoReqBo 企业用户信息
     * @return 转换信息
     */
    public static UserOrgDo updateUserOrgConvert(OrgInfoReqBo orgInfoReqBo) {
        UserOrgDo userOrgDo = new UserOrgDo();
        userOrgDo.setUserInfoNo(orgInfoReqBo.getUserInfoNo());
        userOrgDo.setName(orgInfoReqBo.getCompanyName());
        userOrgDo.setEnName(orgInfoReqBo.getEnName());
        userOrgDo.setIdDfsId(orgInfoReqBo.getIdDfsId());
        userOrgDo.setIdNo(orgInfoReqBo.getIdNo());
        userOrgDo.setTaxRegistrationCertDfsId(orgInfoReqBo.getTaxRegistrationCertificateDfsId());
        userOrgDo.setOrgCodeCertDfsId(orgInfoReqBo.getOrgCodeCertificateDfsId());
        userOrgDo.setLegalName(orgInfoReqBo.getLegalName());
        userOrgDo.setLegalIdNo(SecurityUtil.desEncrypt(orgInfoReqBo.getLegalIdNo(), Constants.CARD_DES_KEY));
        userOrgDo.setLegalIdFrontDfsId(orgInfoReqBo.getLegalIdFrontDfsId());
        userOrgDo.setLegalIdReverseDfsId(orgInfoReqBo.getLegalIdReverseDfsId());
        userOrgDo.setAddress(orgInfoReqBo.getAddress());
        userOrgDo.setPostCode(orgInfoReqBo.getPostCode());
        userOrgDo.setUpdateBy(orgInfoReqBo.getUpdateBy());
        userOrgDo.setRealnameStatus(RealNameStatusEnum.WAIT.getState());
        userOrgDo.setProvince(orgInfoReqBo.getProvince());
        userOrgDo.setCity(orgInfoReqBo.getCity());
        userOrgDo.setArea(orgInfoReqBo.getArea());
        userOrgDo.setIdType(orgInfoReqBo.getIdType());
        userOrgDo.setEmail(orgInfoReqBo.getEmail());

        return userOrgDo;
    }
}
