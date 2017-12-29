package com.baofu.cbpayservice.biz.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baofu.cbpayservice.biz.CbPayNotifySettleBiz;
import com.baofu.cbpayservice.manager.CmdManager;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.slf4j.helpers.SystemMarker;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * 通知清算MQ消息监听
 * User: wanght Date:2017/02/27 ProjectName: cbpay-service Version: 1.0
 */
@Slf4j
@Service
public class CbPayNotifySettleListener implements MessageListener {

    /**
     * 通知清算处理服务Service
     */
    @Autowired
    private CbPayNotifySettleBiz cbPayNotifySettleBiz;

    /**
     * 异步组件业务处理接口
     */
    @Autowired
    private CmdManager cmdManager;

    /**
     * 监听MQ获取消息
     *
     * @param message MQ获取到的消息
     */
    public void onMessage(Message message) {
        String batchNo;
        try {
            MDC.put(SystemMarker.TRACE_LOG_ID, UUID.randomUUID().toString());

            // 模拟接收消息
            String msgText = new String(message.getBody());
            String binding = message.getMessageProperties().getReceivedRoutingKey();
            log.info("收到通知清算MQ，队列名称：{}，消息内容：{}", binding, msgText);

            JSONObject jsonObject = JSON.parseObject(msgText);
            batchNo = jsonObject.getString("batchNo");
            log.info("收到{}消息转换对象之后参数：{}", binding, batchNo);
            cbPayNotifySettleBiz.notifySettle(batchNo, jsonObject.getString("updateBy"));
        } catch (Exception e) {
            log.error("收到通知清算MQ，处理消息失败 Exception:{}", e);
        }

    }
}
