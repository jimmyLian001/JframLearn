package com.baofu.cbpayservice.dal.mapper;

import com.baofu.cbpayservice.dal.models.FiCbPayMemberApiRqstDo;
import org.apache.ibatis.annotations.Param;

/**
 *  FiCbPayMemberApiRqstMapper
 */
public interface FiCbPayMemberApiRqstMapper {

    /**
     * 根据组件查询
     *
     * @param businessNo 业务号
     * @return 请求信息
     */
    FiCbPayMemberApiRqstDo selectByKey(Long businessNo);

    /**
     * 插入数据
     *
     * @param apiRqstDo 请求信息
     * @return 影响行数
     */
    int insert(FiCbPayMemberApiRqstDo apiRqstDo);

    /**
     * 根据商户流水号和商户号查询
     *
     * @param memberId     商户号
     * @param memberReqId  商户请求流水号
     * @param businessType 业务类型
     * @return 请求信息
     */
    FiCbPayMemberApiRqstDo queryByMIdReqId(@Param("memberId") Long memberId, @Param("memberReqId") String memberReqId,
                                           @Param("businessType") String businessType);

    /**
     * 根据申请单号统计申请记录数
     *
     * @param remitApplyNo 商户申请单号
     * @param memberId     商户号
     * @return 结果
     */
    Integer countByMemberReqId(@Param("memberId") Long memberId, @Param("remitApplyNo") String remitApplyNo);

    /**
     * @param remitApplyNo 商户汇款申请编号
     * @param proxyOrderId 汇款批次号
     * @return 结果
     */
    int updateApiRqstInfoByReqNo(@Param("remitApplyNo") String remitApplyNo, @Param("proxyOrderId") Long proxyOrderId);

    /**
     * @param memberReqId  商户请求ID
     * @param rst          0-待回执、1-已回执、2-已通知
     * @param businessType 业务类型
     * @return 结果
     */
    int updateApiRqstInfoStatusByReqNo(@Param("memberReqId") String memberReqId, @Param("rst") int rst,
                                       @Param("businessType") String businessType);
}