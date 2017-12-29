package com.baofu.cbpayservice.manager;

import com.baofu.cbpayservice.dal.models.FiCbPayOrderDo;

import java.util.List;

/**
 * 跨境人民币订单信息
 * User: 不良人 Date:2017/2/28 ProjectName: cbpayservice Version: 1.0
 */
public interface FiCbPayOrderManager {

    /**
     * 根据商户号和商户订单号查询跨境订单信息判断信息是否存在
     *
     * @param memberId      商户号
     * @param memberTransId 商户订单号
     * @return 跨境订单信息
     */
    FiCbPayOrderDo queryByTransId(Long memberId, String memberTransId);

    /**
     * 根据商户号和多个商户订单号查询跨境订单信息判断信息是否存在
     *
     * @param memberId       商户号
     * @param memberTransIds 商户订单号集合
     * @return 跨境订单信息
     */
    List<String> selectMerchantTrans(Long memberId, List<String> memberTransIds);
}
