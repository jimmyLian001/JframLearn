package com.baofu.cbpayservice.facade;

import com.baofu.cbpayservice.facade.models.CbPayMemberRateAddDto;
import com.baofu.cbpayservice.facade.models.CbPayMemberRateModifyDto;
import com.baofu.cbpayservice.facade.models.ExchangeRateResultDto;
import com.system.commons.result.Result;

/**
 * <p>
 * 1、新增浮动汇率
 * 2、修改浮动汇率
 * 3、提供首页外币账户汇率展示
 * </p>
 * User: yangjian  Date: 2017-05-15 ProjectName:  Version: 1.0
 */
public interface CbPayMemberRateFacade {

    /**
     * 新增浮动汇率
     *
     * @param cbPayMemberRateAddDto 新增浮动汇率参数信息
     * @param traceLogId            日志编号
     * @return 返回添加结果
     */
    Result<Boolean> addMemberRate(CbPayMemberRateAddDto cbPayMemberRateAddDto, String traceLogId);

    /**
     * 修改浮动汇率
     *
     * @param cbPayMemberRateModifyDto 更新商户浮动汇率
     * @param traceLogId               日志编号
     * @return 返回更新結果
     */
    Result<Boolean> modifyMemberRate(CbPayMemberRateModifyDto cbPayMemberRateModifyDto, String traceLogId);

    /**
     * 提供首页外币账户汇率展示
     *
     * @param memberId   渠道号
     * @param ccy        币种
     * @param traceLogId 日志id
     * @return 返回银行渠道汇率信息
     */
    Result<ExchangeRateResultDto> queryExchangeRateByChannel(Long memberId, String ccy, String traceLogId);
}