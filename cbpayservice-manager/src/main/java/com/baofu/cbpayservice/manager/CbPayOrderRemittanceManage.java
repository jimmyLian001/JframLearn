package com.baofu.cbpayservice.manager;

import com.baofu.cbpayservice.dal.models.FiCbPayOrderSumDo;

import java.math.BigDecimal;
import java.util.List;

/**
 * 提现订单(非文件上传订单)
 * <p>
 * User: 不良人 Date:2017/5/10 ProjectName: cbpayservice Version: 1.0
 */
public interface CbPayOrderRemittanceManage {

    /**
     * 批量更新跨境订单状态
     *
     * @param memberId    商户号
     * @param orderIdList 跨境订单号
     * @param fileBatchNo 文件批次号
     */
    void batchModifyCbPayOrder(Long memberId, List<Long> orderIdList, Long fileBatchNo);

    /**
     * 文件批次订单总金额
     *
     * @param fileBatchNo 文件批次号
     * @return 订单总金额
     */
    BigDecimal sumOrderTotalAmt(Long fileBatchNo);

    /**
     * 根据商户号和跨境订单号集合查询
     *
     * @param memberId    商户号
     * @param orderIdList 跨境订单号集合
     * @return 查询结果
     */
    List<Long> queryByOrderId(Long memberId, List<Long> orderIdList);

    /**
     * 根据商户号和起始时间集合查询
     *
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param memberId  商户号
     * @return 查询结果
     */
    FiCbPayOrderSumDo queryOrderByTime(String beginTime, String endTime, Long memberId);

    /**
     * 根据商户号和起始时间集合查询
     *
     * @param beginTime   开始时间
     * @param endTime     结束时间
     * @param memberId    商户号
     * @param fileBatchNo 文件批次号
     * @param updateBy    更新人
     */
    void updateCbPayOrderByTime(String beginTime, String endTime, Long memberId, Long fileBatchNo, String updateBy);
}
