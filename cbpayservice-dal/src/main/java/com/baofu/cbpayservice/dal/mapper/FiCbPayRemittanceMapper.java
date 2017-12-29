package com.baofu.cbpayservice.dal.mapper;

import com.baofu.cbpayservice.dal.models.FiCbPayRemittanceAdditionDo;
import com.baofu.cbpayservice.dal.models.FiCbPayRemittanceDo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * 跨境人民币汇款操作
 * <p>
 * 1、跨境人民汇款订单新增
 * 2、跨境人民汇款明细新增
 * 3、查询跨境人民汇款订单信息
 * 4、查询跨境人民汇款订单明细信息
 * 5、跨境人民汇款订单状态更新
 * </p>
 * User: wanght Date:2016/11/10 ProjectName: asias-icpaygate Version: 1.0
 */
public interface FiCbPayRemittanceMapper {

    /**
     * 跨境人民汇款订单新增
     *
     * @param fiCbPayRemittanceDo 订单参数信息
     * @return 返回受影响行数
     */
    int createRemittanceOrder(FiCbPayRemittanceDo fiCbPayRemittanceDo);

    /**
     * 跨境人民汇款订单附加信息
     *
     * @param fiCbPayRemittanceAdditionDo 附加参数信息
     * @return 返回受影响行数
     */
    int createRemittanceAddition(FiCbPayRemittanceAdditionDo fiCbPayRemittanceAdditionDo);

    /**
     * 查询跨境人民汇款订单信息
     *
     * @param batchNo 批次号
     * @return 汇款订单信息
     */
    FiCbPayRemittanceDo queryRemittanceOrder(@Param("batchNo") String batchNo);

    /**
     * 查询跨境人民所有初始化汇款订单信息
     *
     * @param channelId      渠道id
     * @param purchaseStatus 购汇状态
     * @param time           时间
     * @return 汇款订单集合
     */
    List<FiCbPayRemittanceDo> queryInitRemittanceOrder(@Param("channelId") Long channelId,
                                                       @Param("purchaseStatus") Integer purchaseStatus,
                                                       @Param("createTime") String time);

    /**
     * 查询跨境人民汇款附加信息
     *
     * @param batchNo  批次号
     * @param memberNo 商户号
     * @return 汇款附加信息
     */
    FiCbPayRemittanceAdditionDo queryRemittanceAddition(@Param("batchNo") String batchNo, @Param("memberNo") Long memberNo);

    /**
     * 跨境人民汇款订单状态更新
     *
     * @param fiCbPayRemittanceDo 汇款订单参数信息
     * @return 更新结果
     */
    int updateRemittanceOrder(FiCbPayRemittanceDo fiCbPayRemittanceDo);

    /**
     * 跨境人民汇款附加信息更新
     *
     * @param fiCbPayRemittanceAdditionDo 汇款附加参数信息
     * @return 更新结果
     */
    int updateRemittanceAddition(FiCbPayRemittanceAdditionDo fiCbPayRemittanceAdditionDo);

    /**
     * 根据汇款编号查询汇款申请信息
     *
     * @param batchNo 批次号
     * @return 返回结果
     */
    FiCbPayRemittanceDo selectByBatchNo(String batchNo);

    /**
     * 查询批次总金额
     *
     * @param list 文件批次号
     * @return 批次总金额
     */
    BigDecimal queryRemittanceByBatchNos(List<Long> list);

    /**
     * 查询汇款订单批次号
     *
     * @param beginDate 开始时间
     * @param endDate   结束时间
     * @return 汇款批次号集合
     */
    List<String> queryBatchNos(@Param("beginDate") String beginDate, @Param("endDate") String endDate);

    /**
     * 根据商户号和订单号查询
     *
     * @param memberId 商户号
     * @param batchNo  订单号
     * @return 汇款订单信息
     */
    FiCbPayRemittanceDo queryRemitByBatchNoMemberId(@Param("memberId") Long memberId, @Param("batchNo") String batchNo);

    /**
     * @param list 文件批次号
     * @return 结果
     */
    int countChannelStatusByBatchNo(@Param("list") List<String> list);

    /**
     * 根据文件批次号查询订单行业类型总数
     *
     * @param list 文件批次号
     * @return 文件批次行业类型之和
     */
    int checkCareerTypeByBatchNos(List<Long> list);
}