package com.baofu.cbpayservice.manager.impl;

import com.baofu.cbpayservice.common.enums.ErrorCodeEnum;
import com.baofu.cbpayservice.dal.mapper.FiCbPayRemittanceMapper;
import com.baofu.cbpayservice.dal.models.FiCbPayRemittanceAdditionDo;
import com.baofu.cbpayservice.dal.models.FiCbPayRemittanceDo;
import com.baofu.cbpayservice.manager.CbPayRemittanceManager;
import com.system.commons.exception.BizServiceException;
import com.system.commons.utils.ParamValidate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * 跨境人民币汇款订单信息Manager
 * <p>
 * 1、跨境人民汇款订单新增
 * 2、跨境人民汇款明细新增
 * 3、查询跨境人民汇款订单信息
 * 4、查询跨境人民汇款订单明细信息
 * 5、跨境人民汇款订单状态更新
 * </p>
 * User: wanght Date:2016/11/10 ProjectName: cbpay-service Version: 1.0
 */
@Slf4j
@Repository
public class CbPayRemittanceManagerImpl implements CbPayRemittanceManager {

    /**
     * 汇款mapper
     */
    @Autowired
    private FiCbPayRemittanceMapper fiCbPayRemittanceMapper;

    /**
     * @param fiCbPayRemittanceDo 汇款订单参数信息
     */
    @Override
    public void createRemittanceOrder(FiCbPayRemittanceDo fiCbPayRemittanceDo) {

        ParamValidate.checkUpdate(fiCbPayRemittanceMapper.createRemittanceOrder(fiCbPayRemittanceDo),
                "跨境人民汇款订单新增异常");
    }

    /**
     * 跨境人民汇款订单附加信息
     *
     * @param fiCbPayRemittanceAdditionDo 附加参数信息
     */
    @Override
    public void createRemittanceAddition(FiCbPayRemittanceAdditionDo fiCbPayRemittanceAdditionDo) {

        ParamValidate.checkUpdate(fiCbPayRemittanceMapper.createRemittanceAddition(fiCbPayRemittanceAdditionDo),
                "跨境人民汇款附加信息新增异常");
    }

    /**
     * 查询跨境人民汇款订单信息
     *
     * @param list 批次号
     */
    @Override
    public BigDecimal queryRemittanceByBatchNos(List<Long> list) {
        return fiCbPayRemittanceMapper.queryRemittanceByBatchNos(list);
    }

    /**
     * 查询汇款订单批次号
     *
     * @return 结果
     */
    @Override
    public List<String> queryBatchNos(String beginDate, String endDate) {
        return fiCbPayRemittanceMapper.queryBatchNos(beginDate, endDate);
    }

    /**
     * 查询跨境人民汇款订单信息
     *
     * @param batchNo 批次号
     */
    @Override
    public FiCbPayRemittanceDo queryRemittanceOrder(String batchNo) {

        return fiCbPayRemittanceMapper.queryRemittanceOrder(batchNo);
    }

    /**
     * 查询跨境人民所有初始化汇款订单信息
     */
    @Override
    public List<FiCbPayRemittanceDo> queryInitRemittanceOrder(Long channelId, Integer purchaseStatus, String time) {
        return fiCbPayRemittanceMapper.queryInitRemittanceOrder(channelId, purchaseStatus, time);
    }

    /**
     * 查询跨境人民汇款附加信息
     *
     * @param batchNo  批次号
     * @param memberId 商户号
     */
    @Override
    public FiCbPayRemittanceAdditionDo queryRemittanceAddition(String batchNo, Long memberId) {

        log.info("call 查询跨境人民汇款附加信息 根据商户号:{},批次号:{}查询", memberId, batchNo);
        FiCbPayRemittanceAdditionDo remittanceAdditionDo = fiCbPayRemittanceMapper.queryRemittanceAddition(batchNo, memberId);
        log.info("call 查询跨境人民汇款附加信息 根据商户号订单号查询返回信息：{}", remittanceAdditionDo);
        if (remittanceAdditionDo == null) {
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0086);
        }

        return remittanceAdditionDo;
    }

    /**
     * 跨境人民汇款订单状态更新
     *
     * @param fiCbPayRemittanceDo 汇款订单参数信息
     */
    @Override
    public void updateRemittanceOrder(FiCbPayRemittanceDo fiCbPayRemittanceDo) {

        ParamValidate.checkUpdate(fiCbPayRemittanceMapper.updateRemittanceOrder(fiCbPayRemittanceDo),
                "跨境人民汇款订单更新异常");
    }

    /**
     * 跨境人民汇款附加信息更新
     *
     * @param fiCbPayRemittanceAdditionDo 汇款附加参数信息
     */
    @Override
    public void updateRemittanceAddition(FiCbPayRemittanceAdditionDo fiCbPayRemittanceAdditionDo) {
        ParamValidate.checkUpdate(fiCbPayRemittanceMapper.updateRemittanceAddition(fiCbPayRemittanceAdditionDo),
                "跨境人民汇款附加信息更新异常");
    }

    /**
     * 根据batchNo查询汇款申请信息
     *
     * @param batchNo 批次号
     * @return 返回汇款信息
     */
    @Override
    public FiCbPayRemittanceDo queryRemittanceByBatchNo(String batchNo) {

        return fiCbPayRemittanceMapper.selectByBatchNo(batchNo);
    }

    /**
     * 根据商户号和订单号查询
     *
     * @param memberId 商户号
     * @param batchNo  订单号
     * @return 汇款订单信息
     */
    @Override
    public FiCbPayRemittanceDo queryRemitByBatchNoMemberId(Long memberId, String batchNo) {

        log.info("call 根据商户号:{},订单号:{}查询", memberId, batchNo);
        FiCbPayRemittanceDo fiCbPayRemittanceDo = fiCbPayRemittanceMapper.queryRemitByBatchNoMemberId(memberId, batchNo);
        log.info("call 根据商户号订单号查询返回信息：{}", fiCbPayRemittanceDo);
        if (fiCbPayRemittanceDo == null) {
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0055);
        }
        return fiCbPayRemittanceDo;
    }

    /**
     * 查询跨境人民汇款订单信息行业类型总数
     *
     * @param list 文件批次号
     * @return 文件批次行业类型之和
     */
    @Override
    public int checkCareerTypeByBatchNos(List<Long> list) {
        return fiCbPayRemittanceMapper.checkCareerTypeByBatchNos(list);
    }

}
