package com.baofu.international.global.account.core.service.convert;

import com.baofu.international.global.account.core.biz.models.UserRegisterTelInfoBo;
import com.baofu.international.global.account.core.facade.model.UserRegisterTelInfoDto;

/**
 * ${title}
 * <p>
 * User: 蒋文哲 Date: 2017/11/6 Version: 1.0
 * </p>
 */
public class UserRegisterTelInfoConvert {
    private UserRegisterTelInfoConvert() {

    }

    /**
     * 转换
     *
     * @param userRegisterTelInfoBo UserRegisterTelInfoBo
     * @return UserRegisterTelInfoDto
     */
    public static UserRegisterTelInfoDto convert(UserRegisterTelInfoBo userRegisterTelInfoBo) {
        if (userRegisterTelInfoBo == null) {
            return null;
        }
        UserRegisterTelInfoDto userRegisterTelInfoDto = new UserRegisterTelInfoDto();
        userRegisterTelInfoDto.setAfterFixPhoneNumber(userRegisterTelInfoBo.getAfterFixPhoneNumber());
        userRegisterTelInfoDto.setAnswer(userRegisterTelInfoBo.getAnswer());
        userRegisterTelInfoDto.setCurrentPhoneNumber(userRegisterTelInfoBo.getCurrentPhoneNumber());
        userRegisterTelInfoDto.setQuestionNo(userRegisterTelInfoBo.getQuestionNo());
        userRegisterTelInfoDto.setQuestionSequence(userRegisterTelInfoBo.getQuestionSequence());
        userRegisterTelInfoDto.setUserNo(userRegisterTelInfoBo.getUserNo());
        return userRegisterTelInfoDto;
    }
}
