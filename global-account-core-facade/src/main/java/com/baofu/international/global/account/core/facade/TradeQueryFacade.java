package com.baofu.international.global.account.core.facade;

import com.baofu.international.global.account.core.facade.model.*;
import com.baofu.international.global.account.core.facade.model.user.PageDataRespDto;
import com.system.commons.result.Result;

import java.util.List;

/**
 * 交易查询接口
 *
 * @author 莫小阳  on 2017/11/7.
 */
public interface TradeQueryFacade {

    /**
     * 交易查询
     *
     * @param tradeQueryReqDto 查询条件对象
     * @param traceLogId       日志ID
     * @return 结果
     */
    Result<PageDataRespDto> tradeAccQuery(TradeQueryReqDto tradeQueryReqDto, String traceLogId);


    /**
     * 提现查询
     *
     * @param tradeQueryReqDto 查询条件对象
     * @param traceLogId       日志ID
     * @return 结果
     */
    Result<PageDataRespDto> withdrawalQuery(TradeQueryReqDto tradeQueryReqDto, String traceLogId);


    /**
     * 管控台 ——》 子账户收支明细查询
     *
     * @param subAccTradeDetailQueryReqDto 参数
     * @param traceLogId                   日志ID
     * @return 结果
     */
    Result<PageDataRespDto> subAccTradeDetailQuery(SubAccTradeDetailQueryReqDto subAccTradeDetailQueryReqDto, String traceLogId);


    /**
     * 根据用户号查询用户账户信息
     *
     * @param userNo     查询条件对象
     * @param userAccNo  用户账号
     * @param traceLogId 日志ID
     * @return 结果
     */
    Result<List<TUserPayeeAccountDto>> queryPayeeAccount(Long userNo, String userAccNo, String traceLogId);


    /**
     * 根据用户号和币种查询用户账户信息
     *
     * @param userAccInfoReqDto 用户信息
     * @param traceLogId        日志ID
     * @return 结果
     */
    Result<List<UserStoreInfoRespDto>> queryUserStoreByCcy(UserAccInfoReqDto userAccInfoReqDto, String traceLogId);


}
