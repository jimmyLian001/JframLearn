package com.baofu.cbpayservice.manager.impl;

import com.baofu.cbpayservice.common.enums.OrederAckStatusEnum;
import com.baofu.cbpayservice.dal.mapper.FiCbPayOrderMapper;
import com.baofu.cbpayservice.dal.models.FiCbPayOrderSumDo;
import com.baofu.cbpayservice.manager.CbPayOrderRemittanceManage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单汇款(非文件上传订单)
 * <p>
 * User: 不良人 Date:2017/5/10 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
@Repository
public class CbPayOrderRemittanceManageImpl implements CbPayOrderRemittanceManage {

    /**
     * 提现订单操作
     */
    @Autowired
    private FiCbPayOrderMapper fiCbPayOrderMapper;

    /**
     * 批量更新跨境订单状态
     *
     * @param memberId    商户号
     * @param orderIdList 跨境订单号
     */
    @Override
    public void batchModifyCbPayOrder(Long memberId, List<Long> orderIdList, Long fileBatchNo) {
        fiCbPayOrderMapper.batchUpdateCbPayOrder(memberId, OrederAckStatusEnum.YES.getCode(), orderIdList, fileBatchNo);
    }

    /**
     * 文件批次订单总金额
     *
     * @param fileBatchNo 文件批次号
     * @return 订单总金额
     */
    @Override
    public BigDecimal sumOrderTotalAmt(Long fileBatchNo) {
        return fiCbPayOrderMapper.sumOrderTotalAmt(fileBatchNo);
    }

    /**
     * 根据商户号和跨境订单号集合查询
     *
     * @param memberId    商户号
     * @param orderIdList 跨境订单号集合
     * @return 查询结果
     */
    @Override
    public List<Long> queryByOrderId(Long memberId, List<Long> orderIdList) {
        return fiCbPayOrderMapper.queryByOrderId(memberId, orderIdList);
    }

    /**
     * 根据商户号和起始时间集合查询
     *
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param memberId  商户号
     * @return 查询结果
     */
    @Override
    public FiCbPayOrderSumDo queryOrderByTime(String beginTime, String endTime, Long memberId) {
        return fiCbPayOrderMapper.queryOrderByTime(beginTime, endTime, memberId);
    }

    /**
     * 根据商户号和起始时间集合查询
     *
     * @param beginTime   开始时间
     * @param endTime     结束时间
     * @param memberId    商户号
     * @param fileBatchNo 文件批次号
     * @param updateBy    更新人
     */
    @Override
    public void updateCbPayOrderByTime(String beginTime, String endTime, Long memberId, Long fileBatchNo, String updateBy) {
        fiCbPayOrderMapper.updateCbPayOrderByTime(beginTime, endTime, memberId, fileBatchNo, updateBy);
    }
}
