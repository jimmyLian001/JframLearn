package com.baofu.cbpayservice.service.convert;

import com.baofoo.cbcgw.facade.dto.gw.request.CgwQueryRateReqDto;

/**
 * Service层参数转换
 * <p>
 * 1、自动查询汇率转换
 * </p>
 * User: wanght Date:2017/03/29 ProjectName: cbpayservice Version: 1.0
 */
public final class CbPayJobConvert {

    private CbPayJobConvert() {

    }

    /**
     * 自动查询汇率转换
     *
     * @param channelId  渠道号
     * @param traceLogId 日志id
     * @return 请求参数信息
     */
    public static CgwQueryRateReqDto queryExchangeParamConvert(Long channelId, String traceLogId) {

        CgwQueryRateReqDto cgwQueryRateReqDto = new CgwQueryRateReqDto();
        cgwQueryRateReqDto.setChannelId(channelId.intValue());
        cgwQueryRateReqDto.setTraceLogId(traceLogId);
        return cgwQueryRateReqDto;
    }
}
