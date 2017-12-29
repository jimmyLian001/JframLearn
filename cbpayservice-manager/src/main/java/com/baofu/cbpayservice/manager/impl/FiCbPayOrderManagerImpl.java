package com.baofu.cbpayservice.manager.impl;

import com.baofu.cbpayservice.dal.mapper.FiCbPayOrderMapper;
import com.baofu.cbpayservice.dal.models.FiCbPayOrderDo;
import com.baofu.cbpayservice.manager.FiCbPayOrderManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 跨境订单信息相关操作
 * <p>
 * User: 不良人 Date:2017/2/28 ProjectName: cbpayservice Version: 1.0
 */
@Repository
@Slf4j
public class FiCbPayOrderManagerImpl implements FiCbPayOrderManager {

    /**
     * 跨境人民币数据库操作服务
     */
    @Autowired
    private FiCbPayOrderMapper fiCbPayOrderMapper;

    /**
     * 根据商户号和商户订单号查询跨境订单信息判断信息是否存在
     * 存在抛异常提示
     *
     * @param memberId      商户号
     * @param memberTransId 商户订单号
     * @return 跨境订单信息
     */
    @Override
    public FiCbPayOrderDo queryByTransId(Long memberId, String memberTransId) {

        FiCbPayOrderDo fiCbPayOrderDo = fiCbPayOrderMapper.selectOrderByMemberAndTransId(memberId, memberTransId);

        return fiCbPayOrderDo;
    }

    /**
     * 根据商户号和多个商户订单号查询跨境订单信息判断信息是否存在
     *
     * @param memberId       商户号
     * @param memberTransIds 商户订单号集合
     * @return 跨境订单信息
     */
    @Override
    public List<String> selectMerchantTrans(Long memberId, List<String> memberTransIds) {
        return fiCbPayOrderMapper.selectMerchantTrans(memberId, memberTransIds);
    }
}
