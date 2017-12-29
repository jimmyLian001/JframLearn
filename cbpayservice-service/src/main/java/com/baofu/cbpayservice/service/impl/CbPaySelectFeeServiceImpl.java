package com.baofu.cbpayservice.service.impl;

import com.baofoo.cbcgw.facade.dto.gw.response.result.CgwExchangeRateResultDto;
import com.baofu.cbpayservice.biz.*;
import com.baofu.cbpayservice.biz.integration.cm.CmClearBizImpl;
import com.baofu.cbpayservice.biz.models.ExchangeRateQueryBo;
import com.baofu.cbpayservice.biz.models.MemberFeeResBo;
import com.baofu.cbpayservice.biz.models.QueryBalanceBo;
import com.baofu.cbpayservice.common.constants.Constants;
import com.baofu.cbpayservice.common.enums.ChannelTypeEnum;
import com.baofu.cbpayservice.common.enums.ErrorCodeEnum;
import com.baofu.cbpayservice.common.enums.FunctionEnum;
import com.baofu.cbpayservice.common.enums.RemittanceOrderType;
import com.baofu.cbpayservice.common.util.StringUtil;
import com.baofu.cbpayservice.facade.CbPaySelectFeeFacade;
import com.baofu.cbpayservice.facade.models.CbPayCalculateFeeDto;
import com.baofu.cbpayservice.facade.models.CbPaySelectFeeDto;
import com.baofu.cbpayservice.facade.models.res.CbPayFeeRespDto;
import com.baofu.cbpayservice.facade.models.res.ExchangeRateQueryRespDto;
import com.baofu.cbpayservice.manager.CbPayRemittanceManager;
import com.baofu.cbpayservice.manager.OrderIdManager;
import com.baofu.cbpayservice.manager.RedisManager;
import com.baofu.cbpayservice.service.convert.CbPayRemittanceConvert;
import com.baofu.cbpayservice.service.convert.ExchangeRateQueryConvert;
import com.system.commons.exception.BizServiceException;
import com.system.commons.result.Result;
import com.system.commons.utils.ExceptionUtils;
import com.system.commons.utils.ParamValidate;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.slf4j.helpers.SystemMarker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * 查汇服务
 * <p>
 * 1、查汇
 * </p>
 * User: 莫小阳 Date:2017/03/28 ProjectName: cbpay-service Version: 1.0
 */

@Slf4j
@Service
public class CbPaySelectFeeServiceImpl implements CbPaySelectFeeFacade {

    /**
     * 汇款订单操作服务
     */
    @Autowired
    private CbPayRemittanceManager cbPayRemittanceManager;
    /**
     * 渠道服务
     */
    @Autowired
    private CbPayChannelFacadeBiz cbPayChannelFacadeBiz;

    /**
     * 会员信息服务
     */
    @Autowired
    private CmClearBizImpl cmClearBizImpl;

    /**
     * 计费操作服务
     */
    @Autowired
    private CbPayCmBiz cbPayCmBiz;

    /**
     * 创建宝付订单号
     */
    @Autowired
    private OrderIdManager orderIdManager;

    /**
     * 多币种账户信息biz接口
     */
    @Autowired
    private CbPaySettleBankBiz cbPaySettleBankBiz;

    /**
     * 网关公共服务信息
     */
    @Autowired
    private CbPayCommonBiz cbPayCommonBiz;

    /**
     * 缓存服务
     */
    @Autowired
    private RedisManager redisManager;

    /**
     * 代理报关服务
     */
    @Autowired
    private ProxyCustomsBiz proxyCustomsBiz;

    /**
     * 汇率服务
     */
    @Autowired
    private ExchangeRateQueryBiz exchangeRateQueryBiz;

    /**
     * @param cbPaySelectFeeDto 试算参数信息
     * @param traceLogId        日志ID
     * @return CbPayFeeRespDto  查汇结果返回结果
     */
    @Override
    public Result<CbPayFeeRespDto> selectFee(CbPaySelectFeeDto cbPaySelectFeeDto, String traceLogId) {

        MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
        log.info("call 试算请求参数:{}", cbPaySelectFeeDto);

        //交易总金额=出款金额+手续费；结算金额=出款金额/交易汇率
        Result<CbPayFeeRespDto> result;

        try {
            ParamValidate.validateParams(cbPaySelectFeeDto);
            // 判断批次币种是否一致
            List<String> ccyList = proxyCustomsBiz.queryAmlCcy(cbPaySelectFeeDto.getFileBatchNoList());

            //查询反洗钱币种
            if (!CollectionUtils.isEmpty(ccyList) && ccyList.size() > 1) {
                log.error("call 文件批次包含多种币种：{}，请检查后重试", ccyList);
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00141);
            }
            if (!CollectionUtils.isEmpty(ccyList) && !ccyList.get(0).equals(cbPaySelectFeeDto.getTargetCcy())) {
                log.error("call 文件批次币种：{}和购汇币种：{},不一致，请检查后重试", ccyList.get(0),
                        cbPaySelectFeeDto.getTargetCcy());
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00152);
            }
            log.info("商户号:{}, 币种：{}", cbPaySelectFeeDto.getMemberId(), cbPaySelectFeeDto.getTargetCcy());

            // 判断账户主体编号是否一致
            for (Long fileBatchId : cbPaySelectFeeDto.getFileBatchNoList()) {
                String entityNo = redisManager.queryObjectByKey(Constants.CBPAY_AML_ENTITY_KEY +
                        cbPaySelectFeeDto.getMemberId() + ":" + cbPaySelectFeeDto.getTargetCcy() + ":" + fileBatchId);
                if (!StringUtil.isBlank(entityNo) && !cbPaySelectFeeDto.getEntityNo().equals(entityNo)) {
                    log.info("商户账户主体编号不一致，汇款选择编号：{}，反洗钱选择编号：{}", cbPaySelectFeeDto.getEntityNo(), entityNo);
                    throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00150);
                }
            }

            //判断币种是否存在
            cbPaySettleBankBiz.checkMemberCcy(cbPaySelectFeeDto.getMemberId(), cbPaySelectFeeDto.getTargetCcy());

            //出款金额;
            BigDecimal totalTranceMoney = cbPayRemittanceManager.queryRemittanceByBatchNos(cbPaySelectFeeDto.getFileBatchNoList());
            log.info("memberId:{},batchNos:{},查询总金额：{}", cbPaySelectFeeDto.getEntityNo(), cbPaySelectFeeDto.getFileBatchNoList(),
                    totalTranceMoney);

            //调用计费服务，计算交易手续费
            Long proxyOrderId = orderIdManager.orderIdCreate();
            int functionId = FunctionEnum.getFunctionId(RemittanceOrderType.PROXY_REMITTANCE_ORDER.getCode(), cbPaySelectFeeDto.getTargetCcy());
            int productId = FunctionEnum.getProductId(RemittanceOrderType.PROXY_REMITTANCE_ORDER.getCode());
            MemberFeeResBo feeResult = cbPayCmBiz.getFeeResult(String.valueOf(proxyOrderId), totalTranceMoney,
                    cbPaySelectFeeDto.getMemberId(), functionId, productId, traceLogId);

            //交易总金额 = 出款金额 + 交易手续费;
            BigDecimal totalAllMoney = totalTranceMoney.add(feeResult.getFeeAmount());

            log.info("出款金额:{},交易手续费:{},交易总金额:{}", totalTranceMoney, feeResult.getFeeAmount(), totalAllMoney);

            //查询渠道汇率
            Long channelId = cbPayCommonBiz.queryChannelId(cbPaySelectFeeDto.getMemberId(),
                    cbPaySelectFeeDto.getTargetCcy(), ChannelTypeEnum.PURCHASE_PAYMENT.getCode());
            CgwExchangeRateResultDto exchangeRate = cbPayChannelFacadeBiz.getExchangeRate(channelId, cbPaySelectFeeDto.getTargetCcy());
            if (exchangeRate.getCode() != 1) {
                log.error("请求查汇失败,商户号：{}, 币种：{}", cbPaySelectFeeDto.getMemberId(), cbPaySelectFeeDto.getTargetCcy());
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00123);
            }
            //调用封装好的查询接口
            ExchangeRateQueryBo exchangeRateQueryBo = exchangeRateQueryBiz.queryFloatRate(cbPaySelectFeeDto.getMemberId(),
                    cbPaySelectFeeDto.getTargetCcy(), exchangeRate, cbPaySelectFeeDto.getEntityNo());
            BigDecimal rate = exchangeRateQueryBo.getSellRateOfCcy();

            log.info("计算后的汇率：{}", rate);
            //结算金额=出款金额/交易汇率;
            BigDecimal balanceAllMoney = totalTranceMoney.divide(rate, 2, BigDecimal.ROUND_DOWN);
            log.info("结算金额:{}", balanceAllMoney);

            QueryBalanceBo accountBalanceReq = new QueryBalanceBo(cbPaySelectFeeDto.getMemberId().intValue(), 1);
            //账户余额;
            BigDecimal accountMoney = cmClearBizImpl.queryBalance(accountBalanceReq);
            log.info("查询账户余额：{}", accountMoney);

            CbPayFeeRespDto cbPayFeeRespDto = CbPayRemittanceConvert.feeParamConvert(exchangeRate, totalTranceMoney,
                    feeResult.getFeeAmount(), totalAllMoney, balanceAllMoney, accountMoney, rate);
            result = new Result<>(cbPayFeeRespDto);
            log.info("网关查汇接口返回结果：{}", result);

        } catch (Exception e) {
            log.error("网关selectFee（）发生异常:{}", e);
            result = ExceptionUtils.getResponse(e);
        }
        return result;
    }

    /**
     * 汇率查询API接口
     *
     * @param memberId   商户号
     * @param ccy        币总
     * @param traceLogId 日志ID
     * @return 查询结果
     */
    @Override
    public Result<ExchangeRateQueryRespDto> exchangeRateQuery(Long memberId, String ccy, String traceLogId) {

        MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
        log.info("call 汇率查询API接口入参:商户号:{},查询币种:{}", memberId, ccy);
        Result<ExchangeRateQueryRespDto> result;

        try {
            ExchangeRateQueryBo exchangeRateQueryBo = exchangeRateQueryBiz.queryRateByMemberIdAndCcy(memberId, ccy);
            ExchangeRateQueryRespDto exchangeRateQueryRespDto = ExchangeRateQueryConvert.toExchangeRateQueryRespDto(exchangeRateQueryBo);
            result = new Result<>(exchangeRateQueryRespDto);

        } catch (Exception e) {
            log.error("call 汇率查询API接口异常", e);
            result = ExceptionUtils.getResponse(e);
        }

        log.info("call 汇率查询API接口返回结果:{}", result);
        return result;
    }

    /**
     * 跨境汇款汇率试算
     *
     * @param cbPayCalculateFeeDto 跨境汇款试算参数信息
     * @param traceLogId           日志ID
     * @return CbPayFeeRespDto  查汇结果返回结果
     */
    @Override
    public Result<CbPayFeeRespDto> preCalculateFee(CbPayCalculateFeeDto cbPayCalculateFeeDto, String traceLogId) {

        MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
        log.info("call 试算请求参数:{}", cbPayCalculateFeeDto);

        //交易总金额=出款金额+手续费；结算金额=出款金额/交易汇率
        Result<CbPayFeeRespDto> result;

        try {
            ParamValidate.validateParams(cbPayCalculateFeeDto);
            //判断汇款文件批次集合行业类型是否唯一
            int careerTypeCount = cbPayRemittanceManager.checkCareerTypeByBatchNos(cbPayCalculateFeeDto.getFileBatchNoList());
            if (careerTypeCount != 1) {
                log.info("待汇款的批次文件集合包含多个行业类型");
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00216);
            }
            // 判断批次币种是否一致
            List<String> ccyList = proxyCustomsBiz.queryAmlCcy(cbPayCalculateFeeDto.getFileBatchNoList());

            //查询反洗钱币种
            if (!CollectionUtils.isEmpty(ccyList) && ccyList.size() > 1) {
                log.error("call 文件批次包含多种币种：{}，请检查后重试", ccyList);
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00141);
            }
            if (!CollectionUtils.isEmpty(ccyList) && !ccyList.get(0).equals(cbPayCalculateFeeDto.getTargetCcy())) {
                log.error("call 文件批次币种：{}和购汇币种：{},不一致，请检查后重试", ccyList.get(0),
                        cbPayCalculateFeeDto.getTargetCcy());
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00152);
            }
            log.info("商户号:{}, 币种：{}", cbPayCalculateFeeDto.getMemberId(), cbPayCalculateFeeDto.getTargetCcy());

            // 判断账户主体编号是否一致
            for (Long fileBatchId : cbPayCalculateFeeDto.getFileBatchNoList()) {
                String entityNo = redisManager.queryObjectByKey(Constants.CBPAY_AML_ENTITY_KEY +
                        cbPayCalculateFeeDto.getMemberId() + ":" + cbPayCalculateFeeDto.getTargetCcy() + ":" + fileBatchId);
                if (!StringUtil.isBlank(entityNo) && !cbPayCalculateFeeDto.getEntityNo().equals(entityNo)) {
                    log.info("商户账户主体编号不一致，汇款选择编号：{}，反洗钱选择编号：{}", cbPayCalculateFeeDto.getEntityNo(), entityNo);
                    throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00150);
                }
            }

            //判断币种是否存在
            cbPaySettleBankBiz.checkMemberCcy(cbPayCalculateFeeDto.getMemberId(), cbPayCalculateFeeDto.getTargetCcy());

            //出款金额;
            BigDecimal totalTranceMoney = cbPayRemittanceManager.queryRemittanceByBatchNos(cbPayCalculateFeeDto.getFileBatchNoList());
            if (totalTranceMoney.compareTo(cbPayCalculateFeeDto.getTotalAmount()) != 0) {
                log.info("商户申请汇款总金额与文件批次集合汇款总金额不一致，商户申请汇款金额：{}，汇款批次文件集合汇款总金额：{}",
                        cbPayCalculateFeeDto.getTotalAmount(), totalTranceMoney);
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00217);
            }
            log.info("memberId:{},batchNos:{},查询总金额：{}", cbPayCalculateFeeDto.getEntityNo(), cbPayCalculateFeeDto.getFileBatchNoList(),
                    totalTranceMoney);

            //调用计费服务，计算交易手续费
            Long proxyOrderId = orderIdManager.orderIdCreate();
            int functionId = FunctionEnum.getFunctionId(RemittanceOrderType.PROXY_REMITTANCE_ORDER.getCode(), cbPayCalculateFeeDto.getTargetCcy());
            int productId = FunctionEnum.getProductId(RemittanceOrderType.PROXY_REMITTANCE_ORDER.getCode());
            MemberFeeResBo feeResult = cbPayCmBiz.getFeeResult(String.valueOf(proxyOrderId), totalTranceMoney,
                    cbPayCalculateFeeDto.getMemberId(), functionId, productId, traceLogId);

            //交易总金额 = 出款金额 + 交易手续费;
            BigDecimal totalAllMoney = totalTranceMoney.add(feeResult.getFeeAmount());

            log.info("出款金额:{},交易手续费:{},交易总金额:{}", totalTranceMoney, feeResult.getFeeAmount(), totalAllMoney);

            //查询渠道汇率
            Long channelId = cbPayCommonBiz.queryChannelId(cbPayCalculateFeeDto.getMemberId(),
                    cbPayCalculateFeeDto.getTargetCcy(), ChannelTypeEnum.PURCHASE_PAYMENT.getCode());
            CgwExchangeRateResultDto exchangeRate = cbPayChannelFacadeBiz.getExchangeRate(channelId, cbPayCalculateFeeDto.getTargetCcy());
            if (exchangeRate.getCode() != 1) {
                log.error("请求查汇失败,商户号：{}, 币种：{}", cbPayCalculateFeeDto.getMemberId(), cbPayCalculateFeeDto.getTargetCcy());
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00123);
            }
            //调用封装好的查询接口
            ExchangeRateQueryBo exchangeRateQueryBo = exchangeRateQueryBiz.queryFloatRate(cbPayCalculateFeeDto.getMemberId(),
                    cbPayCalculateFeeDto.getTargetCcy(), exchangeRate, cbPayCalculateFeeDto.getEntityNo());
            BigDecimal rate = exchangeRateQueryBo.getSellRateOfCcy();

            log.info("计算后的汇率：{}", rate);
            //结算金额=出款金额/交易汇率;
            BigDecimal balanceAllMoney = totalTranceMoney.divide(rate, 2, BigDecimal.ROUND_DOWN);
            log.info("结算金额:{}", balanceAllMoney);

            QueryBalanceBo accountBalanceReq = new QueryBalanceBo(cbPayCalculateFeeDto.getMemberId().intValue(), 1);
            //账户余额;
            BigDecimal accountMoney = cmClearBizImpl.queryBalance(accountBalanceReq);
            log.info("查询账户余额：{}", accountMoney);
            CbPayFeeRespDto cbPayFeeRespDto = CbPayRemittanceConvert.feeParamConvert(exchangeRate, totalTranceMoney,
                    feeResult.getFeeAmount(), totalAllMoney, balanceAllMoney, accountMoney, rate);
            result = new Result<>(cbPayFeeRespDto);
            log.info("网关查汇接口返回结果：{}", result);

        } catch (Exception e) {
            log.error("网关preCalculateFee（）发生异常:{}", e);
            result = ExceptionUtils.getResponse(e);
        }
        return result;
    }

}
