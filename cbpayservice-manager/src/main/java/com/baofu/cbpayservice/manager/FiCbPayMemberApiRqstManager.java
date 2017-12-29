package com.baofu.cbpayservice.manager;

import com.baofu.cbpayservice.dal.models.FiCbPayFileUploadDo;
import com.baofu.cbpayservice.dal.models.FiCbPayMemberApiRqstDo;

/**
 * Created by 莫小阳 on 2017/9/28.
 */
public interface FiCbPayMemberApiRqstManager {

    /**
     * 保存商户请求信息
     *
     * @param fiCbPayMemberApiRqstDo 商户请求参数
     */
    void addFiCbPayMemberApiRqst(FiCbPayMemberApiRqstDo fiCbPayMemberApiRqstDo);

    /**
     * 根据申请单号查询申请记录数
     *
     * @param remitApplyNo 商户申请单号
     * @param memberId     商户号
     * @return 结果
     */
    Integer countByMemberReqId(Long memberId, String remitApplyNo);

    /**
     * @param remitApplyNo 商户汇款申请编号
     * @param proxyOrderId 汇款批次号
     */
    void updateApiRqstInfoByReqNo(String remitApplyNo, Long proxyOrderId);

    /**
     * 根据业务编号查询商户请求信息
     *
     * @param bfBatchId 请求对应业务编号
     * @return
     */
    FiCbPayMemberApiRqstDo queryReqInfoByBussNo(String bfBatchId);

    /**
     * @param memberReqId  商户请求Id
     * @param two          0-待回执、1-已回执、2-已通知
     * @param businessType 业务类型
     */
    void updateApiRqstInfoStatusByReqNo(String memberReqId, int two, String businessType);

    /**
     * 根据商户号和商户申请流水号查询
     *
     * @param memberId     商户号
     * @param memberReqId  商户申请流水号
     * @param businessType 业务类型
     * @return 汇款关系
     */
    FiCbPayMemberApiRqstDo queryFiCbPayMemberApiRqst(Long memberId, String memberReqId, String businessType);

    /**
     * 根据批次号查询代理报关文件表
     * @param batchNo 批次号
     * @return j结果
     */
    FiCbPayFileUploadDo queryByApplyId(Long batchNo);
}
