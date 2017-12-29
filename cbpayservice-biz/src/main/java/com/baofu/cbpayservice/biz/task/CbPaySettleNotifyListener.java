package com.baofu.cbpayservice.biz.task;

import com.baofoo.cbcgw.facade.dto.gw.base.CgwBaseRespDto;
import com.baofu.cbpayservice.biz.CbPaySettleBiz;
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
 * 结汇申请发往银行通知
 * <p>
 * 1，结汇申请发往银行通知
 * </p>
 * User: 康志光 Date: 2017/4/14 ProjectName: cbpay-customs-service Version: 1.0
 */
@Slf4j
@Service
public class CbPaySettleNotifyListener implements MessageListener {

    @Autowired
    private CbPaySettleBiz cbPaySettleBiz;

    /**
     * 监听MQ获取消息
     *
     * @param message MQ获取到的消息
     */
    @Override
    public void onMessage(Message message) {
        try {
            MDC.put(SystemMarker.TRACE_LOG_ID, UUID.randomUUID().toString());
            String msgText = new String(message.getBody());
            String binding = message.getMessageProperties().getReceivedRoutingKey();
            log.info("call 结汇申请渠道响应（1）收到结汇申请发往银行MQ通知，队列名称：{}，消息内容：{}", binding, msgText);
            CgwBaseRespDto cgwBaseRespDto = JsonUtil.toObject(msgText, CgwBaseRespDto.class);

            cbPaySettleBiz.settleFirstCallback(cgwBaseRespDto);
        } catch (Exception e) {
            log.error("call 结汇申请渠道响应（1）收到结汇申请发往银行MQ通知，处理消息失败 Exception:{}", e);
        }
    }
}
