package com.baofu.international.global.account.client.web.convert;

import com.baofu.international.global.account.client.common.constant.NumberDict;
import com.baofu.international.global.account.client.service.models.OrgAuthReq;
import com.baofu.international.global.account.client.service.models.PersonAuthReq;
import com.baofu.international.global.account.client.web.models.OrgAuthVo;
import com.baofu.international.global.account.client.web.models.PersonAuthVo;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * 实名认证数据转换服务
 * <p>
 * 1、个人实名认证参数转换
 * 2、企业实名认证参数转换
 * </p>
 * @author : hetao  Date: 2017/11/21 ProjectName: account-client Version: 1.0
 */
public final class RealNameAuthConvert {

    private RealNameAuthConvert() {

    }

    /**
     * 个人实名认证参数转换
     *
     * @param personAuthVo   个人实名form参数
     * @param idFrontImage   身份证正面照
     * @param idReverseImage 身份证反面照
     * @return 响应信息
     */
    public static PersonAuthReq personAuthParamConvert(PersonAuthVo personAuthVo, MultipartFile idFrontImage, MultipartFile idReverseImage) {
        PersonAuthReq personAuthReq = new PersonAuthReq();
        personAuthReq.setIdFrontImage(idFrontImage);
        personAuthReq.setIdReverseImage(idReverseImage);
        personAuthReq.setCardHolder(personAuthVo.getCardHolder());
        personAuthReq.setCardNo(personAuthVo.getCardNo());
        personAuthReq.setEnglishName(personAuthVo.getEnglishName());
        personAuthReq.setIdNo(personAuthVo.getIdNo().toUpperCase());
        personAuthReq.setProvince(personAuthVo.getProvince().split("_")[1]);
        personAuthReq.setArea(personAuthVo.getArea().split("_")[1]);
        personAuthReq.setCity(personAuthVo.getCity().split("_")[1]);
        personAuthReq.setAddress(personAuthVo.getAddress());
        personAuthReq.setPostCode(personAuthVo.getPostCode());
        personAuthReq.setUserNo(personAuthVo.getUserNo());
        personAuthReq.setLoginNo(personAuthVo.getLoginNo());
        personAuthReq.setEmail(personAuthVo.getEmail());

        return personAuthReq;
    }

    /**
     * 个人实名认证参数转换
     *
     * @param personAuthVo   个人实名form参数
     * @param idFrontImage   身份证正面照
     * @param idReverseImage 身份证反面照
     * @return 响应信息
     */
    public static PersonAuthReq personAuthUpdateParamConvert(PersonAuthVo personAuthVo, MultipartFile idFrontImage, MultipartFile idReverseImage) {
        PersonAuthReq personAuthReq = new PersonAuthReq();
        personAuthReq.setIdFrontImage(idFrontImage);
        personAuthReq.setIdReverseImage(idReverseImage);
        personAuthReq.setCardHolder(personAuthVo.getCardHolder());
        personAuthReq.setCardNo(personAuthVo.getCardNo());
        personAuthReq.setEnglishName(personAuthVo.getEnglishName());
        personAuthReq.setIdNo(personAuthVo.getIdNo().toUpperCase());
        personAuthReq.setProvince(personAuthVo.getProvince().split("_")[1]);
        personAuthReq.setCity(personAuthVo.getCity().split("_")[1]);
        personAuthReq.setArea(personAuthVo.getArea().split("_")[1]);
        personAuthReq.setAddress(personAuthVo.getAddress());
        personAuthReq.setPostCode(personAuthVo.getPostCode());
        personAuthReq.setUserNo(personAuthVo.getUserNo());
        personAuthReq.setLoginNo(personAuthVo.getLoginNo());
        personAuthReq.setUserInfoNo(Long.valueOf(personAuthVo.getUserInfoNo()));
        personAuthReq.setEmail(personAuthVo.getEmail());

        return personAuthReq;
    }

    /**
     * 企业实名认证参数转换
     *
     * @param orgAuthVo        企业实名form参数
     * @param idFrontImage     身份证正面照
     * @param idReverseImage   身份证反面照
     * @param orgCodeCertImage 组织机构代码证照片
     * @param taxRegCertImage  税务登记证照片
     * @param licenseImage     营业执照照片
     * @return 响应信息
     */
    public static OrgAuthReq orgAuthParamConvert(OrgAuthVo orgAuthVo, MultipartFile idFrontImage, MultipartFile idReverseImage,
                                                 MultipartFile orgCodeCertImage, MultipartFile taxRegCertImage, MultipartFile licenseImage) {
        OrgAuthReq orgAuthReq = new OrgAuthReq();
        int multiLicenseType = orgAuthVo.getMultiLicenseType();
        // 普通营业执照
        if (multiLicenseType == NumberDict.TWO) {
            orgAuthReq.setOrgCodeCertImage(orgCodeCertImage);
            orgAuthReq.setTaxRegCertImage(taxRegCertImage);
        }

        orgAuthReq.setLicenseType(multiLicenseType);
        orgAuthReq.setLicenseNo(orgAuthVo.getLicenseNo());

        orgAuthReq.setLicenseImage(licenseImage);
        orgAuthReq.setIdFrontImage(idFrontImage);
        orgAuthReq.setIdReverseImage(idReverseImage);

        orgAuthReq.setCompanyName(orgAuthVo.getCompanyName());
        orgAuthReq.setEnglishName(orgAuthVo.getEnglishName());
        orgAuthReq.setLegalNo(orgAuthVo.getLegalNo());
        orgAuthReq.setLegalName(orgAuthVo.getLegalName());
        orgAuthReq.setCity(orgAuthVo.getCity().split("_")[1]);
        orgAuthReq.setProvince(orgAuthVo.getProvince().split("_")[1]);
        orgAuthReq.setArea(orgAuthVo.getArea().split("_")[1]);
        orgAuthReq.setUserNo(orgAuthVo.getUserNo());
        orgAuthReq.setLoginNo(orgAuthVo.getLoginNo());
        orgAuthReq.setAddress(orgAuthVo.getAddress());
        orgAuthReq.setPostCode(orgAuthVo.getPostCode());
        orgAuthReq.setRequestType(Integer.parseInt(orgAuthVo.getRequestType()));
        orgAuthReq.setEmail(StringUtils.isEmpty(orgAuthVo.getEmail()) ? null : orgAuthVo.getEmail());

        return orgAuthReq;
    }

    /**
     * 企业实名认证参数转换
     *
     * @param orgAuthVo        企业实名form参数
     * @param idFrontImage     身份证正面照
     * @param idReverseImage   身份证反面照
     * @param orgCodeCertImage 组织机构代码证照片
     * @param taxRegCertImage  税务登记证照片
     * @param licenseImage     营业执照照片
     * @return 响应信息
     */
    public static OrgAuthReq orgAuthUpdateParamConvert(OrgAuthVo orgAuthVo, MultipartFile idFrontImage, MultipartFile idReverseImage,
                                                       MultipartFile orgCodeCertImage, MultipartFile taxRegCertImage, MultipartFile licenseImage) {
        OrgAuthReq orgAuthReq = new OrgAuthReq();
        int multiLicenseType = orgAuthVo.getMultiLicenseType();
        // 普通营业执照
        if (multiLicenseType == NumberDict.TWO) {
            orgAuthReq.setOrgCodeCertImage(orgCodeCertImage);
            orgAuthReq.setTaxRegCertImage(taxRegCertImage);
        }

        orgAuthReq.setLicenseImage(licenseImage);
        orgAuthReq.setIdFrontImage(idFrontImage);
        orgAuthReq.setIdReverseImage(idReverseImage);

        orgAuthReq.setCompanyName(orgAuthVo.getCompanyName());
        orgAuthReq.setLicenseType(multiLicenseType);
        orgAuthReq.setLicenseNo(orgAuthVo.getLicenseNo());
        orgAuthReq.setEnglishName(orgAuthVo.getEnglishName());
        orgAuthReq.setLegalNo(orgAuthVo.getLegalNo());
        orgAuthReq.setLegalName(orgAuthVo.getLegalName());
        orgAuthReq.setArea(orgAuthVo.getArea().split("_")[1]);
        orgAuthReq.setProvince(orgAuthVo.getProvince().split("_")[1]);
        orgAuthReq.setCity(orgAuthVo.getCity().split("_")[1]);
        orgAuthReq.setUserNo(orgAuthVo.getUserNo());
        orgAuthReq.setLoginNo(orgAuthVo.getLoginNo());
        orgAuthReq.setAddress(orgAuthVo.getAddress());
        orgAuthReq.setPostCode(orgAuthVo.getPostCode());
        orgAuthReq.setUserInfoNo(orgAuthVo.getUserInfoNo());
        orgAuthReq.setEmail(orgAuthVo.getEmail());

        return orgAuthReq;
    }
}
