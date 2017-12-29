package com.baofu.cbpayservice.biz;

import com.baofu.cbpayservice.biz.models.CbPayVerifyReqBo;
import com.baofu.cbpayservice.biz.models.CbPayVerifyResBo;

/**
 * 跨境认证Biz层相关操作
 * <p>
 * 1、身份认证
 * </p>
 * User: wanght Date:2017/1/12 ProjectName: cbpay-service Version: 1.0
 */
public interface CbPayVerifyBiz {
    /**
     * 身份认证
     *
     * @param cbPayVerifyReqBo 参数
     * @return 认证结果
     */
    CbPayVerifyResBo idCardAuth(CbPayVerifyReqBo cbPayVerifyReqBo);

    /**
     * 风控实名认证
     *
     * @param cbPayVerifyReqBo
     * @return
     */
    CbPayVerifyResBo idCardAuthOfRisk(CbPayVerifyReqBo cbPayVerifyReqBo);
}