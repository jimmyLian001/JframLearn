package com.baofu.cbpayservice.facade;

import com.baofu.cbpayservice.facade.models.BatchRemitDto;
import com.baofu.cbpayservice.facade.models.BatchRemitTrialDto;
import com.baofu.cbpayservice.facade.models.ProxyCustomsDto;
import com.baofu.cbpayservice.facade.models.res.AccountBalanceRespDto;
import com.baofu.cbpayservice.facade.models.res.BatchRemitTrialRespDto;
import com.system.commons.result.Result;

import java.util.List;

/**
 * 批量汇款接口
 * <p>
 *
 * @author 不良人 Date:2017/9/22 ProjectName: cbpayservice Version: 1.0
 */
public interface BatchRemitFacade {

    /**
     * 查询余额接口
     *
     * @param memberId   商户号
     * @param traceLogId 日志ID
     * @return 账户余额
     */
    Result<AccountBalanceRespDto> queryBalance(Long memberId, String traceLogId);

    /**
     * 批量汇款文件上传
     *
     * @param proxyCustomsDto 请求参数
     * @param traceLogId      日志id
     * @return 批次ID
     */
    Result<Long> batchRemitFileUpload(ProxyCustomsDto proxyCustomsDto, String traceLogId);

    /**
     * 批量汇款-试算
     *
     * @param batchRemitDtoList 汇款参数集合
     * @param traceLogId        日志id
     * @return 创建结果
     */
    Result<List<BatchRemitTrialRespDto>> trial(List<BatchRemitTrialDto> batchRemitDtoList, String traceLogId);

    /**
     * 批量汇款
     *
     * @param batchRemitDtoList 汇款参数集合
     * @param traceLogId        日志id
     * @return 创建结果
     */
    Result<Boolean> batchRemit(List<BatchRemitDto> batchRemitDtoList, String traceLogId);
}
