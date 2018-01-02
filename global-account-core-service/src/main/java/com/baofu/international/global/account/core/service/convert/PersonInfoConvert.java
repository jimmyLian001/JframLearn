package com.baofu.international.global.account.core.service.convert;

import com.baofu.international.global.account.core.biz.models.PersonInfoReqBo;
import com.baofu.international.global.account.core.facade.model.UserPersonalReqDto;

/**
 * 个人认证信息转换
 * <p>
 * 1、新增个人认证信息转换
 * </p>
 *
 * @author : hetao Date: 2017/11/06 ProjectName: account-core Version: 1.0.0
 */
public final class PersonInfoConvert {

    private PersonInfoConvert() {

    }

    /**
     * 新增个人认证信息转换
     *
     * @param personInfoReqDto 个人认证信息
     * @return 转换信息
     */
    public static PersonInfoReqBo updateUserPersonalConvert(UserPersonalReqDto personInfoReqDto) {
        PersonInfoReqBo personInfoReqBo = new PersonInfoReqBo();
        personInfoReqBo.setUserInfoNo(personInfoReqDto.getUserInfoNo());
        personInfoReqBo.setUserNo(personInfoReqDto.getUserNo());
        personInfoReqBo.setIdNo(personInfoReqDto.getIdNo());
        personInfoReqBo.setAddress(personInfoReqDto.getAddress());
        personInfoReqBo.setArea(personInfoReqDto.getArea());
        personInfoReqBo.setCertExpiryDate(personInfoReqDto.getCertExpiryDate());
        personInfoReqBo.setCertStartDate(personInfoReqDto.getCertStartDate());
        personInfoReqBo.setCity(personInfoReqDto.getCity());
        personInfoReqBo.setEnName(personInfoReqDto.getEnName());
        personInfoReqBo.setIdFrontDfsId(personInfoReqDto.getIdFrontDfsId());
        personInfoReqBo.setIdType(personInfoReqDto.getIdType());
        personInfoReqBo.setLongTerm(personInfoReqDto.getLongTerm());
        personInfoReqBo.setManagementCategoryType(personInfoReqDto.getManagementCategoryType());
        personInfoReqBo.setOccupation(personInfoReqDto.getOccupation());
        personInfoReqBo.setPostCode(personInfoReqDto.getPostCode());
        personInfoReqBo.setProvince(personInfoReqDto.getProvince());
        personInfoReqBo.setState(personInfoReqDto.getState());
        personInfoReqBo.setIdReverseDfsId(personInfoReqDto.getIdReverseDfsId());
        personInfoReqBo.setIdName(personInfoReqDto.getIdName());
        personInfoReqBo.setBankCardNo(personInfoReqDto.getBankCardNo());
        personInfoReqBo.setLoginNo(personInfoReqDto.getLoginNo());
        personInfoReqBo.setEmail(personInfoReqDto.getEmail());
        personInfoReqBo.setAuthApplyNo(personInfoReqDto.getAuthApplyNo());

        return personInfoReqBo;
    }
}
