package com.baofu.cbpayservice.biz.task;

import com.baofoo.cbcgw.facade.dto.gw.response.result.CgwReceiptResultDto;
import com.baofu.cbpayservice.biz.CbPaySettleBiz;
import com.baofu.cbpayservice.biz.convert.CbPaySettleConvert;
import com.baofu.cbpayservice.biz.models.SettleMoneyToAccountListenerBo;
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
 * 商户汇款到宝付备付金账户通知
 * <p>
 * User: 不良人 Date:2017/4/13 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
@Service
public class SettleMoneyToAccountListener implements MessageListener {

    /**
     * 结汇操作业务逻辑实现接口
     */
    @Autowired
    private CbPaySettleBiz cbPaySettleBiz;

    @Override
    public void onMessage(Message message) {

        MDC.put(SystemMarker.TRACE_LOG_ID, UUID.randomUUID().toString());
        //队列消息
        String msgText = new String(message.getBody());
        //队列名称
        String queueName = message.getMessageProperties().getReceivedRoutingKey();

        try {
            log.info("call 商户汇款到宝付备付金账户通知 消费者 ：QUEUE_NAME ——> {},内容 ——> {}", queueName, msgText);
            CgwReceiptResultDto cgwReceiptResultRespDto = JsonUtil.toObject(msgText, CgwReceiptResultDto.class);
            log.info("call 商户汇款到宝付备付金账户通知解析对象内容 ——> {}", cgwReceiptResultRespDto);

            SettleMoneyToAccountListenerBo sMTAListenerBo = CbPaySettleConvert.toSettleMoneyToAccountListenerBo(cgwReceiptResultRespDto);

            cbPaySettleBiz.settleMoneyToAccount(sMTAListenerBo);
        } catch (Exception e) {
            log.error("商户汇款到宝付备付金账户通知 异常Exception——> {}", e);
        }

    }

}
