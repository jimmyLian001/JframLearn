package com.baofu.cbpayservice.manager.impl;

import com.baofu.cbpayservice.common.constants.Constants;
import com.baofu.cbpayservice.common.enums.ErrorCodeEnum;
import com.baofu.cbpayservice.common.util.SliptListUtils;
import com.baofu.cbpayservice.dal.mapper.FiCbPayOrderItemMapper;
import com.baofu.cbpayservice.dal.models.FiCbPayOrderItemDo;
import com.baofu.cbpayservice.dal.models.FiCbPayOrderItemInfoDo;
import com.baofu.cbpayservice.dal.models.FiCbPayOrderItemsInfoDo;
import com.baofu.cbpayservice.manager.OrderItemManager;
import com.system.commons.exception.BizServiceException;
import com.system.commons.exception.CommonErrorCode;
import com.system.commons.utils.ParamValidate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 跨境订单商品信息操作
 * <p>
 * 1、批量添加跨境订单商品信息
 * </p>
 * User: 香克斯 Date:2016/10/28 ProjectName: cbpayservice Version: 1.0
 */
@Repository
@Slf4j
public class OrderItemManagerImpl implements OrderItemManager {

    /**
     * 网关订单商品信息相关操作数据服务
     */
    @Autowired
    private FiCbPayOrderItemMapper fiCbPayOrderItemMapper;

    /**
     * 批量添加跨境订单商品信息
     *
     * @param fiCbPayOrderItemDoList 跨境订单商品信息集合
     */
    @Override
    public void addOrderItem(List<FiCbPayOrderItemDo> fiCbPayOrderItemDoList) {

        if (CollectionUtils.isEmpty(fiCbPayOrderItemDoList)) {
            throw new BizServiceException(CommonErrorCode.NULL_IS_ILLEGAL_PARAM, "商品信息为空");
        }

        for (FiCbPayOrderItemDo fiCbPayOrderItemDo : fiCbPayOrderItemDoList) {
            fiCbPayOrderItemDo.getCommodityName().replace("\n","").replace("\r\n","");
            ParamValidate.checkUpdate(fiCbPayOrderItemMapper.insert(fiCbPayOrderItemDo), "添加商品信息异常");
        }
    }

    /**
     * 根据宝付订单号查询网关订单商品信息
     *
     * @param orderId 宝付订单号
     * @return 返回网关订单商品信息
     */
    @Override
    public List<FiCbPayOrderItemInfoDo> queryItemInfo(Long orderId) {
        return fiCbPayOrderItemMapper.queryItemInfo(orderId);
    }

    /**
     * 批量添加跨境订单商品信息
     *
     * @param fiCbPayOrderItemDoList 跨境订单商品信息集合
     */
    @Override
    public void addBatchOrderItem(List<FiCbPayOrderItemDo> fiCbPayOrderItemDoList) {
        log.info("跨境订单商品信息操作,批量添加跨境订单商品信息  ——> {}", fiCbPayOrderItemDoList);
        if (CollectionUtils.isEmpty(fiCbPayOrderItemDoList)) {
            throw new BizServiceException(CommonErrorCode.NULL_IS_ILLEGAL_PARAM, "商品信息为空");
        }
        List<List<FiCbPayOrderItemDo>> list = SliptListUtils.splitList(fiCbPayOrderItemDoList, Constants.INSERT_MAX);
        for (List<FiCbPayOrderItemDo> cbPayOrderItemDos : list) {
            log.info("跨境订单商品信息操作,批量添加200条跨境订单商品信息  ——> {}", cbPayOrderItemDos);
            int i = fiCbPayOrderItemMapper.batchInsert(cbPayOrderItemDos);
            if (i < 1) {
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0068);
            }
        }
    }

    /**
     * @param orderId 宝付订单号  wdj  0503
     * @return 返回商品组合信息
     */
    @Override
    public FiCbPayOrderItemsInfoDo queryItemsInfo(Long orderId) {
        return fiCbPayOrderItemMapper.queryItemsInfo(orderId);
    }
}
