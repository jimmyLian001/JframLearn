package com.baofu.cbpayservice.biz.task;

import com.baofu.cbpayservice.biz.CbPaySettleBiz;
import com.baofu.cbpayservice.biz.models.CbPaySettleConfirmReqBo;
import com.system.commons.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.slf4j.helpers.SystemMarker;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * 结汇申请确认监听
 * <p>
 * 1，结汇申请确认处理
 * </p>
 * User: 康志光 Date: 2017/4/14 ProjectName: cbpay-customs-service Version: 1.0
 */
@Slf4j
@Service
public class CbPaySettleConfirmListener implements MessageListener {

    @Autowired
    private CbPaySettleBiz cbPaySettleBizImpl;

    /**
     * 监听MQ获取消息
     *
     * @param message MQ获取到的消息
     */
    public void onMessage(Message message) {
        try {
            MDC.put(SystemMarker.TRACE_LOG_ID, UUID.randomUUID().toString());
            String msgText = new String(message.getBody());
            String binding = message.getMessageProperties().getReceivedRoutingKey();
            log.info("call 结汇申请确认-->收到结汇申请确认MQ通知，队列名称：{}，消息内容：{}", binding, msgText);
            CbPaySettleConfirmReqBo cbPaySettleReqBo = JsonUtil.toObject(msgText, CbPaySettleConfirmReqBo.class);
            cbPaySettleBizImpl.settleConfirm(cbPaySettleReqBo);
        } catch (Exception e) {
            log.error("call 结汇申请确认-->收到结汇申请确认MQ通知，处理消息失败 Exception:{}", e);
        }
    }
}
