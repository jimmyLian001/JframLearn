package com.baofu.cbpayservice.biz.task;

import com.baofu.cbpayservice.biz.CbPayRemittanceBiz;
import com.baofu.cbpayservice.biz.NotifyBiz;
import com.baofu.cbpayservice.biz.convert.ApiCbPayRemitNotifyConvert;
import com.baofu.cbpayservice.biz.models.ApiRemitCbPayNotfiyBo;
import com.baofu.cbpayservice.biz.models.CbPayRemittanceAuditReqBo;
import com.baofu.cbpayservice.common.enums.RemitBusinessTypeEnum;
import com.baofu.cbpayservice.dal.models.FiCbPayMemberApiRqstDo;
import com.baofu.cbpayservice.dal.models.FiCbPayRemittanceAdditionDo;
import com.baofu.cbpayservice.dal.models.FiCbPayRemittanceDo;
import com.baofu.cbpayservice.manager.CbPayRemittanceManager;
import com.baofu.cbpayservice.manager.FiCbPayMemberApiRqstManager;
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
 * 审核汇款订单MQ消息监听
 * User: wanght Date:2016/12/14 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
@Service
public class CbPayAuditRemittanceListener implements MessageListener {

    /**
     * 跨境人民币创建汇款订单处理服务Service
     */
    @Autowired
    private CbPayRemittanceBiz cbPayRemittanceBiz;

    /**
     * 申请信息服务
     */
    @Autowired
    private FiCbPayMemberApiRqstManager fiCbPayMemberApiRqstManager;

    /**
     * 汇款订单信息服务
     */
    @Autowired
    private CbPayRemittanceManager cbPayRemittanceManager;

    /**
     * 商户异步通知服务
     */
    @Autowired
    private NotifyBiz notifyBiz;

    /**
     * 监听MQ获取消息
     *
     * @param message MQ获取到的消息
     */
    public void onMessage(Message message) {

        CbPayRemittanceAuditReqBo cbPayRemittanceReqBo = null;
        try {
            MDC.put(SystemMarker.TRACE_LOG_ID, UUID.randomUUID().toString());
            // 接收消息
            String msgText = new String(message.getBody());
            String binding = message.getMessageProperties().getReceivedRoutingKey();

            log.info("收到审核汇款订单MQ，队列名称：{}，消息内容：{}", binding, msgText);
            cbPayRemittanceReqBo = JsonUtil.toObject(msgText, CbPayRemittanceAuditReqBo.class);

            log.info("收到{}消息转换对象之后参数：{}", binding, cbPayRemittanceReqBo);
            cbPayRemittanceBiz.auditRemittanceOrder(cbPayRemittanceReqBo);
        } catch (Exception e) {
            log.error("收到审核汇款订单MQ，处理消息失败 Exception:{}", e);
            FiCbPayMemberApiRqstDo fiCbPayMemberApiRqstDo = fiCbPayMemberApiRqstManager.queryReqInfoByBussNo(cbPayRemittanceReqBo.getBatchNo());
            //判断是否需要通知商户
            if (fiCbPayMemberApiRqstDo != null) {
                log.error("call 异步通知商户开始···");
                FiCbPayRemittanceDo fiCbPayRemittanceDo = cbPayRemittanceManager.queryRemittanceOrder(cbPayRemittanceReqBo.getBatchNo());
                FiCbPayRemittanceAdditionDo fiCbPayRemittanceAdditionDo = cbPayRemittanceManager.queryRemittanceAddition(
                        cbPayRemittanceReqBo.getBatchNo(), fiCbPayRemittanceDo.getMemberNo());
                ApiRemitCbPayNotfiyBo apiRemitCbPayNotfiyBo = ApiCbPayRemitNotifyConvert.convertApiRemitCbPayNotfiyBo(
                        fiCbPayRemittanceDo, fiCbPayMemberApiRqstDo, fiCbPayRemittanceAdditionDo, 3, "审核汇款订单失败");
                notifyBiz.notifyMember(String.valueOf(fiCbPayMemberApiRqstDo.getMemberId()), String.valueOf(fiCbPayMemberApiRqstDo.getTerminalId()),
                        String.valueOf(fiCbPayMemberApiRqstDo.getNotifyUrl()), apiRemitCbPayNotfiyBo, fiCbPayMemberApiRqstDo.getMemberReqId(), RemitBusinessTypeEnum.REMIT_APPLY.getCode());
                log.error("call 异步通知商户结束···");
            }
        }
    }
}
