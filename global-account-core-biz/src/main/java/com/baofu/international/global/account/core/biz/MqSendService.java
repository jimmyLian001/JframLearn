package com.baofu.international.global.account.core.biz;


import com.baofu.international.global.account.core.common.enums.MqSendQueueNameEnum;

/**
 * 功能：MQ消息发送服务
 * User: feng_jiang Date:2017/11/7 ProjectName: globalaccount-core Version: 1.0
 **/
public interface MqSendService {

    /**
     * 发送MQ消息
     *
     * @param mqSendQueueNameEnum 消息队列名称
     * @param object              发送消息对象，以JSON形式发送
     */
    void sendMessage(MqSendQueueNameEnum mqSendQueueNameEnum, Object object);
}
