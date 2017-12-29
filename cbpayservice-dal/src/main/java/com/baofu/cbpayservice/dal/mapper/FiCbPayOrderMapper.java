package com.baofu.cbpayservice.dal.mapper;

import com.baofu.cbpayservice.dal.models.FiCbPayEmailDetailDo;
import com.baofu.cbpayservice.dal.models.FiCbPayOrderDo;
import com.baofu.cbpayservice.dal.models.FiCbPayOrderSumDo;
import com.baofu.cbpayservice.dal.models.FiCbPayRemittanceDetailV2Do;
import com.baofu.order.pick.model.Order;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * 跨境人民币订单信息
 * <p>
 * 1、跨境人民订单新增
 * 2、跨境人民订单更新
 * </p>
 * User: 香克斯 Date:2016/10/25 ProjectName: asias-icpaygate Version: 1.0
 */
public interface FiCbPayOrderMapper {

    /**
     * 跨境人民订单新增
     *
     * @param fiCbPayOrderDo 订单参数信息
     * @return 返回受影响行数
     */
    int insert(FiCbPayOrderDo fiCbPayOrderDo);

    /**
     * 跨境人民订单更新
     *
     * @param fiCbPayOrderDo 订单参数信息
     * @return 返回受影响行数
     */
    int update(FiCbPayOrderDo fiCbPayOrderDo);

    /**
     * 唯一条件查询订单，返回匹配唯一的订单或返回空
     *
     * @param orderId 宝付订单号
     * @return 返回订单
     */
    FiCbPayOrderDo selectOrder(Long orderId);

    /**
     * 按商户号和商户订单号查询订单
     *
     * @param memberId      商户编号
     * @param memberTransId 商户订单号
     * @return 查询结果
     */
    FiCbPayOrderDo selectOrderByMemberAndTransId(@Param("memberId") Long memberId,
                                                 @Param("memberTransId") String memberTransId);

    /**
     * 批量新增跨境人民订单
     *
     * @param fiCbPayOrderDos 订单参数信息
     * @return 返回受影响行数
     */
    int batchInsert(List<FiCbPayOrderDo> fiCbPayOrderDos);

    /**
     * 按商户号和文件批次号查询订单
     *
     * @param memberId    商户编号
     * @param batchFileId 文件批次号
     * @return 查询结果
     */
    List<Long> batchQueryOrder(@Param("memberId") Long memberId, @Param("batchFileId") Long batchFileId);

    /**
     * 跨境人民订单批量更新
     *
     * @param remitStatus 汇款状态
     * @param updateBy    更新人
     * @param orderIdList 订单参数信息
     * @param batchNo     汇款批次号
     * @param amlStatus   反洗钱审核状态
     * @return 更新结果
     */
    int batchModifyCbPayOrder(@Param("remitStatus") String remitStatus, @Param("updateBy") String updateBy,
                              @Param("orderIdList") List<Long> orderIdList, @Param("batchNo") String batchNo,
                              @Param("amlStatus") Integer amlStatus);

    /**
     * 查询文件明细成功总金额
     *
     * @param batchFileId 文件批次号
     * @param memberId    商户号
     * @return 成功总金额
     */
    BigDecimal sumSuccessAmt(@Param("batchFileId") Long batchFileId, @Param("memberId") Long memberId);

    /**
     * 跨境人民订单更新
     *
     * @param remittanceStatus 订单参数信息
     * @param updateBy         订单参数信息
     * @param batchNo          订单参数信息
     * @return 更新结果
     */
    int updateCbPayOrderByBatchNo(@Param("remittanceStatus") String remittanceStatus, @Param("updateBy") String updateBy,
                                  @Param("batchNo") String batchNo);

    /**
     * 跨境人民订单更新
     *
     * @param amlStatus   订单参数信息
     * @param updateBy    订单参数信息
     * @param fileBatchId 订单参数信息
     * @return 更新结果
     */
    int updateCbPayOrderByFileBatchId(@Param("amlStatus") Integer amlStatus, @Param("updateBy") String updateBy,
                                      @Param("fileBatchId") Long fileBatchId);

    /**
     * 根据商户号和多个商户订单号查询跨境订单信息判断信息是否存在
     *
     * @param memberId       商户号
     * @param memberTransIds 商户订单号集合
     * @return 跨境订单信息
     */
    List<String> selectMerchantTrans(@Param("memberId") Long memberId, @Param("memberTransIds") List<String>
            memberTransIds);

    /**
     * 根据汇款批次号查询明细信息
     *
     * @param batchNo     汇款批次号
     * @param batchFileId 文件批次号
     * @return 汇款明细信息
     */
    List<FiCbPayRemittanceDetailV2Do> queryRemittanceDetail(@Param("batchNo") String batchNo, @Param("batchFileId") Long batchFileId);

    /**
     * 查询需要风控的订单信息  wdj
     *
     * @param memberId   商户号
     * @param batchNo    批次号
     * @param orderCount 查询订单数量
     * @return 查询结果
     */
    List<FiCbPayOrderDo> queryNeedRiskControlOrder(@Param("memberId") Long memberId, @Param("batchNo") String batchNo,
                                                   @Param("orderCount") Integer orderCount);

    /**
     * 批量更新跨境订单确认状态
     *
     * @param memberId    商户号
     * @param ackStatus   确认状态
     * @param orderIdList orderId集合
     * @param fileBatchNo 文件批次号
     */
    void batchUpdateCbPayOrder(@Param("memberId") Long memberId, @Param("ackStatus") int ackStatus,
                               @Param("orderIdList") List<Long> orderIdList, @Param("batchFileId") Long fileBatchNo);

    /**
     * 文件批次订单总金额
     *
     * @param fileBatchNo 文件批次号
     * @return 订单总金额
     */
    BigDecimal sumOrderTotalAmt(@Param("batchFileId") Long fileBatchNo);

    /**
     * 根据商户号和跨境订单号集合查询
     *
     * @param memberId    商户号
     * @param orderIdList 跨境订单号集合
     * @return 查询结果
     */
    List<Long> queryByOrderId(@Param("memberId") Long memberId, @Param("orderIdList") List<Long> orderIdList);

    /**
     * 根据商户号和起始时间集合查询
     *
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param memberId  商户号
     * @return 查询结果
     */
    FiCbPayOrderSumDo queryOrderByTime(@Param("beginTime") String beginTime, @Param("endTime") String endTime, @Param("memberId") Long memberId);

    /**
     * 根据商户号和起始时间集合查询
     *
     * @param beginTime   开始时间
     * @param endTime     结束时间
     * @param memberId    商户号
     * @param fileBatchNo 文件批次号
     * @param updateBy    更新人
     */
    void updateCbPayOrderByTime(@Param("beginTime") String beginTime, @Param("endTime") String endTime, @Param("memberId") Long memberId,
                                @Param("fileBatchNo") Long fileBatchNo, @Param("updateBy") String updateBy);

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
    Long queryCbPayOrderCount(@Param("beginTime") String beginTime, @Param("endTime") String endTime, @Param("memberId") Long memberId);

    /**
     * 汇款明细
     *
     * @param batchFileId   文件批次号
     * @param amlStatusList 反洗钱审核状态集合
     * @return 汇款备案明细结合
     */
    List<FiCbPayRemittanceDetailV2Do> queryOrderDetail(@Param("batchFileId") Long batchFileId, @Param("amlStatusList") List<Integer> amlStatusList);

    /**
     * 更新订单状态为未确认状态
     *
     * @param fiCbPayOrderDo 更新请求参数
     * @return 返回更新条数
     */
    Long updateAckStausByBatchNo(FiCbPayOrderDo fiCbPayOrderDo);

    /**
     * 汇款明细（机票）
     *
     * @param batchFileId   文件批次号
     * @param amlStatusList 反洗钱审核状态
     * @return 汇款备案明细结合
     */
    List<FiCbPayRemittanceDetailV2Do> queryOrderTicketsDetail(@Param("batchFileId") Long batchFileId,
                                                              @Param("amlStatusList") List<Integer> amlStatusList);

    /**
     * 汇款明细（留学）
     *
     * @param batchFileId   文件批次号
     * @param amlStatusList 反洗钱审核状态
     * @return 汇款备案明细结合
     */
    List<FiCbPayRemittanceDetailV2Do> queryOrderStudyAbroadDetail(@Param("batchFileId") Long batchFileId,
                                                                  @Param("amlStatusList") List<Integer> amlStatusList);

    /**
     * 汇款明细（酒店）
     *
     * @param batchFileId   文件批次号
     * @param amlStatusList 反洗钱审核状态
     * @return 汇款备案明细结合
     */
    List<FiCbPayRemittanceDetailV2Do> queryOrderHotelDetail(@Param("batchFileId") Long batchFileId,
                                                            @Param("amlStatusList") List<Integer> amlStatusList);

    /**
     * 汇款明细（旅游）
     *
     * @param batchFileId   文件批次号
     * @param amlStatusList 反洗钱审核状态
     * @return 汇款备案明细结合
     */
    List<FiCbPayRemittanceDetailV2Do> queryOrderTourismDetail(@Param("batchFileId") Long batchFileId,
                                                              @Param("amlStatusList") List<Integer> amlStatusList);

    /**
     * 根据汇款批次号查询记录数
     *
     * @param batchNo 汇款平批次号
     * @return 结果
     */
    Long queryCbPayOrderCountByBatchN0(@Param("batchNo") String batchNo);

    /**
     * 根据文件批次号查询出对应的商户号
     *
     * @param batchNo 文件批次号
     * @return 商户号
     */
    Long queryMemberNoByBatchNo(@Param("batchNo") String batchNo);

    /**
     * 获取TT编号
     *
     * @param orderId 结汇订单ID
     * @return TT编号
     */
    String queryIncomeNoByOrderId(@Param("orderId") Long orderId);

    /**
     * 根据文件批次查询反洗钱不通过的订单
     *
     * @param fileBatchNo 文件批次
     * @param amlStatus   反洗钱状态
     * @return 汇款备案明细结合
     */
    List<FiCbPayRemittanceDetailV2Do> queryAmlDetailByBatchFileNo(@Param("fileBatchNo") Long fileBatchNo, @Param("amlStatus") Integer amlStatus);

    /**
     * 统计剩余订单可用金额
     *
     * @param memberId 商户号
     * @return 结果
     */
    BigDecimal countOrderAmountByMemberId(@Param("memberId") Long memberId);

    /**
     * 查询订单可用订单
     *
     * @param memberId 商户号
     * @return 结果
     */
    List<Order> selectBatchRemitOrder(@Param("memberId") Long memberId);

    /**
     * 批量汇款更新跨境订单信息
     */
    void batchRemitUpdate(@Param("orderIdList") List<Long> orderIdList, @Param("fileBatchNo") Long fileBatchNo,
                          @Param("memberId") Long memberId);

    /**
     * 跨境人民订单更新
     *
     * @param status 汇款状态
     * @param fileBatchNo      文件批次号
     * @return 更新结果
     */
    int updateOrderByFileBatchNo(@Param("status") String status, @Param("fileBatchNo") Long fileBatchNo);
}