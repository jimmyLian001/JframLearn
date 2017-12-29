package com.baofu.cbpayservice.service.impl;

import com.baofu.cbpayservice.biz.ProxyCustomsBiz;
import com.baofu.cbpayservice.biz.RedisBiz;
import com.baofu.cbpayservice.biz.impl.MqSendServiceImpl;
import com.baofu.cbpayservice.biz.models.ProxyCustomsMqBo;
import com.baofu.cbpayservice.common.constants.Constants;
import com.baofu.cbpayservice.common.enums.ErrorCodeEnum;
import com.baofu.cbpayservice.common.enums.FileStatus;
import com.baofu.cbpayservice.common.enums.MqSendQueueNameEnum;
import com.baofu.cbpayservice.dal.models.FiCbPayFileUploadDo;
import com.baofu.cbpayservice.facade.ProxyCustomsFacade;
import com.baofu.cbpayservice.facade.models.*;
import com.baofu.cbpayservice.manager.CbPayRemittanceManager;
import com.baofu.cbpayservice.manager.OrderIdManager;
import com.baofu.cbpayservice.manager.ProxyCustomsManager;
import com.baofu.cbpayservice.manager.RedisManager;
import com.baofu.cbpayservice.service.convert.ProxyCustomsConvert;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.system.commons.exception.BizServiceException;
import com.system.commons.result.Result;
import com.system.commons.utils.ExceptionUtils;
import com.system.commons.utils.JsonUtil;
import com.system.commons.utils.ParamValidate;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.slf4j.helpers.SystemMarker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * 代理报关facade实现
 * <p/>
 * User: 不良人 Date:2017/1/6 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
@Service
public class ProxyCustomsFacadeImpl implements ProxyCustomsFacade {

    /**
     * 发送mq服务
     */
    @Autowired
    private MqSendServiceImpl mqSendService;

    /**
     * 代理报关服务
     */
    @Autowired
    private ProxyCustomsBiz proxyCustomsBiz;

    /**
     * 主键ID生成服务
     */
    @Autowired
    private OrderIdManager orderIdManager;

    /**
     * 汇款订单操作服务
     */
    @Autowired
    private CbPayRemittanceManager cbPayRemittanceManager;

    /**
     * 代理上报manager服务
     */
    @Autowired
    private ProxyCustomsManager proxyCustomsManager;

    /**
     * 缓存服务
     */
    @Autowired
    private RedisManager redisManager;

    /**
     * redis缓存服务
     */
    @Autowired
    private RedisBiz redisBiz;

    /**
     * 文件代理跨境结算
     *
     * @param proxyCustomsDto 请求参数
     * @return 文件批次
     */
    @Override
    public Result<Long> proxyCustoms(ProxyCustomsDto proxyCustomsDto, String traceLogId) {

        Result<Long> result;
        MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
        log.info("call excel代理跨境结算和报关请求参数:{}", proxyCustomsDto);

        try {
            ParamValidate.validateParams(proxyCustomsDto);
            Long fileBatchNo = orderIdManager.orderIdCreate();
            //文件批次插入
            proxyCustomsBiz.insertFileUpload(ProxyCustomsConvert.toFiCbPayFileUploadBo(proxyCustomsDto, fileBatchNo));
            //发送Mq消息
            ProxyCustomsMqBo proxyCustomsMqBo = ProxyCustomsConvert.toProxyCustomsMqBo(proxyCustomsDto, fileBatchNo);
            mqSendService.sendMessage(MqSendQueueNameEnum.CBPAY_PROXY_CUSTOMS_QUEUE_NAME, proxyCustomsMqBo);
            log.info("call excel代理跨境结算异步处理数据，生产者，消息队列：{},内容为：{}",
                    MqSendQueueNameEnum.CBPAY_PROXY_CUSTOMS_QUEUE_NAME, proxyCustomsMqBo);
            result = new Result<>(fileBatchNo);

        } catch (Exception e) {
            result = ExceptionUtils.getResponse(e);
            log.error("call excel代理跨境结算和报关失败，异常信息：{}", e);

        }
        log.info("call excel代理跨境结算和报关返回结果：{}", result);

        return result;
    }

    /**
     * 代理跨境订单上报
     *
     * @param apiProxyCustomsDto 请求参数
     * @return 跨境订单编号
     */
    @Override
    public Result<Long> apiProxyCustoms(ApiProxyCustomsDto apiProxyCustomsDto, String traceLogId) {

        MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
        log.info("call 代理跨境订单上报请求参数：{}", apiProxyCustomsDto);
        Result<Long> result;
        try {
            Long orderId = proxyCustomsBiz.apiProxyCustom(ProxyCustomsConvert.toProxyCustomsBo(apiProxyCustomsDto));
            result = new Result<>(orderId);

        } catch (Exception e) {
            result = ExceptionUtils.getResponse(e);
            log.error("call 代理跨境订单上报异常，异常信息：{}", e);

        }
        log.info("call 代理跨境订单上报返回结果：{}", result);

        return result;
    }

    /**
     * 跨境支付订单上报V2
     *
     * @param apiProxyCustomsV2Dto 请求参数
     * @return orderId
     */
    @Override
    public Result<Long> apiProxyCustomsV2(ApiProxyCustomsV2Dto apiProxyCustomsV2Dto, String traceLogId) {

        MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
        log.info("call 跨境支付订单上报请求参数：{}", apiProxyCustomsV2Dto);
        Result<Long> result;
        try {
            ParamValidate.validateParams(apiProxyCustomsV2Dto);
            Long orderId = proxyCustomsBiz.apiProxyCustomV2(ProxyCustomsConvert.toProxyCustomsV2Bo(apiProxyCustomsV2Dto));
            result = new Result<>(orderId);

        } catch (Exception e) {
            result = ExceptionUtils.getResponse(e);
            log.error("call 跨境支付订单异常，异常信息：{}", e);

        }
        log.info("call 跨境支付订单上报返回结果：{}", result);

        return result;
    }

    /**
     * 跨境订单批量上传文件总金额校验,包含已校验文件
     * by LZD
     *
     * @param proxyTotalAmountCheckDto 请求参数
     * @param traceLogId               日志id
     * @return 申请汇款金额与批次文件金额是否相等
     */
    @Override
    public Result<RemitTotalAmountCheckRespDto> proxyCustomTotalAmountCheck(ProxyTotalAmountCheckDto proxyTotalAmountCheckDto, String traceLogId) {

        Result<RemitTotalAmountCheckRespDto> result;
        MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
        log.info("call 汇款批次总金额校验请求参数:{}", proxyTotalAmountCheckDto);
        RemitTotalAmountCheckRespDto totalAmountCheckRespDto = new RemitTotalAmountCheckRespDto();

        try {
            ParamValidate.validateParams(proxyTotalAmountCheckDto);
            int careerTypeCount = cbPayRemittanceManager.checkCareerTypeByBatchNos(proxyTotalAmountCheckDto.getFileBatchNoList());
            if (careerTypeCount != 1) {
                log.info("待汇款的批次文件集合包含多个行业类型");
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00216);
            }
            //商户申请的汇款批次文件须处于处理完成状态或反洗钱部分通过
            for (Long fileBatchNo : proxyTotalAmountCheckDto.getFileBatchNoList()) {
                FiCbPayFileUploadDo fileUploadDo = proxyCustomsManager.queryByBatchId(fileBatchNo);
                if (!(fileUploadDo.getStatus().equals(FileStatus.TRUE.getCode()) || fileUploadDo.getStatus().
                        equals(FileStatus.AML_PART_SUCCESS.getCode()))) {
                    log.error("文件批次:{}状态处于{}状态", fileBatchNo,fileUploadDo.getStatus());
                    throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00140, "文件批次状态错误");
                }
            }
            //出款金额;
            BigDecimal totalTranceMoney = cbPayRemittanceManager.queryRemittanceByBatchNos(proxyTotalAmountCheckDto.getFileBatchNoList());
            boolean totalAmountResult = totalTranceMoney.compareTo(proxyTotalAmountCheckDto.getTotalAmount()) == 0 ? Boolean.TRUE : Boolean.FALSE;
            totalAmountCheckRespDto.setFileBatchNoList(proxyTotalAmountCheckDto.getFileBatchNoList());
            totalAmountCheckRespDto.setFileTotalAmount(totalTranceMoney);
            totalAmountCheckRespDto.setTotalAmount(proxyTotalAmountCheckDto.getTotalAmount());
            totalAmountCheckRespDto.setCheckResult(totalAmountResult);
            result = new Result<>(totalAmountCheckRespDto);

        } catch (Exception e) {
            result = ExceptionUtils.getResponse(e);
            log.error("call 汇款批次总金额校验失败，异常信息：{}", e);

        }
        log.info("call 汇款批次总金额校验返回结果：{}", result);

        return result;
    }

    /**
     * 文件代理跨境结算汇款明细文件支持批量上传
     * by LZD
     *
     * @param proxyCustomsBatchDto 请求参数
     * @return 文件批次
     */
    @Override
    public Result<Map> proxyCustomsBatch(ProxyCustomsBatchDto proxyCustomsBatchDto, String traceLogId) {

        Result<Map> result;
        MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
        log.info("call excel代理跨境汇款明细文件批量上传:{}", proxyCustomsBatchDto);
        Map fileBatchResult = Maps.newHashMap();
        try {
            ParamValidate.validateParams(proxyCustomsBatchDto);
            for (ProxyCustomsDto proxyCustomsDto : proxyCustomsBatchDto.getProxyCustomsDtoList()) {
                ParamValidate.validateParams(proxyCustomsDto);
            }
            //判断商户跨境汇款文件明细校验操作是否锁住，文件批次集合是否重复
            Long memberId = proxyCustomsBatchDto.getProxyCustomsDtoList().get(0).getMemberId();
            for (ProxyCustomsDto proxyCustomsDto : proxyCustomsBatchDto.getProxyCustomsDtoList()) {
                if (memberId.longValue() != proxyCustomsDto.getMemberId().longValue()) {
                    log.info("call excel代理跨境汇款明细文件批量上传传入商户号不统一");
                    throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00220);
                }
            }
            boolean lockFlag = redisBiz.isLock(Constants.REMITTANCE_FILE_PROCESSING_FLAG.concat(String.valueOf(memberId)));
            if (lockFlag) {
                log.info("call 商户{}跨境汇款明细文件上传已锁定，请勿频繁操作！", memberId);
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00219);
            }
            Set<Long> dfsFileIdSet = Sets.newHashSet();
            for (ProxyCustomsDto proxyCustomsDto : proxyCustomsBatchDto.getProxyCustomsDtoList()) {
                dfsFileIdSet.add(proxyCustomsDto.getDfsFileId());
            }
            if (dfsFileIdSet.size() != proxyCustomsBatchDto.getProxyCustomsDtoList().size()) {
                log.info("call 代理跨境汇款明细文件批量上传校验解析文件包含重复文件");
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00221);
            }
            //将待校验的汇款批次文件组装成list发送MQ
            List<ProxyCustomsMqBo> proxyCustomsMqBoList = Lists.newArrayList();
            for (ProxyCustomsDto proxyCustomsDto : proxyCustomsBatchDto.getProxyCustomsDtoList()) {
                Long fileBatchNo = orderIdManager.orderIdCreate();
                //文件批次插入
                proxyCustomsBiz.insertFileUpload(ProxyCustomsConvert.toFiCbPayFileUploadBo(proxyCustomsDto, fileBatchNo));
                //发送Mq消息
                fileBatchResult.put(proxyCustomsDto.getDfsFileId(), fileBatchNo);
                ProxyCustomsMqBo proxyCustomsMqBo = ProxyCustomsConvert.toProxyCustomsMqBo(proxyCustomsDto, fileBatchNo);
                proxyCustomsMqBoList.add(proxyCustomsMqBo);
            }
            String jsonProxyCustomsMqBoList = JsonUtil.toJSONString(proxyCustomsMqBoList);
            mqSendService.sendMessage(MqSendQueueNameEnum.CBPAY_PROXY_CUSTOMS_SERIES_QUEUE_NAME, jsonProxyCustomsMqBoList);
            log.info("call excel代理跨境汇款明细文件批量上传异步处理数据，生产者，消息队列：{},内容为：{}",
                    MqSendQueueNameEnum.CBPAY_PROXY_CUSTOMS_SERIES_QUEUE_NAME, jsonProxyCustomsMqBoList);
            result = new Result<>(fileBatchResult);

        } catch (Exception e) {
            result = ExceptionUtils.getResponse(e);
            log.error("call excel代理跨境汇款明细文件批量上传失败，异常信息：{}", e);

        }
        log.info("call excel代理跨境汇款明细文件批量上传返回结果：{}", result);

        return result;
    }

    /**
     * 代理跨境结算汇款明细文件校验结果查询
     * by LZD
     *
     * @param fileBatchList 请求参数
     * @return 文件处理结果
     */
    @Override
    public Result<List<FiCbPayFileUploadRespDto>> proxyCustomsBatchQuery(List<Long> fileBatchList, String traceLogId) {

        Result<List<FiCbPayFileUploadRespDto>> result;
        MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
        log.info("call excel代理跨境汇款明细文件校验结果查询请求参数:{}", fileBatchList);
        List<FiCbPayFileUploadRespDto> respDtoList = Lists.newArrayList();

        try {
            if (fileBatchList.size() == 0) {
                log.info("汇款明细文件校验结果查询申请参数为空");
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00218);
            }

            for (Long fileBatchNo : fileBatchList) {
                FiCbPayFileUploadDo fileUploadDo = proxyCustomsManager.queryByBatchId(fileBatchNo);
                String processingSchedule = redisManager.queryObjectByKey(fileBatchNo.toString()) != null ?
                        redisManager.queryObjectByKey(fileBatchNo.toString()) : "0.00";
                FiCbPayFileUploadRespDto fileUploadRespDto = ProxyCustomsConvert.toFiCbPayFileUploadRespDto(fileUploadDo, processingSchedule);
                respDtoList.add(fileUploadRespDto);
            }
            result = new Result<>(respDtoList);
        } catch (Exception e) {
            result = ExceptionUtils.getResponse(e);
            log.error("call excel代理汇款明细文件校验结果查询失败，异常信息：{}", e);
        }
        log.info("call excel代理汇款明细文件校验结果查询返回结果：{}", result);

        return result;
    }

}
