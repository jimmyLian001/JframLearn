package com.baofu.cbpayservice.dal.mapper;

import com.baofu.cbpayservice.dal.models.FiCbPayVerifyDo;
import com.baofu.cbpayservice.dal.models.VerifyCountResultDo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 征信操作mapper服务
 * <p>
 * 1、添加身份认证信息
 * </p>
 * User: 香克斯 Date:2016/10/25 ProjectName: asias-icpaygate Version: 1.0
 */
public interface FiCbPayVerifyMapper {

    /**
     * 添加身份认证信息
     *
     * @param fiCbPayVerifyDo 认证信息
     * @return 返回受影响行数
     */
    int insert(FiCbPayVerifyDo fiCbPayVerifyDo);

    /**
     * 查询需要实名认证的数据  购付汇
     *
     * @param fileBatchNo 文件批次号
     * @param memberId    商户编号
     * @param authCount   随机抽取的数量
     * @return 抽取结果
     */
    List<FiCbPayVerifyDo> selectNeedVerify(@Param("fileBatchNo") Long fileBatchNo, @Param("memberId") Long memberId,
                                           @Param("authCount") Integer authCount);

    /**
     * 查询需要实名认证的记录   结汇
     *
     * @param fileBatchNo 文件批次号
     * @param memberId    商户号
     * @param authCount   认证数量
     * @return 查询结果
     */
    List<FiCbPayVerifyDo> queryNeedVerifyOfSettle(@Param("fileBatchNo") Long fileBatchNo, @Param("memberId") Long memberId,
                                                  @Param("authCount") Integer authCount);

    int selectVertifyByOrderId(@Param("orderId") Long orderId);

    int updateVertifyByOrderId(FiCbPayVerifyDo fiCbPayVerifyDo);

    /**
     * 统计海关报关数据
     *
     * @return 统计结果
     */
    List<VerifyCountResultDo> statisticVerifyResult(@Param("beginTime") String beginTime, @Param("endTime") String endTime);
}