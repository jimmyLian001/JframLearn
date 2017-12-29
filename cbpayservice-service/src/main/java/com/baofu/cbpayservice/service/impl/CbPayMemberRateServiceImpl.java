package com.baofu.cbpayservice.service.impl;

import com.baofoo.cbcgw.facade.dto.gw.response.result.CgwExchangeRateResultDto;
import com.baofu.cbpayservice.biz.CbPayChannelFacadeBiz;
import com.baofu.cbpayservice.biz.CbPayCommonBiz;
import com.baofu.cbpayservice.biz.CbPayMemberRateBiz;
import com.baofu.cbpayservice.biz.ExchangeRateQueryBiz;
import com.baofu.cbpayservice.biz.models.ExchangeRateQueryBo;
import com.baofu.cbpayservice.common.enums.ChannelTypeEnum;
import com.baofu.cbpayservice.common.enums.ErrorCodeEnum;
import com.baofu.cbpayservice.common.enums.RateSetTypeEnum;
import com.baofu.cbpayservice.facade.CbPayMemberRateFacade;
import com.baofu.cbpayservice.facade.models.CbPayMemberRateAddDto;
import com.baofu.cbpayservice.facade.models.CbPayMemberRateModifyDto;
import com.baofu.cbpayservice.facade.models.ExchangeRateResultDto;
import com.baofu.cbpayservice.service.convert.CbPayMemberRateConvert;
import com.system.commons.exception.BizServiceException;
import com.system.commons.exception.CommonErrorCode;
import com.system.commons.result.Result;
import com.system.commons.utils.ExceptionUtils;
import com.system.commons.utils.ParamValidate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.slf4j.helpers.SystemMarker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * <p>
 * 1、新增浮动汇率
 * 2、修改浮动汇率
 * 3、提供首页外币账户汇率展示
 * </p>
 * User: yangjian  Date: 2017-05-15 ProjectName:  Version: 1.0。
 */
@Slf4j
@Service
public class CbPayMemberRateServiceImpl implements CbPayMemberRateFacade {

    /**
     * 汇率查询biz功能
     */
    @Autowired
    private ExchangeRateQueryBiz exchangeRateQueryBiz;

    /**
     * 会员汇率浮动biz功能
     */
    @Autowired
    private CbPayMemberRateBiz cbPayMemberRateBiz;

    /**
     * 网关公共服务信息biz功能
     */
    @Autowired
    private CbPayCommonBiz cbPayCommonBiz;

    /**
     * 渠道服务中枢biz功能
     */
    @Autowired
    private CbPayChannelFacadeBiz cbPayChannelFacadeBiz;

    /**
     * 新增浮动汇率
     *
     * @param cbPayMemberRateAddDto 新增浮动汇率参数信息
     * @param traceLogId            日志编号
     * @return 返回添加结果
     */
    @Override
    public Result<Boolean> addMemberRate(CbPayMemberRateAddDto cbPayMemberRateAddDto, String traceLogId) {

        Result<Boolean> result;
        MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
        Long startTime = System.currentTimeMillis();
        try {
            ParamValidate.validateParams(cbPayMemberRateAddDto);
            if ((RateSetTypeEnum.BP.getCode() + "").equals(cbPayMemberRateAddDto.getRateSetType())
                    && cbPayMemberRateAddDto.getMemberRateBp() == null) {
                log.error("浮动值设置方式为bp，bp值不能为空");
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00175);
            }
            if ((RateSetTypeEnum.PERCENTAGE.getCode() + "").equals(cbPayMemberRateAddDto.getRateSetType())
                    && cbPayMemberRateAddDto.getMemberRate() == null) {
                log.error("浮动值设置方式为百分比，百分比值不能为空");
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00176);
            }
            log.info("新增浮动汇率参数：{}", cbPayMemberRateAddDto);
            cbPayMemberRateBiz.addMemberRate(CbPayMemberRateConvert.paramConvert(cbPayMemberRateAddDto));
            result = new Result<>(Boolean.TRUE);
        } catch (Exception e) {
            log.error("新增浮动汇率异常，信息{}", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("返回新增参数:{},执行时间:{}", result, System.currentTimeMillis() - startTime);
        return result;
    }

    /**
     * 修改浮动汇率
     *
     * @param cbPayMemberRateModifyDto 更新商户浮动汇率
     * @param traceLogId               日志编号
     * @return 返回更新結果
     */
    @Override
    public Result<Boolean> modifyMemberRate(CbPayMemberRateModifyDto cbPayMemberRateModifyDto, String traceLogId) {

        Result<Boolean> result;
        MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
        Long startTime = System.currentTimeMillis();
        try {
            if ((RateSetTypeEnum.BP.getCode() + "").equals(cbPayMemberRateModifyDto.getRateSetType())
                    && cbPayMemberRateModifyDto.getMemberRateBp() == null) {
                log.error("浮动值设置方式为bp，bp值不能为空");
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00175);
            }
            if ((RateSetTypeEnum.PERCENTAGE.getCode() + "").equals(cbPayMemberRateModifyDto.getRateSetType())
                    && cbPayMemberRateModifyDto.getMemberRate() == null) {
                log.error("浮动值设置方式为百分比，百分比值不能为空");
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00176);
            }
            log.info("更新浮动汇率参数：{}", cbPayMemberRateModifyDto);
            ParamValidate.validateParams(cbPayMemberRateModifyDto);
            cbPayMemberRateBiz.modifyMemberRate(CbPayMemberRateConvert.paramConvert(cbPayMemberRateModifyDto));
            result = new Result<>(Boolean.TRUE);
        } catch (Exception e) {
            log.error("更新浮动汇率异常，信息{}", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("返回更新后结果：{},执行时间:{}", result, System.currentTimeMillis() - startTime);
        return result;
    }

    /**
     * 提供首页外币账户汇率展示
     *
     * @param memberId       渠道号
     * @param targetCurrency 币种
     * @param traceLogId     日志id
     * @return 返回银行渠道汇率信息
     */
    @Override
    public Result<ExchangeRateResultDto> queryExchangeRateByChannel(Long memberId, String targetCurrency, String traceLogId) {
        Result<ExchangeRateResultDto> result;
        MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
        try {
            log.info("外币账户汇率查询请求参数，商户号：{}，币种：{}", memberId, targetCurrency);
            if (memberId == null || StringUtils.isBlank(targetCurrency)) {
                throw new BizServiceException(CommonErrorCode.PARAMETER_VALID_NOT_PASS, "查询汇率商户号或币种不能为空");
            }
            Long channelId = cbPayCommonBiz.queryChannelId(memberId, targetCurrency,
                    ChannelTypeEnum.PURCHASE_PAYMENT.getCode());
            CgwExchangeRateResultDto exchangeRate = cbPayChannelFacadeBiz.getExchangeRate(channelId, targetCurrency);
            if (exchangeRate.getCode() != 1) {
                log.error("请求查汇失败,商户号：{}, 币种：{}", memberId, targetCurrency);
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00123);
            }
            ExchangeRateQueryBo exchangeRateQueryBo = exchangeRateQueryBiz.queryFloatRate(memberId, targetCurrency, exchangeRate, null);
            exchangeRate.setSellRateOfCcy(exchangeRateQueryBo.getSellRateOfCcy().multiply(new BigDecimal("100")));      //现汇卖出价 购汇汇率
            exchangeRate.setBuyRateOfCcy(exchangeRateQueryBo.getBuyRateOfCcy().multiply(new BigDecimal("100")));        //现汇买入价 结汇汇率
            result = new Result<>(CbPayMemberRateConvert.paramConvert(exchangeRate));
        } catch (Exception e) {
            log.error("外币汇率查询异常", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("外币账户汇率查询响应结果：{}", result);
        return result;
    }
}
