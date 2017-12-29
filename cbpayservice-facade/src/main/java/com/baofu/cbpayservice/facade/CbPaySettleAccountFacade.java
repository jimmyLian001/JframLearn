package com.baofu.cbpayservice.facade;

import com.baofu.cbpayservice.facade.models.SettleAccountDto;
import com.system.commons.result.Result;

/**
 * 结汇操作结汇账户管理接口服务
 * <p>
 * User: lian zd Date:2017/7/28 ProjectName: cbpayservice Version: 1.0
 */
public interface CbPaySettleAccountFacade {

    /**
     * 结汇账户管理新增账户信息(API)
     * 由商户前台发起
     *
     * @param settleAccountDto 请求参数
     * @param traceLogId       日志id
     * @return 批次ID
     */
    Result<Long> addSettleAccount(SettleAccountDto settleAccountDto, String traceLogId);

    /**
     * 结汇账户管理修改账户信息(API)
     * 由商户前台发起
     *
     * @param settleAccountDto 请求参数
     * @param recordId         请求参数
     * @param traceLogId       日志id
     * @return 批次ID
     */
    Result<Boolean> modifySettleAccount(SettleAccountDto settleAccountDto, Long recordId, String traceLogId);

    /**
     * 结汇账户管理删除账户信息(API)
     * 由商户前台发起
     *
     * @param settleAccountDto 请求参数
     * @param recordId         请求参数
     * @param traceLogId       日志id
     * @return 批次ID
     */
    Result<Boolean> deleteSettleAccount(SettleAccountDto settleAccountDto, Long recordId, String traceLogId);

}
