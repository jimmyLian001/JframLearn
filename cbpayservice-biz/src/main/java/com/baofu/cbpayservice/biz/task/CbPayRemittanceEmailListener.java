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
 * 邮件MQ消息监听
 * <p>
 * 1、付汇成功发送明细邮件
 * </p>
 * User: wanght Date:2017/04/06 ProjectName: cbpay-service Version: 1.0
 */
@Slf4j
@Service
public class CbPayRemittanceEmailListener implements MessageListener {

    /**
     * 跨境人民币邮件处理service
     */
    @Autowired
    private CbPayEmailBiz cbPayEmailBiz;

    /**
     * 下载文件地址
     */
    @Value("${remit_download_detail}")
    private String downloadDetail;

    /**
     * 邮箱Host
     */
    @Value("${mail.to.reciver}")
    private String emailToReceiver;

    /**
     * 邮箱Host
     */
    @Value("${mail.cc.reciver}")
    private String emailCcReceiver;

    /**
     * 监听MQ获取消息
     *
     * @param message MQ获取到的消息
     */
    public void onMessage(Message message) {
        try {
            MDC.put(SystemMarker.TRACE_LOG_ID, UUID.randomUUID().toString());

            // 接收消息
            String msgText = new String(message.getBody());
            String binding = message.getMessageProperties().getReceivedRoutingKey();

            log.info("收到付汇成功发送明细MQ，队列名称：{}，消息内容：{}", binding, msgText);
            cbPayEmailBiz.sendUploadFile(msgText, downloadDetail, emailToReceiver, emailCcReceiver);
        } catch (Exception e) {
            log.error("收到付汇成功发送明细MQ，处理消息失败 Exception:{}", e);
        }
    }
}
