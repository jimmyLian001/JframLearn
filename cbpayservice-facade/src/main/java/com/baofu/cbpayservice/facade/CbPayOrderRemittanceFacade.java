package com.baofu.cbpayservice.facade;

import com.baofu.cbpayservice.facade.models.CbPaySumFileDto;
import com.baofu.cbpayservice.facade.models.OrderRemittanceDto;
import com.system.commons.result.Result;

/**
 * 提现订单(非文件上传订单)
 * <p>
 * User: 不良人 Date:2017/5/10 ProjectName: cbpayservice Version: 1.0
 */
public interface CbPayOrderRemittanceFacade {

    /**
     * 提现订单（汇款订单）文件上传
     * 由商户前台导入excel发起
     *
     * @param orderRemittanceDto 请求参数
     * @param traceLogId         日志id
     * @return 批次ID
     */
    Result<Long> orderRemitFileUpload(OrderRemittanceDto orderRemittanceDto, String traceLogId);

    /**
     * 根据时间生成汇款订单
     *
     * @param cbPaySumFileDto 请求对象
     * @param traceLogId      日志ID
     * @return 处理结果
     */
    Result<Boolean> createRemittanceOrderByTime(CbPaySumFileDto cbPaySumFileDto, String traceLogId);


    /**
     * 根据时间生成汇款订单(增加了商户自动汇款配置)
     *
     * @param cbPaySumFileDto 请求对象
     * @param traceLogId      日志ID
     * @return 处理结果
     */
    Result<Boolean> autoRemittanceOrderByTime(CbPaySumFileDto cbPaySumFileDto, String traceLogId);

}
