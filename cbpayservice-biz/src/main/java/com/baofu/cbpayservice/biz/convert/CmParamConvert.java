package com.baofu.cbpayservice.biz.convert;

import com.baofoo.cache.bill.service.facade.model.MemberFeeReqDTO;
import com.baofoo.cache.bill.service.facade.model.MemberFeeResDTO;
import com.baofoo.clearing.facade.enums.CmResultEnum;
import com.baofoo.clearing.facade.model.AccountBalanceReqDTO;
import com.baofoo.clearing.facade.model.ClearingRequestV2DTO;
import com.baofoo.clearing.facade.model.ClearingResponseDTO;
import com.baofu.cbpayservice.biz.models.*;
import com.baofu.cbpayservice.common.enums.ClearAccResultEnum;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * 清算参数信息转换
 * <p>
 * 1、清算记账参数信息转换
 * 2、计费参数转换
 * </p>
 * User: wanght Date:2016/11/11 ProjectName: cbpay-service Version: 1.0
 */
public class CmParamConvert {

    /**
     * 清算记账参数信息转换
     *
     * @param dealCode   处理码
     * @param channelId  渠道
     * @param payerCode  商户号
     * @param payeeCode  商户号
     * @param functionId 功能id
     * @param orderId    汇款批次
     * @param amount     汇款金额
     * @param payerFee   汇款手续费
     * @param feeAcctId  计费账户：1 基本 2 预付费
     * @return 清算转换信息
     */
    public static ClearRequestBo cmRequestConvert(Integer dealCode, Long channelId, String payerCode, String payeeCode,
                                                  Integer functionId, Long orderId, BigDecimal amount, BigDecimal payerFee,
                                                  BigDecimal payeeFee, Integer feeAcctId, Integer productId) {

        ClearRequestBo request = new ClearRequestBo();
        request.setPayMemberId(0L);
        request.setBusinessNo("");
        request.setTradeNo("");
        request.setMemberTransId("");
        request.setFeeAcctId(feeAcctId);
        // 订单号
        request.setOrderId(orderId);
        // 发生金额
        request.setDealAmount(amount);
        //第一步code
        request.setDealCode(dealCode);
        //功能
        request.setFunctionId(functionId);
        request.setProductId(productId);
        // 默认0
        request.setChannelId(channelId);
        //自己算费，赋值1
        request.setHasCalculatePrice(1);
        request.setPayerFee(payerFee);
        request.setPayeeFee(payeeFee);
        // 商户号
        request.setPayerAcctCode(payerCode);
        // 商户号
        request.setPayeeAcctCode(payeeCode);
        return request;
    }

    /**
     * 计费请求参数转换V2
     *
     * @param orderId    订单号
     * @param amount     交易金额
     * @param memberId   商户id
     * @param functionId 功能id
     * @return 返回计费请求参数信息
     */
    public static MemberFeeReqBo feeRequestConvertV2(String orderId, BigDecimal amount, Long memberId, Integer functionId, Integer productId, String traceLogId) {

        MemberFeeReqBo memberFeeBo = new MemberFeeReqBo();
        memberFeeBo.setAmount(amount);
        memberFeeBo.setMemberId(String.valueOf(memberId));
        memberFeeBo.setFunctionId(String.valueOf(functionId));
        memberFeeBo.setProductId(String.valueOf(productId));
        memberFeeBo.setOrderId(orderId);
        memberFeeBo.setTransLogId(traceLogId);
        memberFeeBo.setTransSysCode("cross-border-pay:cbpay-service");

        return memberFeeBo;
    }

    /**
     * 清算查询参数转换
     *
     * @param queryBalanceBo
     * @return
     */
    public static AccountBalanceReqDTO cmRequestConvert(QueryBalanceBo queryBalanceBo) {

        AccountBalanceReqDTO accountBalanceReqDTO = new AccountBalanceReqDTO();
        accountBalanceReqDTO.setMemberId(queryBalanceBo.getMemberId());
        accountBalanceReqDTO.setAccountType(queryBalanceBo.getAccountType());
        accountBalanceReqDTO.setTransLogId(UUID.randomUUID().toString());
        accountBalanceReqDTO.setTransSysCode("cross-border-pay:cbpay-service");
        return accountBalanceReqDTO;
    }

    /**
     * 清算参数转换
     *
     * @param clearRequestBo
     * @return
     */
    public static ClearingRequestV2DTO cmRequestConvert(ClearRequestBo clearRequestBo) {

        ClearingRequestV2DTO clearingRequestV2DTO = new ClearingRequestV2DTO();
        clearingRequestV2DTO.setMemberId(clearRequestBo.getMemberId());
        clearingRequestV2DTO.setBusinessNo(clearRequestBo.getBusinessNo());
        clearingRequestV2DTO.setTradeNo(clearRequestBo.getTradeNo());
        clearingRequestV2DTO.setMemberTransId(clearRequestBo.getMemberTransId());
        clearingRequestV2DTO.setDealCode(clearRequestBo.getDealCode());
        clearingRequestV2DTO.setChannelId(clearRequestBo.getChannelId());
        clearingRequestV2DTO.setPayerAcctCode(clearRequestBo.getPayerAcctCode());
        clearingRequestV2DTO.setPayeeAcctCode(clearRequestBo.getPayeeAcctCode());
        clearingRequestV2DTO.setFunctionId(clearRequestBo.getFunctionId());
        clearingRequestV2DTO.setOrderId(clearRequestBo.getOrderId());
        clearingRequestV2DTO.setDealAmount(clearRequestBo.getDealAmount());
        clearingRequestV2DTO.setPayerFee(clearRequestBo.getPayerFee());
        clearingRequestV2DTO.setPayeeFee(clearRequestBo.getPayeeFee());
        clearingRequestV2DTO.setFeeAcctId(clearRequestBo.getFeeAcctId());
        clearingRequestV2DTO.setProductId(clearRequestBo.getProductId());
        clearingRequestV2DTO.setHasCalculatePrice(clearRequestBo.getHasCalculatePrice());
        clearingRequestV2DTO.setTransLogId(UUID.randomUUID().toString());
        clearingRequestV2DTO.setTransSysCode("cross-border-pay:cbpay-service");
        /**
         * 下面几个参数基本账户充值用到CbPayCmBizImpl,rmbAccRecharge
         */
        clearingRequestV2DTO.setPayerCurrencyCode(clearRequestBo.getPayerCurrencyCode());
        clearingRequestV2DTO.setPayeeCurrencyCode(clearRequestBo.getPayeeCurrencyCode());
        clearingRequestV2DTO.setPayMemberId(clearRequestBo.getPayMemberId());
        clearingRequestV2DTO.setSuccessTime(clearRequestBo.getSuccessTime());
        clearingRequestV2DTO.setSynNoticeMa(clearRequestBo.isSynNoticeMa());

        return clearingRequestV2DTO;
    }

    public static MemberFeeReqDTO cmRequestConvert(MemberFeeReqBo memberFeeBo) {

        MemberFeeReqDTO memberFeeReqDTO = new MemberFeeReqDTO();
        memberFeeReqDTO.setProductId(memberFeeBo.getProductId());
        memberFeeReqDTO.setFunctionId(memberFeeBo.getFunctionId());
        memberFeeReqDTO.setAmount(memberFeeBo.getAmount());
        memberFeeReqDTO.setMemberId(memberFeeBo.getMemberId());
        memberFeeReqDTO.setOrderId(memberFeeBo.getOrderId());
        memberFeeReqDTO.setTransLogId(memberFeeBo.getTransLogId());
        memberFeeReqDTO.setTransSysCode(memberFeeBo.getTransSysCode());

        return memberFeeReqDTO;
    }

    public static ClearingResponseBo cmResponseConvert(ClearingResponseDTO clearingResponseDTO) {

        ClearingResponseBo clearingResponseBo = new ClearingResponseBo();
        clearingResponseBo.setClearAccResultEnum(enumConvert(clearingResponseDTO.getCmResultEnum()));
        clearingResponseBo.setDealId(clearingResponseDTO.getDealId());
        clearingResponseBo.setAmount(clearingResponseDTO.getAmount());
        clearingResponseBo.setNotifyMaState(clearingResponseDTO.isNotifyMaState());
        clearingResponseBo.setPayerFee(clearingResponseDTO.getPayerFee());
        clearingResponseBo.setPayeeFee(clearingResponseDTO.getPayeeFee());
        clearingResponseBo.setDealDate(clearingResponseDTO.getDealDate());

        return clearingResponseBo;
    }

    public static MemberFeeResBo cmResponseConvert(MemberFeeResDTO memberFeeResDTO) {

        MemberFeeResBo memberFeeResBo = new MemberFeeResBo();
        memberFeeResBo.setFeeRuleId(memberFeeResDTO.getFeeRuleId());
        memberFeeResBo.setFeeStrategyId(memberFeeResDTO.getFeeStrategyId());
        memberFeeResBo.setFeeAmount(memberFeeResDTO.getFeeAmount());
        memberFeeResBo.setFeeModelId(memberFeeResDTO.getFeeModelId());
        memberFeeResBo.setFeeRate(memberFeeResDTO.getFeeRate());
        memberFeeResBo.setFeeAccId(memberFeeResDTO.getFeeAccId());
        memberFeeResBo.setMinTarget(memberFeeResDTO.getMinTarget());
        memberFeeResBo.setMaxTarget(memberFeeResDTO.getMaxTarget());
        memberFeeResBo.setMinFee(memberFeeResDTO.getMinFee());
        memberFeeResBo.setMaxFee(memberFeeResDTO.getMaxFee());
        memberFeeResBo.setBaseFee(memberFeeResDTO.getBaseFee());

        return memberFeeResBo;
    }

    public static ClearAccResultEnum enumConvert(CmResultEnum cmResultEnum) {

        return ClearAccResultEnum.explain(cmResultEnum.getCode());
    }
}
