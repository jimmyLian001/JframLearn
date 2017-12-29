package com.baofu.cbpayservice.manager.impl;

import com.baofu.cbpayservice.common.constants.Constants;
import com.baofu.cbpayservice.common.enums.ErrorCodeEnum;
import com.baofu.cbpayservice.common.util.SliptListUtils;
import com.baofu.cbpayservice.dal.mapper.FiCbPayOrderMapper;
import com.baofu.cbpayservice.dal.models.FiCbPayEmailDetailDo;
import com.baofu.cbpayservice.dal.models.FiCbPayOrderDo;
import com.baofu.cbpayservice.dal.models.FiCbPayRemittanceDetailV2Do;
import com.baofu.cbpayservice.manager.CbPayOrderManager;
import com.google.common.collect.Lists;
import com.system.commons.exception.BizServiceException;
import com.system.commons.utils.ParamValidate;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * 跨境人民币订单信息Manager
 * <p>
 * 1、跨境人民订单新增
 * 2、跨境人民订单更新
 * 3、跨境人民币订单查询
 * 4、更新跨境人民币订单支付成功订单信息
 * 4、更新跨境人民币订单支付成功订单信息
 * </p>
 * User: 香克斯 Date:2016/10/25 ProjectName: asias-icpaygate Version: 1.0
 */
@Slf4j
@Repository
public class CbPayOrderManagerImpl implements CbPayOrderManager {

    /**
     * 跨境人民币订单信息Mapper
     */
    @Autowired
    private FiCbPayOrderMapper fiCbPayOrderMapper;

    /**
     * 跨境人民订单新增
     *
     * @param fiCbPayOrderDo 订单参数信息
     */
    @Override
    public void addCbPayOrder(FiCbPayOrderDo fiCbPayOrderDo) {

        ParamValidate.checkUpdate(fiCbPayOrderMapper.insert(fiCbPayOrderDo), "跨境人民订单新增异常");
    }

    /**
     * 批量新增跨境人民订单
     *
     * @param fiCbPayOrderDos 订单参数信息集合
     */
    @Override
    public void addBatchCbPayOrder(List<FiCbPayOrderDo> fiCbPayOrderDos) {

        log.info("跨境人民币订单信息Manager,批量新增跨境人民订单入参 ——> {}", fiCbPayOrderDos);
        List<List<FiCbPayOrderDo>> list = SliptListUtils.splitList(fiCbPayOrderDos, Constants.INSERT_MAX);
        for (List<FiCbPayOrderDo> fiCbPayOrderDoList : list) {
            log.info("跨境人民币订单信息Manager,批量新增200条跨境人民订单参数 ——> {}", fiCbPayOrderDoList);
            int size = fiCbPayOrderMapper.batchInsert(fiCbPayOrderDoList);
            if (size < 1) {
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0071);
            }
        }
    }

    /**
     * 跨境人民订单更新
     *
     * @param fiCbPayOrderDo 订单参数信息
     */
    @Override
    public void modifyCbPayOrder(FiCbPayOrderDo fiCbPayOrderDo) {

        ParamValidate.checkUpdate(fiCbPayOrderMapper.update(fiCbPayOrderDo), "跨境人民订单更新异常");
    }

    /**
     * 唯一条件查询订单，返回匹配唯一的订单或返回空
     *
     * @param orderId 宝付订单号
     * @return 返回订单
     */
    @Override
    public FiCbPayOrderDo queryOrder(Long orderId) {

        FiCbPayOrderDo fiCbPayOrder = fiCbPayOrderMapper.selectOrder(orderId);
        if (fiCbPayOrder == null) {
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0094);
        }
        return fiCbPayOrder;
    }

    /**
     * 按商户号和商户订单号查询订单
     *
     * @param memberId      商户编号
     * @param memberTransId 商户订单号
     * @return 查询结果
     */
    @Override
    public FiCbPayOrderDo queryOrderByMemberAndTransId(Long memberId, String memberTransId) {

        return fiCbPayOrderMapper.selectOrderByMemberAndTransId(memberId, memberTransId);
    }

    /**
     * 按商户号和文件批次号查询订单
     *
     * @param memberId    商户编号
     * @param batchFileId 文件批次号
     * @return 查询结果
     */
    @Override
    public List<Long> batchQueryOrder(Long memberId, Long batchFileId) {

        return fiCbPayOrderMapper.batchQueryOrder(memberId, batchFileId);
    }

    /**
     * 跨境人民订单批量更新
     *
     * @param orderIdList 订单参数信息
     */
    @Override
    public void batchModifyCbPayOrder(String remitStatus, String updateBy, List<Long> orderIdList, String batchNo, Integer amlStatus) {
        fiCbPayOrderMapper.batchModifyCbPayOrder(remitStatus, updateBy, orderIdList, batchNo, amlStatus);
    }

    /**
     * 查询文件明细成功总金额
     *
     * @param batchFileId 文件批次号
     * @param memberId    商户号
     */
    @Override
    public BigDecimal sumSuccessAmt(Long batchFileId, Long memberId) {

        BigDecimal amt = fiCbPayOrderMapper.sumSuccessAmt(batchFileId, memberId);
        if (amt == null) {
            log.info("查询汇款总金额失败,商户号：{},文件批次号：{}", memberId, batchFileId);
            amt = BigDecimal.ZERO;
        }
        return amt;
    }

    /**
     * 跨境人民订单更新
     *
     * @param remittanceStatus 订单参数信息
     * @param updateBy         订单参数信息
     * @param batchNo          订单参数信息
     */
    @Override
    public void updateCbPayOrderByBatchNo(String remittanceStatus, String updateBy, String batchNo) {

        fiCbPayOrderMapper.updateCbPayOrderByBatchNo(remittanceStatus, updateBy, batchNo);
    }

    /**
     * 跨境人民订单更新
     *
     * @param amlStatus   订单参数信息
     * @param updateBy    订单参数信息
     * @param fileBatchId 订单参数信息
     */
    @Override
    public void updateCbPayOrderByFileBatchId(Integer amlStatus, String updateBy, Long fileBatchId) {
        fiCbPayOrderMapper.updateCbPayOrderByFileBatchId(amlStatus, updateBy, fileBatchId);
    }

    /**
     * 汇款明细
     *
     * @param batchNo     汇款批次号
     * @param batchFileId 文件批次号
     */
    @Override
    public List<FiCbPayRemittanceDetailV2Do> queryRemittanceDetail(String batchNo, Long batchFileId) {
        return fiCbPayOrderMapper.queryRemittanceDetail(batchNo, batchFileId);
    }

    /**
     * 查询需要风控的订单
     *
     * @param batchNo    汇款批次号
     * @param orderCount 查询订单数量
     * @return 返回订单信息
     */
    @Override
    public List<FiCbPayOrderDo> queryNeedRiskControlOrder(Long memberId, String batchNo, Integer orderCount) {
        return fiCbPayOrderMapper.queryNeedRiskControlOrder(memberId, batchNo, orderCount);
    }

    /**
     * 根据汇款批次号查询邮件明细信息
     *
     * @param batchNo 汇款批次号
     * @return 明细信息
     */
    @Override
    public List<FiCbPayEmailDetailDo> queryEmailDetail(@Param("batchNo") String batchNo) {
        return fiCbPayOrderMapper.queryEmailDetail(batchNo);
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
        return fiCbPayOrderMapper.queryCbPayOrderCount(beginTime, endTime, memberId);
    }

    /**
     * 根据汇款批次号统计记录数
     *
     * @param batchNo 汇款批次号
     * @return 结果
     */
    @Override
    public Long queryCbPayOrderCountByBatchN0(String batchNo) {
        return fiCbPayOrderMapper.queryCbPayOrderCountByBatchN0(batchNo);
    }

    /**
     * 根据文件批次号查询出对应的商户号
     *
     * @param batchNo 文件批次号
     * @return 商户号
     */
    @Override
    public Long queryMemberNoByBatchNo(String batchNo) {
        return fiCbPayOrderMapper.queryMemberNoByBatchNo(batchNo);
    }

    /**
     * TT编号
     *
     * @param orderId 结汇订单明细ID
     * @return TT编号
     */
    @Override
    public String queryIncomeNoByOrderId(Long orderId) {
        return fiCbPayOrderMapper.queryIncomeNoByOrderId(orderId);
    }

    /**
     * 汇款明细（含机票、留学、旅游、酒店）
     *
     * @param batchFileId   文件批次号
     * @param amlStatusList 反洗钱审核状态
     * @param careerType    行业类型
     */
    @Override
    public List<FiCbPayRemittanceDetailV2Do> queryOrderDetailInfo(Long batchFileId, List<Integer> amlStatusList, String careerType) {
        switch (Integer.parseInt(careerType)) {
            case 1://电商
                return fiCbPayOrderMapper.queryOrderDetail(batchFileId, amlStatusList);
            case 2://机票
                return fiCbPayOrderMapper.queryOrderTicketsDetail(batchFileId, amlStatusList);
            case 3://留学
                return fiCbPayOrderMapper.queryOrderStudyAbroadDetail(batchFileId, amlStatusList);
            case 4://酒店
                return fiCbPayOrderMapper.queryOrderHotelDetail(batchFileId, amlStatusList);
            case 5://旅游
                return fiCbPayOrderMapper.queryOrderTourismDetail(batchFileId, amlStatusList);
        }
        return Lists.newArrayList();
    }

    /**
     * 更新订单为未确认状态
     *
     * @param fiCbPayOrderDo 请求参数
     * @return 返回执行条数
     * add by yangjian
     */
    public Long modifyACKStatus(FiCbPayOrderDo fiCbPayOrderDo) {
        return fiCbPayOrderMapper.updateAckStausByBatchNo(fiCbPayOrderDo);
    }

    /**
     * 根据文件批次查询反洗钱不通过的订单
     *
     * @param fileBatchNo 文件批次
     * @param amlStatus   反洗钱状态
     */
    @Override
    public List<FiCbPayRemittanceDetailV2Do> queryAmlDetailByBatchFileNo(Long fileBatchNo, Integer amlStatus) {
        return fiCbPayOrderMapper.queryAmlDetailByBatchFileNo(fileBatchNo, amlStatus);
    }

    /**
     * 根据文件批次查询反洗钱不通过的订单
     *
     * @param status      汇款状态
     * @param fileBatchNo 文件批次
     */
    @Override
    public void updateOrderByFileBatchNo(String status, Long fileBatchNo) {
        fiCbPayOrderMapper.updateOrderByFileBatchNo(status, fileBatchNo);
    }
}
