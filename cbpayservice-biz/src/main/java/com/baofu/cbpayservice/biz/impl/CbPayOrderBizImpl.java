package com.baofu.cbpayservice.biz.impl;

import com.baofu.cbpayservice.biz.CbPayOrderBiz;
import com.baofu.cbpayservice.biz.convert.ParamConvert;
import com.baofu.cbpayservice.biz.models.CbPayOrderReqBo;
import com.baofu.cbpayservice.common.enums.ErrorCodeEnum;
import com.baofu.cbpayservice.dal.models.FiCbPayOrderDo;
import com.baofu.cbpayservice.manager.CbPayOrderManager;
import com.baofu.cbpayservice.manager.OrderAdditionManager;
import com.baofu.cbpayservice.manager.OrderItemManager;
import com.system.commons.exception.BizServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 跨境订单Biz层相关操作
 * <p>
 * 1、添加跨境订单信息和B2C网银订单信息
 * </p>
 * User: 香克斯 Date:2016/10/25 ProjectName: asias-icpaygate Version: 1.0
 */
@Slf4j
@Service
public class CbPayOrderBizImpl implements CbPayOrderBiz {

    /**
     * 跨境人民币订单信息Manager
     */
    @Autowired
    private CbPayOrderManager cbPayOrderManager;

    /**
     * 订单附加信息Manager服务
     */
    @Autowired
    private OrderAdditionManager orderAdditionManager;

    /**
     * 跨境订单商品信息操作
     */
    @Autowired
    private OrderItemManager orderItemManager;


    /**
     * 添加跨境订单信息和B2C网银订单信息
     *
     * @param cbPayOrderReqBo 跨境订单信息
     * @return 返回宝付订单号
     */
    @Override
    @Transactional
    public Long addCbPayOrder(CbPayOrderReqBo cbPayOrderReqBo) {

        log.info("宝付订单号:{}开始创建跨境订单信息开始", cbPayOrderReqBo);

        FiCbPayOrderDo fiCbPayOrderDo = ParamConvert.convert(cbPayOrderReqBo);
        FiCbPayOrderDo cbPayOrderDo = cbPayOrderManager.queryOrderByMemberAndTransId(cbPayOrderReqBo.getMemberId(),
                cbPayOrderReqBo.getMemberTransId());
        if (cbPayOrderDo != null) {
            log.info("商户号：{},商户订单号：{},已存在", cbPayOrderDo.getMemberId(), cbPayOrderDo.getMemberTransId());
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0076);
        }
        //添加跨境主订单信息
        cbPayOrderManager.addCbPayOrder(fiCbPayOrderDo);

        //添加跨境附加表订单信息
        orderAdditionManager.addOrderAddition(ParamConvert.additionConvert(cbPayOrderReqBo));

        //添加商品信息
        if (cbPayOrderReqBo.getCbPayOrderItemBos() != null) {
            orderItemManager.addOrderItem(ParamConvert.paramConvert(cbPayOrderReqBo));
            log.info("宝付订单号:{}开始创建跨境订单信息完成", cbPayOrderReqBo.getOrderId());
        }

        //返回宝付订单号
        return cbPayOrderReqBo.getOrderId();
    }

    /**
     * 查询订单数量(定制)
     *
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param memberId  商户号
     * @return 返回订单数量
     */
    @Override
    public Long queryCbPayOrderCount(String beginTime, String endTime, Long memberId) {
        return cbPayOrderManager.queryCbPayOrderCount(beginTime, endTime, memberId);
    }
}
