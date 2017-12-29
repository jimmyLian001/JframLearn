package com.baofu.cbpayservice.service.convert;

import com.baofu.cbpayservice.biz.models.ExchangeRateQueryBo;
import com.baofu.cbpayservice.facade.models.res.ExchangeRateQueryRespDto;

/**
 * 返回汇率查询结果
 * <p>
 * User: 不良人 Date:2017/1/5 ProjectName: cbpayservice Version: 1.0
 */
public class ExchangeRateQueryConvert {

    /**
     * 汇率查询结果转换成返回结果
     *
     * @param exchangeRateQueryBo 查询结果
     * @return 汇率查询
     */
    public static ExchangeRateQueryRespDto toExchangeRateQueryRespDto(ExchangeRateQueryBo exchangeRateQueryBo) {

        ExchangeRateQueryRespDto exchangeRateQueryRespDto = new ExchangeRateQueryRespDto();
        exchangeRateQueryRespDto.setSourceCurrency(exchangeRateQueryBo.getSourceCurrency());
        exchangeRateQueryRespDto.setTargetCurrency(exchangeRateQueryBo.getTargetCurrency());
        exchangeRateQueryRespDto.setBuyRateOfCcy(exchangeRateQueryBo.getBuyRateOfCcy());
        exchangeRateQueryRespDto.setSellRateOfCcy(exchangeRateQueryBo.getSellRateOfCcy());
        exchangeRateQueryRespDto.setBuyRateOfCash(exchangeRateQueryBo.getBuyRateOfCash());
        exchangeRateQueryRespDto.setSellRateOfCash(exchangeRateQueryBo.getSellRateOfCash());
        exchangeRateQueryRespDto.setUpdateDate(exchangeRateQueryBo.getUpdateDate());
        return exchangeRateQueryRespDto;
    }
}
