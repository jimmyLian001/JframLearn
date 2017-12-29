package com.baofu.cbpayservice.biz.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.IOUtils;
import com.baofoo.cbcgw.facade.dto.gw.request.CgwRemitReqDto;
import com.baofoo.cbcgw.facade.dto.gw.request.CgwRetryReqDto;
import com.baofoo.cbcgw.facade.dto.gw.request.CgwSumExchangeReqDto;
import com.baofoo.cbcgw.facade.dto.gw.request.CgwSumRemitReqDto;
import com.baofoo.cbcgw.facade.dto.gw.response.result.CgwExchangeRateResultDto;
import com.baofoo.dfs.client.DfsClient;
import com.baofoo.dfs.client.model.CommandResDTO;
import com.baofoo.dfs.client.model.InsertReqDTO;
import com.baofu.cbpayservice.biz.*;
import com.baofu.cbpayservice.biz.convert.*;
import com.baofu.cbpayservice.biz.integration.cm.CmClearBizImpl;
import com.baofu.cbpayservice.biz.models.*;
import com.baofu.cbpayservice.common.constants.Constants;
import com.baofu.cbpayservice.common.enums.*;
import com.baofu.cbpayservice.common.util.StringUtil;
import com.baofu.cbpayservice.dal.models.*;
import com.baofu.cbpayservice.manager.*;
import com.google.common.collect.Lists;
import com.system.commons.exception.BizServiceException;
import com.system.commons.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.slf4j.helpers.SystemMarker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 跨境人民币汇款操作接口
 * <p>
 * 1、创建汇款订单
 * 2、汇款订单审核
 * 3、汇款订单状态更新
 * 4、汇款订单自动创建
 * 5、调用清算系统，查询订单
 * 6、手动创建汇款订单
 * </p>
 * User: wanght Date:2016/11/10 ProjectName: cbpay-service Version: 1.0
 */
@Slf4j
@Service
public class CbPayRemittanceBizImpl implements CbPayRemittanceBiz {

    /**
     * 汇率查询服务
     */
    @Autowired
    ExchangeRateQueryBiz exchangeRateQueryBiz;
    /**
     * cbpay订单操作服务
     */
    @Autowired
    private CbPayOrderManager cbPayOrderManager;
    /**
     * 汇款订单信息操作服务
     */
    @Autowired
    private CbPayRemittanceManager cbPayRemittanceManager;
    /**
     * 清算服务
     */
    @Autowired
    private CmClearBizImpl cmClearBizImpl;
    /**
     * cbpay渠道成本配置操作服务
     */
    @Autowired
    private FiCbPayChannelFeeManager fiCbPayChannelFeeManager;
    @Autowired
    private CbPayCmBiz cbPayCmBiz;
    /**
     * 渠道服务
     */
    @Autowired
    private CbPayChannelFacadeBiz cbPayChannelFacadeBiz;
    /**
     * 发送MQ服务
     */
    @Autowired
    private MqSendService mqSendService;

    @Value("${remittance_file_path}")
    private String remittanceFilePath;

    @Value("${remittance_download_path}")
    private String remittanceDownloadPath;

    @Value("${pageSize}")
    private String pageSize;

    /**
     * 厦门民生银行-线下渠道
     */
    @Value("${online_channne}")
    private String onLineChannne;

    /**
     * 创建宝付订单号
     */
    @Autowired
    private OrderIdManager orderIdManager;

    /**
     * 多币种账户信息数据服务接口
     */
    @Autowired
    private CbPaySettleBankManager cbPaySettleBankManager;

    /**
     * 网关公共服务信息
     */
    @Autowired
    private CbPayCommonBiz cbPayCommonBiz;

    @Autowired
    private ProxyCustomsManager proxyCustomsManager;

    /**
     * 商户API申请信息操作类
     */
    @Autowired
    private FiCbPayMemberApiRqstManager fiCbpayMemberApiRqstManager;


    /**
     * 创建汇款订单
     * 1、商户基本信息校验
     * 2、查询FI_CBPAY_ORDER
     * 3、校验账户金额是否大于汇款金额
     * 4、根据订单类型创建不同的汇款批次（）
     *
     * @param cbPayRemittanceReqBo 请求参数
     */
    @Override
    @Transactional
    public Long createRemittanceOrderV2(CbPayRemittanceReqBo cbPayRemittanceReqBo, BigDecimal sumAmt) {
        String traceLogId = MDC.get(SystemMarker.TRACE_LOG_ID);
        Long startTime = System.currentTimeMillis();

        //查询商户币种账户信息
        FiCbPaySettleBankDo fiCbPaySettleBankDo = cbPaySettleBankManager.selectBankAccByEntityNo(cbPayRemittanceReqBo.getMemberId(),
                cbPayRemittanceReqBo.getTargetCcy(), cbPayRemittanceReqBo.getEntityNo());
        log.info("查询商户币种账户信息：{}", fiCbPaySettleBankDo);
        if (fiCbPaySettleBankDo == null) {
            log.info("未查询到商户号：{}，币种：{}，子账户号：{}的信息：{}", cbPayRemittanceReqBo.getMemberId(),
                    cbPayRemittanceReqBo.getTargetCcy(), cbPayRemittanceReqBo.getEntityNo());
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00130);
        }

        Long channelId = cbPayCommonBiz.queryChannelId(cbPayRemittanceReqBo.getMemberId(),
                cbPayRemittanceReqBo.getTargetCcy(), ChannelTypeEnum.PURCHASE_PAYMENT.getCode());
        log.info("渠道id：{}，商户：{},币种：{}", channelId, cbPayRemittanceReqBo.getMemberId(), cbPayRemittanceReqBo.getTargetCcy());
        if (channelId == null) {
            log.info("未查询到商户分组对应的渠道。memberId:{}，币种：{}", cbPayRemittanceReqBo.getMemberId(), cbPayRemittanceReqBo.getTargetCcy());
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0064);
        }
        Long proxyOrderId = orderIdManager.orderIdCreate();
        log.info("代理订单（非宝付订单）批次号：{}", proxyOrderId);

        // 判断商户账户余额
        BigDecimal balanceResult = cmClearBizImpl.queryBalance(
                new QueryBalanceBo(cbPayRemittanceReqBo.getMemberId().intValue(), 1));

        // 查询手续费
        int functionId = FunctionEnum.getFunctionId(RemittanceOrderType.PROXY_REMITTANCE_ORDER.getCode(), fiCbPaySettleBankDo.getSettleCcy());
        int productId = FunctionEnum.getProductId(RemittanceOrderType.PROXY_REMITTANCE_ORDER.getCode());
        MemberFeeResBo feeResult = cbPayCmBiz.getFeeResult(String.valueOf(proxyOrderId), sumAmt,
                cbPayRemittanceReqBo.getMemberId(), functionId, productId, traceLogId);

        if (balanceResult == null || balanceResult.compareTo(sumAmt.add(feeResult.getFeeAmount())) == -1) {
            log.info("memberId:{},账户余额不足，账户余额——》{}，汇款订单总金额——》{}，无法创建汇款订单",
                    cbPayRemittanceReqBo.getMemberId(), balanceResult, sumAmt);
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0065);
        }

        // 需更新跨境订单列表
        List<Long> fiCbPayOrderDos = new ArrayList<>();
        for (Long orderId : cbPayRemittanceReqBo.getOrderIdList()) {
            fiCbPayOrderDos.add(orderId);

            if (fiCbPayOrderDos.size() == Constants.BATCH_DEAL_NUM) {
                // 添加批次号
                cbPayOrderManager.batchModifyCbPayOrder(RemittanceStatus.PROCESSING.getCode(), cbPayRemittanceReqBo.getCreateBy(), fiCbPayOrderDos, String.valueOf(proxyOrderId), null);
                log.info("更新订单：{}", Constants.BATCH_DEAL_NUM);
                fiCbPayOrderDos.clear();
            }
        }
        if (fiCbPayOrderDos.size() > 0) {
            cbPayOrderManager.batchModifyCbPayOrder(RemittanceStatus.PROCESSING.getCode(), cbPayRemittanceReqBo.getCreateBy(), fiCbPayOrderDos, String.valueOf(proxyOrderId), null);
            log.info("剩余跨境订单更新完成:{}", fiCbPayOrderDos.size());
        }

        // 创建代理跨境汇款订单批次
        FiCbPayRemittanceDo proxyFiCbPayRemittanceDo = ParamConvert.remittanceParamConvert(cbPayRemittanceReqBo,
                channelId, proxyOrderId, sumAmt, fiCbPaySettleBankDo, BigDecimal.ZERO);
        cbPayRemittanceManager.createRemittanceOrder(proxyFiCbPayRemittanceDo);
        log.info("汇款订单创建成功！商户：{},批次：{}", cbPayRemittanceReqBo.getMemberId(), proxyOrderId);

        // 创建代理跨境汇款订单附加信息
        FiCbPayRemittanceAdditionDo additionDo = ParamConvert.remittanceAdditionParamConvert(cbPayRemittanceReqBo.getMemberId(),
                proxyFiCbPayRemittanceDo.getCreateBy(), proxyFiCbPayRemittanceDo.getUpdateBy(),
                String.valueOf(proxyOrderId), fiCbPaySettleBankDo, null);
        cbPayRemittanceManager.createRemittanceAddition(additionDo);
        log.info("汇款订单附加信息创建成功！商户：{},批次：{}", cbPayRemittanceReqBo.getMemberId(), proxyOrderId);

        //判断是否是商户API发起的申请，如果是，则更新汇款批次号到表 FI_CBPAY_MEMBER_API_RQST 的 BUSINESS_NO 字段
        if (!StringUtil.isBlank(cbPayRemittanceReqBo.getRemitApplyNo())) {
            fiCbpayMemberApiRqstManager.updateApiRqstInfoByReqNo(cbPayRemittanceReqBo.getRemitApplyNo(), proxyOrderId);
            log.info("call 商户API创建汇款更新关系表成功，汇款批次号：{},商户请求编号：{}",
                    proxyOrderId, cbPayRemittanceReqBo.getRemitApplyNo());
        }
        log.info("批次：{}，跨境订单生成总时间：{}", proxyOrderId, System.currentTimeMillis() - startTime);
        return proxyOrderId;
    }

    /**
     * 汇款订单商户审核
     *
     * @param cbPayRemittanceAuditReqBo 请求参数
     */
    @Override
    public void auditRemittanceOrder(CbPayRemittanceAuditReqBo cbPayRemittanceAuditReqBo) {
        String traceLogId = MDC.get(SystemMarker.TRACE_LOG_ID);

        log.info("订单审核请求参数：{}", cbPayRemittanceAuditReqBo);
        FiCbPayRemittanceDo fiCbPayRemittanceDo = cbPayRemittanceManager.queryRemittanceOrder(cbPayRemittanceAuditReqBo.getBatchNo());
        FiCbPayRemittanceAdditionDo fiCbPayRemittanceAdditionDo = cbPayRemittanceManager.queryRemittanceAddition(
                cbPayRemittanceAuditReqBo.getBatchNo(), cbPayRemittanceAuditReqBo.getMemberId());

        // 判断订单是否存在
        if (fiCbPayRemittanceDo == null) {
            log.info("memberId:{},batchNo:{},该汇款订单不存在", cbPayRemittanceAuditReqBo.getMemberId(),
                    cbPayRemittanceAuditReqBo.getBatchNo());
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0055);
        }

        // 判断是否已审核
        if (!AuditStatus.INIT.getCode().equals(fiCbPayRemittanceDo.getAuditStatus())) {
            log.info("memberId:{},batchNo:{},该汇款订单已审核", cbPayRemittanceAuditReqBo.getMemberId(),
                    cbPayRemittanceAuditReqBo.getBatchNo());
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0058);
        }

        Long memberId = cbPayRemittanceAuditReqBo.getMemberId();

        String status = cbPayRemittanceAuditReqBo.getAuditStatus();
        if (ApplyStatus.TRUE.getCode().equals(status)) {
            log.info("审核通过操作开始...");
            int functionId = FunctionEnum.getFunctionId(fiCbPayRemittanceDo.getOrderType(), fiCbPayRemittanceDo.getSystemCcy());
            int productId = FunctionEnum.getProductId(fiCbPayRemittanceDo.getOrderType());
            MemberFeeResBo feeResult = cbPayCmBiz.getFeeResult(fiCbPayRemittanceDo.getBatchNo(), fiCbPayRemittanceDo.getTransMoney(),
                    fiCbPayRemittanceDo.getMemberNo(), functionId, productId, traceLogId);
            // 调用清算服务
            ClearRequestBo cmRequest = CmParamConvert.cmRequestConvert(DealCodeEnums.remitDealCodeOne(fiCbPayRemittanceDo.getSystemCcy()),
                    fiCbPayRemittanceDo.getChannelId(), String.valueOf(memberId), "", functionId,
                    Long.parseLong(fiCbPayRemittanceDo.getBatchNo()), fiCbPayRemittanceDo.getTransMoney(),
                    feeResult.getFeeAmount(), new BigDecimal("0.00"), feeResult.getFeeAccId(), productId);
            log.info("汇款调用清算第一步请求报文：{}", cmRequest);
            ClearingResponseBo response = cmClearBizImpl.transferAccount(cmRequest);
            log.info("汇款调用清算第一步响应报文：{}", response);
            if (Long.parseLong(onLineChannne) == fiCbPayRemittanceDo.getChannelId()) {
                //汇款线下处理,渠道状态变成银行处理中（PROCESSING）
                updateRemittanceOrder(ApplyStatus.TRUE.getCode(), ChannelStatus.PROCESSING.getCode(), null,
                        fiCbPayRemittanceDo, cbPayRemittanceAuditReqBo.getAuditBy(), null);
            } else {
                fiCbPayRemittanceDo.setApplyStatus(ApplyStatus.TRUE.getCode());
                fiCbPayRemittanceDo.setChannelStatus(ChannelStatus.INIT.getCode());
                fiCbPayRemittanceDo.setUpdateBy(cbPayRemittanceAuditReqBo.getAuditBy());
                fiCbPayRemittanceDo.setTransFee(feeResult.getFeeAmount());

                //查询币种账户信息
                FiCbPaySettleBankDo fiCbPaySettleBankDo = cbPaySettleBankManager.selectBankAccByEntityNo(cbPayRemittanceAuditReqBo.getMemberId(),
                        cbPayRemittanceAuditReqBo.getTargetCcy(), fiCbPayRemittanceAdditionDo.getEntityNo());
                log.info("查询币种账户信息：{}", fiCbPaySettleBankDo);

                List<FiCbPayFileUploadDo> fiCbPayFileUploadDos = proxyCustomsManager.queryByBatchNo(String.valueOf(cbPayRemittanceAuditReqBo.getBatchNo()));
                log.info("查询文件批次信息：{}", fiCbPayFileUploadDos);

                if (CollectionUtils.isEmpty(fiCbPayFileUploadDos)) {
                    log.info("查询文件批次信息为空");
                    throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00139);
                }

                List<CgwRemitReqDto> cgwRemitReqDtoList = new ArrayList<>();
                List<Long> batchFileList = new ArrayList<>();
                BigDecimal remitSumAmt = BigDecimal.ZERO;
                //至查询反洗钱审核状态 0：初始 1：审核通过的状态
                List<Integer> amlStatusList = Lists.newArrayList(0, 1);

                //反洗钱汇率处理
                CgwExchangeRateResultDto exchangeRate = cbPayChannelFacadeBiz.getExchangeRate(fiCbPayRemittanceDo.getChannelId(),
                        fiCbPayRemittanceDo.getSystemCcy());
                if (exchangeRate.getCode() != 1) {
                    log.error("call 创建汇款订单调用渠道查汇失败,商户号：{}, 币种：{}", fiCbPayRemittanceDo.getMemberNo(),
                            fiCbPayRemittanceDo.getSystemCcy());
                    throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00123);
                }
                log.info("call 创建汇款订单查询汇率：{}", exchangeRate.getSellRateOfCcy());
                //计算浮动汇率
                ExchangeRateQueryBo exchangeRateQueryBo = exchangeRateQueryBiz.queryFloatRate(fiCbPayRemittanceDo.getMemberNo(),
                        fiCbPayRemittanceDo.getSystemCcy(), exchangeRate, fiCbPayRemittanceAdditionDo.getEntityNo());
                BigDecimal rate = exchangeRateQueryBo.getSellRateOfCcy();
                log.info("call 创建汇款订单实际汇率为：{}", rate);

                for (FiCbPayFileUploadDo fiCbPayFileUploadDo : fiCbPayFileUploadDos) {
                    BigDecimal amlRate = fiCbPayFileUploadDo.getAmlRate();
                    //更新反洗钱汇率
                    if (fiCbPayFileUploadDo.getAmlRate() == null || fiCbPayFileUploadDo.getAmlRate().compareTo(BigDecimal.ZERO) <= 0) {
                        FiCbPayFileUploadDo fileUploadDo = new FiCbPayFileUploadDo();
                        fileUploadDo.setFileBatchNo(fiCbPayFileUploadDo.getFileBatchNo());
                        fileUploadDo.setAmlRate(rate);
                        proxyCustomsManager.updateFilestatus(fileUploadDo);
                        amlRate = rate;
                    }

                    // 根据文件批次号 + 反洗钱审核状态(通过） 查询汇款明细
                    batchFileList.add(fiCbPayFileUploadDo.getFileBatchNo());
                    List<FiCbPayRemittanceDetailV2Do> fiCbPayRemittanceDetailDo =
                            cbPayOrderManager.queryOrderDetailInfo(fiCbPayFileUploadDo.getFileBatchNo(), amlStatusList, fiCbPayFileUploadDo.getCareerType());
                    log.info("文件批次汇率，批次号：{},汇率：{}", fiCbPayFileUploadDo.getFileBatchNo(), amlRate);
                    CgwRemitReqDetailDto cgwRemitReqDetailDto = DfsParamConvert.channelDetailRequestConvert(
                            cbPayRemittanceAuditReqBo.getBatchNo(), fiCbPayRemittanceDetailDo,
                            fiCbPaySettleBankDo, fiCbPayRemittanceDo, amlRate);
                    cgwRemitReqDtoList.addAll(cgwRemitReqDetailDto.getCgwRemitReqDtoList());
                    remitSumAmt = remitSumAmt.add(cgwRemitReqDetailDto.getRemitSumAmt());
                }

                //更新实际的汇率
                BigDecimal totalTransMoney = cbPayRemittanceManager.queryRemittanceByBatchNos(batchFileList);
                if (fiCbPayRemittanceDo.getExchangeType() != 0) {
                    fiCbPayRemittanceDo.setSystemRate(totalTransMoney.divide(remitSumAmt, 6, BigDecimal.ROUND_DOWN).multiply(new BigDecimal("100")));
                    fiCbPayRemittanceDo.setSystemMoney(remitSumAmt);
                }
                // 上传明细文件
                Long fileId = uploadDetail(cgwRemitReqDtoList, cbPayRemittanceAuditReqBo.getBatchNo(), cbPayRemittanceAuditReqBo.getMemberId());

                if (Currency.CNY.getCode().equals(fiCbPayRemittanceDo.getSystemCcy())) {
                    fiCbPayRemittanceDo.setRealMoney(fiCbPayRemittanceDo.getTransMoney());
                }
                cbPayRemittanceManager.updateRemittanceOrder(fiCbPayRemittanceDo);
                // 更新汇款附加信息
                FiCbPayRemittanceAdditionDo additionDo = ParamConvert.remittanceAdditionParamConvert(cbPayRemittanceAuditReqBo.getMemberId(),
                        null, cbPayRemittanceAuditReqBo.getAuditBy(), cbPayRemittanceAuditReqBo.getBatchNo(), fiCbPaySettleBankDo, fileId);

                log.info("附加信息更新开始：{}", additionDo);
                cbPayRemittanceManager.updateRemittanceAddition(additionDo);
                log.info("附加信息更新完成。");

                autoPurchase(cbPayRemittanceAuditReqBo, fiCbPayRemittanceDo.getSystemCcy(), remitSumAmt);
            }
        } else if (ApplyStatus.FALSE.getCode().equals(status)) {
            // 更新汇款订单状态
            log.info("审核拒绝操作开始...");
            updateRemittanceOrder(ApplyStatus.FALSE.getCode(), ChannelStatus.INIT.getCode(), null, fiCbPayRemittanceDo,
                    cbPayRemittanceAuditReqBo.getAuditBy(), null);
            log.info("汇款审核拒绝更新汇款订单表成功");

            // 更新订单表状态
            updateOrder(RemittanceStatus.INIT.getCode(), cbPayRemittanceAuditReqBo.getAuditBy(), cbPayRemittanceAuditReqBo.getBatchNo());
            log.info("汇款审核拒绝更新订单表成功");
        } else {
            log.info("memberId:{},batchNo:{},该汇款订单状态不正确", cbPayRemittanceAuditReqBo.getMemberId(),
                    cbPayRemittanceAuditReqBo.getBatchNo());
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0054);
        }

        log.info("订单：{}审核成功", cbPayRemittanceAuditReqBo.getBatchNo());
    }

    /**
     * 汇款订单商户审核
     *
     * @param cbPayRemittanceAuditReqBo 请求参数
     */
    @Override
    public void batchRemit(CbPayRemittanceAuditReqBo cbPayRemittanceAuditReqBo) {
        String traceLogId = MDC.get(SystemMarker.TRACE_LOG_ID);
        FiCbPayRemittanceDo fiCbPayRemittanceDo = cbPayRemittanceManager.queryRemittanceOrder(cbPayRemittanceAuditReqBo.getBatchNo());

        // 判断订单是否存在
        if (fiCbPayRemittanceDo == null) {
            log.info("memberId:{},batchNo:{},该汇款订单不存在", cbPayRemittanceAuditReqBo.getMemberId(),
                    cbPayRemittanceAuditReqBo.getBatchNo());
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0055);
        }

        // 判断是否已审核
        if (!AuditStatus.INIT.getCode().equals(fiCbPayRemittanceDo.getAuditStatus())) {
            log.info("memberId:{},batchNo:{},该汇款订单已审核", cbPayRemittanceAuditReqBo.getMemberId(),
                    cbPayRemittanceAuditReqBo.getBatchNo());
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0058);
        }

        // 查询汇款附加信息
        FiCbPayRemittanceAdditionDo additionDo = cbPayRemittanceManager.queryRemittanceAddition(
                cbPayRemittanceAuditReqBo.getBatchNo(), cbPayRemittanceAuditReqBo.getMemberId());

        // 判断订单是否存在
        if (additionDo == null) {
            log.info("memberId:{},batchNo:{},该汇款附加信息不存在", cbPayRemittanceAuditReqBo.getMemberId(),
                    cbPayRemittanceAuditReqBo.getBatchNo());
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0086);
        }

        //查询币种账户信息
        FiCbPaySettleBankDo fiCbPaySettleBankDo = cbPaySettleBankManager.selectBankAccByEntityNo(cbPayRemittanceAuditReqBo.getMemberId(),
                cbPayRemittanceAuditReqBo.getTargetCcy(), additionDo.getEntityNo());

        // 判断订单是否存在
        if (fiCbPaySettleBankDo == null) {
            log.info("memberId:{},batchNo:{},该币种账户不存在", cbPayRemittanceAuditReqBo.getMemberId(),
                    cbPayRemittanceAuditReqBo.getBatchNo());
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00130);
        }

        try {
            // 调用渠道服务
            CgwSumRemitReqDto cgwSumRemitReqDO = DfsParamConvert.channelRequestConvert(fiCbPayRemittanceDo,
                    fiCbPaySettleBankDo, additionDo);
            cgwSumRemitReqDO.setTraceLogId(traceLogId);
            log.info("渠道汇款请求报文：fileId:{},cgwSumRemitReqDO:{}", additionDo.getDfsFileId(), cgwSumRemitReqDO);

            mqSendService.sendMessage(MqSendQueueNameEnum.CGW_CROSS_BORDER_SERVICE_REMIT, cgwSumRemitReqDO);
            // 更新汇款订单状态
            updateRemittanceOrder(ApplyStatus.TRUE.getCode(), ChannelStatus.PROCESSING.getCode(), null,
                    fiCbPayRemittanceDo, cbPayRemittanceAuditReqBo.getAuditBy(), null);
            log.info("渠道响应成功，更新汇款订单表成功");
        } catch (Exception e) {
            log.error("创建汇款文件异常", e);
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0056);
        }
    }

    /**
     * 汇款订单状态更新
     *
     * @param cbPayRemtStatusChangeReqBo 请求参数
     */
    @Override
    public void changeRemittanceOrderStatus(CbPayRemtStatusChangeReqBo cbPayRemtStatusChangeReqBo) {
        FiCbPayRemittanceDo fiCbPayRemittanceDo = cbPayRemittanceManager.queryRemittanceOrder(cbPayRemtStatusChangeReqBo.getBatchNo());
        // 判断订单是否存在
        if (fiCbPayRemittanceDo == null) {
            log.info("memberId:{},batchNo:{},汇款订单状态更新,该汇款订单不存在", cbPayRemtStatusChangeReqBo.getMemberId(),
                    cbPayRemtStatusChangeReqBo.getBatchNo());
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0055);
        }

        log.info("汇款订单状态更新参数：{}", cbPayRemtStatusChangeReqBo);
        updateRemittanceOrder(cbPayRemtStatusChangeReqBo, fiCbPayRemittanceDo);
    }

    /**
     * 汇款订单线下，后台审核接口
     *
     * @param cbPayRemtStatusChangeReqBo 状态更新请求参数
     */
    @Override
    public void backgroundAudit(CbPayRemtStatusChangeReqBo cbPayRemtStatusChangeReqBo) {
        FiCbPayRemittanceDo fiCbPayRemittanceDo = CbPayRemittanceConvert.toFiCbPayRemittanceDo(cbPayRemtStatusChangeReqBo);
        cbPayRemittanceManager.updateRemittanceOrder(fiCbPayRemittanceDo);
    }

    /**
     * 银行返回成功，从用户在途账户扣除汇款金额到备付金
     *
     * @param cbPayRemtStatusChangeReqBo 审核参数对象
     */
    @Override
    public void deductionAmount(CbPayRemtStatusChangeReqBo cbPayRemtStatusChangeReqBo) {
        FiCbPayRemittanceDo fiCbPayRemittanceDo = cbPayRemittanceManager.queryRemittanceOrder(cbPayRemtStatusChangeReqBo.getBatchNo());
        // 判断订单是否存在
        if (fiCbPayRemittanceDo == null) {
            log.info("memberId:{},batchNo:{},该汇款订单不存在", cbPayRemtStatusChangeReqBo.getMemberId(), cbPayRemtStatusChangeReqBo.getBatchNo());
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0055);
        }

        if (AuditStatus.SUCCESS_SECOND_TRUE.getCode().equals(fiCbPayRemittanceDo.getAuditStatus())) {
            log.info("memberId:{},batchNo:{},订单已成功，请勿重复操作", cbPayRemtStatusChangeReqBo.getMemberId(), fiCbPayRemittanceDo.getBatchNo());
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0081);
        }

        if (CMStatus.TRUE.getCode().equals(fiCbPayRemittanceDo.getCmStatus())) {
            log.info("已调用清算系统，请勿重复调用：{}", fiCbPayRemittanceDo);
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0074, "该汇款订单已清算，请勿重复执行");
        }

        // 查询渠道成本配置
        FiCbPayChannelFeeDo fiCbPayChannelFeeDo = fiCbPayChannelFeeManager.queryChannelFee(fiCbPayRemittanceDo.getChannelId(),
                ChannelFeeStatus.TRUE.getCode());
        log.info("渠道成本配置:{}", fiCbPayChannelFeeDo);
        if (fiCbPayChannelFeeDo == null) {
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0063);
        }

        // 调用清算服务成功,更新汇款订单表
        cbPayRemtStatusChangeReqBo.setAuditStatus(AuditStatus.SUCCESS_SECOND_TRUE.getCode());
        cbPayRemtStatusChangeReqBo.setChannelStatus(ChannelStatus.TRUE.getCode());
        cbPayRemtStatusChangeReqBo.setCmStatus(CMStatus.INIT.getCode());
        fiCbPayRemittanceDo = CbPayRemittanceConvert.toFiCbPayRemittanceDo(cbPayRemtStatusChangeReqBo, fiCbPayRemittanceDo);

        //更新汇款订单信息
        log.info("更新汇款订单状态：{}", fiCbPayRemittanceDo);
        cbPayRemittanceManager.updateRemittanceOrder(fiCbPayRemittanceDo);

        // 更新跨境订单表状态
        log.info("更新跨境订单状态,batchNo:{}", cbPayRemtStatusChangeReqBo.getBatchNo());
        updateOrder(RemittanceStatus.TRUE.getCode(), cbPayRemtStatusChangeReqBo.getUpdateBy(), cbPayRemtStatusChangeReqBo.getBatchNo());

        //渠道成本记录
        ChannelCostCalBo channelCostCalBo = new ChannelCostCalBo();
        channelCostCalBo.setCcy(fiCbPayRemittanceDo.getTransCcy());
        channelCostCalBo.setOrderAmt(fiCbPayRemittanceDo.getRealMoney());
        channelCostCalBo.setBatchNo(fiCbPayRemittanceDo.getBatchNo());
        channelCostCalBo.setChannelId(fiCbPayRemittanceDo.getChannelId());
        channelCostCalBo.setMemberId(fiCbPayRemittanceDo.getMemberNo());
        cbPayCommonBiz.channelCostCal(channelCostCalBo);

        // 通知清算
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("batchNo", cbPayRemtStatusChangeReqBo.getBatchNo());
        jsonObject.put("updateBy", cbPayRemtStatusChangeReqBo.getUpdateBy());

        log.info("人工审核成功发送清算MQ：{}", jsonObject.toJSONString());
        mqSendService.sendMessage(MqSendQueueNameEnum.CBPAY_NOTIFY_SETTLE_QUEUE_NAME, jsonObject.toJSONString());
    }

    /**
     * 银行返回失败，从用户在途账户扣除汇款金额到用户基本账户，并且返回汇款手续费
     *
     * @param cbPayRemtStatusChangeReqBo 审核参数对象
     */
    @Override
    public void returnAmount(CbPayRemtStatusChangeReqBo cbPayRemtStatusChangeReqBo) {
        FiCbPayRemittanceDo fiCbPayRemittanceDo = cbPayRemittanceManager.queryRemittanceOrder(cbPayRemtStatusChangeReqBo.getBatchNo());
        // 判断订单是否存在
        if (fiCbPayRemittanceDo == null) {
            log.info("memberId:{},batchNo:{},该汇款订单不存在", cbPayRemtStatusChangeReqBo.getMemberId(), cbPayRemtStatusChangeReqBo.getBatchNo());
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0055);
        }

        if (CMStatus.TRUE.getCode().equals(fiCbPayRemittanceDo.getCmStatus())) {
            log.info("已调用清算系统，请勿重复调用：{}", fiCbPayRemittanceDo);
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0074, "该汇款订单已清算，请勿重复执行");
        }

        cbPayRemtStatusChangeReqBo.setAuditStatus(AuditStatus.FAIL_SECOND_TRUE.getCode());
        cbPayRemtStatusChangeReqBo.setChannelStatus(ChannelStatus.FALSE.getCode());
        cbPayRemtStatusChangeReqBo.setCmStatus(CMStatus.INIT.getCode());
        fiCbPayRemittanceDo = CbPayRemittanceConvert.toFiCbPayRemittanceDo(cbPayRemtStatusChangeReqBo, fiCbPayRemittanceDo);

        //更新汇款订单信息
        cbPayRemittanceManager.updateRemittanceOrder(fiCbPayRemittanceDo);

        // 跨境订单状态改为初始，允许继续创建汇款订单
        updateOrder(RemittanceStatus.INIT.getCode(), cbPayRemtStatusChangeReqBo.getUpdateBy(), cbPayRemtStatusChangeReqBo.getBatchNo());

        // 通知清算
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("batchNo", cbPayRemtStatusChangeReqBo.getBatchNo());
        jsonObject.put("updateBy", cbPayRemtStatusChangeReqBo.getUpdateBy());

        log.info("人工审核失败发送清算MQ：{}", jsonObject.toJSONString());
        mqSendService.sendMessage(MqSendQueueNameEnum.CBPAY_NOTIFY_SETTLE_QUEUE_NAME, jsonObject.toJSONString());
    }

    /**
     * 批量购汇
     *
     * @param cbPayRemittanceAuditReqBo 请求参数
     */
    @Override
    public void batchPurchase(CbPayRemittanceAuditReqBo cbPayRemittanceAuditReqBo, BigDecimal sumAmt) {
        String traceLogId = MDC.get(SystemMarker.TRACE_LOG_ID);
        FiCbPayRemittanceDo fiCbPayRemittanceDo = cbPayRemittanceManager.queryRemittanceOrder(cbPayRemittanceAuditReqBo.getBatchNo());

        // 判断订单是否存在
        if (fiCbPayRemittanceDo == null) {
            log.info("memberId:{},batchNo:{},该汇款订单不存在", cbPayRemittanceAuditReqBo.getMemberId(),
                    cbPayRemittanceAuditReqBo.getBatchNo());
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0055);
        }

        // 判断是否已审核
        if (fiCbPayRemittanceDo.getPurchaseStatus() != 0) {
            log.info("memberId:{},batchNo:{},该购汇订单已发往渠道", cbPayRemittanceAuditReqBo.getMemberId(),
                    cbPayRemittanceAuditReqBo.getBatchNo());
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0081);
        }

        // 查询汇款附加信息
        FiCbPayRemittanceAdditionDo additionDo = cbPayRemittanceManager.queryRemittanceAddition(
                cbPayRemittanceAuditReqBo.getBatchNo(), cbPayRemittanceAuditReqBo.getMemberId());

        // 判断订单是否存在
        if (additionDo == null) {
            log.info("memberId:{},batchNo:{},该汇款附加信息不存在", cbPayRemittanceAuditReqBo.getMemberId(),
                    cbPayRemittanceAuditReqBo.getBatchNo());
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0086);
        }

        //查询币种账户信息
        FiCbPaySettleBankDo fiCbPaySettleBankDo = cbPaySettleBankManager.selectBankAccByEntityNo(cbPayRemittanceAuditReqBo.getMemberId(),
                cbPayRemittanceAuditReqBo.getTargetCcy(), additionDo.getEntityNo());

        // 判断订单是否存在
        if (fiCbPaySettleBankDo == null) {
            log.info("memberId:{},batchNo:{},该币种账户不存在", cbPayRemittanceAuditReqBo.getMemberId(),
                    cbPayRemittanceAuditReqBo.getBatchNo());
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00130);
        }

        try {
            // 发送MQ通知渠道
            CgwSumExchangeReqDto cgwSumExchangeReq = DfsParamConvert.convertToPurchase(fiCbPayRemittanceDo,
                    additionDo.getDfsFileId(), fiCbPaySettleBankDo, sumAmt);
            cgwSumExchangeReq.setTraceLogId(traceLogId);
            log.info("发送MQ通知渠道报文：", cgwSumExchangeReq);
            mqSendService.sendMessage(MqSendQueueNameEnum.CGW_CROSS_BORDER_SERVICE_EXCHANGE, cgwSumExchangeReq);
            // 更新购汇状态-购汇中
            updateRemittanceOrder(ApplyStatus.TRUE.getCode(), ChannelStatus.INIT.getCode(), 2,
                    fiCbPayRemittanceDo, cbPayRemittanceAuditReqBo.getAuditBy(), null);
        } catch (Exception e) {
            log.error("购汇异常", e);
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0082);
        }

    }

    /**
     * 付汇入账费用更新
     *
     * @param cbPayRemittanceBankFeeReqBo 审核参数对象
     */
    @Override
    public void updateBankFee(CbPayRemittanceBankFeeReqBo cbPayRemittanceBankFeeReqBo) {

        //判断入账费用是否为空,凭证DFS文件ID是否为空
        if (cbPayRemittanceBankFeeReqBo.getBankFee() == null || cbPayRemittanceBankFeeReqBo.getReceiptId() == null) {
            log.info("memberId:{},batchNo:{},入账费用为空或凭证ID为空", cbPayRemittanceBankFeeReqBo.getMemberId(), cbPayRemittanceBankFeeReqBo.getBatchNo());
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0003);
        }
        FiCbPayRemittanceDo queryRemittanceOrder = cbPayRemittanceManager.queryRemittanceOrder(cbPayRemittanceBankFeeReqBo.getBatchNo());
        FiCbPayRemittanceAdditionDo fiCbPayRemittanceAdditionDo = cbPayRemittanceManager.queryRemittanceAddition(cbPayRemittanceBankFeeReqBo.getBatchNo(), cbPayRemittanceBankFeeReqBo.getMemberId());

        // 判断订单是否存在
        if (queryRemittanceOrder == null) {
            log.info("memberId:{},batchNo:{},该汇款订单不存在", cbPayRemittanceBankFeeReqBo.getMemberId(), cbPayRemittanceBankFeeReqBo.getBatchNo());
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0055);
        }

        // 判断订单是否汇款成功
        if (!ChannelStatus.TRUE.getCode().equals(queryRemittanceOrder.getChannelStatus())) {
            log.info("memberId:{},batchNo:{},该汇款订单未完成，不可设置入账费用", cbPayRemittanceBankFeeReqBo.getMemberId(), cbPayRemittanceBankFeeReqBo.getBatchNo());
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0054);
        }

        //查询币种账户信息
        FiCbPaySettleBankDo fiCbPaySettleBankDo = cbPaySettleBankManager.selectBankAccByEntityNo(queryRemittanceOrder.getMemberNo(),
                queryRemittanceOrder.getSystemCcy(), fiCbPayRemittanceAdditionDo.getEntityNo());

        if (!"3".equals(fiCbPaySettleBankDo.getCostBorne())) {
            log.info("memberId:{},batchNo:{},costBorne:{},费用承担方非宝付", cbPayRemittanceBankFeeReqBo.getMemberId(),
                    cbPayRemittanceBankFeeReqBo.getBatchNo(), fiCbPaySettleBankDo.getCostBorne());
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00100);
        }
        //判断入账费用状态是否为待审核或已审核通过
        if (queryRemittanceOrder.getFeeStatus() == 1 || queryRemittanceOrder.getFeeStatus() == 2) {
            log.info("memberId:{},batchNo:{},入账费用待审核或已审核通过", cbPayRemittanceBankFeeReqBo.getMemberId(), cbPayRemittanceBankFeeReqBo.getBatchNo());
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00101);
        }
        //参数转化
        FiCbPayRemittanceDo fiCbPayRemittanceDo = CbPayRemittanceConvert.bankFeeParamConvert(cbPayRemittanceBankFeeReqBo);
        //设置入账费用的同时，更改状态为待审核
        fiCbPayRemittanceDo.setFeeStatus(1);
        //调用Manager层
        cbPayRemittanceManager.updateRemittanceOrder(fiCbPayRemittanceDo);

    }

    /**
     * 付汇入账费用审核
     *
     * @param cbPayRemittanceBankFeeReqBo 审核参数对象
     */
    @Override
    public void bankFeeAudit(CbPayRemittanceBankFeeReqBo cbPayRemittanceBankFeeReqBo) {

        FiCbPayRemittanceDo queryRemittanceOrder = cbPayRemittanceManager.queryRemittanceOrder(cbPayRemittanceBankFeeReqBo.getBatchNo());
        FiCbPayRemittanceAdditionDo fiCbPayRemittanceAdditionDo = cbPayRemittanceManager.queryRemittanceAddition(cbPayRemittanceBankFeeReqBo.getBatchNo(), cbPayRemittanceBankFeeReqBo.getMemberId());
        // 判断订单是否存在
        if (queryRemittanceOrder == null) {
            log.info("memberId:{},batchNo:{},该汇款订单不存在", cbPayRemittanceBankFeeReqBo.getMemberId(), cbPayRemittanceBankFeeReqBo.getBatchNo());
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0055);
        }
        //查询币种账户信息
        FiCbPaySettleBankDo fiCbPaySettleBankDo = cbPaySettleBankManager.selectBankAccByEntityNo(queryRemittanceOrder.getMemberNo(),
                queryRemittanceOrder.getSystemCcy(), fiCbPayRemittanceAdditionDo.getEntityNo());
        log.info("商户币种账户信息:{}", fiCbPaySettleBankDo);

        if (!"3".equals(fiCbPaySettleBankDo.getCostBorne())) {
            log.info("memberId:{},batchNo:{},costBorne:{},费用承担方非宝付", cbPayRemittanceBankFeeReqBo.getMemberId(),
                    cbPayRemittanceBankFeeReqBo.getBatchNo(), fiCbPaySettleBankDo.getCostBorne());
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00100);
        }
        //判断入账费用是否已设置
        if (queryRemittanceOrder.getFeeStatus() == 0) {
            log.info("memberId:{},batchNo:{},入账费用未设置", cbPayRemittanceBankFeeReqBo.getMemberId(), cbPayRemittanceBankFeeReqBo.getBatchNo());
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00102);
        }
        //判断入账费用是否已经审核
        if (queryRemittanceOrder.getFeeStatus() == 2 || queryRemittanceOrder.getFeeStatus() == 3) {
            log.info("memberId:{},batchNo:{},入账费用已审核", cbPayRemittanceBankFeeReqBo.getMemberId(), cbPayRemittanceBankFeeReqBo.getBatchNo());
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00103);
        }
        //校验入账费用审核状态参数
        if (cbPayRemittanceBankFeeReqBo.getBankFeeStatus() != 2 && cbPayRemittanceBankFeeReqBo.getBankFeeStatus() != 3) {
            log.info("memberId:{},batchNo:{},入账费用审核状态参数错误", cbPayRemittanceBankFeeReqBo.getMemberId(), cbPayRemittanceBankFeeReqBo.getBatchNo());
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0003);
        }
        //参数转化
        FiCbPayRemittanceDo fiCbPayRemittanceDo = CbPayRemittanceConvert.bankFeeParamConvert(cbPayRemittanceBankFeeReqBo);
        //因bankFee,receiptId在参数转化时有可能被赋值，所以审核时置空
        fiCbPayRemittanceDo.setBankFee(null);
        fiCbPayRemittanceDo.setReceiptId(null);
        //调用Manager层
        cbPayRemittanceManager.updateRemittanceOrder(fiCbPayRemittanceDo);

    }

    /**
     * @param applyStatus         商户申请状态
     * @param channelStatus       渠道状态
     * @param fiCbPayRemittanceDo 汇款对象
     * @param auditBy             商户操作人
     * @param fee                 手续费
     */
    private void updateRemittanceOrder(String applyStatus, String channelStatus, Integer purchaseStatus, FiCbPayRemittanceDo fiCbPayRemittanceDo,
                                       String auditBy, BigDecimal fee) {
        fiCbPayRemittanceDo.setApplyStatus(applyStatus);
        fiCbPayRemittanceDo.setAuditBy(auditBy);
        fiCbPayRemittanceDo.setAuditDate(new Date());
        fiCbPayRemittanceDo.setUpdateBy(auditBy);
        fiCbPayRemittanceDo.setChannelStatus(channelStatus);
        fiCbPayRemittanceDo.setPurchaseStatus(purchaseStatus);
        fiCbPayRemittanceDo.setTransFee(fee);
        log.info("更新汇款订单表：{}", fiCbPayRemittanceDo);

        cbPayRemittanceManager.updateRemittanceOrder(fiCbPayRemittanceDo);
    }

    private void updateRemittanceOrder(CbPayRemtStatusChangeReqBo cbPayRemtStatusChangeReqBo, FiCbPayRemittanceDo fiCbPayRemittanceDo) {
        fiCbPayRemittanceDo.setAuditStatus(cbPayRemtStatusChangeReqBo.getAuditStatus());
        fiCbPayRemittanceDo.setAuditDate(new Date());
        fiCbPayRemittanceDo.setUpdateBy(cbPayRemtStatusChangeReqBo.getUpdateBy());
        fiCbPayRemittanceDo.setChannelStatus(cbPayRemtStatusChangeReqBo.getChannelStatus());
        log.info("更新汇款订单表：{}", fiCbPayRemittanceDo);

        cbPayRemittanceManager.updateRemittanceOrder(fiCbPayRemittanceDo);
    }

    private void updateOrder(String remittanceStatus, String updateBy, String batchNo) {
        // 更新订单表
        try {
            cbPayOrderManager.updateCbPayOrderByBatchNo(remittanceStatus, updateBy, batchNo);
        } catch (Exception e) {
            log.error("更新跨境订单状态异常", e);
        }
    }

    /**
     * 上传收支申报文件
     *
     * @param fiCbPayRemittanceDetailDo 明细集合
     * @param batchNo                   批次号
     * @param memberId                  商户号
     * @return 文件ID
     */
    private Long uploadDetail(List<CgwRemitReqDto> fiCbPayRemittanceDetailDo, String batchNo, Long memberId) {

        String fileName = batchNo + "_" + DateUtil.getCurrent(DateUtil.fullPatterns) + ".txt";
        String filePath = remittanceFilePath + File.separator + fileName;
        log.info("文件路径，filePath：{}", filePath);
        // 生成文件
        File file = new File(filePath);
        FileOutputStream fos = null;
        try {
            String content = ConvertDsfFile.paramConvert(fiCbPayRemittanceDetailDo, ConvertType.REMITTANCE_LIST);
            log.info("明细文件笔数:{}", fiCbPayRemittanceDetailDo.size());
            if (!file.exists()) {
                file.createNewFile();
            }
            fos = new FileOutputStream(file);
            fos.write(content.getBytes("UTF-8"));

            InsertReqDTO insertReqDTO = DfsParamConvert.dfsRequestConvert(filePath, content.getBytes("UTF-8").length,
                    file.getName(), String.valueOf(memberId));
            log.info("DFS请求报文：{}", insertReqDTO);
            CommandResDTO resDTO = DfsClient.upload(insertReqDTO);
            log.info("DFS请求响应：{}", resDTO);

            return resDTO.getFileId();
        } catch (Exception e) {
            log.error("上传文件明细异常", e);
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0083);
        } finally {
            IOUtils.close(fos);
        }
    }

    private void autoPurchase(CbPayRemittanceAuditReqBo cbPayRemittanceAuditReqBo, String currency, BigDecimal sumAmt) {
        if (!Currency.CNY.getCode().equals(currency)) {
            log.info("自动购汇开始,CbPayRemittanceAuditReqBo:{}, 币种：{}", cbPayRemittanceAuditReqBo, currency);
            batchPurchase(cbPayRemittanceAuditReqBo, sumAmt);
        }
    }

    /**
     * 汇款异常重发
     *
     * @param batchNo  汇款批次号
     * @param memberId 商户ID
     * @param updateBy 更新人
     */
    @Override
    public void remittanceResend(String batchNo, Long memberId, String updateBy) {

        FiCbPayRemittanceDo queryRemittanceOrder = cbPayRemittanceManager.queryRemittanceOrder(batchNo);
        FiCbPayRemittanceAdditionDo fiCbPayRemittanceAdditionDo = cbPayRemittanceManager.queryRemittanceAddition(batchNo, memberId);

        // 判断订单是否存在
        if (queryRemittanceOrder == null) {
            log.info("memberId:{},batchNo:{},该汇款订单不存在", memberId, batchNo);
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0055);
        }

        // 查询汇款附加信息
        FiCbPayRemittanceAdditionDo additionDo = cbPayRemittanceManager.queryRemittanceAddition(batchNo, memberId);
        // 判断订单附加信息是否存在
        if (additionDo == null) {
            log.info("memberId:{},batchNo:{},该汇款附加信息不存在", memberId, batchNo);
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0086);
        }

        // 判断订单购汇或付汇状态是否失败
        if (!ChannelStatus.FALSE.getCode().equals(queryRemittanceOrder.getChannelStatus())
                && queryRemittanceOrder.getPurchaseStatus() != 3) {
            log.info("memberId:{},batchNo:{},该汇款订单状态不正确", memberId, batchNo);
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0054);
        }

        // 1、购汇失败，清算不成功 2、购汇成功，付汇失败
        if ((queryRemittanceOrder.getPurchaseStatus() == 3 && CMStatus.TRUE.getCode().equals(queryRemittanceOrder.getCmStatus()))
                || (queryRemittanceOrder.getPurchaseStatus() == 1 && CMStatus.TRUE.getCode().equals(queryRemittanceOrder.getCmStatus())
                && Currency.CNY.getCode().equals(queryRemittanceOrder.getSystemCcy()))) {
            log.info("memberId:{},batchNo:{},该汇款订单状态不正确, 购汇状态：{}，渠道状态：{}", memberId, batchNo);
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0054);
        }

        // 查询币种账户信息
        FiCbPaySettleBankDo fiCbPaySettleBankDo = cbPaySettleBankManager.selectBankAccByEntityNo(memberId, queryRemittanceOrder.getSystemCcy(), fiCbPayRemittanceAdditionDo.getEntityNo());
        // 判断币种账户是否存在
        if (fiCbPaySettleBankDo == null) {
            log.info("memberId:{},batchNo:{},该币种账户不存在", memberId, batchNo);
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00130);
        }

        try {
            //组装MQ参数
            CgwRetryReqDto cgwRetryReqDto = new CgwRetryReqDto();
            FiCbPayRemittanceDo updateRemittanceOrder = new FiCbPayRemittanceDo();
            //购汇失败
            if (queryRemittanceOrder.getPurchaseStatus() == 3) {
                //根据汇款表重新设置定额类型
                fiCbPaySettleBankDo.setExchangeType(queryRemittanceOrder.getExchangeType());
                //计算购汇外币金额
                BigDecimal sumAmt = queryRemittanceOrder.getExchangeType() == 1 ?
                        queryRemittanceOrder.getSystemMoney() : queryRemittanceOrder.getTransMoney();
                cgwRetryReqDto.setCgwSumExchangeReqDto(DfsParamConvert.convertToPurchase(queryRemittanceOrder, additionDo.getDfsFileId(), fiCbPaySettleBankDo, sumAmt));
                //设置重发类型为购汇
                cgwRetryReqDto.setBusinessType(2);
                //发送汇款重发MQ
                log.info("渠道购汇重发请求报文：cgwRetryReqDto:{}", cgwRetryReqDto);
                mqSendService.sendMessage(MqSendQueueNameEnum.CGW_CROSS_BORDER_SERVICE_RETRY, cgwRetryReqDto);
                //更新购汇状态
                updateRemittanceOrder.setPurchaseStatus(2);

                //付汇失败
            } else if (ChannelStatus.FALSE.getCode().equals(queryRemittanceOrder.getChannelStatus())) {
                cgwRetryReqDto.setCgwSumRemitReqDto(DfsParamConvert.channelRequestConvert(queryRemittanceOrder, fiCbPaySettleBankDo, additionDo));
                //设置重发类型为付汇
                cgwRetryReqDto.setBusinessType(1);
                //发送汇款重发MQ
                log.info("渠道付汇重发请求报文：cgwRetryReqDto:{}", cgwRetryReqDto);
                mqSendService.sendMessage(MqSendQueueNameEnum.CGW_CROSS_BORDER_SERVICE_RETRY, cgwRetryReqDto);
                //更新付汇状态
                updateRemittanceOrder.setChannelStatus(ChannelStatus.PROCESSING.getCode());
            }
            //更新汇款订单状态
            updateRemittanceOrder.setBatchNo(batchNo);
            updateRemittanceOrder.setUpdateBy(updateBy);
            cbPayRemittanceManager.updateRemittanceOrder(updateRemittanceOrder);
            log.info("渠道响应成功，更新汇款订单状态成功");
        } catch (Exception e) {
            log.error("汇款重发异常", e);
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00142);
        }

    }
}
