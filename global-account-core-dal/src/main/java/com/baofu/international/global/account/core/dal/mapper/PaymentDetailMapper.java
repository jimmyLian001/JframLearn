package com.baofu.international.global.account.core.dal.mapper;

import com.baofu.international.global.account.core.dal.model.*;

import java.util.List;

/**
 * 描述：收支明细表操作类
 */
public interface PaymentDetailMapper {
    /**
     * 查询交易信息
     *
     * @param tradeQueryDo 交易查询封装参数信息
     * @return 结果
     */
    List<TradeAccQueryDo> tradeAccQuery(TradeQueryDo tradeQueryDo);

    /**
     * 查询收支明细信息
     *
     * @param paymentDetailDo 请求参数
     * @return 收支明细信息
     */
    PaymentDetailDo selectPaymentDetail(PaymentDetailDo paymentDetailDo);

    /**
     * 新增收款账户收支明细
     *
     * @param paymentDetailDo 收款账户收支明细信息
     * @return 受影响的行数
     */
    int insertPaymentDetail(PaymentDetailDo paymentDetailDo);

    /**
     * 子账户收支明细查询
     *
     * @param subAccTradeDetailQueryReqDo 参数
     * @return 结果
     */
    List<SubAccTradeDetailDo> subAccTradeDetailQuery(SubAccTradeDetailQueryReqDo subAccTradeDetailQueryReqDo);
}