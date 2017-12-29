package com.baofu.cbpayservice.manager.impl;

import com.baofu.cbpayservice.dal.mapper.FiCbPaySettleMapper;
import com.baofu.cbpayservice.dal.mapper.FiCbPaySettleOrderMapper;
import com.baofu.cbpayservice.dal.models.FiCbPaySettleDo;
import com.baofu.cbpayservice.dal.models.FiCbPaySettleOrderDo;
import com.baofu.cbpayservice.manager.CbPaySettleManager;
import com.system.commons.utils.ParamValidate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 跨境结汇订单信息Manager实现
 * <p>
 * User: 不良人 Date:2017/4/13 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
@Repository
public class CbPaySettleManagerImpl implements CbPaySettleManager {

    /**
     * 跨境结汇订单数据服务接口
     */
    @Autowired
    private FiCbPaySettleMapper fiCbPaySettleMapper;
    /**
     * 结汇订单服务接口
     */
    @Autowired
    private FiCbPaySettleOrderMapper fiCbPaySettleOrderMapper;

    /**
     * 根据渠道号和商户外币汇入编号查询结汇信息
     *
     * @param channelId 渠道号
     * @param incomeNo  商户外币汇入编号
     * @return 跨境结汇订单信息
     */
    @Override
    public FiCbPaySettleDo queryByInNoAndChannelId(Long channelId, String incomeNo) {

        return fiCbPaySettleMapper.queryByInNoAndChannelId(channelId, incomeNo);
    }

    /**
     * 新增跨境结汇信息
     *
     * @param fiCbPaySettleDo 结汇信息
     */
    @Override
    @Transactional
    public void addSettle(FiCbPaySettleDo fiCbPaySettleDo) {

        ParamValidate.checkUpdate(fiCbPaySettleMapper.insert(fiCbPaySettleDo), "新增跨境结汇信息异常");
    }

    /**
     * 更具跨境结算订单号查询
     *
     * @param settleOrderId 跨境结汇订单号
     * @return 跨境结算订单信息
     */
    @Override
    public FiCbPaySettleDo queryByOrderId(Long settleOrderId) {

        return fiCbPaySettleMapper.queryByOrderId(settleOrderId);
    }

    /**
     * 查询跨境结汇信息
     *
     * @param fileBatchNo 文件批次号
     * @return 跨境结汇信息
     */
    @Override
    public FiCbPaySettleDo queryByFileBatchNo(Long fileBatchNo) {
        return fiCbPaySettleMapper.queryByFileBatchNo(fileBatchNo);
    }

    /**
     * 修改结汇订单信息
     *
     * @param fiCbPaySettleDo 结汇信息
     */
    @Override
    public void modify(FiCbPaySettleDo fiCbPaySettleDo) {

        ParamValidate.checkUpdate(fiCbPaySettleMapper.updateByKeySelective(fiCbPaySettleDo), "更新结汇订单失败");
    }

    /**
     * 根据渠TT编号查询结汇信息
     *
     * @param incomeNo TT编号
     * @return 跨境结汇订单信息
     */
    @Override
    public FiCbPaySettleDo queryByIncomeNo(String incomeNo) {

        return fiCbPaySettleMapper.queryByIncomeNo(incomeNo);
    }

    /**
     * 根据商户号和文件批次号查询订单信息
     *
     * @param memberId         商户号
     * @param batchNo          文件批次号
     * @param settleOrderCount 单次查询数量
     * @return 结果
     */
    @Override
    public List<FiCbPaySettleOrderDo> queryNeedRiskControlOrder(Long memberId, String batchNo, Integer settleOrderCount) {
        return fiCbPaySettleOrderMapper.queryNeedRiskControlOrder(memberId, batchNo, settleOrderCount);
    }

    /**
     * 根据订单号更新订单风控状态
     *
     * @param fiCbPaySettleOrderDo 订单信息
     */
    @Override
    public void modifyCbPaySettleOrder(FiCbPaySettleOrderDo fiCbPaySettleOrderDo) {
        ParamValidate.checkUpdate(fiCbPaySettleMapper.updateByOrderId(fiCbPaySettleOrderDo.getOrderId(),
                fiCbPaySettleOrderDo.getRisk_flag()), "更新结汇订单失败");
    }

    /**
     * 查询文件
     *
     * @return 结果
     */
    @Override
    public List<String> queryBatchNos(String beginDate, String endDate) {
        return fiCbPaySettleMapper.queryBatchNos(beginDate, endDate);
    }


    /**
     * 根据文件批次号统计订单数量
     *
     * @param batchNo 文件批次号
     * @return 结果
     */
    @Override
    public Long queryCbPaySettleOrderCountByBatchN0(String batchNo) {
        return fiCbPaySettleOrderMapper.queryCbPaySettleOrderCountByBatchN0(batchNo);
    }

    /**
     * 根据文件批次号查询商户号
     *
     * @param batchNo 文件批次号
     * @return
     */
    @Override
    public Long queryMemberIdByBatchNo(String batchNo) {
        return fiCbPaySettleOrderMapper.queryMemberIdByBatchNo(batchNo);
    }

    /**
     * 查询结汇订单信息
     *
     * @param todayBegin       当天时间的开始  2017-08-08 00:00:00
     * @param todayEnd         当天时间的结束  2017-08-08 23:59:59
     * @param oldBegin         指定日期的开始 2017-08-07 00:00:00
     * @param oldEnd           指定日期的结束 2017-08-07 23:59:59
     * @param settleOrderCount 单次查询笔数
     * @return 结汇订单信息
     */
    @Override
    public List<FiCbPaySettleOrderDo> queryNeedRiskControlOrderV2(String todayBegin, String todayEnd, String oldBegin, String oldEnd, Integer settleOrderCount) {
        return fiCbPaySettleOrderMapper.queryNeedRiskControlOrderV2(todayBegin, todayEnd, oldBegin, oldEnd, settleOrderCount);
    }


}
