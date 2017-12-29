package com.baofu.cbpayservice.biz;

import com.baofoo.cbcgw.facade.dto.gw.response.result.CgwExchangeRateResultDto;

/**
 * 渠道服务中枢
 * <p>
 * 1、查询汇率
 * </p>
 * User: wanght Date:2017/05/16 ProjectName: cbpay-service Version: 1.0
 */
public interface CbPayChannelFacadeBiz {

    /**
     * 查询汇率
     *
     * @param channelId 渠道号
     * @param ccy       币种
     * @return CgwExchangeRateResultDto
     */
    CgwExchangeRateResultDto getExchangeRate(Long channelId, String ccy);
}
