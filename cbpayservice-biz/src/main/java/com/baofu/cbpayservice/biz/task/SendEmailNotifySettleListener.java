package com.baofu.cbpayservice.biz.task;

import com.baofu.cbpayservice.biz.CbPayEmailBiz;
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
 * 跨境人民币邮件通知清算消息队列
 * <p>
 * User: wanght Date:2017/5/15 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
@Service
public class SendEmailNotifySettleListener implements MessageListener {

    /**
     * 跨境人民币邮件处理service
     */
    @Autowired
    private CbPayEmailBiz cbPayEmailBiz;

    /**
     * 收件人
     */
    @Value("${settle.mail.to.receiver}")
    private String emailToReceiver;

    /**
     * 抄送人
     */
    @Value("${settle.mail.cc.receiver}")
    private String emailCcReceiver;

    /**
     * 密送人
     */
    @Value("${settle.mail.bcc.receiver}")
    private String emailBCCReceiver;

    /**
     * 跨境人民币邮件通知清算消息队列
     *
     * @param message 队列消息
     */
    @Override
    public void onMessage(Message message) {
        try {
            MDC.put(SystemMarker.TRACE_LOG_ID, UUID.randomUUID().toString());

            // 接收消息
            String msgText = new String(message.getBody());
            String binding = message.getMessageProperties().getReceivedRoutingKey();

            log.info("收到邮件通知清算消息队列MQ，队列名称：{}，消息内容：{}", binding, msgText);
            cbPayEmailBiz.sendEmailNotifySettle(msgText, emailToReceiver, emailCcReceiver, emailBCCReceiver);
        } catch (Exception e) {
            log.error("收到邮件通知清算消息，Exception:{}", e);
        }
    }
}
