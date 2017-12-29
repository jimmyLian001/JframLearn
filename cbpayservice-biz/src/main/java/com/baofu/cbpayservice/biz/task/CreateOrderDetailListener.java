package com.baofu.cbpayservice.biz.task;

import com.baofu.cbpayservice.biz.CreateRemitFileBiz;
import com.baofu.cbpayservice.biz.models.CreateOrderDetailBo;
import com.baofu.cbpayservice.common.constants.Constants;
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
 * 批量汇款-创建批量汇款明细文件 监听
 * <p>
 * User: 不良人 Date:2017/1/4 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
@Service
public class CreateOrderDetailListener implements MessageListener {

    /**
     * 创建汇款文件服务
     */
    @Autowired
    private CreateRemitFileBiz createRemitFileBiz;

    /**
     * 创建批量汇款明细文件队列（CREATE_BATCH_REMIT_DETAIL_QUEUE_NAME）
     *
     * @param message 队列消息
     */
    @Override
    public void onMessage(Message message) {

        try {
            //队列消息
            String msgText = new String(message.getBody(), Constants.UTF8);
            //队列名称
            String queueName = message.getMessageProperties().getReceivedRoutingKey();
            log.info("[创建汇款明细文件] 消费者：QUEUE_NAME:{},内容:{}", queueName, msgText);
            CreateOrderDetailBo detailBo = JsonUtil.toObject(msgText, CreateOrderDetailBo.class);
            MDC.put(SystemMarker.TRACE_LOG_ID, detailBo.getTraceLogId());
            log.info("[创建汇款明细文件] 消费者，内容解析对象:{}", detailBo);

            switch (Integer.parseInt(detailBo.getCareerType())) {
                case 1://电商
                    createRemitFileBiz.electronicCommerce(detailBo);
                     break;
                case 2://机票
                    createRemitFileBiz.airTickets(detailBo);
                    break;
                case 3://留学
                    createRemitFileBiz.studyAbroad(detailBo);
                    break;
                case 4://酒店
                    createRemitFileBiz.hotel(detailBo);
                    break;
                case 5://旅游
                    createRemitFileBiz.tourism(detailBo);
                    break;
            }
        } catch (Exception e) {
            log.error("[创建汇款明细文件] 异常信息", e);
        }
    }
}
