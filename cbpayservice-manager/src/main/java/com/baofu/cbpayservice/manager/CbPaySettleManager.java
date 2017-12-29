package com.baofu.cbpayservice.manager;

import com.baofu.cbpayservice.dal.models.FiCbPaySettleDo;
import com.baofu.cbpayservice.dal.models.FiCbPaySettleOrderDo;

import java.util.List;

/**
 * 跨境结汇订单信息Manager
 * <p>
 * User: 不良人 Date:2017/4/13 ProjectName: cbpayservice Version: 1.0
 */
public interface CbPaySettleManager {

    /**
     * 根据渠道号和商户外币汇入编号
     *
     * @param channelId 渠道号
     * @param incomeNo  商户外币汇入编号
     * @return 跨境结汇订单信息
     */
    FiCbPaySettleDo queryByInNoAndChannelId(Long channelId, String incomeNo);

    /**
     * 新增跨境结汇信息
     *
     * @param fiCbPaySettleDo 结汇信息
     */
    void addSettle(FiCbPaySettleDo fiCbPaySettleDo);

    /**
     * 查询跨境结汇信息
     *
     * @param settleOrderId 跨境结汇订单号
     */
    FiCbPaySettleDo queryByOrderId(Long settleOrderId);

    /**
     * 查询跨境结汇信息
     * @param fileBatchNo 文件批次号
     * @return 跨境结汇信息
     */
    FiCbPaySettleDo queryByFileBatchNo(Long fileBatchNo);

    /**
     * 修改结汇订单信息
     *
     * @param fiCbPaySettleDo 结汇信息
     */
    void modify(FiCbPaySettleDo fiCbPaySettleDo);

    /**
     * 根据渠TT编号查询结汇信息
     *
     * @param incomeNo TT编号
     * @return 跨境结汇订单信息
     */
    FiCbPaySettleDo queryByIncomeNo(String incomeNo);

    /**
     * 更新结汇订单信息
     *
     * @param fiCbPaySettleOrderDo 结汇订单信息
     */
    void modifyCbPaySettleOrder(FiCbPaySettleOrderDo fiCbPaySettleOrderDo);

    /**
     * 根据商户号和文件批次号查询结汇订单
     *
     * @param memberId         商户号
     * @param batchNo          文件批次号
     * @param settleOrderCount 单次查询数量
     * @return 结果
     */
    List<FiCbPaySettleOrderDo> queryNeedRiskControlOrder(Long memberId, String batchNo, Integer settleOrderCount);

    /**
     * 查询出当天的结汇
     *
     * @param beginDate 开始时间
     * @param endDate   结束时间
     * @return 结汇
     */
    List<String> queryBatchNos(String beginDate, String endDate);

    /**
     * 结汇订单统计
     *
     * @param batchNo 文件批次号
     * @return 结汇订单数量
     */
    Long queryCbPaySettleOrderCountByBatchN0(String batchNo);

    /**
     * 根据文件批次号查询商户号
     *
     * @param batchNo 文件批次号
     * @return 商户号
     */
    Long queryMemberIdByBatchNo(String batchNo);

    /**
     * 查询待风控的结汇订单
     *
     * @param todayBegin       当天时间的开始  2017-08-08 00:00:00
     * @param todayEnd         当天时间的结束  2017-08-08 23:59:59
     * @param oldBegin         指定日期的开始 2017-08-07 00:00:00
     * @param oldEnd           指定日期的结束 2017-08-07 23:59:59
     * @param settleOrderCount 结汇订单数量
     * @return 结果
     */
    List<FiCbPaySettleOrderDo> queryNeedRiskControlOrderV2(String todayBegin, String todayEnd, String oldBegin,
                                                           String oldEnd, Integer settleOrderCount);

}
