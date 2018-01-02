package com.baofu.international.global.account.core.facade;

import com.baofu.international.global.account.core.facade.model.user.PageDataRespDto;
import com.baofu.international.global.account.core.facade.model.UserWithdrawApplyQueryReqDto;
import com.baofu.international.global.account.core.facade.model.UserWithdrawSumQueryReqDto;
import com.system.commons.result.Result;

/**
 * description:用户提现查询接口
 * <p/>
 * Created by liy on 2017/11/22 ProjectName：account
 */
public interface UserWithdrawQueryFacade {

    /**
     * 用户提现汇总查询
     *
     * @param reqDto 查询条件对象
     * @param logId  日志ID
     * @return 结果集
     */
    Result<PageDataRespDto> userWithdrawSumQuery(UserWithdrawSumQueryReqDto reqDto, String logId);

    /**
     * 用户提现申请查询
     *
     * @param reqDto 查询条件对象
     * @param logId  日志ID
     * @return 结果集
     */
    Result<PageDataRespDto> userWithdrawApplyQuery(UserWithdrawApplyQueryReqDto reqDto, String logId);
}
