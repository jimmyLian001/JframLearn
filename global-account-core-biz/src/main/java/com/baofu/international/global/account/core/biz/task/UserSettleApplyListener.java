package com.baofu.international.global.account.core.biz.task;

import com.baofu.global.cross.border.logback.ex.constant.MDCPropertyConsts;
import com.baofu.international.global.account.core.biz.SettleFileProcessBiz;
import com.baofu.international.global.account.core.biz.UserWithdrawCbPayDealBiz;
import com.baofu.international.global.account.core.biz.models.UserSettleAppReqBo;
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
 * 描述：用户汇入汇款申请服务监听
 * <p>
 * User: feng_jiang Date:2017/11/14 ProjectName: globalaccount-core Version: 1.0
 */
@Slf4j
@Service
public class UserSettleApplyListener implements MessageListener {

    /**
     * 汇入汇款申请文件处理服务
     */
    @Autowired
    private SettleFileProcessBiz settleFileProcessBiz;

    /**
     * 用户提现与跨境API交互服务
     */
    @Autowired
    private UserWithdrawCbPayDealBiz userWithdrawCbPayDealBiz;

    /**
     * 描述：用户发起汇入汇款申请处理队列（GLOBAL_USER_SETTLE_APPLY_QUEUE_NAME）
     * 解析汇入汇款申请消息
     *
     * @param message 队列消息
     */
    @Override
    public void onMessage(Message message) {
        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, UUID.randomUUID().toString());
        long startTime = System.currentTimeMillis();
        try {
            String msgText = new String(message.getBody(), StandardCharsets.UTF_8);
            String binding = message.getMessageProperties().getReceivedRoutingKey();
            log.info("call 用户发起汇入汇款申请处理，队列名称：{}，消息内容：{}", binding, msgText);
            UserSettleAppReqBo appReqBo = JsonUtil.toObject(msgText, UserSettleAppReqBo.class);
            log.info("call 用户发起汇入汇款申请处理，消费者，内容解析对象内容 ——> {}", appReqBo);
            String fileName = settleFileProcessBiz.createSettleFile(appReqBo.getWithdrawBatchId());
            settleFileProcessBiz.uploadFtp(fileName);
            userWithdrawCbPayDealBiz.processSettleAPI(appReqBo.getWithdrawBatchId(), fileName);
        } catch (Exception e) {
            log.info("call 用户发起汇入汇款申请异常--> Exception:{}", e);
        }
        log.info("call 用户发起汇入汇款申请，处理总时长：{}", System.currentTimeMillis() - startTime);
    }
}
