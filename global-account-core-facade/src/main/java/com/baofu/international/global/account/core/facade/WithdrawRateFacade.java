package com.baofu.international.global.account.core.facade;

import com.baofu.international.global.account.core.facade.model.user.PageDataRespDto;
import com.baofu.international.global.account.core.facade.model.WithdrawRateReqDto;
import com.system.commons.result.Result;

/**
 * <p>
 * 提现费率.
 * </p>
 * User: yimo  Date: 2017/11/22. ProjectName:  Version: 1.0
 */
public interface WithdrawRateFacade {
    /**
     * 根据条件查询提现费率信息
     *
     * @param reqDto reqDto
     * @return 返回结果
     */
    Result<PageDataRespDto> searchWithdrawRateList(WithdrawRateReqDto reqDto);


    /**
     * 修改提现费率
     *
     * @param reqDto reqDto
     * @return 返回结果
     */
    Result<Boolean> editWithdrawRate(WithdrawRateReqDto reqDto);

    /**
     * 新增提现费率
     *
     * @param reqDto reqDto
     * @return 返回结果
     */
    Result<Boolean> addWithdrawRate(WithdrawRateReqDto reqDto);
}
