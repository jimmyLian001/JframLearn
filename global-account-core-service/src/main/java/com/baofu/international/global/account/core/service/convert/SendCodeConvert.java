package com.baofu.international.global.account.core.service.convert;

import com.baofu.international.global.account.core.biz.models.SendCodeReqBo;
import com.baofu.international.global.account.core.biz.models.SendCodeRespBo;
import com.baofu.international.global.account.core.facade.model.SendCodeReqDto;
import com.baofu.international.global.account.core.facade.model.SendCodeRespDto;

/**
 * description:
 * <p/>
 * Created by liy on 2017/11/6 0006 ProjectName：account-core
 */
public class SendCodeConvert {

    private SendCodeConvert() {
    }

    /**
     * DTO --》 BO
     *
     * @param dto SendCodeReqDto
     * @return 结果
     */
    public static SendCodeReqBo convert(SendCodeReqDto dto) {

        SendCodeReqBo bo = new SendCodeReqBo();
        bo.setParam(dto.getParam());
        bo.setContent(dto.getContent());
        bo.setRedisKey(dto.getRedisKey());
        bo.setTimeOut(dto.getTimeOut());
        bo.setSubject(dto.getEmailTitle());
        return bo;
    }

    public static SendCodeRespDto convert(SendCodeRespBo sendCodeRespBo) {
        if (sendCodeRespBo == null) {
            return null;
        }
        SendCodeRespDto sendCodeRespDto = new SendCodeRespDto();
        sendCodeRespDto.setSendAddress(sendCodeRespBo.getSendAddress());
        sendCodeRespDto.setSendFlag(sendCodeRespBo.getSendFlag());
        sendCodeRespDto.setAddressType(sendCodeRespBo.getAddressType());
        sendCodeRespDto.setErrorMsg(sendCodeRespBo.getErrorMsg());
        return sendCodeRespDto;
    }

}
