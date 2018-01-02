package com.baofu.international.global.account.core.biz.task;

import com.baofoo.cbcgw.facade.dto.gw.response.CgwReceiptAndDisbursementRespDto;
import com.baofu.global.cross.border.logback.ex.constant.MDCPropertyConsts;
import com.baofu.international.global.account.core.biz.UserAccountPaymentDetailBiz;
import com.baofu.international.global.account.core.biz.convert.PaymentDetailBizConvert;
import com.baofu.international.global.account.core.biz.external.UserAccountBizImpl;
import com.baofu.international.global.account.core.biz.models.AccountDetailBo;
import com.baofu.international.global.account.core.common.constant.ConfigDict;
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
public class PaymentDetailListener implements MessageListener {

    /**
     * 收款账户收支明细 Biz
     */
    @Autowired
    private UserAccountPaymentDetailBiz userAccountPaymentDetailBiz;

    /**
     * 用户账户相关服务
     */
    @Autowired
    private UserAccountBizImpl userAccountBiz;

    /**
     * 配置中心
     */
    @Autowired
    private ConfigDict configDict;

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
            log.info("call 同步收款账户收支明细，队列名称：{}，消息内容：{}", binding, msgText);
            CgwReceiptAndDisbursementRespDto respDto = JsonUtil.toObject(msgText, CgwReceiptAndDisbursementRespDto.class);
            log.info("call 同步收款账户收支明细，解析内容={}", respDto);
            UserPayeeAccountDo userPayeeAccountDo = userAccountBiz.queryUserAccountByWalletId(respDto.getUserId(),
                    Long.valueOf(configDict.getWyreChannelId()));

            AccountDetailBo accountDetailBo = PaymentDetailBizConvert.paramConvert(respDto);
            accountDetailBo.setAccountNo(userPayeeAccountDo.getAccountNo());
            userAccountPaymentDetailBiz.dealPayeePaymentDetail(accountDetailBo);
        } catch (Exception e) {
            log.error("同步收款账户收支明细异常：{}", e);
        }
    }
}
