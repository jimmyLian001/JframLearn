package com.baofu.cbpayservice.biz.impl;

import com.baofoo.cbcgw.facade.dict.BaseResultEnum;
import com.baofoo.cbcgw.facade.dto.gw.response.result.CgwExchangeRateResultDto;
import com.baofu.cbpayservice.biz.CbPayChannelFacadeBiz;
import com.baofu.cbpayservice.biz.CbPayCommonBiz;
import com.baofu.cbpayservice.biz.CbPayMemberRateBiz;
import com.baofu.cbpayservice.biz.ExchangeRateQueryBiz;
import com.baofu.cbpayservice.biz.convert.ExRateQueryBizConvert;
import com.baofu.cbpayservice.biz.models.CbPayMemberRateReqBo;
import com.baofu.cbpayservice.biz.models.ExchangeRateQueryBo;
import com.baofu.cbpayservice.common.constants.Constants;
import com.baofu.cbpayservice.common.enums.BusinessTypeEnum;
import com.baofu.cbpayservice.common.enums.ChannelTypeEnum;
import com.baofu.cbpayservice.common.enums.ErrorCodeEnum;
import com.baofu.cbpayservice.common.enums.RateSetTypeEnum;
import com.baofu.cbpayservice.dal.models.FiCbPaySettleBankDo;
import com.baofu.cbpayservice.manager.CbPaySettleBankManager;
import com.system.commons.exception.BizServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * 功能：汇率查询实现类
 * <p>
 * User: feng_jiang Date:2017/8/22 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
@Service
public class ExchangeRateQueryBizImpl implements ExchangeRateQueryBiz {

    /**
     * 网关公共服务信息
     */
    @Autowired
    private CbPayCommonBiz cbPayCommonBiz;

    /**
     * 渠道服务
     */
    @Autowired
    private CbPayChannelFacadeBiz cbPayChannelFacadeBiz;

    /**
     * 浮动汇率biz接口
     */
    @Autowired
    private CbPayMemberRateBiz cbPayMemberRateBiz;

    /**
     * 定额币种服务
     */
    @Autowired
    private CbPaySettleBankManager cbPaySettleBankManager;

    /**
     * 汇率保留小数位数
     */
    private static final int RATE_SCALE = 6;

    /**
     * 功能：查询浮动汇率(此方法返回的汇率都是除以100的)
     *
     * @param memberId     商户号
     * @param ccy          币种
     * @param exchangeRate 渠道返回汇率
     * @param entityNo     备案主体编号
     * @return
     * @author feng_jiang
     */
    @Override
    public ExchangeRateQueryBo queryFloatRate(Long memberId, String ccy, CgwExchangeRateResultDto exchangeRate, String entityNo) {
        //查询购汇汇率
        ExchangeRateQueryBo remitFloatRateBo = this.queryRemitFloatRate(memberId, ccy, exchangeRate, entityNo);
        //查询结汇汇率
        ExchangeRateQueryBo settleFloatRateBo = this.querySettleFloatRate(memberId, ccy, exchangeRate);
        return ExRateQueryBizConvert.transToRateQueryBo(remitFloatRateBo, settleFloatRateBo);
    }

    /**
     * 功能：查询浮动汇率（购付汇)
     *
     * @param memberId     商户号
     * @param ccy          币种
     * @param exchangeRate 渠道返回汇率
     * @param entityNo     备案主体编号
     * @return ExchangeRateQueryBo
     * @author feng_jiang
     */
    private ExchangeRateQueryBo queryRemitFloatRate(Long memberId, String ccy, CgwExchangeRateResultDto exchangeRate, String entityNo) {
        ExchangeRateQueryBo retBo = ExRateQueryBizConvert.toExchangeRateQueryBo(exchangeRate);
        retBo.setSellRateOfCcy(retBo.getSellRateOfCcy().divide(new BigDecimal("100"), RATE_SCALE, BigDecimal.ROUND_DOWN));
        if (entityNo == null) {
            //调用查询浮动汇率接口
            List<FiCbPaySettleBankDo> fiCbPaySettleBankDoList = cbPaySettleBankManager.selectBankAccsByMIdCcy(memberId, ccy);
            //如果会员币种信息不为空并且定额类型为定额人民币
            if (fiCbPaySettleBankDoList != null && fiCbPaySettleBankDoList.size() > 0 && fiCbPaySettleBankDoList.get(0).getExchangeType() == 0) {
                return retBo;
            }
        } else {
            //调用查询浮动汇率接口
            FiCbPaySettleBankDo fiCbPaySettleBankDo = cbPaySettleBankManager.selectBankAccByEntityNo(memberId, ccy, entityNo);
            if (fiCbPaySettleBankDo != null && fiCbPaySettleBankDo.getExchangeType() == 0) {
                return retBo;
            }
        }
        CbPayMemberRateReqBo cbPayMemberRateReqBo = new CbPayMemberRateReqBo();
        cbPayMemberRateReqBo.setMemberId(memberId);
        cbPayMemberRateReqBo.setCcy(ccy);
        List<CbPayMemberRateReqBo> memberRateList = cbPayMemberRateBiz.queryMemberRateList(cbPayMemberRateReqBo);
        if (memberRateList == null || memberRateList.size() <= 0) {
            return retBo;
        }
        Boolean isJpy = Constants.JPY_CURRENCY.equals(ccy);
        BigDecimal rate;
        for (CbPayMemberRateReqBo bo : memberRateList) {
            if (1 != bo.getStatus()) {
                continue;
            }
            if (BusinessTypeEnum.REMITTANCE.getCode() == bo.getBusinessType()) { //购汇
                rate = this.getActualRate(isJpy ? exchangeRate.getSellRateOfCcy().multiply(new BigDecimal("100"))
                        : exchangeRate.getSellRateOfCcy(), bo);
                retBo.setSellRateOfCcy(isJpy ? rate.divide(new BigDecimal("100"), RATE_SCALE, BigDecimal.ROUND_DOWN) : rate);
            }
        }
        return retBo;
    }

    /**
     * 功能：查询浮动汇率(结汇、垫资结汇)
     *
     * @param memberId     商户号
     * @param ccy          币种
     * @param exchangeRate 渠道返回汇率
     * @return ExchangeRateQueryBo
     * @author feng_jiang
     */
    private ExchangeRateQueryBo querySettleFloatRate(Long memberId, String ccy, CgwExchangeRateResultDto exchangeRate) {
        ExchangeRateQueryBo retBo = ExRateQueryBizConvert.toExchangeRateQueryBo(exchangeRate);
        retBo.setBuyRateOfCcy(retBo.getBuyRateOfCcy().divide(new BigDecimal("100"), RATE_SCALE, BigDecimal.ROUND_DOWN));
        retBo.setBuyAdvanceRateOfCcy(exchangeRate.getBuyRateOfCcy().divide(new BigDecimal("100"), RATE_SCALE, BigDecimal.ROUND_DOWN));
        CbPayMemberRateReqBo cbPayMemberRateReqBo = new CbPayMemberRateReqBo();
        cbPayMemberRateReqBo.setMemberId(memberId);
        cbPayMemberRateReqBo.setCcy(ccy);
        List<CbPayMemberRateReqBo> memberRateList = cbPayMemberRateBiz.queryMemberRateList(cbPayMemberRateReqBo);
        if (memberRateList == null || memberRateList.size() <= 0) {
            return retBo;
        }
        Boolean isJpy = Constants.JPY_CURRENCY.equals(ccy);
        BigDecimal rate;
        for (CbPayMemberRateReqBo bo : memberRateList) {
            if (1 != bo.getStatus()) {
                continue;
            }
            if (BusinessTypeEnum.SETTLE.getCode() == bo.getBusinessType()) { //结汇
                rate = this.getActualRate(isJpy ? exchangeRate.getBuyRateOfCcy().multiply(new BigDecimal("100"))
                        : exchangeRate.getBuyRateOfCcy(), bo);
                retBo.setBuyRateOfCcy(isJpy ? rate.divide(new BigDecimal("100"), RATE_SCALE, BigDecimal.ROUND_DOWN) : rate);
            } else if (BusinessTypeEnum.ADVANCE_SETTLE.getCode() == bo.getBusinessType()) {
                rate = this.getActualRate(isJpy ? exchangeRate.getBuyRateOfCcy().multiply(new BigDecimal("100"))
                        : exchangeRate.getBuyRateOfCcy(), bo);
                retBo.setBuyAdvanceRateOfCcy(isJpy ? rate.divide(new BigDecimal("100"), RATE_SCALE, BigDecimal.ROUND_DOWN) : rate);
            }
        }
        return retBo;
    }

    /**
     * 功能：获取处理后的汇率
     *
     * @param rateOfCcy 汇率
     * @param bo        设置汇率
     * @return 汇率
     */
    private BigDecimal getActualRate(BigDecimal rateOfCcy, CbPayMemberRateReqBo bo) {
        BigDecimal rate = null;
        if (RateSetTypeEnum.BP.getCode() == bo.getRateSetType()) {
            rate = this.getBpRate(rateOfCcy, bo.getMemberRateBp(), bo.getBusinessType());
        } else if (RateSetTypeEnum.PERCENTAGE.getCode() == bo.getRateSetType()) {
            rate = this.getPercentRate(rateOfCcy, bo.getMemberRate(), bo.getBusinessType());
        }
        log.info("商户汇率加bp Rate：{}", rate);
        return rate;
    }

    /**
     * 功能：获取浮动费率设置方式为百分比的浮动费率
     *
     * @param rateOfCcy    渠道汇率
     * @param memberRate   设置汇率
     * @param businessType 业务类型
     * @return 百分比汇率
     */
    private BigDecimal getPercentRate(BigDecimal rateOfCcy, BigDecimal memberRate, Integer businessType) {
        BigDecimal markup = BigDecimal.ONE;
        if (BusinessTypeEnum.REMITTANCE.getCode() == businessType) { //购汇
            markup = markup.add(memberRate.divide(new BigDecimal("100")));
        } else if (BusinessTypeEnum.SETTLE.getCode() == businessType) { //结汇
            markup = markup.subtract(memberRate.divide(new BigDecimal("100")));
        }
        markup = markup.add(memberRate.divide(new BigDecimal("100")));
        log.info("markup：{}", markup);
        return rateOfCcy.multiply(markup).divide(new BigDecimal("100"), RATE_SCALE, BigDecimal.ROUND_DOWN);
    }

    /**
     * 功能：获取浮动费率设置方式为bp的浮动费率
     *
     * @param rateOfCcy    汇率
     * @param memberRateBp bp值
     * @param businessType 业务类型
     * @return bp计算汇率
     */
    private BigDecimal getBpRate(BigDecimal rateOfCcy, Long memberRateBp, Integer businessType) {
        BigDecimal bp = new BigDecimal("" + memberRateBp).divide(new BigDecimal("10000"));
        log.info("bp：{}", bp);
        BigDecimal rate = rateOfCcy.divide(new BigDecimal("100"), RATE_SCALE, BigDecimal.ROUND_DOWN);
        if (BusinessTypeEnum.REMITTANCE.getCode() == businessType) { //购汇
            return rate.add(bp);
        } else if (BusinessTypeEnum.SETTLE.getCode() == businessType || BusinessTypeEnum.ADVANCE_SETTLE.getCode() == businessType) { //结汇和垫资结汇
            return rate.subtract(bp);
        }
        return rate;
    }

    /**
     * 功能：根据商户号和币种查询汇率
     *
     * @param memberId  商户号
     * @param channelId 渠道ID
     * @param ccy       币种
     * @return 查询结果
     */
    @Override
    public ExchangeRateQueryBo queryRateByMemberIdAndCcy(Long memberId, Long channelId, String ccy) {
        //查询渠道汇率
        return this.queryRateByMemberInfo(memberId, ccy, channelId);
    }

    /**
     * 功能：根据商户号和币种查询汇率
     *
     * @param memberId 商户号
     * @param ccy      币种
     * @return 查询结果
     */
    @Override
    public ExchangeRateQueryBo queryRateByMemberIdAndCcy(Long memberId, String ccy) {
        //查询渠道汇率
        Long channelId = cbPayCommonBiz.queryChannelId(memberId, ccy, ChannelTypeEnum.PURCHASE_PAYMENT.getCode());
        return this.queryRateByMemberInfo(memberId, ccy, channelId);
    }

    /**
     * 功能：查询计算后的浮动汇率
     *
     * @param memberId  商户号
     * @param ccy       币种
     * @param channelId 渠道ID
     * @return ExchangeRateQueryBo
     */
    private ExchangeRateQueryBo queryRateByMemberInfo(Long memberId, String ccy, Long channelId) {
        log.info("call 调用渠道查汇接口参数：渠道ID:{},币种:{}", channelId, ccy);
        CgwExchangeRateResultDto exchangeRate = cbPayChannelFacadeBiz.getExchangeRate(channelId, ccy);
        log.info("call 调用渠道查汇接口返回结果：{}", exchangeRate);
        if (BaseResultEnum.SUCCESS.getCode() != exchangeRate.getCode()) {
            log.info("call 调用渠道查汇接口返回异常信息：{}", exchangeRate.getMessage());
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00171);
        }
        ExchangeRateQueryBo exchangeRateQueryBo = this.queryFloatRate(memberId, ccy, exchangeRate, null);
        return ExRateQueryBizConvert.toExchangeRateQueryBo(exchangeRate, exchangeRateQueryBo);
    }
}