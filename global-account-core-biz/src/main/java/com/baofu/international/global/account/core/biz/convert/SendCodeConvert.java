package com.baofu.international.global.account.core.biz.convert;

import com.baofu.international.global.account.core.biz.models.SendCodeRespBo;
import org.apache.commons.lang3.StringUtils;


/**
 * 发送验证码转换
 * <p>
 * 1 , 发送验证码转换
 * <p>
 *
 * @author : wuyazi
 * @date: 2017/11/04
 * @version: 1.0.0
 */
public final class SendCodeConvert {

    private SendCodeConvert() {
    }

    /**
     * 封装发送响应
     *
     * @param sendAddress 发送地址
     * @param errorMsg    发送失败信息
     * @return 发送响应
     */
    public static SendCodeRespBo convert(String sendAddress, String errorMsg) {
        SendCodeRespBo sendCodeRespBo = new SendCodeRespBo();
        sendCodeRespBo.setSendFlag(StringUtils.isNotBlank(sendAddress));
        sendCodeRespBo.setSendAddress(sendAddress);
        sendCodeRespBo.setErrorMsg(errorMsg);
        return sendCodeRespBo;
    }

}