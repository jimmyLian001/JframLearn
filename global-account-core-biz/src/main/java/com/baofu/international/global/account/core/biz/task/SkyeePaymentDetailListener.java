package com.baofu.international.global.account.core.biz.task;

import com.baofu.global.cross.border.logback.ex.constant.MDCPropertyConsts;
import com.baofu.international.global.account.core.biz.UserAccountPaymentDetailBiz;
import com.baofu.international.global.account.core.biz.convert.PaymentDetailBizConvert;
import com.baofu.international.global.account.core.biz.models.AccountDetailBo;
import com.baofu.international.global.account.core.biz.models.SkyeePaymentDetailBo;
import com.baofu.international.global.account.core.dal.mapper.UserAccountMapper;
import com.baofu.international.global.account.core.dal.model.UserPayeeAccountDo;
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
 * 描述：收款账户收支明细队列
 * <p>
 * User: feng_jiang Date:2017/11/5 ProjectName: globalaccount-core Version: 1.0
 */
@Slf4j
@Service
public class SkyeePaymentDetailListener implements MessageListener {

    /**
     * 收款账户收支明细 Biz
     */
    @Autowired
    private UserAccountPaymentDetailBiz userAccountPaymentDetailBiz;

    /**
     *收款賬戶信息操作
     */
    @Autowired
    private UserAccountMapper userAccountMapper;

    /**
     * 收款账户收支明细MQ队列消息处理
     *
     * @param message 消息内容
     */
    @Override
    public void onMessage(Message message) {
        try {
            MDC.put(MDCPropertyConsts.TRACE_LOG_ID, UUID.randomUUID().toString());
            String msgText = new String(message.getBody(), StandardCharsets.UTF_8);
            String binding = message.getMessageProperties().getReceivedRoutingKey();
            log.info("call Skyee同步收款账户收支明细，队列名称：{}，消息内容：{}", binding, msgText);
            SkyeePaymentDetailBo skyeePaymentDetailBo = JsonUtil.toObject(msgText, SkyeePaymentDetailBo.class);
            log.info("call Skyee同步收款账户收支明细，解析内容={}", skyeePaymentDetailBo);
            UserPayeeAccountDo userPayeeAccountDo = userAccountMapper.selectUserAccount(skyeePaymentDetailBo.getSellerId(),
                    skyeePaymentDetailBo.getBankCardNo());

            AccountDetailBo accountDetailBo = PaymentDetailBizConvert.paramConvert(skyeePaymentDetailBo, userPayeeAccountDo);
            userAccountPaymentDetailBiz.dealPayeePaymentDetail(accountDetailBo);
        } catch (Exception e) {
            log.error("同步收款账户收支明细异常：{}", e);
        }
    }
}
