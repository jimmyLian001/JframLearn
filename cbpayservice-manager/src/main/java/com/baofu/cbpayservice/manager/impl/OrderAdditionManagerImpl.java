package com.baofu.cbpayservice.manager.impl;

import com.baofu.cbpayservice.common.constants.Constants;
import com.baofu.cbpayservice.common.enums.ErrorCodeEnum;
import com.baofu.cbpayservice.common.util.SliptListUtils;
import com.baofu.cbpayservice.dal.mapper.FiCbPayOrderAdditionMapper;
import com.baofu.cbpayservice.dal.models.FiCbPayOrderAdditionDo;
import com.baofu.cbpayservice.manager.OrderAdditionManager;
import com.system.commons.exception.BizServiceException;
import com.system.commons.exception.CommonErrorCode;
import com.system.commons.utils.ParamValidate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 订单附加信息Manager服务实现
 * <p>
 * 1、根据宝付订单号查询网关订单附加信息
 * 2、新增数据至网关订单附加信息表
 * </p>
 * User: 香克斯 Date:2016/9/24 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
@Repository
public class OrderAdditionManagerImpl implements OrderAdditionManager {

    /**
     * 网关订单附加信息服务
     */
    @Autowired
    private FiCbPayOrderAdditionMapper orderAdditionMapper;

    /**
     * 根据宝付订单号查询网关订单附加信息
     *
     * @param orderId 宝付订单号
     * @return 返回网关订单附加信息
     */
    public FiCbPayOrderAdditionDo queryOrderAddition(Long orderId) {

        FiCbPayOrderAdditionDo fiIcPayOrderAdditionDo = orderAdditionMapper.selectOrderAdditionByOrderId(orderId);
        if (fiIcPayOrderAdditionDo == null) {
            throw new BizServiceException(CommonErrorCode.QUERY_RESULT_NULL, "网关订单附加信息为空");
        }
        return fiIcPayOrderAdditionDo;
    }

    /**
     * 新增数据至网关订单附加信息表
     *
     * @param fiCbPayOrderAdditionDo 网关订单附加信息
     */
    @Override
    public void addOrderAddition(FiCbPayOrderAdditionDo fiCbPayOrderAdditionDo) {

        ParamValidate.checkUpdate(orderAdditionMapper.insert(fiCbPayOrderAdditionDo), "添加网关订单附加信息异常");
    }

    /**
     * 批量新增数据至网关订单附加信息表
     *
     * @param fiCbPayOrderAdditionDoList 网关订单附加信息集合
     */
    @Override
    public void addBatchOrderAddition(List<FiCbPayOrderAdditionDo> fiCbPayOrderAdditionDoList) {
        log.info("订单附加信息Manager服务实现,批量新增数据至网关订单附加信息表入参 ——> {}", fiCbPayOrderAdditionDoList);
        List<List<FiCbPayOrderAdditionDo>> list = SliptListUtils.splitList(fiCbPayOrderAdditionDoList, Constants.INSERT_MAX);
        for (List<FiCbPayOrderAdditionDo> cbPayOrderAdditionDos : list) {
            log.info("订单附加信息Manager服务实现,批量新增200条数据至网关订单附加信息表 ——> {}", fiCbPayOrderAdditionDoList);
            int i = orderAdditionMapper.batchInsert(cbPayOrderAdditionDos);
            if (i < 1) {
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0068);
            }
        }
    }
}
