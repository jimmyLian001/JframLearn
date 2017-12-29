package com.baofu.cbpayservice.biz;

import com.baofu.accountcenter.service.facade.dto.req.RechargeReqDto;
import com.baofu.accountcenter.service.facade.dto.resp.QueryBalanceRespDto;
import com.baofu.cbpayservice.biz.models.CbPaySettleBo;
import com.baofu.cbpayservice.biz.models.MemberFeeResBo;
import com.baofu.cbpayservice.dal.models.CbPaySettlePrepaymentDo;

import java.math.BigDecimal;

/**
 * 跨境调用清结算Biz层相关操作
 * <p>
 * 1、调用计费服务
 * </p>
 * User: wanght Date:2017/03/03 ProjectName: cbpay-service Version: 1.0
 */
public interface CbPayCmBiz {

    /**
     * 调用计费服务
     *
     * @param orderId    订单号
     * @param amount     交易金额
     * @param memberId   商户号
     * @param functionId 功能id
     * @return 计费结果
     */
    MemberFeeResBo getFeeResult(String orderId, BigDecimal amount, Long memberId, Integer functionId, Integer productId, String traceLogId);

    /**
     * 外币账户到在途
     *
     * @param cbPaySettleBo 结汇信息
     */
    void transferAcc(CbPaySettleBo cbPaySettleBo);

    /**
     * 在途到外币账户
     *
     * @param cbPaySettleBo 结汇信息
     */
    void transferToAcc(CbPaySettleBo cbPaySettleBo);

    /**
     * 外币提现
     * @param cbPaySettleBo 结汇申请回执请求对象
     */
    void withdrawDeposit(CbPaySettleBo cbPaySettleBo);

    /**
     * 人民币账户基本账户充值
     *
     * @param cbPaySettleBo 结汇订单参数信息
     * @return 返回清算之后的商户手续费
     */
    BigDecimal rmbAccRecharge(CbPaySettleBo cbPaySettleBo);

    /**
     * 查询外币账户
     *
     * @param ccy      币种
     * @param memberId 商户号
     */
    QueryBalanceRespDto queryForeignCurrencyAcc(String ccy, Long memberId);

    /**
     * 外币账户充值
     *
     * @param rechargeReqDto 外币账户充值请求参数信息
     */
    void foreignCurrencyRecharge(RechargeReqDto rechargeReqDto);

    /**
     * 垫资账户转账到商户基本商户
     *
     * @param cbPaySettlePrepaymentDo 结汇垫资Do对象
     * @param channelId               渠道ID
     */
    BigDecimal memberAccTransfer(CbPaySettlePrepaymentDo cbPaySettlePrepaymentDo, Long channelId);

    /**
     * 垫资账户充值
     *
     * @param cbPaySettleBo 结汇对象
     */
    void prepaymentAccRecharge(CbPaySettleBo cbPaySettleBo);
}
