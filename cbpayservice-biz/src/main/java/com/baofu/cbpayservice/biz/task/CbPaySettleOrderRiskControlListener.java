package com.baofu.cbpayservice.biz.task;

import com.baofu.cbpayservice.biz.CbpayOrderRiskControlBiz;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.slf4j.helpers.SystemMarker;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * 结汇订单风控MQ
 * <p>
 * Created by 莫小阳 on 2017/7/20.
 */

@Slf4j
@Service
public class CbPaySettleOrderRiskControlListener implements MessageListener {

    /**
     * 订单风控服务
     */
    @Autowired
    private CbpayOrderRiskControlBiz cbpayOrderRiskControlBiz;

    /**
     * 监听MQ获取消息
     *
     * @param message MQ获取到的消息
     */
    @Override
    public void onMessage(Message message) {

        try {
            MDC.put(SystemMarker.TRACE_LOG_ID, UUID.randomUUID().toString());
            // 接收消息
            String msgText = new String(message.getBody());
            String binding = message.getMessageProperties().getReceivedRoutingKey();
            log.info("call 结汇 收到跨境订单风控请求MQ，队列名称：{}，消息内容：{}", binding, msgText);
            String replace = msgText.replace("\"", "").replace("[", "").
                    replace("]", "");
            String[] strings = replace.split(",");
            List<String> batchNos = Arrays.asList(strings);
            cbpayOrderRiskControlBiz.handelSettleOrderRiskControl(batchNos);
        } catch (Exception e) {
            log.error("处理跨境订单风控请求发生异常：{}", e);
        }
    }
}
