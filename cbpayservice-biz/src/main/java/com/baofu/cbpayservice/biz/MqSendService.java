package com.baofu.cbpayservice.biz;

import com.baofu.cbpayservice.common.enums.MqSendQueueNameEnum;

/**
 * MQ消息发送服务接口
 * <p>
 * 1、发送MQ消息
 * </p>
 * User: 香克斯 Date:2016/9/24 ProjectName: asias-icpservice Version: 1.0
 */
public interface MqSendService {

    /**
     * 发送MQ消息
     *
     * @param mqSendQueueNameEnum 消息队列名称
     * @param object              发送消息对象，以JSON形式发送
     */
    void sendMessage(MqSendQueueNameEnum mqSendQueueNameEnum, Object object);
}
