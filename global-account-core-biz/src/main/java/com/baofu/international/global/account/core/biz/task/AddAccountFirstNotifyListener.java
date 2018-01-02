package com.baofu.international.global.account.core.biz.task;

import com.baofoo.cbcgw.facade.dto.gw.response.CgwLookUserRespDto;
import com.baofu.global.cross.border.logback.ex.constant.MDCPropertyConsts;
import com.baofu.international.global.account.core.biz.ChannelNotifyApplyStatusBiz;
import com.baofu.international.global.account.core.biz.convert.PayeeAccountConvert;
import com.system.commons.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * 收款账户开户第一次MQ消息监听
 * <p>
 * User: yangjian  Date: 2017-11-07 ProjectName:  Version: 1.0
 */
@Slf4j
@Service
public class AddAccountFirstNotifyListener implements MessageListener {

    /**
     *
     */
    @Autowired
    private ChannelNotifyApplyStatusBiz chNotifyStatusBiz;

    /**
     * 监听MQ获取消息
     *
     * @param message MQ获取到的消息
     */
    @Override
    public void onMessage(Message message) {

        try {
            MDC.put(MDCPropertyConsts.TRACE_LOG_ID, UUID.randomUUID().toString());

            String msgText = new String(message.getBody(), StandardCharsets.UTF_8);
            String binding = message.getMessageProperties().getReceivedRoutingKey();
            log.info("收到收款账户开户渠道受理第一次异步通知MQ，队列名称：{}，消息内容：{}", binding, msgText);
            CgwLookUserRespDto cgwLookUserResp = JsonUtil.toObject(msgText, CgwLookUserRespDto.class);
            log.info("收到{}消息转换对象之后参数：{}", binding, cgwLookUserResp);
            chNotifyStatusBiz.addApplyAccountInfo(PayeeAccountConvert.paramConvert(cgwLookUserResp));

        } catch (Exception e) {
            log.error("收到收款账户开户渠道受理第一次异步通知MQ，处理消息失败 Exception:{}", e);
        }
    }
}
