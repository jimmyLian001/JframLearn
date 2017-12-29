package com.baofu.cbpayservice.biz.task;

import com.baofu.cbpayservice.biz.SettleNotifyMemberBiz;
import com.baofu.cbpayservice.biz.SettleQueryBiz;
import com.baofu.cbpayservice.biz.convert.SettleApplyConvert;
import com.baofu.cbpayservice.biz.models.SettleNotifyBo;
import com.baofu.cbpayservice.biz.models.SettleNotifyMemberBo;
import com.baofu.cbpayservice.biz.models.SettleQueryReqParamBo;
import com.baofu.cbpayservice.dal.mapper.FiCbPaySettleApplyMapper;
import com.baofu.cbpayservice.dal.models.FiCbPaySettleApplyDo;
import com.system.commons.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.MDC;
import org.slf4j.helpers.SystemMarker;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * 通知MQ消息监听
 * <p>
 * 1，通知MQ消息监听
 * </p>
 * User: 康志光 Date:2017年07月20日 ProjectName: cbayservice Version: 1.0.0
 */
@Slf4j
@Service
public class SettlementAsyncNotifyListener implements MessageListener {

    /**
     * 结汇通知商户处理
     */
    @Autowired
    private SettleQueryBiz settleQueryBiz;

    /**
     * 结汇通知商户处理
     */
    @Autowired
    private SettleNotifyMemberBiz settleNotifyMemberBiz;

    /**
     * 商户汇入申请
     */
    @Autowired
    private FiCbPaySettleApplyMapper fiCbPaySettleApplyMapper;

    /**
     * 监听MQ获取消息
     *
     * @param message MQ获取到的消息
     */
    public void onMessage(Message message) {

        try {

            MDC.put(SystemMarker.TRACE_LOG_ID, UUID.randomUUID().toString());
            // 模拟接收消息
            String msgText = new String(message.getBody());
            String binding = message.getMessageProperties().getReceivedRoutingKey();
            log.info("收到结汇异步通知商户MQ，队列名称：{}，消息内容：{}", binding, msgText);
            SettleQueryReqParamBo settleQueryReqParamBo = JsonUtil.toObject(msgText, SettleQueryReqParamBo.class);

            SettleNotifyMemberBo settleNotifyBo = settleQueryBiz.querySettleNotify(settleQueryReqParamBo);
            log.info("参数转换结果为：{}", settleNotifyBo);
            FiCbPaySettleApplyDo settleApply = fiCbPaySettleApplyMapper.queryByParams(settleNotifyBo.getMemberId(),
                    settleNotifyBo.getRemitReqNo());
            if (StringUtils.isBlank(settleApply.getNotifyUrl())) {
                log.warn("商户通知地址信息为空，结束结汇通知流程，商户申请信息：{}", settleApply);
                return;
            }
            //通知商户信息
            settleNotifyMemberBiz.httpNotify(settleNotifyBo, SettleApplyConvert.paramConvert(settleApply));
        } catch (Exception e) {
            log.error("结汇异步通知MQ，处理消息失败 Exception{}:", e);
        }

    }

}
