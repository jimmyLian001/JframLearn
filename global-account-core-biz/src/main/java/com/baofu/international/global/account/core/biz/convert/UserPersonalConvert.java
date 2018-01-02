package com.baofu.international.global.account.core.biz.convert;

import com.baofu.international.global.account.core.biz.models.PersonInfoReqBo;
import com.baofu.international.global.account.core.common.constant.Constants;
import com.baofu.international.global.account.core.common.enums.RealNameStatusEnum;
import com.baofu.international.global.account.core.common.util.SecurityUtil;
import com.baofu.international.global.account.core.dal.model.UserPersonalDo;

/**
 * 个人认证信息转换
 * <p>
 * 1、新增个人认证信息转换
 * </p>
 *
 * @author : hetao Date: 2017/11/06 ProjectName: account-core Version: 1.0.0
 */
public final class UserPersonalConvert {

    private UserPersonalConvert() {

    }

    /**
     * 新增个人认证信息转换
     *
     * @param personInfoReqBo 个人认证信息
     * @param recordId        卡表主键
     * @return 转换信息
     */
    public static UserPersonalDo addUserPersonalConvert(PersonInfoReqBo personInfoReqBo, Long recordId) {
        UserPersonalDo tPersonInfoDo = new UserPersonalDo();
        tPersonInfoDo.setUserInfoNo(personInfoReqBo.getUserInfoNo());
        tPersonInfoDo.setIdType(personInfoReqBo.getIdType());
        tPersonInfoDo.setIdNo(SecurityUtil.desEncrypt(personInfoReqBo.getIdNo(), Constants.CARD_DES_KEY));
        tPersonInfoDo.setName(personInfoReqBo.getIdName());
        tPersonInfoDo.setEnName(personInfoReqBo.getEnName());
        tPersonInfoDo.setLongTerm(personInfoReqBo.getLongTerm());
        tPersonInfoDo.setCertExpiryDate(personInfoReqBo.getCertExpiryDate());
        tPersonInfoDo.setCertStartDate(personInfoReqBo.getCertStartDate());
        tPersonInfoDo.setOccupation(personInfoReqBo.getOccupation());
        tPersonInfoDo.setProvince(personInfoReqBo.getProvince());
        tPersonInfoDo.setIdFrontDfsId(personInfoReqBo.getIdFrontDfsId());
        tPersonInfoDo.setIdReverseDfsId(personInfoReqBo.getIdReverseDfsId());
        tPersonInfoDo.setCity(personInfoReqBo.getCity());
        tPersonInfoDo.setArea(personInfoReqBo.getArea());
        tPersonInfoDo.setAddress(personInfoReqBo.getAddress());
        tPersonInfoDo.setPostCode(personInfoReqBo.getPostCode());
        tPersonInfoDo.setBankCardRecordNo(recordId);
        tPersonInfoDo.setCreateBy(personInfoReqBo.getLoginNo());
        tPersonInfoDo.setUpdateBy(personInfoReqBo.getLoginNo());
        tPersonInfoDo.setRealnameStatus(RealNameStatusEnum.WAIT.getState());
        tPersonInfoDo.setPhoneNumber(personInfoReqBo.getLoginNo());
        tPersonInfoDo.setEmail(personInfoReqBo.getEmail());
        tPersonInfoDo.setManagementCategoryType(personInfoReqBo.getManagementCategoryType());

        return tPersonInfoDo;
    }

    /**
     * 更新个人认证信息转换
     *
     * @param personInfoReqBo 个人认证信息
     * @return 转换信息
     */
    public static UserPersonalDo updateUserPersonalConvert(PersonInfoReqBo personInfoReqBo) {
        UserPersonalDo tPersonInfoDo = new UserPersonalDo();
        tPersonInfoDo.setUserInfoNo(personInfoReqBo.getUserInfoNo());
        tPersonInfoDo.setIdFrontDfsId(personInfoReqBo.getIdFrontDfsId());
        tPersonInfoDo.setIdReverseDfsId(personInfoReqBo.getIdReverseDfsId());
        tPersonInfoDo.setLongTerm(personInfoReqBo.getLongTerm());
        tPersonInfoDo.setCertExpiryDate(personInfoReqBo.getCertExpiryDate());
        tPersonInfoDo.setCertStartDate(personInfoReqBo.getCertStartDate());
        tPersonInfoDo.setManagementCategoryType(personInfoReqBo.getManagementCategoryType());
        tPersonInfoDo.setOccupation(personInfoReqBo.getOccupation());
        tPersonInfoDo.setProvince(personInfoReqBo.getProvince());
        tPersonInfoDo.setCity(personInfoReqBo.getCity());
        tPersonInfoDo.setArea(personInfoReqBo.getArea());
        tPersonInfoDo.setAddress(personInfoReqBo.getAddress());
        tPersonInfoDo.setPostCode(personInfoReqBo.getPostCode());
        tPersonInfoDo.setUpdateBy(personInfoReqBo.getLoginNo());
        tPersonInfoDo.setRealnameStatus(RealNameStatusEnum.WAIT.getState());
        tPersonInfoDo.setEmail(personInfoReqBo.getEmail());

        return tPersonInfoDo;
    }
}
