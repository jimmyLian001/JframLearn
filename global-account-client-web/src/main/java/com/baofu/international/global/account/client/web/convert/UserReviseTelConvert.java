package com.baofu.international.global.account.client.web.convert;

import com.baofu.international.global.account.client.web.models.FixPhoneNoApplyForm;
import com.baofu.international.global.account.client.web.models.SessionVo;
import com.baofu.international.global.account.core.facade.model.FixPhoneNoApplyDto;

/**
 * 手机号更改参数转换
 * <p>
 * </p>
 * User: lian zd  Date: 2017/11/16 ProjectName: globalaccount Version: 1.0
 */
public final class UserReviseTelConvert {

    private UserReviseTelConvert() {

    }

    /**
     * 手机号更改请求参数转换
     *
     * @param form 请求信息
     * @return 响应信息
     */
    public static FixPhoneNoApplyDto fixPhoneNoApplyDtoConvert(FixPhoneNoApplyForm form) {
        if (form == null) {
            return null;
        }
        FixPhoneNoApplyDto dto = new FixPhoneNoApplyDto();
        dto.setUserNo(form.getUserNo());
        dto.setCurrentPhoneNumber(form.getCurrentPhoneNumber());
        dto.setAfterFixPhoneNumber(form.getAfterFixPhoneNumber());
        return dto;
    }

    public static FixPhoneNoApplyDto fixPhoneNoApplyDtoConvert(String phoneNumber, String oldPhone, SessionVo sessionVo) {
        FixPhoneNoApplyDto dto = new FixPhoneNoApplyDto();
        dto.setUserNo(String.valueOf(sessionVo.getUserNo()));
        dto.setCurrentPhoneNumber(oldPhone);
        dto.setAfterFixPhoneNumber(phoneNumber);
        dto.setLoginType(sessionVo.getUserType());
        dto.setLoginNo(sessionVo.getLoginNo());
        return dto;
    }

}
