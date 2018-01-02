package com.baofu.international.global.account.core.facade;

import com.baofu.international.global.account.core.facade.model.CardBinInfoRespDto;
import com.system.commons.result.Result;

/**
 * 卡bin操作接口
 * <p>
 * 1,根据卡bin查询数据
 * </p>
 *
 * @author : hetao  Date: 2017/11/04 ProjectName: globalaccount Version: 1.0
 */
public interface CardBinFacade {

    /**
     * 查询用户安全问题
     *
     * @param cardBin    卡bin
     * @param traceLogId 日志ID
     */
    Result<CardBinInfoRespDto> queryCardBin(String cardBin, String traceLogId);
}
