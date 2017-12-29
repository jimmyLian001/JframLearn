package com.baofu.cbpayservice.manager;

import com.baofu.cbpayservice.dal.models.FiCbPayVerifyDo;
import com.baofu.cbpayservice.dal.models.VerifyCountResultDo;

import java.util.List;

/**
 * 订单操作Manager服务
 * <p>
 * 1、初始化订单信息
 * </p>
 * User: 香克斯 Date:2016/9/23 ProjectName: asias-icpservice Version: 1.0
 */
public interface FiCbPayVerifyManager {

    /**
     * 添加订单总表信息
     *
     * @param fiCbPayVerifyDo 订单总表信息
     */
    void insert(FiCbPayVerifyDo fiCbPayVerifyDo);

    /**
     * 查询需要实名认证的订单  购付汇
     *
     * @param fileBatchNo 文件批次号
     * @param memberId    商户号
     * @param authCount   认证数量
     * @return 查询结果
     */
    List<FiCbPayVerifyDo> queryNeedVerify(Long fileBatchNo, Long memberId, Integer authCount);

    /**
     * 抽查实名认证   结汇
     *
     * @param fileBatchNo 文件批次号
     * @param memberId    商户号
     * @param authCount   抽查数量
     * @return 返回查询结果
     */
    List<FiCbPayVerifyDo> queryNeedVerifyOfSettle(Long fileBatchNo, Long memberId, Integer authCount);

    /**
     * 根据宝付订单号查询
     *
     * @param orderId 宝付订单号
     * @return 查询结果
     */
    int queryVertifyByOrderId(Long orderId);

    /**
     * 根据宝付订单号更新
     *
     * @param fiCbPayVerifyDo 更新信息
     */
    void updateVertifyByOrderId(FiCbPayVerifyDo fiCbPayVerifyDo);

    /**
     * 统计海关报关数据
     *
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @return 统计结果
     */
    List<VerifyCountResultDo> statisticVerifyResult(String beginTime, String endTime);

}
