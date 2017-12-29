package com.baofu.cbpayservice.facade;

import com.baofu.cbpayservice.facade.models.FiCbpayRemitConfigDto;
import com.system.commons.result.Result;

/**
 * <p>
 * 1 ,获取商户自动汇款配置信息
 * <p>
 *
 * @author : wuyazi
 * @date: 2017/12/25
 * @version: 1.0.0
 */
public interface FiCbpayRemitConfigFacade {

    /**
     * 获取商户自动汇款配置信息
     *
     * @param memberId   商户号
     * @param traceLogId 日志ID
     * @return 自动汇款配置信息
     */
    Result<FiCbpayRemitConfigDto> getRemitConfigs(Long memberId, String traceLogId);

}
