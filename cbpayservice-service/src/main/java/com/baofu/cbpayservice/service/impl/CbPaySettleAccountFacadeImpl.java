package com.baofu.cbpayservice.service.impl;

import com.baofu.cbpayservice.biz.CbPaySettleAccountBiz;
import com.baofu.cbpayservice.facade.CbPaySettleAccountFacade;
import com.baofu.cbpayservice.facade.models.SettleAccountDto;
import com.baofu.cbpayservice.service.convert.CbPaySettleConvert;
import com.system.commons.result.Result;
import com.system.commons.utils.ExceptionUtils;
import com.system.commons.utils.ParamValidate;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.slf4j.helpers.SystemMarker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 结汇操作结汇账户管理接口服务实现
 * <p>
 * User: lian zd Date:2017/7/28  ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
@Service
public class CbPaySettleAccountFacadeImpl implements CbPaySettleAccountFacade {

    /**
     * 结汇操作业务逻辑实现接口
     */
    @Autowired
    private CbPaySettleAccountBiz cbPaySettleAccountBiz;

    /**
     * 结汇账户管理新增账户信息(API)
     * 由商户前台发起
     *
     * @param settleAccountDto 请求参数
     * @return 批次ID
     */
    @Override
    public Result<Long> addSettleAccount(SettleAccountDto settleAccountDto, String traceLogId) {
        MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
        log.info("call 跨境结汇账户管理，新增账户信息传入参数:{}", settleAccountDto);
        Result<Long> result;
        try {
            ParamValidate.validateParams(settleAccountDto);
            Long recordId = cbPaySettleAccountBiz.addSettleAccount(CbPaySettleConvert.toCbPaySettleAccountBo(settleAccountDto));
            result = new Result<>(recordId);
        } catch (Exception e) {
            log.error("call 跨境结汇账户管理，新增账户信息异常", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("call 跨境结汇账户管理，新增账户信息返回结果:{}", result);
        return result;
    }

    /**
     * 结汇账户管理修改账户信息(API)
     * 由商户前台发起
     *
     * @param settleAccountDto 请求参数
     * @param recordId         请求参数
     * @return 批次ID
     */
    @Override
    public Result<Boolean> modifySettleAccount(SettleAccountDto settleAccountDto, Long recordId, String traceLogId) {
        MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
        log.info("call 跨境结汇账户管理，修改账户信息传入参数:{}", settleAccountDto);
        Result<Boolean> result;
        try {

            ParamValidate.validateParams(settleAccountDto);
            cbPaySettleAccountBiz.checkSettleAccount(recordId);
            cbPaySettleAccountBiz.modifySettleAccount(CbPaySettleConvert.toCbPaySettleAccountBo(settleAccountDto), recordId);
            result = new Result<>(true);
        } catch (Exception e) {
            log.error("call 跨境结汇账户管理，修改账户信息异常", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("call 跨境结汇账户管理，修改账户信息返回结果:{}", result);
        return result;
    }

    /**
     * 结汇账户管理删除账户信息(API)
     * 由商户前台发起
     *
     * @param settleAccountDto 请求参数
     * @param recordId         请求参数
     * @return 批次ID
     */
    @Override
    public Result<Boolean> deleteSettleAccount(SettleAccountDto settleAccountDto, Long recordId, String traceLogId) {
        MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
        log.info("call 跨境结汇账户管理，删除账户信息传入参数:{}", recordId);
        Result<Boolean> result;
        try {
            ParamValidate.validateParams(settleAccountDto);
            cbPaySettleAccountBiz.checkSettleAccount(recordId);
            cbPaySettleAccountBiz.deleteSettleAccount(CbPaySettleConvert.toCbPaySettleAccountBo(settleAccountDto), recordId);
            result = new Result<>(true);
        } catch (Exception e) {
            log.error("call 跨境结汇账户管理，删除账户信息异常", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("call 跨境结汇账户管理，删除账户信息返回结果:{}", result);
        return result;
    }
}
