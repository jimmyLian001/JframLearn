package com.baofu.cbpayservice.biz.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baofu.cbpayservice.biz.CbPayRemitDocEmailBiz;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.slf4j.helpers.SystemMarker;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * 发送汇款凭证邮件MQ
 * <p>
 * User: wdj Date:2017/07/17 ProjectName: cbpay-service Version: 1.0
 */
@Slf4j
@Service
public class CbPayRemitDocEmailListener implements MessageListener {

    @Autowired
    private CbPayRemitDocEmailBiz cbPayRemitDocEmailBiz;

    @Override
    public void onMessage(Message message) {

        try {
            MDC.put(SystemMarker.TRACE_LOG_ID, UUID.randomUUID().toString());
            // 接收消息
            String msgText = new String(message.getBody());
            String binding = message.getMessageProperties().getReceivedRoutingKey();
            log.info("收到发送汇款凭证邮件MQ，队列名称：{}，消息内容：{}", binding, msgText);
            JSONObject jsonObject = JSON.parseObject(msgText);
            String batchNo = (String) jsonObject.get("batchNo");
            cbPayRemitDocEmailBiz.sendRemitDocEmail(batchNo);
        } catch (Exception e) {
            log.error("处理发送汇款凭证发生异常：{}", e);
        }
    }
}
