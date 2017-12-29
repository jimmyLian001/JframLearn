package com.baofu.cbpayservice.manager;

import com.baofu.cbpayservice.dal.models.FiCbPayRemittanceAdditionDo;
import com.baofu.cbpayservice.dal.models.FiCbPayRemittanceDo;

import java.math.BigDecimal;
import java.util.List;

/**
 * 跨境人民币汇款订单信息Manager
 * <p>
 * 1、跨境人民汇款订单新增
 * 2、跨境人民汇款明细新增
 * 3、查询跨境人民汇款订单信息
 * </p>
 * User: wanght Date:2016/10/25 ProjectName: asias-icpaygate Version: 1.0
 */
public interface CbPayRemittanceManager {

    /**
     * 跨境人民汇款订单新增
     *
     * @param fiCbPayRemittanceDo 汇款订单参数信息
     */
    void createRemittanceOrder(FiCbPayRemittanceDo fiCbPayRemittanceDo);

    /**
     * 跨境人民汇款订单附加信息
     *
     * @param fiCbPayRemittanceAdditionDo 附加参数信息
     */
    void createRemittanceAddition(FiCbPayRemittanceAdditionDo fiCbPayRemittanceAdditionDo);

    /**
     * 查询跨境人民汇款订单信息
     *
     * @param batchNo 批次号
     * @return 汇款信息
     */
    FiCbPayRemittanceDo queryRemittanceOrder(String batchNo);

    /**
     * 查询跨境人民所有初始化汇款订单信息
     *
     * @param channelId      渠道id
     * @param purchaseStatus 购汇状态
     * @param time           时间
     * @return 汇款订单列表
     */
    List<FiCbPayRemittanceDo> queryInitRemittanceOrder(Long channelId, Integer purchaseStatus, String time);

    /**
     * 查询跨境人民汇款附加信息
     *
     * @param batchNo  批次号
     * @param memberId 商户号
     * @return 汇款附加信息
     */
    FiCbPayRemittanceAdditionDo queryRemittanceAddition(String batchNo, Long memberId);

    /**
     * 跨境人民汇款订单状态更新
     *
     * @param fiCbPayRemittanceDo 汇款订单参数信息
     */
    void updateRemittanceOrder(FiCbPayRemittanceDo fiCbPayRemittanceDo);

    /**
     * 跨境人民汇款附加信息更新
     *
     * @param fiCbPayRemittanceAdditionDo 汇款附加参数信息
     */
    void updateRemittanceAddition(FiCbPayRemittanceAdditionDo fiCbPayRemittanceAdditionDo);

    /**
     * 根据batchNo查询汇款申请信息
     *
     * @param batchNo 批次号
     * @return 返回汇款信息
     */
    FiCbPayRemittanceDo queryRemittanceByBatchNo(String batchNo);

    /**
     * 查询反洗钱初始化或通过的总金额
     *
     * @param list 汇款批次号
     * @return 订单总金额
     */
    BigDecimal queryRemittanceByBatchNos(List<Long> list);

    /**
     * 查询汇款订单批次号
     *
     * @param beginDate 开始时间
     * @param endDate   结束时间
     * @return 结果
     */
    List<String> queryBatchNos(String beginDate, String endDate);

    /**
     * 根据商户号和订单号查询
     *
     * @param memberId 商户号
     * @param batchNo  订单号
     * @return 汇款订单信息
     */
    FiCbPayRemittanceDo queryRemitByBatchNoMemberId(Long memberId, String batchNo);

    /**
     * 查询跨境人民汇款订单信息行业类型总数
     *
     * @param list 汇款文件批次号
     * @return 行业类型总数
     */
    int checkCareerTypeByBatchNos(List<Long> list);


}
