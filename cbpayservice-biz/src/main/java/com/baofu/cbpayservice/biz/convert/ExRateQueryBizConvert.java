package com.baofu.cbpayservice.biz.convert;

import com.baofoo.cbcgw.facade.dto.gw.response.result.CgwExchangeRateResultDto;
import com.baofu.cbpayservice.biz.models.ExchangeRateQueryBo;

import java.math.BigDecimal;

/**
 * 汇率查询转换
 * <p>
 * User: 不良人 Date:2017/1/5 ProjectName: cbpayservice Version: 1.0
 */
public class ExRateQueryBizConvert {

    /**
     * 渠道查询汇率转换返回参数转换
     *
     * @param exchangeRate  渠道返回查询结果
     * @param sourceExchangeRateQueryBo   浮动汇率
     * @return 返回结果
     */
    public static ExchangeRateQueryBo toExchangeRateQueryBo(CgwExchangeRateResultDto exchangeRate,ExchangeRateQueryBo sourceExchangeRateQueryBo) {
        ExchangeRateQueryBo exchangeRateQueryBo = new ExchangeRateQueryBo();
        exchangeRateQueryBo.setSellRateOfCcy(sourceExchangeRateQueryBo.getSellRateOfCcy().multiply(new BigDecimal("100")));
        exchangeRateQueryBo.setSourceCurrency(exchangeRate.getSourceCurrency());
        exchangeRateQueryBo.setTargetCurrency(exchangeRate.getTargetCurrency());
        exchangeRateQueryBo.setBuyRateOfCcy(sourceExchangeRateQueryBo.getBuyRateOfCcy().multiply(new BigDecimal("100")));
        exchangeRateQueryBo.setBuyRateOfCash(exchangeRate.getBuyRateOfCash());
        exchangeRateQueryBo.setSellRateOfCash(exchangeRate.getSellRateOfCash());
        exchangeRateQueryBo.setUpdateDate(exchangeRate.getUpdateDate());
        exchangeRateQueryBo.setBuyAdvanceRateOfCcy(sourceExchangeRateQueryBo.getBuyAdvanceRateOfCcy().multiply(new BigDecimal("100")));
        return exchangeRateQueryBo;
    }
    /**
     * 功能：转换渠道汇率查询汇率
     * @param exchangeRate 渠道汇率
     * @return 汇率查询
     */
    public static ExchangeRateQueryBo toExchangeRateQueryBo(CgwExchangeRateResultDto exchangeRate) {
        if (exchangeRate == null) {
            return null;
        }
        ExchangeRateQueryBo exchangeRateQueryBo = new ExchangeRateQueryBo();
        exchangeRateQueryBo.setSourceCurrency(exchangeRate.getSourceCurrency());
        exchangeRateQueryBo.setTargetCurrency(exchangeRate.getTargetCurrency());
        exchangeRateQueryBo.setBuyRateOfCcy(exchangeRate.getBuyRateOfCcy());
        exchangeRateQueryBo.setSellRateOfCcy(exchangeRate.getSellRateOfCcy());
        exchangeRateQueryBo.setBuyRateOfCash(exchangeRate.getBuyRateOfCash());
        exchangeRateQueryBo.setSellRateOfCash(exchangeRate.getSellRateOfCash());
        exchangeRateQueryBo.setUpdateDate(exchangeRate.getUpdateDate());
        return exchangeRateQueryBo;
    }
    /**
     * 功能：转换渠道汇率查询汇率
     * @param remitFloatRateBo  购汇汇率
     * @param settleFloatRateBo 结汇汇率
     * @return 汇率查询
     */
    public static ExchangeRateQueryBo transToRateQueryBo(ExchangeRateQueryBo remitFloatRateBo, ExchangeRateQueryBo settleFloatRateBo) {
        ExchangeRateQueryBo exchangeRateQueryBo = new ExchangeRateQueryBo();
        exchangeRateQueryBo.setSourceCurrency(remitFloatRateBo.getSourceCurrency());
        exchangeRateQueryBo.setTargetCurrency(remitFloatRateBo.getTargetCurrency());
        exchangeRateQueryBo.setBuyRateOfCash(remitFloatRateBo.getBuyRateOfCash());
        exchangeRateQueryBo.setSellRateOfCash(remitFloatRateBo.getSellRateOfCash());
        exchangeRateQueryBo.setUpdateDate(remitFloatRateBo.getUpdateDate());
        exchangeRateQueryBo.setSellRateOfCcy(remitFloatRateBo.getSellRateOfCcy());
        exchangeRateQueryBo.setBuyRateOfCcy(settleFloatRateBo.getBuyRateOfCcy());
        exchangeRateQueryBo.setBuyAdvanceRateOfCcy(settleFloatRateBo.getBuyAdvanceRateOfCcy());
        return exchangeRateQueryBo;
    }
}