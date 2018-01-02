package com.baofu.international.global.account.core.biz.task;

import com.baofu.global.cross.border.logback.ex.constant.MDCPropertyConsts;
import com.baofu.international.global.account.core.biz.LockBiz;
import com.baofu.international.global.account.core.biz.UserWithdrawBiz;
import com.baofu.international.global.account.core.biz.UserWithdrawOrderBiz;
import com.baofu.international.global.account.core.biz.impl.ChannelRouteBizImpl;
import com.baofu.international.global.account.core.biz.models.BatchWithdrawBo;
import com.baofu.international.global.account.core.biz.models.ChannelRouteBo;
import com.baofu.international.global.account.core.common.constant.Constants;
import com.baofu.international.global.account.core.common.enums.TransferStateEnum;
import com.baofu.international.global.account.core.common.enums.WithdrawStateEnum;
import com.baofu.international.global.account.core.dal.model.UserWithdrawApplyDo;
import com.baofu.international.global.account.core.manager.UserWithdrawApplyManager;
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
 * 功能：用户提现创建订单
 * User: feng_jiang Date:2017/11/21 ProjectName: globalaccount-core Version: 1.0
 **/
@Slf4j
@Service
public class UserWithdrawOrderListener implements MessageListener {

    /**
     * 用户前台提现服务
     */
    @Autowired
    private UserWithdrawOrderBiz userWithdrawOrderBiz;

    /**
     * 用户前台提现服务
     */
    @Autowired
    private UserWithdrawBiz userWithdrawBiz;

    /**
     * redis锁服务
     */
    @Autowired
    private LockBiz lockBiz;

    /**
     * 用户提前申请表操作类
     */
    @Autowired
    private UserWithdrawApplyManager withdrawApplyManager;

    /**
     * 渠道路由
     */
    @Autowired
    private ChannelRouteBizImpl channelRouteBiz;

    /**
     * 用户提现创建订单（GLOBAL_USER_CREATE_ORDER_QUEUE_NAME）队列
     * 解析excel文件
     *
     * @param message 队列消息
     */
    @Override
    public void onMessage(Message message) {
        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, UUID.randomUUID().toString());
        String key = Constants.GLOBAL_ACCOUNT_WITHDRAW_CASH;
        Boolean withdrawFlag = true;
        BatchWithdrawBo batchWithdrawBo = null;
        try {
            //队列消息
            String msgText = new String(message.getBody(), StandardCharsets.UTF_8);
            //队列名称
            String queueName = message.getMessageProperties().getReceivedRoutingKey();
            log.info("call  用户提现创建订单处理 ，消费者：QUEUE_NAME ——> {},内容 ——> {}", queueName, msgText);
            batchWithdrawBo = JsonUtil.toObject(msgText, BatchWithdrawBo.class);
            log.info("call  用户提现创建订单处理，消费者，内容解析对象内容 ——> {}", batchWithdrawBo);
            key = key + batchWithdrawBo.getUserNo() + ":" + batchWithdrawBo.getWithdrawCcy()
                    + ":" + batchWithdrawBo.getAccountNo();
            //提现创建订单
            userWithdrawOrderBiz.createWithdrawFileBatch(batchWithdrawBo);

            //渠道路由获取
            ChannelRouteBo channelRouteBo = channelRouteBiz.channelRouteQuery(batchWithdrawBo.getWithdrawCcy());
            if (!channelRouteBo.getLinkedModel()) {
                lockBiz.unLock(key);
                log.info("渠道为线下模式渠道，开户不发送渠道，参数信息：{}", channelRouteBo);
                return;
            }
            //发送渠道转账通知
            userWithdrawBiz.sendCgwTransferApply(batchWithdrawBo.getUserNo(), batchWithdrawBo.getAccountNo(),
                    batchWithdrawBo.getBatchNo());
        } catch (Exception e) {
            withdrawFlag = false;
            log.error("用户提现处理失败, 释放锁, key：{}", key);
            lockBiz.unLock(key);
            log.error("用户提现处理失败 --> Exception:{}", e);
        } finally {
            if (!withdrawFlag && batchWithdrawBo != null) {
                //更新提现申请订单状态为失败
                updateWithdrawOrderFail(batchWithdrawBo.getBatchNo());
            }
        }
    }

    /**
     * 更新提现申请订单状态为失败
     *
     * @param withdrawId 提现申请编号
     */
    private void updateWithdrawOrderFail(Long withdrawId) {
        try {

            UserWithdrawApplyDo modifyDo = new UserWithdrawApplyDo();
            modifyDo.setWithdrawId(withdrawId);
            modifyDo.setWithdrawState(WithdrawStateEnum.WITHDRAW_FAIL.getCode());
            modifyDo.setTransferState(TransferStateEnum.TRANSFER_ACCOUNTS_FAIL.getCode());
            withdrawApplyManager.modifyWithdrawApplyDo(modifyDo);
        } catch (Exception e) {
            log.error("更新提现申请订单状态异常:", e);
        }
    }
}
