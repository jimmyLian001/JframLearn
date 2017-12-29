package com.baofu.cbpayservice.biz.impl;

import com.baofu.cbpayservice.biz.CbPayCmBiz;
import com.baofu.cbpayservice.biz.CbPayNotifySettleBiz;
import com.baofu.cbpayservice.biz.convert.CmParamConvert;
import com.baofu.cbpayservice.biz.integration.cm.CmClearBizImpl;
import com.baofu.cbpayservice.biz.models.ClearRequestBo;
import com.baofu.cbpayservice.biz.models.ClearingResponseBo;
import com.baofu.cbpayservice.biz.models.MemberFeeResBo;
import com.baofu.cbpayservice.common.enums.*;
import com.baofu.cbpayservice.dal.models.FiCbPayRemittanceDo;
import com.baofu.cbpayservice.manager.CbPayRemittanceManager;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.slf4j.helpers.SystemMarker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 跨境人民币汇款订单通知清算处理
 * <p>
 * 1、汇款订单通知清算系统
 * </p>
 * User: wanght Date:2017/02/27 ProjectName: cbpay-service Version: 1.0
 */
@Slf4j
@Service
public class CbPayNotifySettleBizImpl implements CbPayNotifySettleBiz {

    /**
     * 汇款订单信息操作服务
     */
    @Autowired
    private CbPayRemittanceManager cbPayRemittanceManager;

    /**
     * 清算服务
     */
    @Autowired
    private CmClearBizImpl cmClearBizImpl;

    /**
     * 计费操作服务
     */
    @Autowired
    private CbPayCmBiz cbPayCmBiz;

    /**
     * 汇款订单通知清算系统
     *
     * @param batchNo  批次号
     * @param updateBy 操作用户
     */
    @Override
    public Boolean notifySettle(String batchNo, String updateBy) {
        String traceLogId = MDC.get(SystemMarker.TRACE_LOG_ID);

        //获取订单信息
        FiCbPayRemittanceDo fiCbPayRemittanceDo = cbPayRemittanceManager.queryRemittanceOrder(batchNo);
        log.info("通知清算查询汇款订单信息：{}", fiCbPayRemittanceDo);

        if (fiCbPayRemittanceDo == null) {
            log.error("未查询到该批次汇款订单：{}", batchNo);
            return Boolean.FALSE;
        }

        if (CMStatus.TRUE.getCode().equals(fiCbPayRemittanceDo.getCmStatus())) {
            log.info("该批次汇款订单已清算成功：{}", batchNo);
            return Boolean.TRUE;
        }

        int functionId = FunctionEnum.getFunctionId(fiCbPayRemittanceDo.getOrderType(), fiCbPayRemittanceDo.getSystemCcy());
        int productId = FunctionEnum.getProductId(fiCbPayRemittanceDo.getOrderType());

        MemberFeeResBo feeResult = cbPayCmBiz.getFeeResult(fiCbPayRemittanceDo.getBatchNo(), fiCbPayRemittanceDo.getTransMoney(),
                fiCbPayRemittanceDo.getMemberNo(), functionId, productId, traceLogId);

        if (ChannelStatus.TRUE.getCode().equals(fiCbPayRemittanceDo.getChannelStatus())) {
            log.info("汇款成功，通知清算");
            // 调用清算服务
            ClearRequestBo request = CmParamConvert.cmRequestConvert(DealCodeEnums.remitDealCodeTwo(fiCbPayRemittanceDo.getSystemCcy()),
                    fiCbPayRemittanceDo.getChannelId(), String.valueOf(fiCbPayRemittanceDo.getMemberNo()), "", functionId,
                    Long.parseLong(batchNo), fiCbPayRemittanceDo.getTransMoney(),
                    fiCbPayRemittanceDo.getTransFee(), BigDecimal.ZERO, feeResult.getFeeAccId(), productId);
            log.info("异步通知汇款成功调用清算第二步请求报文：{}", request);
            ClearingResponseBo response = cmClearBizImpl.transferAccount(request);
            log.info("异步通知汇款成功调用清算第二步响应报文：{}", response);

            if (ClearAccResultEnum.CODE_SUCCESS.getCode().equals(response.getClearAccResultEnum().getCode())) {
                // 更新汇款订单表
                updateRemittanceOrder(batchNo, updateBy, CMStatus.TRUE.getCode());
            } else {
                // 更新汇款订单表
                updateRemittanceOrder(batchNo, updateBy, CMStatus.FALSE.getCode());
                log.info("异步通知汇款成功调用清算系统失败：{}", response.getClearAccResultEnum().getDesc());
            }
            return Boolean.TRUE;
        }

        if (ChannelStatus.FALSE.getCode().equals(fiCbPayRemittanceDo.getChannelStatus())) {
            log.info("汇款失败，通知清算");
            // 调用清算服务
            ClearRequestBo request = CmParamConvert.cmRequestConvert(DealCodeEnums.remitDealCodeThree(fiCbPayRemittanceDo.getSystemCcy()),
                    fiCbPayRemittanceDo.getChannelId(), "", String.valueOf(fiCbPayRemittanceDo.getMemberNo()),
                    functionId, Long.parseLong(batchNo), fiCbPayRemittanceDo.getTransMoney(), new BigDecimal("0.00"),
                    fiCbPayRemittanceDo.getTransFee(), feeResult.getFeeAccId(), productId);
            log.info("异步通知汇款失败调用清算第二步请求报文：{}", request);
            ClearingResponseBo response = cmClearBizImpl.transferAccount(request);
            log.info("异步通知汇款失败调用清算第二步响应报文：{}", response);
            if (ClearAccResultEnum.CODE_SUCCESS.getCode().equals(response.getClearAccResultEnum().getCode())) {
                // 更新汇款订单表
                updateRemittanceOrder(batchNo, updateBy, CMStatus.TRUE.getCode());
            } else {
                // 更新汇款订单表
                updateRemittanceOrder(batchNo, updateBy, CMStatus.FALSE.getCode());
                log.info("异步通知汇款失败调用清算系统失败：{}", response.getClearAccResultEnum().getDesc());
            }
            return Boolean.TRUE;
        }

        log.error("汇款状态不是最终状态：{}", fiCbPayRemittanceDo.getChannelStatus());
        return Boolean.FALSE;
    }

    private void updateRemittanceOrder(String batchNo, String updateBy, String cmStatus) {
        FiCbPayRemittanceDo fiCbPayRemittanceDo = new FiCbPayRemittanceDo();
        fiCbPayRemittanceDo.setBatchNo(batchNo);
        fiCbPayRemittanceDo.setUpdateBy(updateBy);
        fiCbPayRemittanceDo.setCmStatus(cmStatus);
        log.info("更新汇款订单表：{}", fiCbPayRemittanceDo);

        cbPayRemittanceManager.updateRemittanceOrder(fiCbPayRemittanceDo);
    }
}
