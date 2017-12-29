package com.baofu.cbpayservice.manager;

import com.baofu.cbpayservice.dal.models.FiCbPayEmailDetailDo;
import com.baofu.cbpayservice.dal.models.FiCbPayOrderDo;
import com.baofu.cbpayservice.dal.models.FiCbPayRemittanceDetailV2Do;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * 跨境人民币订单信息Manager
 * <p>
 * 1、跨境人民订单新增
 * 2、跨境人民订单更新
 * </p>
 * User: 香克斯 Date:2016/10/25 ProjectName: asias-icpaygate Version: 1.0
 */
public interface CbPayOrderManager {

    /**
     * 跨境人民订单新增
     *
     * @param fiCbPayOrderDo 订单参数信息
     */
    void addCbPayOrder(FiCbPayOrderDo fiCbPayOrderDo);

    /**
     * 跨境人民订单批量新增
     *
     * @param fiCbPayOrderDos 订单参数信息集合
     */
    @Deprecated
    void addBatchCbPayOrder(List<FiCbPayOrderDo> fiCbPayOrderDos);

    /**
     * 跨境人民订单更新
     *
     * @param fiCbPayOrderDo 订单参数信息
     */
    void modifyCbPayOrder(FiCbPayOrderDo fiCbPayOrderDo);

    /**
     * 唯一条件查询订单，返回匹配唯一的订单或返回空
     *
     * @param orderId 宝付订单号
     * @return 返回订单
     */
    FiCbPayOrderDo queryOrder(Long orderId);

    /**
     * 按商户号和商户订单号查询订单
     *
     * @param memberId      商户编号
     * @param memberTransId 商户订单号
     * @return 查询结果
     */
    FiCbPayOrderDo queryOrderByMemberAndTransId(Long memberId, String memberTransId);

    /**
     * 唯一条件查询订单，返回匹配唯一的订单或返回空
     *
     * @param memberId    商户号
     * @param batchFileId 文件批次号
     * @return 返回订单
     */
    List<Long> batchQueryOrder(Long memberId, Long batchFileId);

    /**
     * 跨境人民订单批量更新
     *
     * @param remitStatus 汇款状态
     * @param updateBy    更新人
     * @param orderIdList 订单参数信息
     * @param batchNo     汇款批次号
     * @param amlStatus   反洗钱审核状态
     */
    void batchModifyCbPayOrder(String remitStatus, String updateBy, List<Long> orderIdList, String batchNo, Integer amlStatus);

    /**
     * 查询文件明细成功总金额
     *
     * @param batchFileId 文件批次号
     * @param memberId    商户号
     * @return 成功总金额
     */
    BigDecimal sumSuccessAmt(Long batchFileId, Long memberId);

    /**
     * 跨境人民订单更新
     *
     * @param remittanceStatus 订单参数信息
     * @param updateBy         订单参数信息
     * @param batchNo          订单参数信息
     */
    void updateCbPayOrderByBatchNo(String remittanceStatus, String updateBy, String batchNo);

    /**
     * 跨境人民订单更新
     *
     * @param amlStatus   订单参数信息
     * @param updateBy    订单参数信息
     * @param fileBatchId 订单参数信息
     */
    void updateCbPayOrderByFileBatchId(Integer amlStatus, String updateBy, Long fileBatchId);

    /**
     * 汇款明细
     *
     * @param batchNo     汇款批次号
     * @param batchFileId 文件批次号
     * @return 明细信息
     */
    List<FiCbPayRemittanceDetailV2Do> queryRemittanceDetail(String batchNo, Long batchFileId);

    /**
     * 汇款明细（含机票、留学、旅游、酒店）
     *
     * @param batchFileId   文件批次号
     * @param amlStatusList 反洗钱审核状态
     * @param careerType    行业类型
     * @return 明细信息
     */
    List<FiCbPayRemittanceDetailV2Do> queryOrderDetailInfo(Long batchFileId, List<Integer> amlStatusList, String careerType);

    /**
     * 查询需要风控的订单
     *
     * @param batchNo    汇款批次号
     * @param memberId   商户号
     * @param orderCount 订单数量
     * @return 返回订单信息
     */
    List<FiCbPayOrderDo> queryNeedRiskControlOrder(Long memberId, String batchNo, Integer orderCount);

    /**
     * 根据汇款批次号查询邮件明细信息
     *
     * @param batchNo 汇款批次号
     * @return 明细信息
     */
    List<FiCbPayEmailDetailDo> queryEmailDetail(@Param("batchNo") String batchNo);

    /**
     * 查询订单数量(定制)
     *
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param memberId  商户号
     * @return 返回订单数量
     */
    Long queryCbPayOrderCount(String beginTime, String endTime, Long memberId);

    /**
     * 更新订单为未确认状态
     *
     * @param fiCbPayOrderDo 请求参数
     * @return 返回执行条数
     * add by yangjian
     */
    Long modifyACKStatus(FiCbPayOrderDo fiCbPayOrderDo);

    /**
     * 根据汇款批次号统计记录数
     *
     * @param batchNo 汇款批次号
     * @return 结果
     */
    Long queryCbPayOrderCountByBatchN0(String batchNo);

    /**
     * 根据汇款批次号查询出商户号
     *
     * @param batchNo 汇款批次号
     * @return 商户号
     */
    Long queryMemberNoByBatchNo(String batchNo);

    /**
     * 根据ID查询TT编号
     *
     * @param orderId 结汇订单明细ID
     * @return TT编号
     */
    String queryIncomeNoByOrderId(Long orderId);

    /**
     * 根据文件批次查询反洗钱不通过的订单
     *
     * @param fileBatchNo 文件批次
     * @param amlStatus   反洗钱状态
     */
    List<FiCbPayRemittanceDetailV2Do> queryAmlDetailByBatchFileNo(Long fileBatchNo, Integer amlStatus);

    /**
     * 跨境人民订单更新
     *
     * @param remittanceStatus 汇款订单状态
     * @param fileBatchNo      文件批次号
     */
    void updateOrderByFileBatchNo(String remittanceStatus, Long fileBatchNo);

}
