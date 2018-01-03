package com.baofu.international.global.account.client.service.convert;

import com.baofu.international.global.account.client.common.enums.CustomsStateEnum;
import com.baofu.international.global.account.client.service.models.PersonAuthReq;
import com.baofu.international.global.account.client.service.models.UserInfoBo;
import com.baofu.international.global.account.core.facade.model.UserLoginRespDTO;
import com.baofu.international.global.account.core.facade.model.UserPersonalReqDto;
import com.baofu.international.global.account.core.facade.model.user.UserPersonalDto;

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
     * @param personAuthReq  请求信息
     * @param idFrontDfsId   身份证正面dfsId
     * @param idReverseDfsId 身份证反面dfsID
     * @param authApplyNo    认证申请编号
     * @return 响应信息
     */
    public static UserPersonalReqDto addPersonInfoConvert(PersonAuthReq personAuthReq, Long idFrontDfsId, Long idReverseDfsId, Long authApplyNo) {

        UserPersonalReqDto userPersonalReqDto = new UserPersonalReqDto();
        userPersonalReqDto.setUserNo(personAuthReq.getUserNo());
        userPersonalReqDto.setIdNo(personAuthReq.getIdNo());
        userPersonalReqDto.setAddress(personAuthReq.getAddress());
        userPersonalReqDto.setArea(personAuthReq.getArea());
        userPersonalReqDto.setCity(personAuthReq.getCity());
        userPersonalReqDto.setEnName(personAuthReq.getEnglishName());
        userPersonalReqDto.setIdFrontDfsId(idFrontDfsId);
        userPersonalReqDto.setIdReverseDfsId(idReverseDfsId);
        userPersonalReqDto.setIdType(1);
        userPersonalReqDto.setPostCode(personAuthReq.getPostCode());
        userPersonalReqDto.setProvince(personAuthReq.getProvince());
        userPersonalReqDto.setState(CustomsStateEnum.NORMAL.getCode());
        userPersonalReqDto.setIdName(personAuthReq.getCardHolder());
        userPersonalReqDto.setBankCardNo(personAuthReq.getCardNo());
        userPersonalReqDto.setLoginNo(personAuthReq.getLoginNo());
        userPersonalReqDto.setEmail(personAuthReq.getEmail());
        userPersonalReqDto.setAuthApplyNo(authApplyNo);

        return userPersonalReqDto;
    }

    /**
     * 更新个人认证信息转换
     *
     * @param personAuthReq  请求信息
     * @param idFrontDfsId   身份证正面dfsId
     * @param idReverseDfsId 身份证反面dfsID
     * @return 响应信息
     */
    public static UserPersonalReqDto updatePersonInfoConvert(PersonAuthReq personAuthReq, Long idFrontDfsId, Long idReverseDfsId) {

        UserPersonalReqDto userPersonalReqDto = new UserPersonalReqDto();
        userPersonalReqDto.setUserInfoNo(personAuthReq.getUserInfoNo());
        userPersonalReqDto.setUserNo(personAuthReq.getUserNo());
        userPersonalReqDto.setIdNo(personAuthReq.getIdNo());
        userPersonalReqDto.setAddress(personAuthReq.getAddress());
        userPersonalReqDto.setArea(personAuthReq.getArea());
        userPersonalReqDto.setCity(personAuthReq.getCity());
        userPersonalReqDto.setEnName(personAuthReq.getEnglishName());
        userPersonalReqDto.setIdFrontDfsId(idFrontDfsId);
        userPersonalReqDto.setIdReverseDfsId(idReverseDfsId);
        userPersonalReqDto.setPostCode(personAuthReq.getPostCode());
        userPersonalReqDto.setProvince(personAuthReq.getProvince());
        userPersonalReqDto.setIdName(personAuthReq.getCardHolder());
        userPersonalReqDto.setBankCardNo(personAuthReq.getCardNo());
        userPersonalReqDto.setLoginNo(personAuthReq.getLoginNo());
        userPersonalReqDto.setEmail(personAuthReq.getEmail());

        return userPersonalReqDto;
    }

    /**
     * 个人用户的对象转换
     *
     * @param userInfoBo      返回参数
     * @param userPersonalDto 查询结果参数
     * @return 返回参数
     */
    public static void convertUserInfoBo(UserInfoBo userInfoBo, UserPersonalDto userPersonalDto) {
        userInfoBo.setName(userPersonalDto.getName());
        userInfoBo.setIdNo(userPersonalDto.getIdNo());
        userInfoBo.setEmail(userPersonalDto.getEmail());
        userInfoBo.setMobileNo(userPersonalDto.getPhoneNumber());
        userInfoBo.setRealnameStatus(userPersonalDto.getRealnameStatus());
    }

    /**
     * 个人用户的对象转换
     *
     * @param userLoginRespDTO 个人信息
     * @param userPersonalDto  查询结果参数
     * @return 返回参数
     */
    public static UserInfoBo convertUserInfoBo(UserLoginRespDTO userLoginRespDTO, UserPersonalDto userPersonalDto) {
        UserInfoBo userInfoBo = new UserInfoBo();
        userInfoBo.setLoginType(userLoginRespDTO.getLoginType());
        userInfoBo.setUserNo(userLoginRespDTO.getUserNo());
        userInfoBo.setLoginNo(userLoginRespDTO.getLoginNo());
        userInfoBo.setName(userPersonalDto.getName());
        userInfoBo.setIdNo(userPersonalDto.getIdNo());
        userInfoBo.setEmail(userPersonalDto.getEmail());
        userInfoBo.setMobileNo(userPersonalDto.getPhoneNumber());
        userInfoBo.setRealnameStatus(userPersonalDto.getRealnameStatus());
        userInfoBo.setUserInfoNo(userPersonalDto.getUserInfoNo());
        return userInfoBo;
    }

}
