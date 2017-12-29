package com.baofu.cbpayservice.biz.task;

import com.baofu.cbpayservice.biz.BatchRemitBiz;
import com.baofu.cbpayservice.biz.CbPayCreateRemittanceOrderBiz;
import com.baofu.cbpayservice.biz.MqSendService;
import com.baofu.cbpayservice.biz.NotifyBiz;
import com.baofu.cbpayservice.biz.convert.ApiCbPayRemitNotifyConvert;
import com.baofu.cbpayservice.biz.convert.BatchRemitBizConvert;
import com.baofu.cbpayservice.biz.convert.ParamConvert;
import com.baofu.cbpayservice.biz.models.ApiRemitCbPayNotfiyBo;
import com.baofu.cbpayservice.biz.models.CbPayRemittanceReqBo;
import com.baofu.cbpayservice.common.constants.NumberConstants;
import com.baofu.cbpayservice.common.enums.ApplyStatus;
import com.baofu.cbpayservice.common.enums.MqSendQueueNameEnum;
import com.baofu.cbpayservice.common.enums.RemitBusinessTypeEnum;
import com.baofu.cbpayservice.common.enums.RemittanceStatus;
import com.baofu.cbpayservice.common.util.StringUtil;
import com.baofu.cbpayservice.manager.CbPayOrderManager;
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
 * 创建汇款订单MQ消息监听
 * User: wanght Date:2016/12/14 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
@Service
public class CbPayCreateRemittanceV2Listener implements MessageListener {

    /**
     * 跨境人民币创建汇款订单处理服务Service
     */
    @Autowired
    private CbPayCreateRemittanceOrderBiz cbPayRemittanceBiz;

    /**
     * 发送MQ服务
     */
    @Autowired
    private MqSendService mqSendService;

    /**
     * 商户通知服务
     */
    @Autowired
    private NotifyBiz notifyBiz;

    /**
     * 商户通知服务
     */
    @Autowired
    private CbPayOrderManager orderManager;

    /**
     * 批量汇款接口biz服务
     */
    @Autowired
    private BatchRemitBiz batchRemitBiz;

    /**
     * 监听MQ获取消息
     *
     * @param message MQ获取到的消息
     */
    public void onMessage(Message message) {

        CbPayRemittanceReqBo reqBo = null;
        try {
            MDC.put(SystemMarker.TRACE_LOG_ID, UUID.randomUUID().toString());

            // 接收消息
            String msgText = new String(message.getBody());
            String binding = message.getMessageProperties().getReceivedRoutingKey();

            log.info("收到创建汇款订单MQ，队列名称：{}，消息内容：{}", binding, msgText);
            reqBo = JsonUtil.toObject(msgText, CbPayRemittanceReqBo.class);
            log.info("收到{}消息转换对象之后参数：{}", binding, reqBo);

            Long orderId = cbPayRemittanceBiz.createRemittanceOrderV2(reqBo);
            if (orderId == null) {
                log.warn("call 创建汇款订单,审核订单为空");
                //批量汇款创建订单失败，更新订单为初始创建失败汇款订单
                if (reqBo.getSourceType() != null && reqBo.getSourceType() == NumberConstants.ONE) {
                    orderManager.updateOrderByFileBatchNo(RemittanceStatus.INIT.getCode(),
                            reqBo.getBatchFileIdList().get(NumberConstants.ZERO));
                    batchRemitBiz.createErrorRemitOrder(BatchRemitBizConvert.toBatchRemitBo(reqBo));
                }
                return;
            }

            //异步审核
            mqSendService.sendMessage(MqSendQueueNameEnum.CBPAY_AUDIT_REMITTANCE_QUEUE_NAME,
                    ParamConvert.auditParamConvert(String.valueOf(orderId), reqBo, ApplyStatus.TRUE.getCode(),
                            reqBo.getCreateBy()));
            log.info("发送自动审核MQ成功：{}", orderId);

        } catch (Exception e) {
            log.error("收到创建汇款订单MQ，处理消息失败 Exception:{}", e);
            //判断是否需要通知商户：API发起的需要通知商户
            if (!StringUtil.isBlank(reqBo.getRemitApplyNo())) {
                log.error("call API异步创建汇款订单异常,开始通知商户···");
                ApiRemitCbPayNotfiyBo apiRemitCbPayNotfiyBo = ApiCbPayRemitNotifyConvert.convertApiRemitCbPayNotfiyBo(
                        reqBo, 3, "创建汇款订单失败");
                notifyBiz.notifyMember(String.valueOf(reqBo.getMemberId()), String.valueOf(reqBo.getTerminalId()),
                        String.valueOf(reqBo.getNotifyUrl()), apiRemitCbPayNotfiyBo,
                        reqBo.getRemitApplyNo(), RemitBusinessTypeEnum.REMIT_APPLY.getCode());
                log.error("call API异步创建汇款订单异常,结束通知商户···");
            }
        }
    }
}
