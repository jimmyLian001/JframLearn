package com.baofu.cbpayservice.service.impl;

import com.baofoo.cbcgw.facade.api.gw.CgwTaskFacade;
import com.baofoo.cbcgw.facade.dto.gw.base.BaseRespDto;
import com.baofu.cbpayservice.biz.*;
import com.baofu.cbpayservice.common.constants.Constants;
import com.baofu.cbpayservice.common.enums.ChannelTypeEnum;
import com.baofu.cbpayservice.common.enums.Currency;
import com.baofu.cbpayservice.facade.CbPayJobFacade;
import com.baofu.cbpayservice.manager.ChannelGroupManager;
import com.baofu.cbpayservice.service.convert.CbPayJobConvert;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.slf4j.helpers.SystemMarker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 跨境人民币定时任务接口
 * <p>
 * 1、中行自动汇款审核任务
 * 2、中行自动查询结算订单任务
 * 3、平安自动汇款审核任务
 * 4、平安自动查询结算订单任务
 * 5、跨境订单风控定时任务  wdj
 * </p>
 * User: wanght Date:2016/12/29 ProjectName: cbpay-service Version: 1.0
 */
@Slf4j
@Service
public class CbPayJobServiceImpl implements CbPayJobFacade {

    /**
     * 跨境人民币汇款操作接口
     */
    @Autowired
    private CbPayAutoAuditBiz cbPayAutoAuditBiz;

    /**
     * 自动购汇接口
     */
    @Autowired
    private CbPayPurchaseBiz cbPayPurchaseBiz;

    /**
     * 自动查询汇率接口
     */
    @Autowired
    private CgwTaskFacade cgwTaskFacade;

    /**
     * 跨境订单风控接口  wdj
     */
    @Autowired
    private CbpayOrderRiskControlBiz cbpayOrderRiskControlBiz;

    /**
     * 渠道分组信息查询
     */
    @Autowired
    private ChannelGroupManager channelGroupManager;

    /**
     * 海关实名认证发送邮件服务类
     */
    @Autowired
    private CustomsSendEmailBiz customsSendEmailBiz;

    /**
     * 锁
     */
    @Autowired
    private LockBiz lockBiz;

    /**
     * 中行自动汇款审核任务
     */
    @Override
    public void bocAutoAuditJob() {
        log.info("call 跨境人民币自动汇款订单任务开始   ");
        List<Integer> groupIdList = channelGroupManager.queryGroupId(Currency.CNY.getCode(),
                ChannelTypeEnum.PURCHASE_PAYMENT.getCode());
        if (groupIdList == null || groupIdList.size() == 0) {
            log.error("未找到币种对应的分组：{}", Currency.CNY.getCode());
            return;
        }
        for (Integer groupId : groupIdList) {
            try {
                Long channelId = channelGroupManager.queryChannelId(groupId, Currency.CNY.getCode());
                log.info("跨境人民币渠道：{}自动付汇开始", channelId);
                cbPayAutoAuditBiz.doAutoAudit(channelId, "%Y-%m-%d 23:59:59");
            } catch (Exception e) {
                log.error("自动付汇异常", e);
            }
        }
        log.info("call 跨境人民币自动汇款订单任务结束  ");
    }

    /**
     * 自动购汇
     *
     * @param traceLogId 日志ID
     */
    @Override
    public void autoPurchase(Long channelId, String traceLogId) {
        log.info("call 跨境人民币自动购汇任务开始   ");
        try {
            MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
            log.info("请求参数，渠道id：{}", channelId);
            if (channelId == null) {
                log.error("渠道id为空");
                return;
            }
            cbPayPurchaseBiz.doAutoPurchase(channelId, "%Y-%m-%d 23:59:59");
        } catch (Exception e) {
            log.error("自动购汇异常", e);
        }
        log.info("call 跨境人民币中行自动购汇任务结束  ");
    }

    /**
     * 自动查询汇率
     *
     * @param channelId  渠道id
     * @param traceLogId 日志ID
     */
    @Override
    public void autoQueryExchange(Long channelId, String traceLogId) {
        log.info("call 跨境人民币自动查询汇率任务开始   ");
        try {
            MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
            log.info("请求参数，渠道id：{}", channelId);
            if (channelId == null) {
                log.error("渠道id为空");
                return;
            }
            BaseRespDto respDto = cgwTaskFacade.getExchangeRate(CbPayJobConvert.queryExchangeParamConvert(
                    channelId, traceLogId));
            log.info("自动查询汇率返回结果：{}", respDto);
        } catch (Exception e) {
            log.error("自动查询汇率异常", e);
        }
        log.info("call 跨境人民币自动查询汇率任务结束  ");
    }

    /**
     * 跨境订单风控定时任务  购付汇
     *
     * @param traceLogId 日志ID
     * @param date       需要重新风控的日期
     */
    @Override
    public void cbPayOrderRiskControl(String traceLogId, String date) {

        MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
        log.info("call  购付汇  风控定时任务开始：{}", traceLogId);
        //上次任务已经执行完毕
        if (!cbpayOrderRiskControlBiz.isComplete(Constants.CBPAYORDER_RISKCONTROL_END_FLAG, traceLogId)) {
            log.info("call  购付汇    跨境订单风控定时任务开始");
            cbpayOrderRiskControlBiz.doCbPayOrderRiskControl(traceLogId, date);
        } else {
            log.info("call  购付汇    上次跨境订单风控定时任务还没有结束，本次任务暂不执行！");
        }
    }

    /**
     * 结汇订单风控
     *
     * @param traceLogId 日志ID
     */
    @Override
    public void cbPaySettleOrderRiskControl(String traceLogId, String date) {

        MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
        log.info("call  结汇  风控定时任务开始：{}", traceLogId);
        //上次任务已经执行完毕
        if (!cbpayOrderRiskControlBiz.isComplete(Constants.CBPAYORDER_RISKCONTROL_END_FLAG_SETTLE, traceLogId)) {
            log.info("call  结汇    跨境订单风控定时任务开始");
            cbpayOrderRiskControlBiz.doCbPayOrderRiskControlOfSettleV2(traceLogId, date);
        } else {
            log.info("call  结汇    上次跨境订单风控定时任务还没有结束，本次任务暂不执行！");
        }

    }

    /**
     * 海关实名认证发送统计邮件定时任务
     *
     * @param traceLogId 日志ID
     */
    @Override
    public void customsVerifyStatisticsEmail(String traceLogId) {
        MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
        log.info("call 海关实名认证发送统计邮件定时任务：{}", traceLogId);
        try {
            customsSendEmailBiz.sendVerifyEmail();
        } catch (Exception e) {
            log.error("call 海关实名认证发送统计邮件定时任务 出现异常：{}", e);
        }
    }
}
