package com.baofu.cbpayservice.biz.task;

import com.baofoo.cbcgw.facade.dto.gw.response.result.CgwRemitResultDto;
import com.baofu.cbpayservice.biz.CbPayRemittanceOrderNotifyBiz;
import com.baofu.cbpayservice.common.enums.SystemEnum;
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
 * 渠道第二次MQ通知消息监听
 * User: wanght Date:2016/10/28 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
@Service
public class CbPayNotifyRemittanceListener implements MessageListener {

    /**
     * 渠道MQ通知处理服务Service
     */
    @Autowired
    private CbPayRemittanceOrderNotifyBiz cbPayRemittanceOrderNotifyBiz;

    /**
     * 监听MQ获取消息
     *
     * @param message MQ获取到的消息
     */
    public void onMessage(Message message) {
        try {
            MDC.put(SystemMarker.TRACE_LOG_ID, UUID.randomUUID().toString());

            // 模拟接收消息
            String msgText = new String(message.getBody());
            String binding = message.getMessageProperties().getReceivedRoutingKey();
            log.info("收到渠道第二次MQ通知，队列名称：{}，消息内容：{}", binding, msgText);

            // 获取报文内容
            CgwRemitResultDto cgwRemitBatchResultDto = JsonUtil.toObject(msgText, CgwRemitResultDto.class);

            log.info("收到{}消息转换对象之后参数：{}", binding, cgwRemitBatchResultDto);
            cbPayRemittanceOrderNotifyBiz.receiveNotify(cgwRemitBatchResultDto, SystemEnum.SYSTEM.getCode());
        } catch (Exception e) {
            log.error("收到渠道第二次MQ通知，处理消息失败 Exception:{}", e);
        }

    }
}
