package com.baofu.international.global.account.core.facade;

import com.baofu.international.global.account.core.facade.model.ChannelWithdrawDetailsRespDto;
import com.baofu.international.global.account.core.facade.model.UserWithdrawDetailsRespDto;
import com.baofu.international.global.account.core.facade.model.WithdrawDetailsReqDto;
import com.baofu.international.global.account.core.facade.model.WithdrawDistributeRespDto;
import com.system.commons.result.PageRespDTO;
import com.system.commons.result.Result;

/**
 * 提现明细管理后台查询接口
 * <p>
 * 1.用户提现明细 后台查询
 * 2.渠道提现明细 后台查询
 * 3.提现下发 后台查询
 * </p>
 *
 * @author dxy
 * @date 2017/11/21
 */
public interface WithdrawDetailsQueryFacade {

    /**
     * 用户提现明细 后台查询 facade
     *
     * @param req        WithdrawDetailsReqDto
     * @param traceLogId traceLogId
     * @return PageResoDto
     */
    Result<PageRespDTO<UserWithdrawDetailsRespDto>> userWithdrawDetailsQuery(WithdrawDetailsReqDto req, String traceLogId);

    /**
     * 渠道提现明细 后台查询 facade
     *
     * @param req        WithdrawDetailsReqDto
     * @param traceLogId traceLogId
     * @return PageResoDto
     */
    Result<PageRespDTO<ChannelWithdrawDetailsRespDto>> channelWithdrawDetailsQuery(WithdrawDetailsReqDto req, String traceLogId);

    /**
     * 提现下发 后台查询 facade
     *
     * @param req        WithdrawDetailsReqDto
     * @param traceLogId traceLogId
     * @return PageResoDto
     */
    Result<PageRespDTO<WithdrawDistributeRespDto>> withdrawDistributeQuery(WithdrawDetailsReqDto req, String traceLogId);
}
