package com.baofu.cbpayservice.dal.mapper;

import com.baofu.cbpayservice.dal.models.FiCbPaySettleOrderDo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FiCbPaySettleOrderMapper {

    /**
     * 批量更新
     *
     * @param batchNo         文件批次
     * @param detailsOrderIds orderids
     * @param amlStatus       反洗钱状态
     * @return
     */
    int batchUpdateByOrderId(@Param("batchNo") Long batchNo, @Param("detailsOrderIds") List<Long> detailsOrderIds, @Param("amlStatus") int amlStatus);

    /**
     * 批量更新除开指定的结汇订单状态
     *
     * @param batchNo           文件批次
     * @param exDetailsOrderIds orderids
     * @param amlStatus         反洗钱状态
     * @return
     */
    int batchUpdateByExOrderIds(@Param("batchNo") Long batchNo, @Param("detailsOrderIds") List<Long> exDetailsOrderIds, @Param("amlStatus") int amlStatus);

    /**
     * 批量插入数据库记录
     *
     * @param fiCbPayOrderDoList
     */
    void batchInsert(List<FiCbPaySettleOrderDo> fiCbPayOrderDoList);

    /**
     * 批量查询结汇订单明细
     *
     * @param memberId       商户号
     * @param memberTransIds 商户订单集合
     * @return 查询商户订单集合
     */
    List<String> selectMerchantTrans(@Param("memberId") Long memberId, @Param("memberTransIds") List<String> memberTransIds);

    /**
     * 查询批次号和指定OrderId的订单
     *
     * @param batchNo 文件批次号
     * @return List<FiCbPaySettleOrderDo>  批次文件明细
     */
    List<FiCbPaySettleOrderDo> selectByBatchNo(@Param("batchNo") Long batchNo, @Param("detailsOrderIds") List<Long> detailsOrderIds, @Param("amlStatus") Integer amlStatus);

    /**
     * 根据文件批次查询结汇明细信息
     *
     * @param batchNo 文件批次号
     * @return List<FiCbPaySettleOrderDo>  批次文件明细
     */
    List<FiCbPaySettleOrderDo> selectBySettleInfo(Long batchNo);

    /**
     * 查询结汇需要风控的订单
     *
     * @param batchNo          文件批次号
     * @param settleOrderCount 风控数量
     * @return 结果
     */
    List<FiCbPaySettleOrderDo> queryNeedRiskControlOrder(@Param("memberId") Long memberId, @Param("batchNo") String batchNo,
                                                         @Param("settleOrderCount") Integer settleOrderCount);

    /**
     * 查询批次号和非指定OrderId的订单
     *
     * @param batchNo 文件批次号
     * @return List<FiCbPaySettleOrderDo>  批次文件明细
     */
    List<FiCbPaySettleOrderDo> selectByExOrderIds(@Param("batchNo") Long batchNo,
                                                  @Param("detailsOrderIds") List<Long> detailsOrderIds,
                                                  @Param("amlStatus") Integer amlStatus);

    /**
     * 根据文件批次号统计订单数量
     *
     * @param batchNo 文件批次号
     * @return 结果
     */
    Long queryCbPaySettleOrderCountByBatchN0(@Param("batchNo") String batchNo);

    /**
     * 根据文件批次号查询商户号
     *
     * @param batchNo 文件批次号
     * @return 商户号
     */
    Long queryMemberIdByBatchNo(@Param("batchNo") String batchNo);

    /**
     * 查询结汇订单信息
     *
     * @param todayBegin       当天开始时间
     * @param todayEnd         当天结束时间
     * @param oldBegin         指定开始时间
     * @param oldEnd           指定结束时间
     * @param settleOrderCount 单笔查询数量
     * @return 结果
     */
    List<FiCbPaySettleOrderDo> queryNeedRiskControlOrderV2(@Param("todayBegin") String todayBegin, @Param("todayEnd") String todayEnd,
                                                           @Param("oldBegin") String oldBegin, @Param("oldEnd")
                                                           String oldEnd, @Param("settleOrderCount") Integer settleOrderCount);
    /**
     * 根据转账汇总订单号查询结汇明细信息
     * @param fiCbPaySettleOrderDo 查询条件
     * @return List<FiCbPaySettleOrderDo>  批次文件明细
     */
    List<FiCbPaySettleOrderDo> querySettleOrderInfo(FiCbPaySettleOrderDo fiCbPaySettleOrderDo);
}