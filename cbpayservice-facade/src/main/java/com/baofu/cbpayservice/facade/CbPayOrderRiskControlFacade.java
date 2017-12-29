package com.baofu.cbpayservice.facade;

import com.baofu.cbpayservice.facade.models.CbPayOrderRiskControlReqDto;
import com.baofu.cbpayservice.facade.models.FiCbPayVerifyReqDto;
import com.system.commons.result.Result;

/**
 * 跨境风控订单服务接口
 * <p>
 * 1. 跨境订单人工审核接口
 * </p>
 * User: wdj Date:2017/04/28 ProjectName: asias-icpaygate Version: 1.0
 */
public interface CbPayOrderRiskControlFacade {

    /**
     * 跨境订单人工审核接口
     *
     * @param cbPayOrderRiskControlReqDto 请求对象
     * @param traceLogId                  日志ID
     * @return 返回审核操作结果
     */
    Result<Boolean> cbPayOrderRiskControlManualAudit(CbPayOrderRiskControlReqDto cbPayOrderRiskControlReqDto, String traceLogId);

    /**
     * 购汇结汇实名认证接口
     *
     * @param fileBatchNo 文件批次号
     * @param orderType   订单类型  0：购汇订单   1：结汇订单
     * @param authCount   认证笔数
     * @param memberId    商户号
     * @param memberName  商户名称
     * @param traceLogId  日志id
     * @return 实名认证结果
     */
    Result<Boolean> cbPayOrderRiskControlCertification(Long fileBatchNo, Long memberId, String memberName,
                                                       Integer authCount, String orderType, String traceLogId);

    /**
     * 风控单笔实名认证
     *
     * @param fiCbPayVerifyReqDto 请求实体类
     * @param traceLogId          日志ID
     * @return 认证结果
     */
    Result<Boolean> cbPayOrderRiskSingleAuth(FiCbPayVerifyReqDto fiCbPayVerifyReqDto, String traceLogId);


}
