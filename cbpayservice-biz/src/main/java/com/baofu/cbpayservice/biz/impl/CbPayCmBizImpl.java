package com.baofu.cbpayservice.biz.impl;

import com.baofu.accountcenter.service.facade.AccountServiceFacade;
import com.baofu.accountcenter.service.facade.AccountTradeFacade;
import com.baofu.accountcenter.service.facade.dto.req.QueryBalanceReqDto;
import com.baofu.accountcenter.service.facade.dto.req.RechargeReqDto;
import com.baofu.accountcenter.service.facade.dto.req.TransferReqDto;
import com.baofu.accountcenter.service.facade.dto.req.WithdrawReqDto;
import com.baofu.accountcenter.service.facade.dto.resp.QueryBalanceRespDto;
import com.baofu.accountcenter.service.facade.dto.resp.RechargeRespDto;
import com.baofu.accountcenter.service.facade.dto.resp.TransferRespDto;
import com.baofu.accountcenter.service.facade.dto.resp.WithdrawRespDto;
import com.baofu.cbpayservice.biz.CbPayCmBiz;
import com.baofu.cbpayservice.biz.convert.CmParamConvert;
import com.baofu.cbpayservice.biz.convert.SettleFeeConvert;
import com.baofu.cbpayservice.biz.integration.cm.CmClearBizImpl;
import com.baofu.cbpayservice.biz.integration.cm.CmMemberFeeBizImpl;
import com.baofu.cbpayservice.biz.models.*;
import com.baofu.cbpayservice.common.enums.CcyEnum;
import com.baofu.cbpayservice.common.enums.DealCodeEnums;
import com.baofu.cbpayservice.common.enums.ErrorCodeEnum;
import com.baofu.cbpayservice.common.enums.ProFunEnum;
import com.baofu.cbpayservice.dal.models.CbPaySettlePrepaymentDo;
import com.system.commons.exception.BizServiceException;
import com.system.commons.result.Result;
import com.system.commons.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.slf4j.helpers.SystemMarker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 跨境调用清结算Biz层相关操作
 * <p>
 * 1、调用计费服务
 * </p>
 * User: wanght Date:2016/12/19 ProjectName: asias-icpaygate Version: 1.0
 */
@Slf4j
@Service
public class CbPayCmBizImpl implements CbPayCmBiz {

    /**
     * 计费服务
     */
    @Autowired
    private CmMemberFeeBizImpl cmMemberFeeBizImpl;

    /**
     * 清算服务
     */
    @Autowired
    private CmClearBizImpl cmClearBizImpl;

    /**
     * 外币清算交易服务
     */
    @Autowired
    private AccountTradeFacade accountTradeFacade;

    /**
     * 外币币种账户服务
     */
    @Autowired
    private AccountServiceFacade accountServiceFacade;

    /**
     * 垫资商户号
     */
    @Value("${prepayment_member_id}")
    private String prepaymentMemberId;

    /**
     * 调用计费服务
     *
     * @param orderId    订单号
     * @param amount     交易金额
     * @param memberId   商户号
     * @param functionId 功能id
     * @return 计费结果
     */
    @Override
    public MemberFeeResBo getFeeResult(String orderId, BigDecimal amount, Long memberId, Integer functionId,
                                       Integer productId, String traceLogId) {
        // 参数转换
        MemberFeeReqBo memberFeeBo = CmParamConvert.feeRequestConvertV2(orderId, amount, memberId,
                functionId, productId, traceLogId);
        //调用计费系统
        log.info("计费服务请求报文：{}", memberFeeBo);
        MemberFeeResBo memberFeeResBo = cmMemberFeeBizImpl.findMemberFee(memberFeeBo);
        log.info("计费服务响应报文：{}", memberFeeResBo);

        return memberFeeResBo;
    }

    /**
     * 外币账户到在途
     *
     * @param cbPaySettleBo 结汇信息
     */
    public void transferAcc(CbPaySettleBo cbPaySettleBo) {

        if (CcyEnum.CNY.getKey().equals(cbPaySettleBo.getIncomeCcy())) {
            log.warn("外币账户转账支持币种为外币，当前币种为人民币，订单编号为：{}", cbPaySettleBo.getOrderId());
            return;
        }
        //更新商户外币账户到在途账户
        TransferReqDto transferReqDto = new TransferReqDto();
        transferReqDto.setMemberId(cbPaySettleBo.getMemberId());
        transferReqDto.setOrderAmt(cbPaySettleBo.getIncomeAmt());
        transferReqDto.setOrderCcy(cbPaySettleBo.getIncomeCcy());
        transferReqDto.setOrderId(cbPaySettleBo.getOrderId());
        transferReqDto.setOrderSubType(302);
        transferReqDto.setTargetMemberId(cbPaySettleBo.getMemberId());
        log.info("call 调用清算中心转账参数：{}", transferReqDto);
        Result<TransferRespDto> transferRespDtoResult = accountTradeFacade.transferAccounts(transferReqDto,
                MDC.get(SystemMarker.TRACE_LOG_ID));
        log.info("call 调用清算中心转账响应：{}", transferRespDtoResult);

        ResultUtil.handlerResult(transferRespDtoResult);
    }

    /**
     * 在途到外币账户
     *
     * @param cbPaySettleBo 结汇信息
     */
    public void transferToAcc(CbPaySettleBo cbPaySettleBo) {

        if (CcyEnum.CNY.getKey().equals(cbPaySettleBo.getIncomeCcy())) {
            log.warn("外币账户转账支持币种为外币，当前币种为人民币，订单编号为：{}", cbPaySettleBo.getOrderId());
            return;
        }
        //更新商户外币账户到在途账户
        TransferReqDto transferReqDto = new TransferReqDto();
        transferReqDto.setMemberId(cbPaySettleBo.getMemberId());
        transferReqDto.setOrderAmt(cbPaySettleBo.getIncomeAmt());
        transferReqDto.setOrderCcy(cbPaySettleBo.getIncomeCcy());
        transferReqDto.setOrderId(cbPaySettleBo.getOrderId());
        transferReqDto.setOrderSubType(304);
        transferReqDto.setTargetMemberId(cbPaySettleBo.getMemberId());

        log.info("call 调用清算中心转账参数：{}", transferReqDto);
        Result<TransferRespDto> transferRespDtoResult = accountTradeFacade.transferAccounts(transferReqDto,
                MDC.get(SystemMarker.TRACE_LOG_ID));
        log.info("call 调用清算中心转账响应：{}", transferRespDtoResult);
    }


    /**
     * 外币提现
     */
    public void withdrawDeposit(CbPaySettleBo cbPaySettleBo) {

        if (CcyEnum.CNY.getKey().equals(cbPaySettleBo.getIncomeCcy())) {
            log.warn("外币账户提现支持币种为外币，当前币种为人民币，订单编号为：{}", cbPaySettleBo.getOrderId());
            return;
        }
        //从商户外币在途账户至外币汇款科目中
        WithdrawReqDto withdrawReqDto = new WithdrawReqDto();
        withdrawReqDto.setMemberId(cbPaySettleBo.getMemberId());
        withdrawReqDto.setChannelId(cbPaySettleBo.getChannelId());
        withdrawReqDto.setOrderSubType(303);
        withdrawReqDto.setOrderId(cbPaySettleBo.getOrderId());
        withdrawReqDto.setOrderCcy(cbPaySettleBo.getIncomeCcy());
        withdrawReqDto.setOrderAmt(cbPaySettleBo.getIncomeAmt());
        withdrawReqDto.setActualCcy(cbPaySettleBo.getSettleCcy());
        withdrawReqDto.setActualAmt(cbPaySettleBo.getSettleAmt());
        withdrawReqDto.setActualRate(cbPaySettleBo.getSettleRate());
        withdrawReqDto.setTargetCcy(cbPaySettleBo.getSettleCcy());
        withdrawReqDto.setTargetAmt(cbPaySettleBo.getSettleAmt());
        withdrawReqDto.setTargetRate(cbPaySettleBo.getSettleRate());

        log.info("call 调用清算中心提现参数：{}", withdrawReqDto);
        Result<WithdrawRespDto> withdrawResult = accountTradeFacade.withdrawDeposit(withdrawReqDto,
                MDC.get(SystemMarker.TRACE_LOG_ID));
        log.info("call 调用清算中心提现响应：{}", withdrawResult);

        ResultUtil.handlerResult(withdrawResult);
    }


    /**
     * 人民币账户基本账户充值
     *
     * @param cbPaySettleBo 结汇订单参数信息
     * @return 返回清算之后的商户手续费
     */
    public BigDecimal rmbAccRecharge(CbPaySettleBo cbPaySettleBo) {

        //清算计费
        MemberFeeResBo feeResult = getFeeResult(String.valueOf(cbPaySettleBo.getOrderId()),
                cbPaySettleBo.getMemberSettleAmt(),
                cbPaySettleBo.getMemberId(),
                ProFunEnum.PRO_FUN_10180001.getFunctionId(),
                ProFunEnum.PRO_FUN_10180001.getProductId(),
                MDC.get(SystemMarker.TRACE_LOG_ID));

        ClearRequestBo cmRequest = SettleFeeConvert.convertToCmRequest(feeResult, cbPaySettleBo);
        log.info("call 调用清算系统参数，params:{}", cmRequest);
        ClearingResponseBo cmResponse = cmClearBizImpl.acquiringAccount(cmRequest);
        log.info("call 调用清算系统返回结果：{}", cmResponse);

        return feeResult.getFeeAmount();
    }

    /**
     * 查询外币账户
     *
     * @param ccy      币种
     * @param memberId 商户号
     * @return 外币账户
     */
    @Override
    public QueryBalanceRespDto queryForeignCurrencyAcc(String ccy, Long memberId) {

        //查询商户外币账户余额
        QueryBalanceReqDto queryBalanceReqDto = new QueryBalanceReqDto();
        queryBalanceReqDto.setMemberId(memberId);
        queryBalanceReqDto.setAccountType(1);
        queryBalanceReqDto.setCcy(ccy);
        log.info("call 调用清算中心查询账户余额参数：{}", queryBalanceReqDto);
        Result<QueryBalanceRespDto> result = accountServiceFacade.queryAccount(queryBalanceReqDto,
                MDC.get(SystemMarker.TRACE_LOG_ID));
        log.info("call 调用清算中心查询账户余额响应：{}", result);

        ResultUtil.handlerResult(result);

        return result.getResult();
    }

    /**
     * 外币账户充值
     *
     * @param rechargeReqDto 外币账户充值请求参数信息
     */
    @Override
    public void foreignCurrencyRecharge(RechargeReqDto rechargeReqDto) {

        if (CcyEnum.CNY.getKey().equals(rechargeReqDto.getOrderCcy())) {
            log.warn("外币账户充值支持币种为外币，当前币种为人民币，订单编号为：{}", rechargeReqDto.getOrderId());
            return;
        }
        log.info("call 清算系统进行充值，请求参数:{}", rechargeReqDto);
        Result<RechargeRespDto> result = accountTradeFacade.recharge(rechargeReqDto, MDC.get(SystemMarker.TRACE_LOG_ID));
        log.info("call 清算系统进行充值，返回参数:{}", result);

        ResultUtil.handlerResult(result);
    }


    /**
     * 垫资账户转账到商户基本商户
     *
     * @param cbPaySettlePrepaymentDo
     * @param channelId               渠道ID
     */
    @Override
    public BigDecimal memberAccTransfer(CbPaySettlePrepaymentDo cbPaySettlePrepaymentDo, Long channelId) {

        int functionId, productId;
        //0-自动垫资 1-手工垫资
        if (cbPaySettlePrepaymentDo.getAutoFlag() == 0) {
            productId = ProFunEnum.PRO_FUN_10180003.getProductId();
            functionId = ProFunEnum.PRO_FUN_10180003.getFunctionId();
        } else {
            productId = ProFunEnum.PRO_FUN_10180001.getProductId();
            functionId = ProFunEnum.PRO_FUN_10180001.getFunctionId();
        }

        //垫资结汇（垫资账户转账到商户人民币账户+扣除手续费）
        MemberFeeResBo feeResult = getFeeResult(cbPaySettlePrepaymentDo.getApplyId().toString(), cbPaySettlePrepaymentDo.getPreSettleAmt(),
                cbPaySettlePrepaymentDo.getMemberId(), functionId, productId,
                MDC.get(SystemMarker.TRACE_LOG_ID));
        //判断余额是否足够
        BigDecimal balanceResult = cmClearBizImpl.queryBalance(new QueryBalanceBo(Integer.parseInt(prepaymentMemberId), 1));
        if (balanceResult == null || balanceResult.compareTo(cbPaySettlePrepaymentDo.getPreSettleAmt()) == -1) {
            log.info("商户memberId:{},账户余额不足，账户余额——》{}", prepaymentMemberId, balanceResult);
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0065);
        }
        //账户间转账并扣费
        ClearRequestBo request = CmParamConvert.cmRequestConvert(DealCodeEnums.SETTLE_PREPAYMENT.getCode(),
                channelId, prepaymentMemberId, String.valueOf(cbPaySettlePrepaymentDo.getMemberId()),
                functionId, cbPaySettlePrepaymentDo.getApplyId(),
                cbPaySettlePrepaymentDo.getPreSettleAmt(), new BigDecimal("0.00"),
                feeResult.getFeeAmount(), feeResult.getFeeAccId(), productId);
        log.info("结汇垫资清算请求报文：{}", request);
        ClearingResponseBo response = cmClearBizImpl.transferAccount(request);
        log.info("结汇垫资清算响应报文：{}", response);
        return feeResult.getFeeAmount();
    }

    /**
     * 垫资账户充值
     *
     * @param cbPaySettleBo 结汇对象
     */
    public void prepaymentAccRecharge(CbPaySettleBo cbPaySettleBo) {
        ClearRequestBo cmRequest = SettleFeeConvert.convertToCmRequest(cbPaySettleBo, prepaymentMemberId);
        log.info("call 调用清算系统参数，params:{}", cmRequest);
        ClearingResponseBo cmResponse = cmClearBizImpl.acquiringAccount(cmRequest);
        log.info("call 调用清算系统返回结果：{}", cmResponse);
    }

}
