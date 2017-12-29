package com.baofu.cbpayservice.biz.impl;

import com.baofu.cbpayservice.biz.CbPayAutoAuditBiz;
import com.baofu.cbpayservice.biz.CbPayRemittanceBiz;
import com.baofu.cbpayservice.biz.convert.CbPayRemittanceConvert;
import com.baofu.cbpayservice.biz.models.CbPayRemittanceAuditReqBo;
import com.baofu.cbpayservice.common.enums.PurchaseStatus;
import com.baofu.cbpayservice.dal.models.FiCbPayRemittanceDo;
import com.baofu.cbpayservice.manager.CbPayRemittanceManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 跨境汇款订单定时发送渠道Biz层相关操作
 * <p>
 * 1、汇款订单定时发送渠道
 * </p>
 * User: wanght Date:2016/12/19 ProjectName: asias-icpaygate Version: 1.0
 */
@Slf4j
@Service
public class CbPayAutoAuditBizImpl implements CbPayAutoAuditBiz {

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
     * 汇款订单定时发送渠道
     */
    @Override
    @Transactional
    public void doAutoAudit(Long channelId, String time) {
        log.info("汇款订单定时发送渠道开始...");
        // 查询汇款订单信息
        List<FiCbPayRemittanceDo> orderList = cbPayRemittanceManager.queryInitRemittanceOrder(channelId, PurchaseStatus.TRUE.getCode(), time);
        log.info("自动汇款订单查询结果:{}", orderList);
        if (orderList != null && orderList.size() > 0) {
            for (FiCbPayRemittanceDo order : orderList) {
                CbPayRemittanceAuditReqBo cbPayRemittanceAuditReqBo = CbPayRemittanceConvert.autoAuditParamConvert(order);
                log.info("汇款订单定时发送渠道参数:{}", order);
                //添加跨境主订单信息
                try {
                    cbPayRemittanceBiz.batchRemit(cbPayRemittanceAuditReqBo);
                } catch (Exception e) {
                    log.error("汇款订单发往渠道发生异常", e);
                }
            }
        }
        log.info("汇款订单定时发送渠道结束...");
    }
}
