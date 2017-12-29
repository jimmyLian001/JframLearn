package com.baofu.cbpayservice.biz.impl;

import com.baofu.cbpayservice.biz.CbPayPurchaseBiz;
import com.baofu.cbpayservice.biz.CbPayRemittanceBiz;
import com.baofu.cbpayservice.biz.convert.CbPayRemittanceConvert;
import com.baofu.cbpayservice.biz.models.CbPayRemittanceAuditReqBo;
import com.baofu.cbpayservice.common.enums.PurchaseStatus;
import com.baofu.cbpayservice.dal.models.FiCbPayRemittanceDo;
import com.baofu.cbpayservice.manager.CbPayRemittanceManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * </p>
 * User: 康志光 Date: 2017/3/23 ProjectName: cbpay-customs-service Version: 1.0
 */
@Slf4j
@Service
public class CbPayPurchaseBizImpl implements CbPayPurchaseBiz {

    /**
     * 跨境人民币订单信息Manager
     */
    @Autowired
    private CbPayRemittanceManager cbPayRemittanceManager;

    /**
     * 汇款操作biz服务
     */
    @Autowired
    private CbPayRemittanceBiz cbPayRemittanceBiz;

    /**
     * 自动购汇
     *
     * @param channelId 渠道ID
     * @param time      时间
     */
    @Override
    public void doAutoPurchase(Long channelId, String time) {

        log.info("自动购汇定时发送渠道开始...");
        // 查询待购汇信息
        List<FiCbPayRemittanceDo> orderList = cbPayRemittanceManager.queryInitRemittanceOrder(channelId, PurchaseStatus.INIT.getCode(), time);
        log.info("需自动购汇的批次:{}", orderList);
        if (orderList != null && orderList.size() > 0) {
            for (FiCbPayRemittanceDo order : orderList) {
                CbPayRemittanceAuditReqBo cbPayRemittanceAuditReqBo = CbPayRemittanceConvert.autoAuditParamConvert(order);
                log.info("自动购汇定时发送渠道参数:{}", order);
                //添加跨境主订单信息
                try {
                    cbPayRemittanceBiz.batchPurchase(cbPayRemittanceAuditReqBo, order.getSystemMoney());
                } catch (Exception e) {
                    log.error("自动购汇发往渠道发生异常", e);
                }
            }
        }
        log.info("自动购汇定时发送渠道结束...");
    }

}
