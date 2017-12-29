package com.baofu.cbpayservice.manager.impl;

import com.baofu.cbpayservice.dal.mapper.FiCbPayVerifyMapper;
import com.baofu.cbpayservice.dal.models.FiCbPayVerifyDo;
import com.baofu.cbpayservice.dal.models.VerifyCountResultDo;
import com.baofu.cbpayservice.manager.FiCbPayVerifyManager;
import com.system.commons.utils.ParamValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 征信操作Manager服务实现
 * <p>
 * 1、添加身份认证信息
 * </p>
 * User: wanght Date:2017/1/12 ProjectName: cbpay-service Version: 1.0
 */
@Repository
public class FiCbPayVerifyManagerImpl implements FiCbPayVerifyManager {

    /**
     * 征信信息操作mapper
     */
    @Autowired
    private FiCbPayVerifyMapper fiCbPayVerifyMapper;

    /**
     * 添加身份认证信息
     *
     * @param fiCbPayVerifyDo 认证信息
     */
    @Override
    @Transactional
    public void insert(FiCbPayVerifyDo fiCbPayVerifyDo) {
        ParamValidate.checkUpdate(fiCbPayVerifyMapper.insert(fiCbPayVerifyDo), "添加征信信息异常");
    }

    /**
     * 查询需要实名认证的记录   购付汇
     *
     * @param fileBatchNo
     * @param memberId
     * @param authCount
     * @return
     */
    @Override
    public List<FiCbPayVerifyDo> queryNeedVerify(Long fileBatchNo, Long memberId, Integer authCount) {
        return fiCbPayVerifyMapper.selectNeedVerify(fileBatchNo, memberId, authCount);
    }

    /**
     * 查询需要进行实名认证的记录   结汇
     *
     * @param fileBatchNo 文件批次号
     * @param memberId    商户号
     * @param authCount   抽查数量
     * @return 查询结果
     */
    @Override
    public List<FiCbPayVerifyDo> queryNeedVerifyOfSettle(Long fileBatchNo, Long memberId, Integer authCount) {
        return fiCbPayVerifyMapper.queryNeedVerifyOfSettle(fileBatchNo, memberId, authCount);
    }

    /**
     * 根据宝付订单号查询
     *
     * @param orderId 宝付订单号
     * @return 结果
     */
    @Override
    public int queryVertifyByOrderId(Long orderId) {
        return fiCbPayVerifyMapper.selectVertifyByOrderId(orderId);
    }

    /**
     * 根据宝付订单号更新信息
     *
     * @param fiCbPayVerifyDo 更新信息
     */
    @Override
    public void updateVertifyByOrderId(FiCbPayVerifyDo fiCbPayVerifyDo) {
        ParamValidate.checkUpdate(fiCbPayVerifyMapper.updateVertifyByOrderId(fiCbPayVerifyDo), "更新实名认证信息异常");
    }

    /**
     * 统计海关报关结果
     *
     * @return 统计结果
     */
    @Override
    public List<VerifyCountResultDo> statisticVerifyResult(String beginTime, String endTime) {
        return fiCbPayVerifyMapper.statisticVerifyResult(beginTime, endTime);
    }

}
