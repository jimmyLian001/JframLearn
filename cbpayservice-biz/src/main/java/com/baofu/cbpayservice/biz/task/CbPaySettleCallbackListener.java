package com.baofu.cbpayservice.biz.task;

import com.baofoo.cbcgw.facade.dto.gw.response.result.CgwSettleResultDto;
import com.baofu.cbpayservice.biz.CbPaySettleBiz;
import com.baofu.cbpayservice.biz.RedisBiz;
import com.baofu.cbpayservice.common.constants.Constants;
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
 * 结汇申请结果银行渠道响应通知
 * <p>
 * 1，结汇申请结果银行渠道响应通知
 * </p>
 * User: 康志光 Date: 2017/4/14 ProjectName: cbpay-customs-service Version: 1.0
 */
@Slf4j
@Service
public class CbPaySettleCallbackListener implements MessageListener {


    @Autowired
    private CbPaySettleBiz cbPaySettleBizImpl;

    /**
     * redis服务
     */
    @Autowired
    private RedisBiz redisBiz;
    /**
     * 监听MQ获取消息
     *
     * @param message MQ获取到的消息
     */
    @Override
    public void onMessage(Message message) {
        String remSerialNo = null;
        try {
            MDC.put(SystemMarker.TRACE_LOG_ID, UUID.randomUUID().toString());
            // 模拟接收消息
            String msgText = new String(message.getBody());
            String binding = message.getMessageProperties().getReceivedRoutingKey();
            log.info("call 结汇申请渠道响应（2）-->收到结汇申请银行渠道响应MQ通知，队列名称：{}，消息内容：{}", binding, msgText);
            CgwSettleResultDto cgwSettleResultRespDto = JsonUtil.toObject(msgText, CgwSettleResultDto.class);
            remSerialNo = cgwSettleResultRespDto.getRemSerialNo();
            //结汇处理加锁
            Boolean isLock = redisBiz.lock(Constants.PREPAYMENT_PROCESS_FLAG + cgwSettleResultRespDto.getRemSerialNo());
            log.info("结汇处理完成,进行加锁：{}", isLock);

            cbPaySettleBizImpl.settleSecondCallback(cgwSettleResultRespDto);
        } catch (Exception e) {
            log.error("call 结汇申请渠道响应（2）-->结汇申请渠道响应（2）收到结汇申请银行渠道响应MQ通知，处理消息失败 Exception:{}", e);
        } finally {
            log.info("结汇处理完成,进行解锁");
            redisBiz.unLock(Constants.PREPAYMENT_PROCESS_FLAG + remSerialNo);
        }
    }
}
