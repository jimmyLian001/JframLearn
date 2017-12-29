package com.baofu.cbpayservice.biz.task;

import com.baofoo.cbcgw.facade.dto.gw.response.result.CgwRelieveResultDto;
import com.baofu.cbpayservice.biz.CbPaySettleBiz;
import com.system.commons.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.slf4j.helpers.SystemMarker;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * 解付申请，渠道最终返回解付状态
 * <p>
 * User: 不良人 Date:2017/5/16 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
@Service
public class ReleveListener implements MessageListener {

    /**
     * 结汇操作业务逻辑实现接口
     */
    @Autowired
    private CbPaySettleBiz cbPaySettleBiz;

    /**
     * 解付申请，渠道最终返回解付状态
     *
     * @param message
     */
    @Override
    public void onMessage(Message message) {
        try {
            MDC.put(SystemMarker.TRACE_LOG_ID, UUID.randomUUID().toString());
            //队列消息
            String msgText = new String(message.getBody());
            //队列名称
            String queueName = message.getMessageProperties().getReceivedRoutingKey();
            log.info("call  解付申请，渠道最终返回解付状态消费队列 ：QUEUE_NAME ——> {},内容 ——> {}", queueName, msgText);
            CgwRelieveResultDto chRelieveBatchRespDto = JsonUtil.toObject(msgText, CgwRelieveResultDto.class);
            log.info("call  解付申请，渠道最终返回解付状态消费队列内容解析对象内容 ——> {}", chRelieveBatchRespDto);
            cbPaySettleBiz.releve(chRelieveBatchRespDto);
        } catch (Exception e) {
            log.error("call 解付申请，渠道最终返回解付状态消费队列异常", e);
        }
    }
}
