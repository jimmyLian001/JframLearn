package com.baofu.cbpayservice.biz.task;

import com.baofu.cbpayservice.biz.CbPayOrderNotifyBiz;
import com.baofu.cbpayservice.common.enums.SystemEnum;
import com.system.commons.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.slf4j.helpers.SystemMarker;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * 通知商户MQ消息监听
 * User: 香克斯 Date:2016/10/28 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
@Service
public class CbPayNotifyMemberListener implements MessageListener {

    /**
     * 通知商户处理服务Service
     */
    @Autowired
    private CbPayOrderNotifyBiz cbPayOrderNotifyBiz;

    /**
     * 最大通知商户次数，超过次数后将不在通知
     */
    @Value("${NOTIFY_MEMBER_COUNT}")
    private Long notifyMemberCount;

    /**
     * 监听MQ获取消息
     *
     * @param message MQ获取到的消息
     */
    public void onMessage(Message message) {
        Long orderId = 0L;
        try {
            MDC.put(SystemMarker.TRACE_LOG_ID, UUID.randomUUID().toString());

            // 模拟接收消息
            String msgText = new String(message.getBody());
            String binding = message.getMessageProperties().getReceivedRoutingKey();
            log.info("收到通知商户MQ，队列名称：{}，消息内容：{}", binding, msgText);

            orderId = JsonUtil.toObject(msgText, Long.class);

            log.info("收到{}消息转换对象之后参数：{}", binding, orderId);
            cbPayOrderNotifyBiz.notifyMember(orderId, SystemEnum.SYSTEM.getCode());
        } catch (Exception e) {
            log.error("收到通知商户MQ，处理消息失败 Exception:{}", e);
        }

    }
}
