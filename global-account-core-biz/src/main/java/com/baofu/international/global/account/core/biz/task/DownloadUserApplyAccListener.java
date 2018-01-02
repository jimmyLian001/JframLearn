package com.baofu.international.global.account.core.biz.task;

import com.baofu.global.cross.border.logback.ex.constant.MDCPropertyConsts;
import com.baofu.international.global.account.core.biz.DownloadUserApplyAccBiz;
import com.baofu.international.global.account.core.biz.models.UserApplyAccQueryReqBo;
import com.system.commons.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

/**
 * <p>
 * 1. 异步下载Skyee用户申请信息
 * </p>
 *
 * @author 莫小阳
 * @version 1.0.0
 * @date 2017/12/26 0026
 */
@Slf4j
@Service
public class DownloadUserApplyAccListener implements MessageListener {


    /**
     * 下载数据服务类
     */
    @Autowired
    private DownloadUserApplyAccBiz downloadUserApplyAccBiz;

    @Override
    public void onMessage(Message message) {
        try {
            MDC.put(MDCPropertyConsts.TRACE_LOG_ID, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
            String msgText = new String(message.getBody(), StandardCharsets.UTF_8);
            String binding = message.getMessageProperties().getReceivedRoutingKey();
            log.info("call 异步下载Skyee用户申请信息，队列名称：{}，消息内容：{}", binding, msgText);
            UserApplyAccQueryReqBo respDto = JsonUtil.toObject(msgText, UserApplyAccQueryReqBo.class);
            log.info("call 异步下载Skyee用户申请信息，解析内容={}", respDto);
            downloadUserApplyAccBiz.downloadUserApplyAcc(respDto);
        } catch (Exception e) {
            log.error("call 异步下载Skyee用户申请信息,异常信息：{}", e);
        }
    }
}
