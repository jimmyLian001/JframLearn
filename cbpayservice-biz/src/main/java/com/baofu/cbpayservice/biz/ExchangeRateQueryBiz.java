package com.baofu.cbpayservice.biz;

import com.baofoo.cbcgw.facade.dto.gw.response.result.CgwExchangeRateResultDto;
import com.baofu.cbpayservice.biz.models.ExchangeRateQueryBo;

/**
 * 功能：汇率查询
 * <p>
 * User: feng_jiang Date:2017/8/22 ProjectName: cbpayservice Version: 1.0
 */
public interface ExchangeRateQueryBiz {

    /**
     * 功能：查询浮动汇率(查询浮动汇率(此方法返回的汇率都是除以100的，如美元：6.7034))
     * @param memberId     商户号
     * @param ccy          币种
     * @param exchangeRate 渠道返回汇率
     * @param entityNo     备案主体编号
     * @return ExchangeRateQueryBo
     */
    ExchangeRateQueryBo queryFloatRate(Long memberId, String ccy, CgwExchangeRateResultDto exchangeRate, String entityNo);

    /**
     * 功能：根据商户号和币种查询汇率
     * @param memberId 商户号
     * @param ccy      币种
     * @return 查询结果
     */
    ExchangeRateQueryBo queryRateByMemberIdAndCcy(Long memberId, String ccy);

    /**
     * 功能：根据商户号、渠道ID和币种查询汇率
     * @param memberId  商户号
     * @param channelId 渠道ID
     * @param ccy       币种
     * @return 查询结果
     */
    ExchangeRateQueryBo queryRateByMemberIdAndCcy(Long memberId, Long channelId, String ccy);
}