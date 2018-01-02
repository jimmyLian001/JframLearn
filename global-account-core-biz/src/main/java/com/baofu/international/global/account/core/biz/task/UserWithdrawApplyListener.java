package com.baofu.international.global.account.core.biz.task;

import com.baofu.global.cross.border.logback.ex.constant.MDCPropertyConsts;
import com.baofu.international.global.account.core.biz.UserWithdrawBiz;
import com.baofu.international.global.account.core.biz.models.UserWithdrawCashBo;
import com.system.commons.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * 功能：用户提现申请监听
 * User: feng_jiang Date:2017/11/21 ProjectName: globalaccount-core Version: 1.0
 **/
@Slf4j
@Service
public class UserWithdrawApplyListener implements MessageListener {

    /**
     * 用户前台提现服务
     */
    @Autowired
    private UserWithdrawBiz userWithdrawBiz;

    /**
     * 提现文件处理队列（GLOBAL_USER_WITHDRAW_APPLY_QUEUE_NAME）队列
     * 解析excel文件
     *
     * @param message 队列消息
     */
    @Override
    public void onMessage(Message message) {
        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, UUID.randomUUID().toString());
        try {
            //队列消息
            String msgText = new String(message.getBody(), StandardCharsets.UTF_8);
            //队列名称
            String queueName = message.getMessageProperties().getReceivedRoutingKey();
            log.info("call  用户提现处理 ，消费者：QUEUE_NAME ——> {},内容 ——> {}", queueName, msgText);
            UserWithdrawCashBo userWithdrawCashBo = JsonUtil.toObject(msgText, UserWithdrawCashBo.class);
            log.info("call  用户提现处理，消费者，内容解析对象内容 ——> {}", userWithdrawCashBo);
            //提现处理
            userWithdrawBiz.userWithdrawCash(userWithdrawCashBo);
        } catch (Exception e) {
            log.error("用户提现处理，提现失败--> Exception:{}", e);
        }
    }
}
