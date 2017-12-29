package com.baofu.cbpayservice.biz.task;

import com.baofu.cbpayservice.biz.CbPayRemitFileProcessBiz;
import com.baofu.cbpayservice.biz.RedisBiz;
import com.baofu.cbpayservice.biz.models.ProxyCustomsMqBo;
import com.baofu.cbpayservice.common.constants.Constants;
import com.baofu.cbpayservice.common.enums.ErrorCodeEnum;
import com.system.commons.exception.BizServiceException;
import com.system.commons.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.slf4j.helpers.SystemMarker;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * 代理报关批量文件上传消费者
 * <p>监听MQ CBPAY_PROXY_CUSTOMS_SERIES_QUEUE_NAME
 * </p>
 * User: lian zd Date:2017/10/25 ProjectName: cbpayservice Version:1.0
 */
@Slf4j
@Service
public class ProxyCustomsBatchListener implements MessageListener {

    /**
     * 跨境汇款文件校验解析处理
     */
    @Autowired
    private CbPayRemitFileProcessBiz cbPayRemitFileProcessBiz;

    /**
     * redis缓存服务
     */
    @Autowired
    private RedisBiz redisBiz;

    /**
     * 代理报关批量文件上传消费者（CBPAY_PROXY_CUSTOMS_SERIES_QUEUE_NAME）队列
     * 解析excel文件
     *
     * @param message 队列消息
     */
    @Override
    public void onMessage(Message message) {

        MDC.put(SystemMarker.TRACE_LOG_ID, UUID.randomUUID().toString());
        //队列消息
        String msgText = new String(message.getBody());
        //队列名称
        String queueName = message.getMessageProperties().getReceivedRoutingKey();
        log.info("call 代理报关批量文件上传消费队列，QUEUE_NAME：{},内容：{}", queueName, msgText);
        List proxyCustomsMqBoList = JsonUtil.toObject(msgText, List.class);
        log.info("call 代理报关批量文件上传消费队列内容解析对象内容：{}", proxyCustomsMqBoList);

        try {
            Long memberId = JsonUtil.toObject(proxyCustomsMqBoList.get(0).toString(), ProxyCustomsMqBo.class).getMemberId();
            if (memberId == null) {
                log.info("call 代理报关跨境汇款批量文件上传校验解析消费对象参数异常");
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00160);
            }
            //判断商户跨境汇款文件明细校验操作是否锁住
            boolean lockFlag = redisBiz.isLock(Constants.REMITTANCE_FILE_PROCESSING_FLAG.concat(String.valueOf(memberId)));
            if (lockFlag) {
                log.info("call 商户{}跨境汇款明细文件上传已锁定，请勿频繁操作！", memberId);
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00219);
            }

            boolean lock = redisBiz.lock(Constants.REMITTANCE_FILE_PROCESSING_FLAG.concat(String.valueOf(memberId)));
            if (lock) {
                log.info("call 代理跨境成功锁住商户跨境汇款文件上传校验解析功能");
                //获取excel文件流并对汇款文件进行校验
                for (Object proxyCustomsMqBo : proxyCustomsMqBoList) {
                    ProxyCustomsMqBo proxyCustomsMqBo1 = JsonUtil.toObject(proxyCustomsMqBo.toString(), ProxyCustomsMqBo.class);
                    log.info("跨境汇款批量上传文件校验解析开始···，dfsFileId：{}", proxyCustomsMqBo1.getDfsFileId());
                    cbPayRemitFileProcessBiz.remitFileProcess(proxyCustomsMqBo1);
                }
            }
            redisBiz.unLock(Constants.REMITTANCE_FILE_PROCESSING_FLAG.concat(String.valueOf(memberId)));
            log.info("代理报关批量上传文件全部处理完毕，代理跨境汇款文件上传校验解析成功解锁商户");
        } catch (Exception e) {
            log.error("代理报关批量文件上传消费队列，处理消息失败 :{}", e);
        }
    }
}
