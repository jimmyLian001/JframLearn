package com.baofu.international.global.account.core.biz.external;

import com.baofoo.Response;
import com.baofoo.sms.facade.SmsFacade;
import com.baofoo.sms.facade.model.SmsReqDTO;
import com.baofu.international.global.account.core.common.constant.CommonDict;
import com.baofu.international.global.account.core.common.constant.ConfigDict;
import com.baofu.international.global.account.core.common.enums.ErrorCodeEnum;
import com.baofu.international.global.account.core.common.enums.SystemEnum;
import com.google.common.collect.Lists;
import com.system.commons.exception.BizServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 手机短信服务
 * <p>
 * 1、发送手机短信信息
 * </p>
 *
 * @author 香克斯
 * @version : 1.0
 * @date : 2017/11/29
 */
@Slf4j
@Component
public class SmsSendBizImpl {

    /**
     * 短信服务
     */
    @Autowired
    private SmsFacade smsFacade;

    /**
     * 配置中心
     */
    @Autowired
    private ConfigDict configDict;

    /**
     * 发送手机短信信息
     *
     * @param mobiles 手机号码集合
     * @param content 短信内容
     * @return 返回是否发送成功标识
     */
    public Boolean sendSms(List<String> mobiles, String content) {

        SmsReqDTO smsReqDTO = new SmsReqDTO();
        smsReqDTO.setUserName(configDict.getSmsUserName());
        smsReqDTO.setUserPass(configDict.getSmsUserPass());
        smsReqDTO.setDomain(SystemEnum.SYSTEM_NAME.getCode());
        smsReqDTO.setContent(content);
        smsReqDTO.setMobile(StringUtils.join(mobiles.toArray(), CommonDict.SPLIT_FLAG_COMMA));
        log.info("发送手机短信内容：{}", smsReqDTO);
        Response<Integer> response = smsFacade.sendSms(smsReqDTO);
        log.info("发送手机短信返回信息：{}", response);
        if (response == null || !response.isSuccess() || response.getResult() < 1) {
            log.error("发送手机短信信息失败，手机号码：{}", mobiles);
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190003);
        }
        return Boolean.TRUE;
    }

    /**
     * 发送短信信息
     *
     * @param mobile  单个手机号码
     * @param content 短信内容
     * @return 返回是否发送成功标识
     */
    public Boolean sendSms(String mobile, String content) {

        return sendSms(Lists.newArrayList(mobile), content);
    }
}
