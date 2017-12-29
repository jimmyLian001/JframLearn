package com.baofu.cbpayservice.biz.task;

import com.baofu.cbpayservice.biz.CbPaySettleBiz;
import com.baofu.cbpayservice.biz.models.SettleFileUploadReqBo;
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
 * 代理报关消费者
 * <p>
 * </p>
 * User: lian zd Date:2017/7/25 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
@Service
public class SettleFileVerifyProcessListener implements MessageListener {

    /**
     * 结汇操作业务逻辑实现接口
     */
    @Autowired
    private CbPaySettleBiz cbPaySettleBiz;

    /**
     * 结汇文件处理队列（CBPAY_SETTLE_FILE_TEST_AND_VERIFY_PROCESS_QUEUE_NAME）队列
     * 解析excel文件
     *
     * @param message 队列消息
     */
    @Override
    public void onMessage(Message message) {

        MDC.put(SystemMarker.TRACE_LOG_ID, UUID.randomUUID().toString());
        try {

            //队列消息
            String msgText = new String(message.getBody(), "UTF-8");
            //队列名称
            String queueName = message.getMessageProperties().getReceivedRoutingKey();
            log.info("call  明细校验工具结汇文件处理 ，消费者：QUEUE_NAME ——> {},内容 ——> {}", queueName, msgText);
            SettleFileUploadReqBo settleFileUploadReqBo = JsonUtil.toObject(msgText, SettleFileUploadReqBo.class);
            log.info("call  明细校验工具结汇文件处理，消费者，内容解析对象内容 ——> {}", settleFileUploadReqBo);

            cbPaySettleBiz.settleFileVerifyProcess(settleFileUploadReqBo);

        } catch (Exception e) {
            log.error("明细校验工具结汇文件处理，更新文件批次失败 ——> Exception:{}", e);
        }
    }
}
