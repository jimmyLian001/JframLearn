package com.baofu.international.global.account.core.biz.impl;

import com.baofu.international.global.account.core.biz.MqSendService;
import com.baofu.international.global.account.core.common.enums.MqSendQueueNameEnum;
import com.system.commons.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MqSendServiceImpl implements MqSendService {

    /**
     * 发送消息队列服务接口
     */
    @Autowired(required = false)
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送MQ消息
     *
     * @param mqSendQueueNameEnum 消息队列名称
     * @param object              发送消息对象，以JSON形式发送
     */
    @Override
    public void sendMessage(MqSendQueueNameEnum mqSendQueueNameEnum, Object object) {

        String json = JsonUtil.toJSONString(object);
        log.info("准备发送MQ消息，消息队列：{},内容为：{}", mqSendQueueNameEnum.getCode(), json);
        rabbitTemplate.convertAndSend(mqSendQueueNameEnum.getCode(), json);
    }
}
