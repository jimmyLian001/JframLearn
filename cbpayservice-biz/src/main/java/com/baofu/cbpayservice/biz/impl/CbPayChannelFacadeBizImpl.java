package com.baofu.cbpayservice.biz.impl;

import com.baofoo.cbcgw.facade.api.gw.CgwCbPayReqFacade;
import com.baofoo.cbcgw.facade.dto.channel.request.ChQueryRateReqDto;
import com.baofoo.cbcgw.facade.dto.gw.response.result.CgwExchangeRateResultDto;
import com.baofu.cbpayservice.biz.CbPayChannelFacadeBiz;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.slf4j.helpers.SystemMarker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 渠道服务中枢
 * <p>
 * 1、查询汇率
 * </p>
 * User: wanght Date:2017/05/16 ProjectName: cbpay-service Version: 1.0
 */
@Slf4j
@Service
public class CbPayChannelFacadeBizImpl implements CbPayChannelFacadeBiz {

    /**
     * 渠道服务
     */
    @Autowired
    private CgwCbPayReqFacade cgwCbPayReqFacade;

    /**
     * 查询汇率
     *
     * @param channelId  渠道号
     * @param ccy        币种
      */
    @Override
    public CgwExchangeRateResultDto getExchangeRate(Long channelId, String ccy) {

        ChQueryRateReqDto chQueryRateReq = new ChQueryRateReqDto();
        chQueryRateReq.setTraceLogId(MDC.get(SystemMarker.TRACE_LOG_ID));
        chQueryRateReq.setCurrency(ccy);
        chQueryRateReq.setChannelId(channelId.intValue());

        log.info("call 渠道查汇接口参数：{}", chQueryRateReq);
        CgwExchangeRateResultDto exchangeRate = cgwCbPayReqFacade.getExchangeRate(chQueryRateReq);
        log.info("call 渠道查汇返回结果：{}", exchangeRate);

        return exchangeRate;
    }
}
