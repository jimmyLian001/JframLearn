package com.baofu.cbpayservice.biz.task;

import com.baofoo.dfs.client.DfsClient;
import com.baofoo.dfs.client.model.CommandResDTO;
import com.baofu.cbpayservice.biz.convert.SettleEmailConvert;
import com.baofu.cbpayservice.biz.impl.EmailSendServiceImpl;
import com.baofu.cbpayservice.biz.models.SendEmailServiceBo;
import com.baofu.cbpayservice.common.constants.Constants;
import com.system.commons.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.slf4j.helpers.SystemMarker;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.UUID;

/**
 * 发送邮件 监听
 * <p>
 * User: 不良人 Date:2017/1/4 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
@Service
public class SendEmailListener implements MessageListener {

    /**
     * 发送邮件服务
     */
    @Autowired
    private EmailSendServiceImpl emailSendServiceImpl;

    /**
     * 发送邮件附件下载地址
     */
    @Value("${email.attachment.down}")
    private String sendEmailAttachmentPath;

    /**
     * 发送邮件服务队列（CBPAY_SEND_EMAIL_QUEUE_NAME）队列
     *
     * @param message 队列消息
     */
    @Override
    public void onMessage(Message message) {

        MDC.put(SystemMarker.TRACE_LOG_ID, UUID.randomUUID().toString());

        try {
            //队列消息
            String msgText = new String(message.getBody(), Constants.UTF8);

            //队列名称
            String queueName = message.getMessageProperties().getReceivedRoutingKey();
            log.info("call  发送邮件 ，消费者：QUEUE_NAME ——> {},内容 ——> {}", queueName, msgText);
            SendEmailServiceBo sendEmailServiceBo = JsonUtil.toObject(msgText, SendEmailServiceBo.class);
            log.info("call  发送邮件，消费者，内容解析对象 ——> {}", sendEmailServiceBo);

            //处理附件
            File file = null;
            if (sendEmailServiceBo.getFileId() != null) {
                CommandResDTO resDTO = SettleEmailConvert.getCommandResDTO(sendEmailServiceBo.getFileId());
                String filePath = sendEmailAttachmentPath + "//" + resDTO.getFileName();
                DfsClient.download(resDTO.getDfsPath(), filePath);
                file = new File(filePath);

            }

            Boolean flag = emailSendServiceImpl.sendMsg(sendEmailServiceBo.getMailContent(), sendEmailServiceBo.getMailAddressTO(),
                    sendEmailServiceBo.getMailAddressCC(), sendEmailServiceBo.getMailAddressBCC(), file, sendEmailServiceBo.getSubject(),
                    sendEmailServiceBo.getUserName(), sendEmailServiceBo.getUserPassword(),
                    sendEmailServiceBo.getEmailHosts());

            if (file != null) {
                file.delete();
            }

            if (flag) {
                log.info("call 发送邮件成功，收件人:{}", sendEmailServiceBo.getMailAddressTO());
            }

        } catch (Exception e) {
            log.error("call 发送邮件，异常信息 Exception:{}", e);
        }
    }
}
