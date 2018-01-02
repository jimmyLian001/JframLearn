package com.baofu.international.global.account.core.manager.impl;

import com.baofu.international.global.account.core.common.constant.Constants;
import com.baofu.international.global.account.core.common.enums.ErrorCodeEnum;
import com.baofu.international.global.account.core.common.util.SplitListUtils;
import com.baofu.international.global.account.core.dal.mapper.WithdrawOrderItemMapper;
import com.baofu.international.global.account.core.dal.model.WithdrawOrderItemDo;
import com.baofu.international.global.account.core.manager.OrderItemManager;
import com.system.commons.exception.BizServiceException;
import com.system.commons.exception.CommonErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 描述：提现订单商品信息操作
 * <p>
 * 1、批量添加提现订单商品信息
 * </p>
 * User: feng_jiang Date:2017/11/7 ProjectName: globalaccount-core Version: 1.0
 */
@Repository
@Slf4j
public class OrderItemManagerImpl implements OrderItemManager {

    /**
     * 订单商品信息相关操作数据服务
     */
    @Autowired
    private WithdrawOrderItemMapper withdrawOrderItemMapper;

    /**
     * 批量添加提现订单商品信息
     *
     * @param withdrawOrderItemDoList 提现订单商品信息集合
     */
    @Override
    public void addBatchOrderItem(List<WithdrawOrderItemDo> withdrawOrderItemDoList) {
        log.info("跨境订单商品信息操作,批量添加跨境订单商品信息  ——> {}", withdrawOrderItemDoList);
        if (CollectionUtils.isEmpty(withdrawOrderItemDoList)) {
            throw new BizServiceException(CommonErrorCode.NULL_IS_ILLEGAL_PARAM, "商品信息为空");
        }
        List<List<WithdrawOrderItemDo>> list = SplitListUtils.splitList(withdrawOrderItemDoList, Constants.INSERT_MAX);
        for (List<WithdrawOrderItemDo> withdrawOrderItemDos : list) {
            log.info("跨境订单商品信息操作,批量添加200条跨境订单商品信息  ——> {}", withdrawOrderItemDos);
            int i = withdrawOrderItemMapper.batchInsert(withdrawOrderItemDos);
            if (i < 1) {
                throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190022);
            }
        }
    }
}
