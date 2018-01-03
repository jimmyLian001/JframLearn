package com.baofu.international.global.account.client.service.convert;

import com.baofu.international.global.account.client.service.models.OrgAuthReq;
import com.baofu.international.global.account.client.service.models.UserInfoBo;
import com.baofu.international.global.account.core.facade.model.UserLoginRespDTO;
import com.baofu.international.global.account.core.facade.model.UserOrgReqDto;
import com.baofu.international.global.account.core.facade.model.user.OrgInfoRespDto;

/**
 * 企业认证信息转换
 * <p>
 * 1、新增企业认证信息转换
 * </p>
 *
 * @author : hetao Date: 2017/11/06 ProjectName: account-core Version: 1.0.0
 */
public final class OrgInfoConvert {

    private OrgInfoConvert() {

    }

    /**
     * 新增企业认证信息转换
     *
     * @param orgAuthReq  请求信息
     * @param authApplyNo 认证申请编号
     * @return 响应信息
     */
    public static UserOrgReqDto addOrgInfoConvert(OrgAuthReq orgAuthReq, Long authApplyNo) {

        UserOrgReqDto userOrgReqDto = new UserOrgReqDto();
        userOrgReqDto.setUserNo(orgAuthReq.getUserNo());
        userOrgReqDto.setState(1);
        userOrgReqDto.setCompanyName(orgAuthReq.getCompanyName());
        userOrgReqDto.setEnName(orgAuthReq.getEnglishName());
        userOrgReqDto.setIdDfsId(orgAuthReq.getLicenseDfsId());
        userOrgReqDto.setIdNo(orgAuthReq.getLicenseNo());
        userOrgReqDto.setTaxRegistrationCertificateDfsId(orgAuthReq.getTaxRegCertDfsId());
        userOrgReqDto.setOrgCodeCertificateDfsId(orgAuthReq.getOrgCodeCertDfsId());
        userOrgReqDto.setIdType(orgAuthReq.getLicenseType());
        userOrgReqDto.setLegalName(orgAuthReq.getLegalName());
        userOrgReqDto.setLegalIdNo(orgAuthReq.getLegalNo());
        userOrgReqDto.setLegalIdReverseDfsId(orgAuthReq.getIdReverseDfsId());
        userOrgReqDto.setUpdateBy(orgAuthReq.getLoginNo());
        userOrgReqDto.setAddress(orgAuthReq.getAddress());
        userOrgReqDto.setLegalIdFrontDfsId(orgAuthReq.getIdFrontDfsId());
        userOrgReqDto.setPostCode(orgAuthReq.getPostCode());
        userOrgReqDto.setProvince(orgAuthReq.getProvince());
        userOrgReqDto.setCity(orgAuthReq.getCity());
        userOrgReqDto.setArea(orgAuthReq.getArea());
        userOrgReqDto.setRequestType(orgAuthReq.getRequestType());
        userOrgReqDto.setLoginNo(orgAuthReq.getLoginNo());
        userOrgReqDto.setEmail(orgAuthReq.getEmail());
        userOrgReqDto.setAuthApplyNo(authApplyNo);

        return userOrgReqDto;
    }

    /**
     * 新增企业认证信息转换
     *
     * @param orgAuthReq 请求信息
     * @return 响应信息
     */
    public static UserOrgReqDto updateOrgInfoConvert(OrgAuthReq orgAuthReq) {

        UserOrgReqDto userOrgReqDto = new UserOrgReqDto();
        userOrgReqDto.setUserInfoNo(orgAuthReq.getUserInfoNo());
        userOrgReqDto.setUserNo(orgAuthReq.getUserInfoNo());
        userOrgReqDto.setUserNo(orgAuthReq.getUserNo());
        userOrgReqDto.setState(1);
        userOrgReqDto.setCompanyName(orgAuthReq.getCompanyName());
        userOrgReqDto.setEnName(orgAuthReq.getEnglishName());
        userOrgReqDto.setIdType(orgAuthReq.getLicenseType());
        userOrgReqDto.setIdDfsId(orgAuthReq.getLicenseDfsId());
        userOrgReqDto.setIdNo(orgAuthReq.getLicenseNo());
        userOrgReqDto.setTaxRegistrationCertificateDfsId(orgAuthReq.getTaxRegCertDfsId());
        userOrgReqDto.setOrgCodeCertificateDfsId(orgAuthReq.getOrgCodeCertDfsId());
        userOrgReqDto.setLegalName(orgAuthReq.getLegalName());
        userOrgReqDto.setLegalIdNo(orgAuthReq.getLegalNo());
        userOrgReqDto.setLegalIdFrontDfsId(orgAuthReq.getIdFrontDfsId());
        userOrgReqDto.setLegalIdReverseDfsId(orgAuthReq.getIdReverseDfsId());
        userOrgReqDto.setUpdateBy(orgAuthReq.getLoginNo());
        userOrgReqDto.setAddress(orgAuthReq.getAddress());
        userOrgReqDto.setPostCode(orgAuthReq.getPostCode());
        userOrgReqDto.setProvince(orgAuthReq.getProvince());
        userOrgReqDto.setCity(orgAuthReq.getCity());
        userOrgReqDto.setArea(orgAuthReq.getArea());
        userOrgReqDto.setRequestType(orgAuthReq.getRequestType());
        userOrgReqDto.setLoginNo(orgAuthReq.getLoginNo());
        userOrgReqDto.setEmail(orgAuthReq.getEmail());

        return userOrgReqDto;
    }

    /**
     * 用户的对象转换
     *
     * @param userInfoBo      返回参数
     * @param userPersonalDto 查询结果参数
     * @return 返回参数
     */
    public static void convertUserInfoBo(UserInfoBo userInfoBo, OrgInfoRespDto userPersonalDto) {
        userInfoBo.setOrgName(userPersonalDto.getName());
        userInfoBo.setName(userPersonalDto.getLegalName());
        userInfoBo.setIdNo(userPersonalDto.getLegalIdNo());
        userInfoBo.setEmail(userPersonalDto.getEmail());
        userInfoBo.setMobileNo(userPersonalDto.getPhoneNumber());
        userInfoBo.setRealnameStatus(userPersonalDto.getRealnameStatus());
    }

    /**
     * 个人用户的对象转换
     *
     * @param userLoginRespDTO 个人信息
     * @param orgInfoRespDto   查询结果参数
     * @return 返回参数
     */
    public static UserInfoBo convertUserInfoBo(UserLoginRespDTO userLoginRespDTO, OrgInfoRespDto orgInfoRespDto) {
        UserInfoBo userInfoBo = new UserInfoBo();
        userInfoBo.setLoginType(userLoginRespDTO.getLoginType());
        userInfoBo.setUserNo(userLoginRespDTO.getUserNo());
        userInfoBo.setLoginNo(userLoginRespDTO.getLoginNo());
        userInfoBo.setOrgName(orgInfoRespDto.getName());
        userInfoBo.setName(orgInfoRespDto.getLegalName());
        userInfoBo.setIdNo(orgInfoRespDto.getLegalIdNo());
        userInfoBo.setEmail(orgInfoRespDto.getEmail());
        userInfoBo.setMobileNo(orgInfoRespDto.getPhoneNumber());
        userInfoBo.setRealnameStatus(orgInfoRespDto.getRealnameStatus());
        userInfoBo.setUserInfoNo(orgInfoRespDto.getUserInfoNo());
        return userInfoBo;
    }


}
