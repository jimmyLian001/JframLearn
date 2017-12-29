package com.baofu.cbpayservice.biz.task;

import com.baofu.cbpayservice.biz.CbPaySettleBiz;
import com.baofu.cbpayservice.biz.models.SolutionPayApplyMqBo;
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
 * 解付申请 监听
 * <p>
 * User: 不良人 Date:2017/1/4 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
@Service
public class SolutionPayApplyListener implements MessageListener {

    /**
     * 结汇服务
     */
    @Autowired
    private CbPaySettleBiz cbPaySettleBiz;

    /**
     * 解付申请服务队列（CBPAY_SOLUTION_PAY_APPLE_QUEUE_NAME）队列
     *
     * @param message 队列消息
     */
    @Override
    public void onMessage(Message message) {

        MDC.put(SystemMarker.TRACE_LOG_ID, UUID.randomUUID().toString());
        //队列消息
        String msgText = new String(message.getBody());

        //队列名称
        String queueName = message.getMessageProperties().getReceivedRoutingKey();
        log.info("call  解付申请队列，消费者：QUEUE_NAME ——> {},内容 ——> {}", queueName, msgText);
        SolutionPayApplyMqBo solutionPayApplyMqBo = JsonUtil.toObject(msgText, SolutionPayApplyMqBo.class);
        log.info("call  解付申请队列，消费者，内容解析对象 ——> {}", solutionPayApplyMqBo);

        try {
            cbPaySettleBiz.solutionPayApple(solutionPayApplyMqBo);
        } catch (Exception e) {
            log.error("call 解付申请队列，消费者，异常信息 ——> Exception", e);
        }
    }
}
