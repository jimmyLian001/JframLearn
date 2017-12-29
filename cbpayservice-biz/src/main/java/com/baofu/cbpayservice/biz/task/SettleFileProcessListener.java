package com.baofu.cbpayservice.biz.task;

import com.baofu.cbpayservice.biz.CbPaySettleBiz;
import com.baofu.cbpayservice.biz.RedisBiz;
import com.baofu.cbpayservice.biz.models.SettleFileUploadReqBo;
import com.baofu.cbpayservice.common.constants.Constants;
import com.baofu.cbpayservice.common.constants.NumberConstants;
import com.baofu.cbpayservice.common.enums.MatchingStatusEnum;
import com.baofu.cbpayservice.common.enums.SettleFileStatusEnum;
import com.baofu.cbpayservice.common.enums.UploadFileStatus;
import com.baofu.cbpayservice.dal.mapper.FiCbPaySettleMapper;
import com.baofu.cbpayservice.dal.mapper.FiCbpayFileUploadMapper;
import com.baofu.cbpayservice.dal.models.FiCbPayFileUploadDo;
import com.baofu.cbpayservice.dal.models.FiCbPaySettleDo;
import com.system.commons.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.slf4j.helpers.SystemMarker;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 代理报关消费者
 * <p>
 * User: 不良人 Date:2017/1/4 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
@Service
public class SettleFileProcessListener implements MessageListener {

    /**
     * 结汇操作业务逻辑实现接口
     */
    @Autowired
    private CbPaySettleBiz cbPaySettleBiz;

    /**
     * 结汇操作接口
     */
    @Autowired
    private FiCbPaySettleMapper fiCbPaySettleMapper;

    /**
     * 文件操作服务
     */
    @Autowired
    private FiCbpayFileUploadMapper fiCbpayFileUploadMapper;

    @Autowired
    private RedisBiz redisBiz;

    /**
     * 结汇文件处理队列（CBPAY_PROXY_CUSTOMS_QUEUE_NAME）队列
     * 解析excel文件
     *
     * @param message 队列消息
     */
    @Override
    public void onMessage(Message message) {

        SettleFileUploadReqBo settleFileUploadReqBo = null;
        try {
            //队列消息
            String msgText = new String(message.getBody(), "UTF-8");
            //队列名称
            String queueName = message.getMessageProperties().getReceivedRoutingKey();
            log.info("call  结汇文件处理 ，消费者：QUEUE_NAME ——> {},内容 ——> {}", queueName, msgText);
            settleFileUploadReqBo = JsonUtil.toObject(msgText, SettleFileUploadReqBo.class);
            MDC.put(SystemMarker.TRACE_LOG_ID, settleFileUploadReqBo.getTraceLogId());
            log.info("call  结汇文件处理，消费者，内容解析对象内容 ——> {}", settleFileUploadReqBo);

            cbPaySettleBiz.settleFileProcess(settleFileUploadReqBo);
        } catch (Exception e) {
            log.error("结汇文件处理消费队列，处理消息失败:", e);
            if (NumberConstants.TWELVE != settleFileUploadReqBo.getFlag()) {
                //更新文件状态为处理失败
                modifyFileProcessState(settleFileUploadReqBo);
            }
        } finally {
            //处理完文件后，解锁商户   匹配成功
            if (settleFileUploadReqBo.getFlag() != null &&
                    MatchingStatusEnum.YES_MATCH.getCode() == settleFileUploadReqBo.getFlag()) {
                log.info("call 匹配成功，文件处理完后，解锁商户");
                redisBiz.unLock(Constants.SETTLE_OPERATION_FLAG.concat(String.valueOf(settleFileUploadReqBo.getMemberId())));
                log.info("call 结汇文件处理完成，解锁商户成功。");
            }
        }
    }

    private void modifyFileProcessState(SettleFileUploadReqBo settleFileUploadReqBo) {
        try {
            FiCbPayFileUploadDo fiCbpayFileUploadDo = new FiCbPayFileUploadDo();
            fiCbpayFileUploadDo.setFileBatchNo(settleFileUploadReqBo.getFileBatchNo());
            fiCbpayFileUploadDo.setStatus(UploadFileStatus.NO_PASS.getCode());
            fiCbpayFileUploadMapper.updateByPrimaryKeySelective(fiCbpayFileUploadDo);
            FiCbPaySettleDo fiCbPaySettleDo = new FiCbPaySettleDo();
            fiCbPaySettleDo.setOrderId(settleFileUploadReqBo.getSettleOrderId());
            fiCbPaySettleDo.setFileBatchNo(settleFileUploadReqBo.getFileBatchNo());
            fiCbPaySettleDo.setFileStatus(SettleFileStatusEnum.UPLOAD_FAIL.getCode());
            fiCbPaySettleMapper.updateByKeySelective(fiCbPaySettleDo);
        } catch (Exception exception) {
            log.error("结汇文件处理异常，更新文件批次失败：", exception);
        }
    }
}
