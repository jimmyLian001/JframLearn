package com.baofu.cbpayservice.service.impl;

import com.baofu.cbpayservice.dal.mapper.FiCbpayRemitConfigMapper;
import com.baofu.cbpayservice.dal.models.FiCbpayRemitConfigDo;
import com.baofu.cbpayservice.facade.FiCbpayRemitConfigFacade;
import com.baofu.cbpayservice.facade.models.FiCbpayRemitConfigDto;
import com.system.commons.result.Result;
import com.system.commons.utils.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.slf4j.helpers.SystemMarker;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 1 ,获取商户自动汇款配置信息
 * <p>
 *
 * @author : wuyazi
 * @date: 2017/12/25
 * @version: 1.0.0
 */
@Slf4j
@Service
public class FiCbpayRemitConfigFacadeImpl implements FiCbpayRemitConfigFacade {

    /**
     * 自动汇款配置信息服务
     */
    @Autowired
    FiCbpayRemitConfigMapper fiCbpayRemitConfigMapper;

    /**
     * 获取商户自动汇款配置信息
     *
     * @param memberId   商户号
     * @param traceLogId 日志ID
     * @return 自动汇款配置信息
     */
    @Override
    public Result<FiCbpayRemitConfigDto> getRemitConfigs(Long memberId, String traceLogId) {

        Result<FiCbpayRemitConfigDto> result;
        MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
        try {
            log.info("获取商户自动汇款配置信息：{}", memberId);
            FiCbpayRemitConfigDo fiCbpayRemitConfigDo = fiCbpayRemitConfigMapper.selectByKey(memberId);
            FiCbpayRemitConfigDto fiCbpayRemitConfigDto = new FiCbpayRemitConfigDto();
            BeanUtils.copyProperties(fiCbpayRemitConfigDo, fiCbpayRemitConfigDto);
            result = new Result<>(fiCbpayRemitConfigDto);
        } catch (Exception e) {
            log.error("获取商户自动汇款配置信息失败，异常信息：", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("获取商户自动汇款配置信息：{}", result);
        return result;

    }
}
