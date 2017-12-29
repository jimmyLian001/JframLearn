package com.baofu.cbpayservice.biz.task;

import com.baofu.cbpayservice.biz.FileProcessBiz;
import com.baofu.cbpayservice.biz.convert.ProxyCustomConvert;
import com.baofu.cbpayservice.biz.models.ProxyCustomsMqBo;
import com.baofu.cbpayservice.common.constants.Constants;
import com.baofu.cbpayservice.common.enums.UploadFileStatus;
import com.baofu.cbpayservice.dal.models.FiCbPayFileUploadDo;
import com.baofu.cbpayservice.manager.ProxyCustomsManager;
import com.baofu.cbpayservice.manager.RedisManager;
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
 * 代理报关消费者
 * <p>
 * </p>
 * User: 不良人 Date:2017/1/4 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
@Service
public class ProxyCustomsListener implements MessageListener {

    /**
     * 汇款文件处理服务
     */
    @Autowired
    private FileProcessBiz fileProcessBiz;

    /**
     * 代理上报manager服务
     */
    @Autowired
    private ProxyCustomsManager proxyCustomsManager;

    /**
     * 缓存服务
     */
    @Autowired
    private RedisManager redisManager;

    /**
     * 代理报关消费者（CBPAY_PROXY_CUSTOMS_QUEUE_NAME）队列
     * 解析excel文件
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
        log.info("call 代理跨境结算消费队列，QUEUE_NAME：{},内容：{}", queueName, msgText);
        ProxyCustomsMqBo proxyCustomsMqBo = JsonUtil.toObject(msgText, ProxyCustomsMqBo.class);
        log.info("call 代理跨境结算消费队列内容解析对象内容：{}", proxyCustomsMqBo);

        try {

            //获取excel文件流
            List<Object[]> list = ProxyCustomConvert.getCommandResDTO(proxyCustomsMqBo);
            fileProcessBiz.process(proxyCustomsMqBo, list);
        } catch (Exception e) {
            try {
                //更新批次文件错误信息DFSFileId
                FiCbPayFileUploadDo fiCbpayFileUploadDo = new FiCbPayFileUploadDo();
                fiCbpayFileUploadDo.setFileBatchNo(proxyCustomsMqBo.getFileBatchNo());
                fiCbpayFileUploadDo.setStatus(UploadFileStatus.NO_PASS.getCode());
                proxyCustomsManager.updateFilestatus(fiCbpayFileUploadDo);
                log.error("代理报关消费队列，处理消息失败 :{}", e);
            } catch (Exception exception) {
                log.error("更新文件批次失败:{}", exception);
            }
        } finally {
            redisManager.insertObject(1.00, proxyCustomsMqBo.getFileBatchNo().toString(), Constants.FILE_CHECK_SCHEDULE_TIME_OUT);
        }
    }
}
