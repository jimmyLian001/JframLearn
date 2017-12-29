package com.baofu.cbpayservice.biz.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.IOUtils;
import com.baofoo.cbcgw.facade.dict.BaseResultEnum;
import com.baofoo.cbcgw.facade.dto.gw.base.CgwBaseRespDto;
import com.baofoo.cbcgw.facade.dto.gw.request.CgwRemitReqDto;
import com.baofoo.cbcgw.facade.dto.gw.request.CgwSumRemitReqDto;
import com.baofoo.cbcgw.facade.dto.gw.response.CgwExChangeRespDto;
import com.baofoo.cbcgw.facade.dto.gw.response.result.CgwRemitResultDto;
import com.baofoo.dfs.client.DfsClient;
import com.baofoo.dfs.client.model.CommandResDTO;
import com.baofoo.dfs.client.model.InsertReqDTO;
import com.baofoo.provision.vo.RSIS150;
import com.baofu.accountcenter.service.facade.AccountTradeFacade;
import com.baofu.accountcenter.service.facade.dto.req.RechargeReqDto;
import com.baofu.accountcenter.service.facade.dto.req.TransferReqDto;
import com.baofu.accountcenter.service.facade.dto.req.WithdrawReqDto;
import com.baofu.accountcenter.service.facade.dto.resp.RechargeRespDto;
import com.baofu.accountcenter.service.facade.dto.resp.TransferRespDto;
import com.baofu.accountcenter.service.facade.dto.resp.WithdrawRespDto;
import com.baofu.cbpayservice.biz.*;
import com.baofu.cbpayservice.biz.convert.*;
import com.baofu.cbpayservice.biz.integration.cm.CmClearBizImpl;
import com.baofu.cbpayservice.biz.models.*;
import com.baofu.cbpayservice.common.constants.Constants;
import com.baofu.cbpayservice.common.enums.*;
import com.baofu.cbpayservice.dal.models.*;
import com.baofu.cbpayservice.manager.*;
import com.google.common.collect.Lists;
import com.system.commons.exception.BizServiceException;
import com.system.commons.result.Result;
import com.system.commons.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.slf4j.helpers.SystemMarker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 人民币订单通知处理
 * <p>
 * 1、接收渠道发往银行异步通知
 * 2、接收银行处理成功异步通知
 * </p>
 * User: wanght Date:2016/10/28 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
@Service
public class CbPayRemittanceOrderNotifyBizImpl implements CbPayRemittanceOrderNotifyBiz {

    /**
     * 汇款订单信息操作服务
     */
    @Autowired
    private CbPayRemittanceManager cbPayRemittanceManager;

    /**
     * cbpay订单操作服务
     */
    @Autowired
    private CbPayOrderManager cbPayOrderManager;

    /**
     * 发送MQ服务
     */
    @Autowired
    private MqSendService mqSendService;

    /**
     * 计费操作服务
     */
    @Autowired
    private CbPayCmBiz cbPayCmBiz;

    /**
     * 清算服务
     */
    @Autowired
    private CmClearBizImpl cmClearBizImpl;

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

    /**
     * 跨境结汇订单信息Manager
     */
    @Autowired
    private CbPaySettleManager cbPaySettleManager;

    /**
     * 调用外币账户清算系统
     */
    @Autowired
    private AccountTradeFacade accountTradeFacade;

    @Value("${remittance_file_path}")
    private String remittanceFilePath;

    @Autowired
    private ProxyCustomsManager proxyCustomsManager;

    /**
     * 反洗钱处理
     */
    @Autowired
    private CbPayAmlNotifyBiz cbPayAmlNotifyBiz;

    /**
     * 异步通知服务
     */
    @Autowired
    private NotifyBiz notifyBiz;

    /**
     * 商户请求信息操作类
     */
    @Autowired
    private FiCbPayMemberApiRqstManager fiCbPayMemberApiRqstManager;


    /**
     * 接收银行处理成功异步通知
     *
     * @param cgwRemitBatchResultDto 请求参数
     */
    @Override
    public void receiveNotify(CgwRemitResultDto cgwRemitBatchResultDto, String updateBy) {
        String traceLogId = MDC.get(SystemMarker.TRACE_LOG_ID);
        log.info("第二次异步通知开始：{}", cgwRemitBatchResultDto);
        FiCbPayRemittanceDo fiCbPayRemittanceDo = cbPayRemittanceManager.queryRemittanceOrder(cgwRemitBatchResultDto.getRemSerialNo());

        if (fiCbPayRemittanceDo == null) {
            log.info("batchNo:{},异步通知，没有该批次汇款订单", cgwRemitBatchResultDto.getRemSerialNo());
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0055);
        }

        FiCbPayRemittanceAdditionDo fiCbPayRemittanceAdditionDo = cbPayRemittanceManager.queryRemittanceAddition(
                cgwRemitBatchResultDto.getRemSerialNo(), fiCbPayRemittanceDo.getMemberNo());
        if (ChannelStatus.TRUE.getCode().equals(fiCbPayRemittanceDo.getChannelStatus())) {
            log.info("batchNo:{},异步通知，该批次汇款订单已处理成功，请勿重复操作", cgwRemitBatchResultDto.getRemSerialNo());
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0054);
        }

        if (cgwRemitBatchResultDto.getAmlState() != null) {
            cbPayAmlNotifyBiz.amlApplySecondNotify(cgwRemitBatchResultDto, SystemEnum.SYSTEM.getCode());
        }
        //查询币种账户信息
        FiCbPaySettleBankDo fiCbPaySettleBankDo = cbPaySettleBankManager.selectBankAccByEntityNo(fiCbPayRemittanceDo.getMemberNo(),
                fiCbPayRemittanceDo.getSystemCcy(), fiCbPayRemittanceAdditionDo.getEntityNo());

        switch (cgwRemitBatchResultDto.getType()) {
            case 0:
                if (cgwRemitBatchResultDto.getRemitFlag() == BaseResultEnum.SUCCESS.getCode()) {

                    //渠道成本记录
                    ChannelCostCalBo channelCostCalBo = new ChannelCostCalBo();
                    channelCostCalBo.setCcy(fiCbPayRemittanceDo.getTransCcy());
                    channelCostCalBo.setOrderAmt(fiCbPayRemittanceDo.getRealMoney());
                    channelCostCalBo.setBatchNo(fiCbPayRemittanceDo.getBatchNo());
                    channelCostCalBo.setChannelId(fiCbPayRemittanceDo.getChannelId());
                    channelCostCalBo.setMemberId(fiCbPayRemittanceDo.getMemberNo());
                    BigDecimal channelFee = cbPayCommonBiz.channelCostCal(channelCostCalBo);

                    // 更新订单表状态
                    updateOrder(RemittanceStatus.TRUE.getCode(), updateBy, cgwRemitBatchResultDto.getRemSerialNo());
                    log.info("银行处理成功，更新订单表成功");

                    // 外汇结算
                    if (!Currency.CNY.getCode().equals(fiCbPayRemittanceDo.getSystemCcy())) {
                        //付汇成功，在途账户扣钱
                        WithdrawReqDto withdrawReqDto = CbPayRemittanceConvert.toWithdrawReqDto(cgwRemitBatchResultDto, fiCbPayRemittanceDo);
                        log.info("网关--付汇--外币系统账户提现参数：{}", withdrawReqDto);
                        Result<WithdrawRespDto> result = accountTradeFacade.withdrawDeposit(withdrawReqDto, MDC.get(SystemMarker.TRACE_LOG_ID));
                        log.info("网关--付汇--外币系统账户提现返回参数：{}", result);
                        // 更新汇款订单表
                        updateRemittanceOrder(cgwRemitBatchResultDto.getRemSerialNo(), null, ApplyStatus.TRUE.getCode(),
                                ChannelStatus.TRUE.getCode(), updateBy, CMStatus.TRUE.getCode(), null, null, null, null, new Date(), null, null, null);
                        log.info("汇款批次：{}外汇结算完成", cgwRemitBatchResultDto.getRemSerialNo());
                        //发送汇款凭证
                        JSONObject remitEmailObject = new JSONObject();
                        remitEmailObject.put("batchNo", fiCbPayRemittanceDo.getBatchNo());
                        mqSendService.sendMessage(MqSendQueueNameEnum.CBPAY_REMIT_EMAIL_QUEUE_NAME, remitEmailObject.toJSONString());
                        //判断是否需要通知商户：API请求需要通知商户
                        FiCbPayMemberApiRqstDo fiCbPayMemberApiRqstDo = fiCbPayMemberApiRqstManager.queryReqInfoByBussNo(cgwRemitBatchResultDto.getRemSerialNo());
                        if (fiCbPayMemberApiRqstDo != null) {
                            fiCbPayRemittanceDo = cbPayRemittanceManager.queryRemittanceOrder(cgwRemitBatchResultDto.getRemSerialNo());
                            ApiRemitCbPayNotfiyBo apiRemitCbPayNotfiyBo = ApiCbPayRemitNotifyConvert.convertApiRemitCbPayNotfiyBo(
                                    fiCbPayRemittanceDo, fiCbPayMemberApiRqstDo, fiCbPayRemittanceAdditionDo, 4, "");
                            notifyBiz.notifyMember(String.valueOf(fiCbPayMemberApiRqstDo.getMemberId()), String.valueOf(fiCbPayMemberApiRqstDo.getTerminalId()),
                                    String.valueOf(fiCbPayMemberApiRqstDo.getNotifyUrl()), apiRemitCbPayNotfiyBo, fiCbPayMemberApiRqstDo.getMemberReqId(), RemitBusinessTypeEnum.REMIT_APPLY.getCode());
                            log.info("call 渠道第二次通知付汇成功，通知商户成功。");
                        }
                        break;
                    }

                    // 更新汇款订单表
                    updateRemittanceOrder(cgwRemitBatchResultDto.getRemSerialNo(), null, ApplyStatus.TRUE.getCode(),
                            ChannelStatus.TRUE.getCode(), updateBy, CMStatus.INIT.getCode(), null, null, null, null, new Date(),
                            fiCbPayRemittanceDo.getSystemMoney(), null, null);
                    log.info("银行处理成功，更新汇款订单表成功");

                    //发送汇款凭证
                    JSONObject remitEmailObject = new JSONObject();
                    remitEmailObject.put("batchNo", fiCbPayRemittanceDo.getBatchNo());
                    mqSendService.sendMessage(MqSendQueueNameEnum.CBPAY_REMIT_EMAIL_QUEUE_NAME, remitEmailObject.toJSONString());

                    // 通知清算cbPayRemitDocEmailListener
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("batchNo", cgwRemitBatchResultDto.getRemSerialNo());
                    jsonObject.put("updateBy", SystemEnum.SYSTEM.getCode());

                    mqSendService.sendMessage(MqSendQueueNameEnum.CBPAY_NOTIFY_SETTLE_QUEUE_NAME, jsonObject.toJSONString());
                    log.info("通知清算发送MQ消息：{}", cgwRemitBatchResultDto.getRemSerialNo());

                    //人民币付汇成功，记人民币备付金账户出金，发送通知央行MQ
                    RSIS150 rsis150 = ParamConvert.remitNotifyCentralBankConvert(cgwRemitBatchResultDto, fiCbPayRemittanceDo, fiCbPaySettleBankDo, channelFee);
                    log.info("付汇完成，人民币备付金变动通知央行报文：rsis150:{},成功时间:{}", JSON.toJSONString(rsis150), rsis150.getSuccTime());
                    mqSendService.sendMessage(MqSendQueueNameEnum.CBPAY_NOTIFY_CENTRAL_BANK, rsis150);

                    //判断是否需要通知商户：API请求需要通知商户
                    FiCbPayMemberApiRqstDo fiCbPayMemberApiRqstDo = fiCbPayMemberApiRqstManager.queryReqInfoByBussNo(cgwRemitBatchResultDto.getRemSerialNo());
                    if (fiCbPayMemberApiRqstDo != null) {
                        fiCbPayRemittanceDo = cbPayRemittanceManager.queryRemittanceOrder(cgwRemitBatchResultDto.getRemSerialNo());
                        ApiRemitCbPayNotfiyBo apiRemitCbPayNotfiyBo = ApiCbPayRemitNotifyConvert.convertApiRemitCbPayNotfiyBo(
                                fiCbPayRemittanceDo, fiCbPayMemberApiRqstDo, fiCbPayRemittanceAdditionDo, 4, "");
                        notifyBiz.notifyMember(String.valueOf(fiCbPayMemberApiRqstDo.getMemberId()), String.valueOf(fiCbPayMemberApiRqstDo.getTerminalId()),
                                String.valueOf(fiCbPayMemberApiRqstDo.getNotifyUrl()), apiRemitCbPayNotfiyBo, fiCbPayMemberApiRqstDo.getMemberReqId(), RemitBusinessTypeEnum.REMIT_APPLY.getCode());
                        log.info("call 第二次通知付汇成功，通知商户成功。");
                    }

                } else if (cgwRemitBatchResultDto.getRemitFlag() == BaseResultEnum.FAIL.getCode() ||
                        cgwRemitBatchResultDto.getRemitFlag() == BaseResultEnum.INIT.getCode()) {
                    // 更新汇款订单表
                    updateRemittanceOrder(cgwRemitBatchResultDto.getRemSerialNo(), null, ApplyStatus.TRUE.getCode(),
                            ChannelStatus.FALSE.getCode(), updateBy, null, null, null, null, null, null, null, null, cgwRemitBatchResultDto.getCgwRemitRespDto().getRemark());
                    log.info("银行处理失败，更新汇款订单表成功");

                    //判断是否需要通知商户：API请求需要通知商户
                    FiCbPayMemberApiRqstDo fiCbPayMemberApiRqstDo = fiCbPayMemberApiRqstManager.queryReqInfoByBussNo(cgwRemitBatchResultDto.getRemSerialNo());
                    if (fiCbPayMemberApiRqstDo != null) {
                        fiCbPayRemittanceDo = cbPayRemittanceManager.queryRemittanceOrder(cgwRemitBatchResultDto.getRemSerialNo());
                        ApiRemitCbPayNotfiyBo apiRemitCbPayNotfiyBo = ApiCbPayRemitNotifyConvert.convertApiRemitCbPayNotfiyBo(
                                fiCbPayRemittanceDo, fiCbPayMemberApiRqstDo, fiCbPayRemittanceAdditionDo, 3, "银行处理失败");
                        notifyBiz.notifyMember(String.valueOf(fiCbPayMemberApiRqstDo.getMemberId()), String.valueOf(fiCbPayMemberApiRqstDo.getTerminalId()),
                                String.valueOf(fiCbPayMemberApiRqstDo.getNotifyUrl()), apiRemitCbPayNotfiyBo, fiCbPayMemberApiRqstDo.getMemberReqId(), RemitBusinessTypeEnum.REMIT_APPLY.getCode());
                        log.info("call 第二次通知付汇失败，通知商户成功。");
                    }
                }
                break;
            case 1:
                CgwExChangeRespDto exchangeResultDtoList = cgwRemitBatchResultDto.getCgwExChangeRespDto();
                if (cgwRemitBatchResultDto.getRemitFlag() == BaseResultEnum.SUCCESS.getCode()) {
                    int functionId = FunctionEnum.getFunctionId(fiCbPayRemittanceDo.getOrderType(), fiCbPayRemittanceDo.getSystemCcy());
                    int productId = FunctionEnum.getProductId(fiCbPayRemittanceDo.getOrderType());

                    // 调用计费服务
                    MemberFeeResBo feeResult = cbPayCmBiz.getFeeResult(fiCbPayRemittanceDo.getBatchNo(), fiCbPayRemittanceDo.getTransMoney(),
                            fiCbPayRemittanceDo.getMemberNo(), functionId, productId, traceLogId);

                    // 调用清算服务
                    ClearRequestBo request = CmParamConvert.cmRequestConvert(DealCodeEnums.remitDealCodeTwo(fiCbPayRemittanceDo.getSystemCcy()),
                            fiCbPayRemittanceDo.getChannelId(), String.valueOf(fiCbPayRemittanceDo.getMemberNo()),
                            "", functionId, Long.parseLong(cgwRemitBatchResultDto.getRemSerialNo()),
                            fiCbPayRemittanceDo.getTransMoney(), fiCbPayRemittanceDo.getTransFee(),
                            new BigDecimal("0.00"), feeResult.getFeeAccId(), productId);
                    log.info("异步通知购汇成功调用清算第二步请求报文：{}", request);
                    ClearingResponseBo response = cmClearBizImpl.transferAccount(request);
                    log.info("异步通知购汇成功调用清算第二步响应报文：{}", response);
                    if (ClearAccResultEnum.CODE_SUCCESS.getCode().equals(response.getClearAccResultEnum().getCode())) {
                        // 购汇金额
                        BigDecimal exchangeAmt = exchangeResultDtoList.getExcHAmt();
                        // 汇率
                        BigDecimal rate = exchangeResultDtoList.getExcHRate();
                        // 实际扣款金额(人民币)
                        BigDecimal realMoney = exchangeResultDtoList.getExcHCnyAmt();

                        //给商户的外币账户加资金
                        RechargeReqDto rechargeReqDto = CbPayRemittanceConvert.toRechargeReqDto(
                                cgwRemitBatchResultDto, fiCbPayRemittanceDo);
                        log.info("网关--购汇--外币系统账户充值参数：{}", rechargeReqDto);
                        Result<RechargeRespDto> result = accountTradeFacade.recharge(rechargeReqDto,
                                MDC.get(SystemMarker.TRACE_LOG_ID));
                        log.info("网关--购汇--外币账户充值结果：{}", result);

                        // 中行渠道
                        if (Constants.CHINA_BANK_AISLE_ID.equals(String.valueOf(fiCbPayRemittanceDo.getChannelId()).substring(0, 5))) {
                            fiCbPayRemittanceDo.setSystemMoney(exchangeAmt);
                            fiCbPayRemittanceDo.setRemitMoney(exchangeAmt);
                            fiCbPayRemittanceDo.setSystemRate(rate);
                            // 平安渠道
                        } else if (Constants.PINGAN_BANK_AISLE_ID.equals(String.valueOf(fiCbPayRemittanceDo.getChannelId()).substring(0, 5))) {
                            BigDecimal purchaseRate = fiCbPayRemittanceDo.getExchangeType() == 0 ?
                                    rate.divide(new BigDecimal("100"), 6, BigDecimal.ROUND_DOWN) : null;

                            List<FiCbPayFileUploadDo> fiCbPayFileUploadDos = proxyCustomsManager.queryByBatchNo(String.valueOf(cgwRemitBatchResultDto.getRemSerialNo()));
                            log.info("查询文件批次信息：{}", fiCbPayFileUploadDos);

                            if (CollectionUtils.isEmpty(fiCbPayFileUploadDos)) {
                                log.info("查询文件批次信息为空");
                                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00139);
                            }

                            List<CgwRemitReqDto> cgwRemitReqDtoList = Lists.newArrayList();
                            List<Long> batchFileList = Lists.newArrayList();
                            BigDecimal remitSumAmt = BigDecimal.ZERO;
                            List<Integer> amlStatusList = Lists.newArrayList(1);
                            for (FiCbPayFileUploadDo fiCbPayFileUploadDo : fiCbPayFileUploadDos) {
                                batchFileList.add(fiCbPayFileUploadDo.getFileBatchNo());
                                // 根据文件批次号 + 反洗钱审核状态(通过） 查询汇款明细
                                List<FiCbPayRemittanceDetailV2Do> fiCbPayRemittanceDetailDo =
                                        cbPayOrderManager.queryOrderDetailInfo(fiCbPayFileUploadDo.getFileBatchNo(), amlStatusList, fiCbPayFileUploadDo.getCareerType());

                                log.info("文件批次汇率，批次号：{},汇率：{}", fiCbPayFileUploadDo.getFileBatchNo(), fiCbPayFileUploadDo.getAmlRate());

                                CgwRemitReqDetailDto cgwRemitReqDetailDto = DfsParamConvert.channelDetailRequestConvert(
                                        cgwRemitBatchResultDto.getRemSerialNo(), fiCbPayRemittanceDetailDo,
                                        fiCbPaySettleBankDo, fiCbPayRemittanceDo, purchaseRate == null ? fiCbPayFileUploadDo.getAmlRate() : purchaseRate);

                                cgwRemitReqDtoList.addAll(cgwRemitReqDetailDto.getCgwRemitReqDtoList());
                                remitSumAmt = remitSumAmt.add(cgwRemitReqDetailDto.getRemitSumAmt());
                            }

                            //更新实际的汇率
                            BigDecimal totalTransMoney = cbPayRemittanceManager.queryRemittanceByBatchNos(batchFileList);
                            fiCbPayRemittanceDo.setSystemRate(totalTransMoney.divide(remitSumAmt, 6, BigDecimal.ROUND_DOWN).multiply(new BigDecimal("100")));
                            fiCbPayRemittanceDo.setRemitMoney(remitSumAmt);

                            Long dfsFileId = uploadDetail(cgwRemitReqDtoList, fiCbPayRemittanceDo);

                            fiCbPayRemittanceAdditionDo.setDfsFileId(dfsFileId);
                            fiCbPayRemittanceAdditionDo.setUpdateBy(updateBy);
                            fiCbPayRemittanceAdditionDo.setMemberNo(fiCbPayRemittanceDo.getMemberNo());
                            log.info("更新购汇订单附加信息：{}", fiCbPayRemittanceAdditionDo);
                            cbPayRemittanceManager.updateRemittanceAddition(fiCbPayRemittanceAdditionDo);
                            log.info("更新购汇订单附加信息成功");

                            // TODO 其他银行渠道
                        } else {
                            log.error("暂不支持的银行通道：{}", fiCbPayRemittanceDo.getChannelId());
                            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00153);
                        }
                        FiCbPayRemittanceDo remittanceDo = new FiCbPayRemittanceDo();
                        remittanceDo.setBatchNo(cgwRemitBatchResultDto.getRemSerialNo());
                        remittanceDo.setChannelStatus(ChannelStatus.PROCESSING.getCode());
                        remittanceDo.setCmStatus(CMStatus.TRUE.getCode());
                        remittanceDo.setPurchaseStatus(PurchaseStatus.TRUE.getCode());
                        remittanceDo.setRealRate(rate);
                        remittanceDo.setRealMoney(realMoney);
                        remittanceDo.setRemitMoney(fiCbPayRemittanceDo.getRemitMoney());
                        if (fiCbPayRemittanceDo.getExchangeType() == 0) {
                            remittanceDo.setSystemRate(fiCbPayRemittanceDo.getSystemRate());
                            remittanceDo.setSystemMoney(exchangeAmt);
                        }
                        remittanceDo.setExchangeSuccDate(new Date());
                        remittanceDo.setUpdateBy(updateBy);

                        cbPayRemittanceManager.updateRemittanceOrder(remittanceDo);
                        log.info("银行处理成功，更新购汇订单成功,更新信息：{}", remittanceDo);

                        // 调用渠道服务
                        CgwSumRemitReqDto cgwSumRemitReqDO = DfsParamConvert.remitRequestConvert(fiCbPayRemittanceDo,
                                fiCbPaySettleBankDo, fiCbPayRemittanceAdditionDo);
                        log.info("购汇完成，发送汇款请求报文：fileId:{},cgwSumRemitReqDO:{}", fiCbPayRemittanceAdditionDo.getDfsFileId(), cgwSumRemitReqDO);

                        mqSendService.sendMessage(MqSendQueueNameEnum.CGW_CROSS_BORDER_SERVICE_REMIT, cgwSumRemitReqDO);

                        //购汇成功，记人民币备付金账户出金，发送通知央行MQ
                        RSIS150 rsis150 = ParamConvert.exchangeNotifyCentralBankConvert(cgwRemitBatchResultDto, fiCbPayRemittanceDo, fiCbPaySettleBankDo);
                        log.info("购汇完成，人民币备付金变动通知央行报文：rsis150:{},成功时间:{}", JSON.toJSONString(rsis150), rsis150.getSuccTime());
                        mqSendService.sendMessage(MqSendQueueNameEnum.CBPAY_NOTIFY_CENTRAL_BANK, rsis150);

                        //以下部分为计算汇率损益并记录科目账户，宝付扣除商户金额 - 银行扣除宝付金额
                        BigDecimal profit = fiCbPayRemittanceDo.getTransMoney().subtract(exchangeResultDtoList.getExcHCnyAmt());
                        if (BigDecimal.ZERO.compareTo(profit) == 0) {
                            log.info("跨境购汇损益金额等于0无需记录损益科目，订单号：{}", fiCbPayRemittanceDo.getBatchNo());
                            return;
                        }
                        Integer dealCode = DealCodeEnums.REMIT_PROFIT.getCode();
                        if (profit.compareTo(BigDecimal.ZERO) < 0) {
                            profit = profit.multiply(new BigDecimal("-1"));
                            dealCode = DealCodeEnums.REMIT_LOSS.getCode();
                        }
                        request = CmParamConvert.cmRequestConvert(dealCode,
                                fiCbPayRemittanceDo.getChannelId(), String.valueOf(fiCbPayRemittanceDo.getMemberNo()),
                                "", functionId, Long.parseLong(cgwRemitBatchResultDto.getRemSerialNo()),
                                profit, BigDecimal.ZERO,
                                BigDecimal.ZERO, feeResult.getFeeAccId(), productId);
                        cmClearBizImpl.transferAccount(request);
                    }

                } else if (cgwRemitBatchResultDto.getRemitFlag() == BaseResultEnum.FAIL.getCode()
                        || cgwRemitBatchResultDto.getRemitFlag() == BaseResultEnum.INIT.getCode()) {
                    // 更新汇款订单表
                    updateRemittanceOrder(cgwRemitBatchResultDto.getRemSerialNo(), null, ApplyStatus.TRUE.getCode(),
                            ChannelStatus.FALSE.getCode(), updateBy, null, 3, null, null, null, null, null, null, cgwRemitBatchResultDto.getCgwExChangeRespDto().getErrorMsg());
                    log.info("银行处理失败，更新购汇订单成功");

                    //判断是否需要通知商户：API请求需要通知商户
                    FiCbPayMemberApiRqstDo fiCbPayMemberApiRqstDo = fiCbPayMemberApiRqstManager.queryReqInfoByBussNo(cgwRemitBatchResultDto.getRemSerialNo());
                    if (fiCbPayMemberApiRqstDo != null) {
                        fiCbPayRemittanceDo = cbPayRemittanceManager.queryRemittanceOrder(cgwRemitBatchResultDto.getRemSerialNo());
                        ApiRemitCbPayNotfiyBo apiRemitCbPayNotfiyBo = ApiCbPayRemitNotifyConvert.convertApiRemitCbPayNotfiyBo(
                                fiCbPayRemittanceDo, fiCbPayMemberApiRqstDo, fiCbPayRemittanceAdditionDo, 3, "银行处理失败。");
                        notifyBiz.notifyMember(String.valueOf(fiCbPayMemberApiRqstDo.getMemberId()), String.valueOf(fiCbPayMemberApiRqstDo.getTerminalId()),
                                String.valueOf(fiCbPayMemberApiRqstDo.getNotifyUrl()), apiRemitCbPayNotfiyBo, fiCbPayMemberApiRqstDo.getMemberReqId(), RemitBusinessTypeEnum.REMIT_APPLY.getCode());
                        log.info("call 第二次通知购汇失败，通知商户成功。");
                    }

                }
                break;
            default:
                log.info("第二次异步通知交易类型不正确");
        }
        log.info("第二次异步通知结束。");
    }

    /**
     * 接收渠道发往银行异步通知
     *
     * @param cgwBaseResultDO 请求参数
     */
    @Override
    public void receiveOrderNotify(CgwBaseRespDto cgwBaseResultDO, String updateBy) {

        log.info("第一次异步通知开始：{}", cgwBaseResultDO);
        FiCbPayRemittanceDo fiCbPayRemittanceDo;
        switch (cgwBaseResultDO.getType()) {
            case 0:
                fiCbPayRemittanceDo = cbPayRemittanceManager.queryRemittanceOrder(cgwBaseResultDO.getBfBatchId());
                if (fiCbPayRemittanceDo == null) {
                    log.info("batchNo:{},汇款订单异步通知，没有该批次汇款订单", cgwBaseResultDO.getBfBatchId());
                    throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0055);
                }
                if (cgwBaseResultDO.getCode() == BaseResultEnum.FAIL.getCode()) {
                    log.info("batchNo:{},汇款订单异步通知，渠道处理失败", cgwBaseResultDO.getBfBatchId());
                    //判断是否需要通知商户：API请求需要通知商户
                    FiCbPayMemberApiRqstDo fiCbPayMemberApiRqstDo = fiCbPayMemberApiRqstManager.queryReqInfoByBussNo(cgwBaseResultDO.getBfBatchId());
                    if (fiCbPayMemberApiRqstDo != null) {
                        //查询汇款附加信息
                        FiCbPayRemittanceAdditionDo fiCbPayRemittanceAdditionDo =
                                cbPayRemittanceManager.queryRemittanceAddition(cgwBaseResultDO.getBfBatchId(), fiCbPayMemberApiRqstDo.getMemberId());
                        ApiRemitCbPayNotfiyBo apiRemitCbPayNotfiyBo = ApiCbPayRemitNotifyConvert.convertApiRemitCbPayNotfiyBo(
                                fiCbPayRemittanceDo, fiCbPayMemberApiRqstDo, fiCbPayRemittanceAdditionDo, 3, "付汇：渠道处理失败");
                        notifyBiz.notifyMember(String.valueOf(fiCbPayMemberApiRqstDo.getMemberId()), String.valueOf(fiCbPayMemberApiRqstDo.getTerminalId()),
                                String.valueOf(fiCbPayMemberApiRqstDo.getNotifyUrl()), apiRemitCbPayNotfiyBo, fiCbPayMemberApiRqstDo.getMemberReqId(), RemitBusinessTypeEnum.REMIT_APPLY.getCode());
                        log.info("call 第一次付汇：汇款订单异步通知，渠道处理失败，通知商户成功。");
                    }
                } else if (cgwBaseResultDO.getCode() == BaseResultEnum.SUCCESS.getCode()) {
                    log.info("batchNo:{},汇款订单异步通知，渠道处理成功", cgwBaseResultDO.getBfBatchId());
                    updateRemittanceOrder(cgwBaseResultDO.getBfBatchId(), cgwBaseResultDO.getBatchId(), ApplyStatus.TRUE.getCode(),
                            ChannelStatus.PROCESSING.getCode(), updateBy, null, null,
                            null, null, null, null, null, null, null);

                } else if (cgwBaseResultDO.getCode() == BaseResultEnum.DISPOSE.getCode()) {
                    if (Currency.CNY.getCode().equals(fiCbPayRemittanceDo.getSystemCcy())) {
                        // 邮件发送备案明细
                        mqSendService.sendMessage(MqSendQueueNameEnum.CBPAY_REMITTANCE_EMAIL_QUEUE_NAME, cgwBaseResultDO.getBfBatchId());
                        log.info("邮件发送备案明细MQ消息：{}", cgwBaseResultDO.getBfBatchId());
                    } else {
                        //付汇申请成功，基本账户转在途账户
                        TransferReqDto transferReqDto = CbPayRemittanceConvert.toTransferReqDto(cgwBaseResultDO, fiCbPayRemittanceDo);
                        log.info("网关--付汇--外币系统账户转账参数：{}", transferReqDto);
                        Result<TransferRespDto> result = accountTradeFacade.transferAccounts(transferReqDto, MDC.get(SystemMarker.TRACE_LOG_ID));
                        log.info("网关--付汇--外币系统账户转账返回结果：{}", result);
                    }

                    // 邮件发送通知清算
                    mqSendService.sendMessage(MqSendQueueNameEnum.CBPAY_EMAIL_NOTIFY_SETTLE_QUEUE_NAME, cgwBaseResultDO.getBfBatchId());
                    log.info("邮件发送通知清算MQ消息：{}", cgwBaseResultDO.getBfBatchId());
                } else {
                    // 更新订单状态-渠道处理为初始
                    updateRemittanceOrder(cgwBaseResultDO.getBfBatchId(), null, ApplyStatus.TRUE.getCode(),
                            ChannelStatus.INIT.getCode(), updateBy, null, null, null,
                            null, null, null, null, null, null);
                    log.info("汇款渠道响应异常，更新汇款订单状态");
                }
                break;
            case 1:
                fiCbPayRemittanceDo = cbPayRemittanceManager.queryRemittanceOrder(cgwBaseResultDO.getBfBatchId());
                if (fiCbPayRemittanceDo == null) {
                    log.info("batchNo:{},汇款订单异步通知，没有该批次汇款订单", cgwBaseResultDO.getBfBatchId());
                    throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0055);
                }
                if (cgwBaseResultDO.getCode() == BaseResultEnum.SUCCESS.getCode()) {
                    log.info("batchNo:{},购汇订单异步通知，渠道处理成功", cgwBaseResultDO.getBfBatchId());
                    updateRemittanceOrder(cgwBaseResultDO.getBfBatchId(), cgwBaseResultDO.getBatchId(), null, ChannelStatus.PROCESSING.getCode(),
                            updateBy, null, 2, null, null, null, null, null, null, null);
                } else if (cgwBaseResultDO.getCode() == BaseResultEnum.FAIL.getCode()) {
                    log.info("batchNo:{},汇款订单异步通知，渠道处理失败", cgwBaseResultDO.getBfBatchId());
                    //判断是否需要通知商户：API请求需要通知商户
                    FiCbPayMemberApiRqstDo fiCbPayMemberApiRqstDo = fiCbPayMemberApiRqstManager.queryReqInfoByBussNo(cgwBaseResultDO.getBfBatchId());
                    if (fiCbPayMemberApiRqstDo != null) {
                        //查询汇款附加信息
                        FiCbPayRemittanceAdditionDo fiCbPayRemittanceAdditionDo =
                                cbPayRemittanceManager.queryRemittanceAddition(cgwBaseResultDO.getBfBatchId(), fiCbPayMemberApiRqstDo.getMemberId());
                        ApiRemitCbPayNotfiyBo apiRemitCbPayNotfiyBo = ApiCbPayRemitNotifyConvert.convertApiRemitCbPayNotfiyBo(
                                fiCbPayRemittanceDo, fiCbPayMemberApiRqstDo, fiCbPayRemittanceAdditionDo, 3, "付汇：渠道处理失败");
                        notifyBiz.notifyMember(String.valueOf(fiCbPayMemberApiRqstDo.getMemberId()), String.valueOf(fiCbPayMemberApiRqstDo.getTerminalId()),
                                String.valueOf(fiCbPayMemberApiRqstDo.getNotifyUrl()), apiRemitCbPayNotfiyBo, fiCbPayMemberApiRqstDo.getMemberReqId(), RemitBusinessTypeEnum.REMIT_APPLY.getCode());
                        log.info("call 第一次购汇：汇款订单异步通知，渠道处理失败，通知商户成功。");
                    }
                } else if (cgwBaseResultDO.getCode() == BaseResultEnum.DISPOSE.getCode()) {
                    // 调用邮件服务
                    mqSendService.sendMessage(MqSendQueueNameEnum.CBPAY_REMITTANCE_EMAIL_QUEUE_NAME, cgwBaseResultDO.getBfBatchId());
                    log.info("发送邮件MQ消息：{}", cgwBaseResultDO.getBfBatchId());
                } else {
                    updateRemittanceOrder(cgwBaseResultDO.getBfBatchId(), null, null, ChannelStatus.INIT.getCode(),
                            updateBy, null, 0, null, null, null, null, null, null, null);
                    log.info("购汇渠道响应异常，更新购汇订单状态");
                }
                break;
            case 3:
                //更新收汇通知状态为解付申请中
                FiCbPaySettleDo cbPaySettleDo = new FiCbPaySettleDo();
                cbPaySettleDo.setOrderId(Long.parseLong(cgwBaseResultDO.getBfBatchId()));
                if (cgwBaseResultDO.getCode() == BaseResultEnum.SUCCESS.getCode()) {
                    log.info("收汇订单号={},解付渠道接受异步通知，渠道接受成功", cgwBaseResultDO.getBfBatchId());
                    cbPaySettleDo.setIncomeStatus(InComeStatusEnum.CHANNEL_INCOME.getCode());
                } else if (cgwBaseResultDO.getCode() == BaseResultEnum.FAIL.getCode()) {
                    log.info("收汇订单号={},解付渠道接受异步通知，渠道处理失败", cgwBaseResultDO.getBfBatchId());
                    cbPaySettleDo.setIncomeStatus(InComeStatusEnum.FAIL.getCode());
                } else {
                    cbPaySettleDo.setIncomeStatus(InComeStatusEnum.FAIL.getCode());
                    log.info("收汇订单号={},解付渠道接受异步通知,渠道响应异常", cgwBaseResultDO.getBfBatchId());
                }
                cbPaySettleManager.modify(cbPaySettleDo);
                break;
            case 5:
                if (cgwBaseResultDO.getCode() == 1) {
                    log.info("batchNo:{},反洗钱异步通知，渠道处理成功", cgwBaseResultDO.getBatchId());
                } else {
                    log.error("购汇渠道响应异常，更新反洗钱状态");
                    // TODO 待考虑 在此重发，有可能会在系统间形成死循环
                }
                break;
            default:
                log.error("第一次异步通知,交易类型不正确");
        }
        log.info("第一次异步通知结束");
    }

    private void updateRemittanceOrder(String batchNo, String bankBatchNo, String applyStatus, String channelStatus, String updateBy,
                                       String cmStatus, Integer purchaseStatus, BigDecimal systemMoney, BigDecimal rate,
                                       Date exchangeSuccDate, Date remitSuccDate, BigDecimal realMoney, BigDecimal remitMoney, String remarks) {
        FiCbPayRemittanceDo fiCbPayRemittanceDo = new FiCbPayRemittanceDo();
        fiCbPayRemittanceDo.setApplyStatus(applyStatus);
        fiCbPayRemittanceDo.setChannelStatus(channelStatus);
        fiCbPayRemittanceDo.setBatchNo(batchNo);
        //银行受理批次号
        fiCbPayRemittanceDo.setBankBatchNo(bankBatchNo);
        fiCbPayRemittanceDo.setUpdateBy(updateBy);
        fiCbPayRemittanceDo.setChannelDate(new Date());
        fiCbPayRemittanceDo.setCmStatus(cmStatus);
        fiCbPayRemittanceDo.setPurchaseStatus(purchaseStatus);
        fiCbPayRemittanceDo.setSystemMoney(systemMoney);
        //银行返回的实际汇率
        fiCbPayRemittanceDo.setRealRate(rate);
        fiCbPayRemittanceDo.setExchangeSuccDate(exchangeSuccDate);
        fiCbPayRemittanceDo.setRemitSuccDate(remitSuccDate);
        fiCbPayRemittanceDo.setRealMoney(realMoney);
        fiCbPayRemittanceDo.setRemitMoney(remitMoney);
        fiCbPayRemittanceDo.setRemarks(remarks);
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
     * @param fiCbPayRemittanceDo       汇款订单信息
     * @return 文件ID
     */
    private Long uploadDetail(List<CgwRemitReqDto> fiCbPayRemittanceDetailDo, FiCbPayRemittanceDo fiCbPayRemittanceDo) {

        String fileName = fiCbPayRemittanceDo.getBatchNo() + "_" + DateUtil.getCurrent(DateUtil.fullPatterns) + ".txt";
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
                    file.getName(), String.valueOf(fiCbPayRemittanceDo.getMemberNo()));
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
}
