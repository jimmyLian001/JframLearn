package com.baofu.cbpayservice.facade;

import com.baofu.cbpayservice.facade.models.*;
import com.system.commons.result.Result;

import java.util.List;
import java.util.Map;

/**
 * 代理报关服务
 * <p>
 * 1、跨境订单批量上传
 * 2、API接口创建跨境订单，可进行汇款和报关
 * </p>
 * <p>
 * User: 不良人 Date:2017/1/6 ProjectName: cbpayservice Version: 1.0
 */
public interface ProxyCustomsFacade {

    /**
     * 跨境订单批量上传
     * 由商户前台导入excel发起报关
     *
     * @param proxyCustomsDto 请求参数
     * @param traceLogId      日志id
     * @return 批次ID
     */
    Result<Long> proxyCustoms(ProxyCustomsDto proxyCustomsDto, String traceLogId);

    /**
     * 代理单跨境结算订单上报
     *
     * @param apiProxyCustomsDto 请求参数
     * @param traceLogId         日志id
     * @return orderId
     */
    Result<Long> apiProxyCustoms(ApiProxyCustomsDto apiProxyCustomsDto, String traceLogId);

    /**
     * 跨境支付订单上报V2
     *
     * @param apiProxyCustomsV2Dto 请求参数
     * @param traceLogId           日志id
     * @return orderId
     */
    Result<Long> apiProxyCustomsV2(ApiProxyCustomsV2Dto apiProxyCustomsV2Dto, String traceLogId);

    /**
     * by lian zd
     * 跨境订单批量上传,包含已校验文件
     * 由商户前台导入excel发起并直接申请汇款
     *
     * @param proxyTotalAmountCheckDto 请求参数
     * @param traceLogId               日志id
     * @return 批次ID
     */
    Result<RemitTotalAmountCheckRespDto> proxyCustomTotalAmountCheck(ProxyTotalAmountCheckDto proxyTotalAmountCheckDto, String traceLogId);

    /**
     * 文件代理跨境结算汇款明细文件支持批量上传
     *
     * @param proxyCustomsBatchDto 请求参数
     * @return 文件批次
     */
    Result<Map> proxyCustomsBatch(ProxyCustomsBatchDto proxyCustomsBatchDto, String traceLogId);

    /**
     * 文件代理跨境结算汇款明细文件校验结果查询
     *
     * @param fileBatchList 请求参数
     * @return 文件批次
     */
    Result<List<FiCbPayFileUploadRespDto>> proxyCustomsBatchQuery(List<Long> fileBatchList, String traceLogId);
}
