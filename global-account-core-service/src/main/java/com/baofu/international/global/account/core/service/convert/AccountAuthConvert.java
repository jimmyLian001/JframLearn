package com.baofu.international.global.account.core.service.convert;

import com.baofu.international.global.account.core.biz.models.OrgAuthReqBo;
import com.baofu.international.global.account.core.biz.models.PersonAuthReqBo;
import com.baofu.international.global.account.core.facade.model.OrgAuthReqDto;
import com.baofu.international.global.account.core.facade.model.PersonalAuthReqDto;

/**
 * 认证信息转换
 * <p>
 * 1、二要素信息转换
 * </p>
 *
 * @author : hetao Date: 2017/11/06 ProjectName: account-core Version: 1.0.0
 */
public final class AccountAuthConvert {

    private AccountAuthConvert() {

    }

    /**
     * 个人信息转换
     *
     * @param personalAuthReqDto 请求信息
     * @return 响应信息
     */
    public static PersonAuthReqBo personalAuthConvert(PersonalAuthReqDto personalAuthReqDto) {

        PersonAuthReqBo personAuthReqBo = new PersonAuthReqBo();
        personAuthReqBo.setIdCardName(personalAuthReqDto.getIdCardName());
        personAuthReqBo.setIdCardNo(personalAuthReqDto.getIdCardNo());
        personAuthReqBo.setUserNo(personalAuthReqDto.getUserNo());
        personAuthReqBo.setLoginNo(personalAuthReqDto.getLoginNo());
        personAuthReqBo.setBankCardNo(personalAuthReqDto.getBankCardNo());

        return personAuthReqBo;
    }

    /**
     * 企业信息转换
     *
     * @param orgAuthReqDto 请求信息
     * @return 响应信息
     */
    public static OrgAuthReqBo orgAuthConvert(OrgAuthReqDto orgAuthReqDto) {

        OrgAuthReqBo orgAuthReqBo = new OrgAuthReqBo();
        orgAuthReqBo.setName(orgAuthReqDto.getCompanyName());
        orgAuthReqBo.setUserNo(orgAuthReqDto.getUserNo());
        orgAuthReqBo.setLoginNo(orgAuthReqDto.getLoginNo());
        orgAuthReqBo.setLegalIdNo(orgAuthReqDto.getLegalIdNo());
        orgAuthReqBo.setLegalName(orgAuthReqDto.getLegalName());
        orgAuthReqBo.setEnName(orgAuthReqDto.getEnName());
        orgAuthReqBo.setIdNo(orgAuthReqDto.getIdNo());
        orgAuthReqBo.setIdType(orgAuthReqDto.getIdType());

        return orgAuthReqBo;
    }
}
