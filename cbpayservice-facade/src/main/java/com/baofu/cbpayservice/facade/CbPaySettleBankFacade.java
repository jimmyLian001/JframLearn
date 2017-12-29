package com.baofu.cbpayservice.facade;

import com.baofu.cbpayservice.facade.models.CbPaySettleBankDto;
import com.baofu.cbpayservice.facade.models.ModifySettleBankDto;
import com.system.commons.result.Result;

/**
 * 多币种账户信息Dubbo接口
 * <p>
 * User: 不良人 Date:2017/4/26 ProjectName: cbpayservice Version: 1.0
 */
public interface CbPaySettleBankFacade {

    /**
     * 商户添加币种账户信息
     *
     * @param cbPaySettleBankDto 商户币种账户信息
     * @param traceLogId         日志ID
     * @return 记录编号
     */
    Result<Long> addSettleBank(CbPaySettleBankDto cbPaySettleBankDto, String traceLogId);

    /**
     * 修改商户币种账户信息
     *
     * @param modifySettleBankDto 修改商户币种账户信息
     * @param traceLogId          日志ID
     * @return 成功或失败
     */
    Result<Boolean> modifySettleBank(ModifySettleBankDto modifySettleBankDto, String traceLogId);

}
