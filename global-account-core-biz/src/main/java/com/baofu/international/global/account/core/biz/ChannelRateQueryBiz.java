package com.baofu.international.global.account.core.biz;

import com.baofoo.cbcgw.facade.dto.gw.response.result.CgwExchangeRateResultDto;

/**
 * 渠道服务中枢
 * 1、查询汇率
 * User: feng_jiang Date:2017/11/6 ProjectName: globalaccount-core Version: 1.0
 */
public interface ChannelRateQueryBiz {

    /**
     * 查询汇率
     *
     * @param channelId 渠道号
     * @param ccy       币种
     * @return CgwExchangeRateResultDto
     */
    CgwExchangeRateResultDto getExchangeRate(Long channelId, String ccy);
}
