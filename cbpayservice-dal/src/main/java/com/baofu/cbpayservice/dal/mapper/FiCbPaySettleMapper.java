package com.baofu.cbpayservice.dal.mapper;

import com.baofu.cbpayservice.dal.models.FiCbPaySettleDo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FiCbPaySettleMapper {

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(FiCbPaySettleDo record);

    /**
     * 根据OrderId更新数据
     *
     * @param record
     */
    int updateByKeySelective(FiCbPaySettleDo record);

    /**
     * 根据渠道号和商户外币汇入编号查询结汇信息
     *
     * @param channelId 渠道号
     * @param incomeNo  商户外币汇入编号
     * @return 跨境结汇订单信息
     */
    FiCbPaySettleDo queryByInNoAndChannelId(@Param("channelId") Long channelId,
                                            @Param("incomeNo") String incomeNo);

    /**
     * 更具跨境结算订单号查询
     *
     * @param settleOrderId 跨境结汇订单号
     * @return 跨境结算订单信息
     */
    FiCbPaySettleDo queryByOrderId(@Param("orderId") Long settleOrderId);

    /**
     * 根据文件批次号查询
     * @param fileBatchNo 文件批次号
     * @return 跨境结算订单信息
     */
    FiCbPaySettleDo queryByFileBatchNo(@Param("fileBatchNo") Long fileBatchNo);

    /**
     * 根据TT编号查询
     *
     * @param incomeNo TT编号
     * @return 银行到账通知对象
     */
    FiCbPaySettleDo queryByIncomeNo(@Param("incomeNo") String incomeNo);

    /**
     * 根据订单号更新订单信息
     *
     * @param orderId  订单号
     * @param riskFlag 风控标识   1：已风控  0：未风控
     * @return 跟新结果
     */
    int updateByOrderId(@Param("orderId") Long orderId, @Param("riskFlag") int riskFlag);

    /**
     * 查询文件批次号
     *
     * @return 结果
     */
    List<String> queryBatchNos(@Param("beginDate") String beginDate, @Param("endDate") String endDate);

}