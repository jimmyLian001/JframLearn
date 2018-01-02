package com.baofu.international.global.account.core.manager;

import com.baofu.international.global.account.core.dal.model.ChannelWithdrawDetailsDo;
import com.baofu.international.global.account.core.dal.model.UserWithdrawDetailsDo;
import com.baofu.international.global.account.core.dal.model.WithdrawDetailsQueryDo;
import com.baofu.international.global.account.core.dal.model.WithdrawDistributeDo;
import com.system.commons.result.PageRespDTO;

/**
 * 提现明细管理后台查询Manager
 * <p>
 * 1.用户提现明细 后台查询
 * 2.渠道提现明细 后台查询
 * 3.提现下发 后台查询
 * </p>
 *
 * @author dxy
 * @date 2017/11/21
 */
public interface WithdrawDetailsManager {

    /**
     * 用户提现明细查询
     *
     * @param req 请求参数
     * @return PageRespDTO
     */
    PageRespDTO<UserWithdrawDetailsDo> userWithdrawDetailsQuery(WithdrawDetailsQueryDo req);

    /**
     * 渠道提现明细查询
     *
     * @param req 请求参数
     * @return PageRespDTO
     */
    PageRespDTO<ChannelWithdrawDetailsDo> channelWithdrawDetailsQuery(WithdrawDetailsQueryDo req);

    /**
     * 提现下发查询
     *
     * @param req 请求参数
     * @return PageRespDTO
     */
    PageRespDTO<WithdrawDistributeDo> withdrawDistributeQuery(WithdrawDetailsQueryDo req);
}
