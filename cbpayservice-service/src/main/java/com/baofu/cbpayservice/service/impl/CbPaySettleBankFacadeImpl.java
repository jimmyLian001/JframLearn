package com.baofu.cbpayservice.service.impl;

import com.baofu.cbpayservice.biz.CbPaySettleBankBiz;
import com.baofu.cbpayservice.biz.RedisBiz;
import com.baofu.cbpayservice.common.enums.AccTypeEnum;
import com.baofu.cbpayservice.common.enums.ErrorCodeEnum;
import com.baofu.cbpayservice.common.util.StringUtil;
import com.baofu.cbpayservice.facade.CbPaySettleBankFacade;
import com.baofu.cbpayservice.facade.models.CbPaySettleBankDto;
import com.baofu.cbpayservice.facade.models.ModifySettleBankDto;
import com.baofu.cbpayservice.service.convert.CbPaySettleBankConvert;
import com.system.commons.exception.BizServiceException;
import com.system.commons.result.Result;
import com.system.commons.utils.ExceptionUtils;
import com.system.commons.utils.ParamValidate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.MDC;
import org.slf4j.helpers.SystemMarker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 多币种账户信息接口实现
 * <p>
 * User: 不良人 Date:2017/4/26 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
@Service
public class CbPaySettleBankFacadeImpl implements CbPaySettleBankFacade {

    /**
     * 多币种账户信息服务层接口
     */
    @Autowired
    private CbPaySettleBankBiz cbPaySettleBankBiz;

    /**
     * redis 服务
     */
    @Autowired
    private RedisBiz redisBiz;

    /**
     * 添加多币种账户信息
     *
     * @param cbPaySettleBankDto 多币种账户信息
     * @param traceLogId         日志ID
     * @return 记录编号
     */
    @Override
    public Result<Long> addSettleBank(CbPaySettleBankDto cbPaySettleBankDto, String traceLogId) {

        MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
        log.info("call 添加多币种账户信息接口,请求参数={}", cbPaySettleBankDto);
        Result<Long> result;
        try {
            ParamValidate.validateParams(cbPaySettleBankDto);
            String bankAccType = cbPaySettleBankDto.getBankAccType();
            if ((AccTypeEnum.TYPE_2.getKey() + "").equals(bankAccType) && StringUtil.isBlank(cbPaySettleBankDto.getCountryCode())) {
                log.error("添加多币种账户失败，账户类型为个人时，国别不能为空");
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00149);
            }
            Long recordId = cbPaySettleBankBiz.addSettleBank(CbPaySettleBankConvert.toCbPaySettleBankBo(cbPaySettleBankDto));
            result = new Result<>(recordId);
        } catch (Exception e) {
            log.error("call 添加多币种账户信息异常:", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("call 添加多币种账户信息接口，返回信息={}", result);

        return result;
    }

    /**
     * 修改商户币种账户信息
     *
     * @param modifySettleBankDto 修改商户币种账户信息
     * @param traceLogId          日志ID
     * @return 成功或失败
     */
    @Override
    public Result<Boolean> modifySettleBank(ModifySettleBankDto modifySettleBankDto, String traceLogId) {

        MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
        log.info("call 修改商户币种账户信息,请求参数={}", modifySettleBankDto);
        Result<Boolean> result;
        String key = null;

        try {
            String bankAccType = modifySettleBankDto.getBankAccType();
            if ((AccTypeEnum.TYPE_2.getKey() + "").equals(bankAccType) && StringUtil.isBlank(modifySettleBankDto.getCountryCode())) {
                log.error("修改多币种账户失败，账户类型为个人时，国别不能为空");
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00149);
            }
            ParamValidate.validateParams(modifySettleBankDto);
            key = redisBiz.preventRepeat(modifySettleBankDto.getMemberId() + modifySettleBankDto.getSettleCcy(), 3000L);
            cbPaySettleBankBiz.modifySettleBank(CbPaySettleBankConvert.toModifySettleBankBo(modifySettleBankDto));
            result = new Result<>(true);
        } catch (Exception e) {
            log.error("call 修改商户币种账户信息异常——》{}", e);
            result = ExceptionUtils.getResponse(e);
        } finally {
            if (StringUtils.isNotBlank(key)) {
                redisBiz.deleteKey(key);
            }
        }
        log.info("call 修改商户币种账户信息,返回结果={}", result);

        return result;
    }
}
