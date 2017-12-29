package com.baofu.cbpayservice.biz.task;

import com.baofu.cbpayservice.biz.CbPayOrderRemittanceBiz;
import com.baofu.cbpayservice.biz.models.CbPaySumFileMqBo;
import com.baofu.cbpayservice.common.enums.UploadFileStatus;
import com.baofu.cbpayservice.dal.models.FiCbPayFileUploadDo;
import com.baofu.cbpayservice.manager.ProxyCustomsManager;
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
 * 提现订单文件处理队列
 * <p>
 * User: wanght Date:2017/5/11 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
@Service
public class OrderSumFileProcessListener implements MessageListener {
    /**
     * 代理上报manager服务
     */
    @Autowired
    private ProxyCustomsManager proxyCustomsManager;

    /**
     * 提现订单(非文件上传订单)
     */
    @Autowired
    private CbPayOrderRemittanceBiz cbPayOrderRemittanceBiz;

    @Override
    public void onMessage(Message message) {

        MDC.put(SystemMarker.TRACE_LOG_ID, UUID.randomUUID().toString());
        //队列消息
        String msgText = new String(message.getBody());

        //队列名称
        String queueName = message.getMessageProperties().getReceivedRoutingKey();
        log.info("call 根据时间提现订单消费队列 ：队列名称={},内容={}", queueName, msgText);
        CbPaySumFileMqBo cbPaySumFileMqBo = JsonUtil.toObject(msgText, CbPaySumFileMqBo.class);
        log.info("call 根据时间提现订单解析内容：{}", cbPaySumFileMqBo);

        try {
            cbPayOrderRemittanceBiz.fileProcessByTime(cbPaySumFileMqBo);
        } catch (Exception e) {
            try {
                //更新批次文件错误信息DFSFileId
                FiCbPayFileUploadDo fiCbpayFileUploadDo = new FiCbPayFileUploadDo();
                fiCbpayFileUploadDo.setFileBatchNo(cbPaySumFileMqBo.getFileBatchNo());
                fiCbpayFileUploadDo.setStatus(UploadFileStatus.NO_PASS.getCode());
                proxyCustomsManager.updateFilestatus(fiCbpayFileUploadDo);
                log.error("根据时间提现订单文件消费队列，处理消息失败", e);
            } catch (Exception e1) {
                log.error("根据时间更新文件批次失败", e1);
            }
        }
    }
}
