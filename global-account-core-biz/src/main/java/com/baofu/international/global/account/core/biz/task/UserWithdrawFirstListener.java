package com.baofu.international.global.account.core.biz.task;

import com.baofoo.cbcgw.facade.dto.gw.response.CgwTransferRespDto;
import com.baofu.global.cross.border.logback.ex.constant.MDCPropertyConsts;
import com.baofu.international.global.account.core.biz.UserWithdrawCgwDealBiz;
import com.baofu.international.global.account.core.common.enums.ErrorCodeEnum;
import com.baofu.international.global.account.core.common.enums.TransferTypeEnum;
import com.system.commons.exception.BizServiceException;
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
 * 功能： 转账渠道第一个通知
 * User: feng_jiang Date:2017/11/5 ProjectName: globalaccount-core Version: 1.0
 **/
@Slf4j
@Service
public class UserWithdrawFirstListener implements MessageListener {

    /**
     * 用户转账&宝付转账至备付金 渠道第一次异步通知(
     */
    @Autowired
    private UserWithdrawCgwDealBiz userWithdrawCgwDealBiz;

    /**
     * wyre 转账渠道第一个通知处理
     *
     * @param message mq通知内容
     */
    @Override
    public void onMessage(Message message) {
        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, UUID.randomUUID().toString());
        try {
            String msgText = new String(message.getBody(), StandardCharsets.UTF_8);
            String queueName = message.getMessageProperties().getReceivedRoutingKey();
            log.info("call  转账渠道第一个通知 ，消费者：QUEUE_NAME ——> {},内容 ——> {}", queueName, msgText);
            //参数转换
            CgwTransferRespDto transferRespDto = JsonUtil.toObject(msgText, CgwTransferRespDto.class);
            log.info("call  转账渠道第一个通知，消费者，内容解析对象内容 ——> {}", transferRespDto);

            if (TransferTypeEnum.ACCOUNT_TO_ACCOUNT.getCode() == transferRespDto.getTransferType()) {
                //转账
                userWithdrawCgwDealBiz.userWithdrawOneProcess(transferRespDto);
            } else if (TransferTypeEnum.ACCOUNT_TO_BANK.getCode() == transferRespDto.getTransferType()) {
                //宝付转账
                userWithdrawCgwDealBiz.userWithdrawMergeOneProcess(transferRespDto);
            } else {
                log.info("call 渠道第一次异步通知,渠道通业务类型不正确：{}", transferRespDto.getTransferType());
                throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190021);
            }

        } catch (Exception e) {
            log.info("call wyre 转账渠道第一次通知处理异常--> Exception:{}", e);
        }
    }
}
