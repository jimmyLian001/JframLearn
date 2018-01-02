package com.baofu.international.global.account.core.biz.impl;

import com.baofoo.cbcgw.facade.api.gw.CgwCbPayReqFacade;
import com.baofoo.cbcgw.facade.dto.channel.request.ChQueryRateReqDto;
import com.baofoo.cbcgw.facade.dto.gw.response.result.CgwExchangeRateResultDto;
import com.baofu.global.cross.border.logback.ex.constant.MDCPropertyConsts;
import com.baofu.international.global.account.core.biz.ChannelRateQueryBiz;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 渠道服务中枢
 * 1、查询汇率
 * User: feng_jiang Date:2017/11/6 ProjectName: globalaccount-core Version: 1.0
 */
@Slf4j
@Service
public class ChannelRateQueryBizImpl implements ChannelRateQueryBiz {

    /**
     * 渠道服务
     */
    @Autowired
    private CgwCbPayReqFacade cgwCbPayReqFacade;

    /**
     * 查询汇率
     *
     * @param channelId 渠道号
     * @param ccy       币种
     */
    @Override
    public CgwExchangeRateResultDto getExchangeRate(Long channelId, String ccy) {

        ChQueryRateReqDto chQueryRateReq = new ChQueryRateReqDto();
        chQueryRateReq.setTraceLogId(MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
        chQueryRateReq.setCurrency(ccy);
        chQueryRateReq.setChannelId(channelId.intValue());

        log.info("call 渠道查汇接口参数：{}", chQueryRateReq);
        CgwExchangeRateResultDto exchangeRate = cgwCbPayReqFacade.getExchangeRate(chQueryRateReq);
        log.info("call 渠道查汇返回结果：{}", exchangeRate);

        return exchangeRate;
    }
}
