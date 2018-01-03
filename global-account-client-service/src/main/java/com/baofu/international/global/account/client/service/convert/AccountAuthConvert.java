package com.baofu.international.global.account.client.service.convert;

import com.baofu.international.global.account.client.service.models.OrgAuthReq;
import com.baofu.international.global.account.client.service.models.PersonAuthReq;
import com.baofu.international.global.account.core.facade.model.OrgAuthReqDto;
import com.baofu.international.global.account.core.facade.model.PersonalAuthReqDto;

/**
 * 认证信息转换
 * <p>
 * 1、二要素信息转换
 * </p>
 * @author : hetao Date: 2017/11/06 ProjectName: account-core Version: 1.0.0
 */
public final class AccountAuthConvert {

    private AccountAuthConvert() {

    }

    /**
     * 二要素信息转换
     *
     * @param personAuthReq 请求信息
     * @return 响应信息
     */
    public static PersonalAuthReqDto personalAuthConvert(PersonAuthReq personAuthReq) {

        PersonalAuthReqDto personalAuthReqDto = new PersonalAuthReqDto();
        personalAuthReqDto.setIdCardName(personAuthReq.getCardHolder());
        personalAuthReqDto.setIdCardNo(personAuthReq.getIdNo());
        personalAuthReqDto.setLoginNo(personAuthReq.getLoginNo());
        personalAuthReqDto.setUserNo(personAuthReq.getUserNo());
        personalAuthReqDto.setBankCardNo(personAuthReq.getCardNo());

        return personalAuthReqDto;
    }

    /**
     * 企业信息转换
     *
     * @param orgAuthReq 请求信息
     * @return 响应信息
     */
    public static OrgAuthReqDto orgAuthConvert(OrgAuthReq orgAuthReq) {

        OrgAuthReqDto orgAuthReqDto = new OrgAuthReqDto();
        orgAuthReqDto.setUserNo(orgAuthReq.getUserNo());
        orgAuthReqDto.setLoginNo(orgAuthReq.getLoginNo());
        orgAuthReqDto.setLegalIdNo(orgAuthReq.getLegalNo());
        orgAuthReqDto.setLegalName(orgAuthReq.getLegalName());
        orgAuthReqDto.setEnName(orgAuthReq.getEnglishName());
        orgAuthReqDto.setIdNo(orgAuthReq.getLicenseNo());
        orgAuthReqDto.setIdType(orgAuthReq.getLicenseType());
        orgAuthReqDto.setCompanyName(orgAuthReq.getCompanyName());

        return orgAuthReqDto;
    }
}
