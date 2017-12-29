package com.baofu.cbpayservice.biz.task;

import com.baofu.cbpayservice.biz.CbPayOrderNotifyBiz;
import com.system.commons.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * 海关网关消费者
 * <p>
 * User: wdj Date:2017/1/4 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
@Service
public class HaiGuanNotifyListener implements MessageListener {

    @Autowired
    private CbPayOrderNotifyBiz cbPayOrderNotifyBiz;

    @Override
    public void onMessage(Message message) {

        try {
            // 模拟接收消息
            String msgText = new String(message.getBody());
            String binding = message.getMessageProperties().getReceivedRoutingKey();
            log.info("海关网关消费者 HaiGuanNotifyListener.onMessage ：QUEUE_NAME ——> {},内容 ——> {}", binding, ": " + msgText);
            HashMap<String, String> paraMap = JsonUtil.toObject(msgText, HashMap.class);
            Long orderId = Long.parseLong(paraMap.get("orderId"));
            String adminStatus = paraMap.get("adminStatus");

            //根据宝付订单号修改报关状态
            cbPayOrderNotifyBiz.modifyStateByOrderId(orderId, adminStatus);

        } catch (Exception e) {
            log.info("海关同步跨境网关报关状态失败 HaiGuanNotifyListener.onMessage");
        }

    }
}
