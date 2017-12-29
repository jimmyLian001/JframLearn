package com.baofu.cbpayservice.service.impl;

import com.baofoo.cbcgw.facade.api.gw.CgwCbPayReqFacade;
import com.baofoo.cbcgw.facade.dto.gw.request.CgwVerifyMerchantReqDto;
import com.baofu.cbpayservice.biz.CbPayCommonBiz;
import com.baofu.cbpayservice.biz.CbPayRemittanceBiz;
import com.baofu.cbpayservice.biz.MqSendService;
import com.baofu.cbpayservice.biz.ProxyCustomsBiz;
import com.baofu.cbpayservice.biz.models.CbPayRemittanceBankFeeReqBo;
import com.baofu.cbpayservice.biz.models.CbPayRemtStatusChangeReqBo;
import com.baofu.cbpayservice.common.constants.Constants;
import com.baofu.cbpayservice.common.enums.*;
import com.baofu.cbpayservice.common.util.StringUtil;
import com.baofu.cbpayservice.common.util.StringUtils;
import com.baofu.cbpayservice.dal.models.FiCbPaySettleBankDo;
import com.baofu.cbpayservice.facade.CbPayRemittanceFacade;
import com.baofu.cbpayservice.facade.models.CbPayRemittanceAuditReqDto;
import com.baofu.cbpayservice.facade.models.CbPayRemittanceBankFeeReqDto;
import com.baofu.cbpayservice.facade.models.CbPayRemittanceOrderReqV2Dto;
import com.baofu.cbpayservice.facade.models.CbPayRemtStatusChangeReqDto;
import com.baofu.cbpayservice.manager.CbPayCacheManager;
import com.baofu.cbpayservice.manager.RedisManager;
import com.baofu.cbpayservice.manager.CbPaySettleBankManager;
import com.baofu.cbpayservice.service.convert.CbPayRemittanceConvert;
import com.baofu.cbpayservice.service.convert.ProxyCustomsConvert;
import com.system.commons.exception.BizServiceException;
import com.system.commons.result.Result;
import com.system.commons.utils.ExceptionUtils;
import com.system.commons.utils.ParamValidate;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.slf4j.helpers.SystemMarker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 跨境人民币汇款操作接口
 * <p>
 * 1、创建汇款订单
 * 2、汇款订单审核
 * 3、汇款订单状态更新
 * </p>
 * User: wanght Date:2016/11/10 ProjectName: cbpay-service Version: 1.0
 */
@Slf4j
@Service
public class CbPayRemittanceServiceImpl implements CbPayRemittanceFacade {

    /**
     * 支付服务
     */
    @Autowired
    private CbPayRemittanceBiz cbPayRemittanceBiz;

    /**
     * 发送MQ服务
     */
    @Autowired
    private MqSendService mqSendService;

    /**
     * 缓存服务
     */
    @Autowired
    private RedisManager redisManager;

    /**
     * 代理报关服务
     */
    @Autowired
    private ProxyCustomsBiz proxyCustomsBiz;

    /**
     * 网关公共服务信息
     */
    @Autowired
    private CbPayCommonBiz cbPayCommonBiz;

    /**
     * 备案查询接口
     */
    @Autowired
    private CgwCbPayReqFacade cgwCbPayReqFacade;

    /**
     * 多币种账户信息数据服务接口
     */
    @Autowired
    private CbPaySettleBankManager cbPaySettleBankManager;

    /**
     * 缓存服务
     */
    @Autowired
    private CbPayCacheManager cbPayCacheManager;

    /**
     * 创建汇款订单
     *
     * @param cbPayRemittanceOrderReqV2Dto 请求参数
     * @param traceLogId                   日志ID
     * @return 返回是否成功
     */
    @Override
    public Result<Boolean> createRemittanceOrderV2(CbPayRemittanceOrderReqV2Dto cbPayRemittanceOrderReqV2Dto, String traceLogId) {
        Result<Boolean> result;

        try {
            MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
            log.info("call 创建汇款订单请求参数:{}", cbPayRemittanceOrderReqV2Dto);
            ParamValidate.validateParams(cbPayRemittanceOrderReqV2Dto);
            Integer functionId = ProFunEnum.PRO_FUN_10160002.getFunctionId();
            if (CcyEnum.CNY.getKey().equals(cbPayRemittanceOrderReqV2Dto.getTargetCcy())) {
                functionId = ProFunEnum.PRO_FUN_10160001.getFunctionId();
            }
            //判断是否开通产品
            cbPayCacheManager.getProductFunctions(cbPayRemittanceOrderReqV2Dto.getMemberId().intValue(),
                    ProFunEnum.PRO_FUN_10160001.getProductId(), functionId);
            //判断商户是否已经备案
            Long channelId = cbPayCommonBiz.queryChannelId(cbPayRemittanceOrderReqV2Dto.getMemberId(),
                    cbPayRemittanceOrderReqV2Dto.getTargetCcy(), ChannelTypeEnum.PURCHASE_PAYMENT.getCode());
            CgwVerifyMerchantReqDto cgwVerifyMerchantReqDto = CbPayRemittanceConvert.verifyMerchantParamConvert(cbPayRemittanceOrderReqV2Dto, channelId);
            Boolean merchantFlag = cgwCbPayReqFacade.verifyMerchant(cgwVerifyMerchantReqDto);
            log.info("call 商户是否已备案：{}", merchantFlag);
            if (!merchantFlag) {
                log.error("call 商户未备案：商户号：{}，子商户号：{}，币种：{}，渠道号：{}", cgwVerifyMerchantReqDto.getMemberId(), cgwVerifyMerchantReqDto.getChildMemberId(),
                        cgwVerifyMerchantReqDto.getCurrency(), cgwVerifyMerchantReqDto.getChannelId());
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00184);
            }

            //校验汇款订单的文件批次号是否为空
            if (CollectionUtils.isEmpty(cbPayRemittanceOrderReqV2Dto.getBatchFileIdList())) {
                log.info("call 商户{}请求创建汇款订单的文件批次号为空", cbPayRemittanceOrderReqV2Dto.getMemberId());
                return new Result<>(Boolean.FALSE);
            }

            // 判定商户是否锁住
            Boolean lockFlag = isLock(cbPayRemittanceOrderReqV2Dto);
            log.info("call 商户号：{}，是否锁住：{}", cbPayRemittanceOrderReqV2Dto.getMemberId(), lockFlag);
            if (lockFlag) {
                log.info("call 商户{}已锁定，请勿频繁操作！", cbPayRemittanceOrderReqV2Dto.getMemberId());
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0081);
            }
            log.info("call 商户{}未锁住。", cbPayRemittanceOrderReqV2Dto.getMemberId());

            //判断批次币种是否一致，币种信息校验
            List<String> ccyList = proxyCustomsBiz.queryAmlCcy(cbPayRemittanceOrderReqV2Dto.getBatchFileIdList());
            //查询反洗钱币种
            if (!CollectionUtils.isEmpty(ccyList) && ccyList.size() > 1) {
                log.error("call 文件批次包含多种币种：{}，请检查后重试", ccyList);
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00141);
            }
            if (!CollectionUtils.isEmpty(ccyList) && !ccyList.get(0).equals(cbPayRemittanceOrderReqV2Dto.getTargetCcy())) {
                log.error("call 文件批次币种：{}和购汇币种：{},不一致，请检查后重试", ccyList.get(0),
                        cbPayRemittanceOrderReqV2Dto.getTargetCcy());
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00152);
            }

            // 判断账户主体编号是否一致
            for (Long fileBatchId : cbPayRemittanceOrderReqV2Dto.getBatchFileIdList()) {
                String entityNo = redisManager.queryObjectByKey(Constants.CBPAY_AML_ENTITY_KEY +
                        cbPayRemittanceOrderReqV2Dto.getMemberId() + ":" + cbPayRemittanceOrderReqV2Dto.getTargetCcy() + ":" + fileBatchId);
                if (!StringUtil.isBlank(entityNo) && !cbPayRemittanceOrderReqV2Dto.getEntityNo().equals(entityNo)) {
                    log.info("商户账户主体编号不一致，汇款选择编号：{}，反洗钱选择编号：{}", cbPayRemittanceOrderReqV2Dto.getEntityNo(), entityNo);
                    throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00150);
                }
            }

            //校验账户的地址信息长度
            FiCbPaySettleBankDo fiCbPaySettleBankDo = cbPaySettleBankManager.selectBankAccByEntityNo(cbPayRemittanceOrderReqV2Dto.getMemberId(),
                    cbPayRemittanceOrderReqV2Dto.getTargetCcy(), cbPayRemittanceOrderReqV2Dto.getEntityNo());
            if (!addressLengthCheck(fiCbPaySettleBankDo.getBankAccName(), fiCbPaySettleBankDo.getPayeeAddress())) {
                log.error("call 账户地址信息总长度超过120字节或分割后的信息长度超过35字节：{}，{}", fiCbPaySettleBankDo.getBankAccName(), fiCbPaySettleBankDo.getPayeeAddress());
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00186);
            }

            for (Long fileBatchId : cbPayRemittanceOrderReqV2Dto.getBatchFileIdList()) {
                redisManager.insertObject(String.valueOf(cbPayRemittanceOrderReqV2Dto.getEntityNo()), Constants.CBPAY_AML_ENTITY_KEY +
                        cbPayRemittanceOrderReqV2Dto.getMemberId() + ":" + cbPayRemittanceOrderReqV2Dto.getTargetCcy() + ":" + fileBatchId);
            }

            //更新文件状态
            proxyCustomsBiz.batchUpdateFileStatus(ProxyCustomsConvert.batchUpdateStatusConvert(cbPayRemittanceOrderReqV2Dto,
                    UploadFileStatus.CREATING.getCode()));
            log.info("call 更新文件批次表成功：{},{}", cbPayRemittanceOrderReqV2Dto.getBatchFileIdList(),
                    UploadFileStatus.CREATING.getCode());

            //发送mq创建汇款订单
            mqSendService.sendMessage(MqSendQueueNameEnum.CBPAY_CREATE_REMITTANCE_ORDER_QUEUE_NAME,
                    CbPayRemittanceConvert.orderParamConvertV2(cbPayRemittanceOrderReqV2Dto));
            result = new Result<>(Boolean.TRUE);

        } catch (Exception e) {
            log.error("call 创建汇款订单 Exception:", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("call 创建汇款订单 RESULT:{}", result);
        return result;
    }

    private boolean isLock(CbPayRemittanceOrderReqV2Dto cbPayRemittanceOrderReqV2Dto) {
        List<Long> fileIdList = cbPayRemittanceOrderReqV2Dto.getBatchFileIdList();
        for (Long fileId : fileIdList) {
            String value = redisManager.queryObjectByKey(Constants.CBPAY_CREATE_REMITTANCE_KEY +
                    cbPayRemittanceOrderReqV2Dto.getMemberId() + ":" + fileId);
            if (FlagEnum.TRUE.getCode().equals(value)) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    /**
     * 账户地址信息长度校验
     *
     * @param bankAccName  银行开户名
     * @param payeeAddress 收款人地址
     * @return 返回是否正确
     * dxy 2017.08.28
     */
    private Boolean addressLengthCheck(String bankAccName, String payeeAddress) {
        String totalAddress = bankAccName + payeeAddress;
        boolean totalAddresssFlag = totalAddress.length() > 120 ? false : true;
        boolean bankAccNameFlag = StringUtils.lengthCheck(bankAccName, "\\s+", 35);
        boolean payeeAddressFlag = StringUtils.lengthCheck(payeeAddress, "\\s+", 35);
        return totalAddresssFlag && bankAccNameFlag && payeeAddressFlag;
    }

    /**
     * 汇款订单商户审核
     *
     * @param cbPayRemittanceAuditReqDto 审核请求参数
     * @param traceLogId                 日志ID
     * @return 返回是否成功
     */
    @Override
    public Result<Boolean> auditRemittanceOrder(CbPayRemittanceAuditReqDto cbPayRemittanceAuditReqDto, String traceLogId) {
        Result<Boolean> result;
        try {
            MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
            log.info("call 汇款订单审核请求参数:{}", cbPayRemittanceAuditReqDto);
            if (AuditStatus.INIT.getCode().equals(cbPayRemittanceAuditReqDto.getAuditStatus())) {
                log.error("call 汇款订单审核状态不正确");
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0054);
            }
            cbPayRemittanceBiz.auditRemittanceOrder(CbPayRemittanceConvert.auditParamConvert(cbPayRemittanceAuditReqDto));
            result = new Result<>(Boolean.TRUE);
        } catch (Exception e) {
            log.error("call 汇款审核 Exception:", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("call 汇款审核 RESULT:{}", result);
        return result;
    }

    /**
     * 汇款订单状态更新
     *
     * @param cbPayRemtStatusChangeReqDto 状态更新请求参数
     * @param traceLogId                  日志ID
     * @return 返回是否成功
     */
    @Override
    public Result<Boolean> changeRemittanceOrderStatus(CbPayRemtStatusChangeReqDto cbPayRemtStatusChangeReqDto, String traceLogId) {
        Result<Boolean> result;
        try {
            MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
            log.info("call 汇款订单状态更新请求参数:{}", cbPayRemtStatusChangeReqDto);
            cbPayRemittanceBiz.changeRemittanceOrderStatus(CbPayRemittanceConvert.statusChangeParamConvert(cbPayRemtStatusChangeReqDto));
            result = new Result<>(Boolean.TRUE);
        } catch (Exception e) {
            log.error("call 汇款订单状态更新 Exception:", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("call 汇款订单状态更新 RESULT:{}", result);
        return result;
    }

    /**
     * 汇款订单线下处理，后台审核接口
     *
     * @param cbPayRemtStatusChangeReqDto 状态更新请求参数
     * @param traceLogId                  日志ID
     * @return 返回是否成功
     */
    @Override
    public Result<Boolean> backgroundAudit(CbPayRemtStatusChangeReqDto cbPayRemtStatusChangeReqDto, String traceLogId) {
        Result<Boolean> result;
        try {
            MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
            log.info("call 汇款订单线下，后台审核接口，请求参数:{}", cbPayRemtStatusChangeReqDto);
            String value = redisManager.queryObjectByKey(Constants.CBPAY_AUDIT_REMITTANCE_KEY +
                    cbPayRemtStatusChangeReqDto.getMemberId() + ":" + cbPayRemtStatusChangeReqDto.getBatchNo());
            if (FlagEnum.TRUE.getCode().equals(value)) {
                log.info("该汇款订单正在操作，请勿重复操作,商户号:{},批次号：{}", cbPayRemtStatusChangeReqDto.getMemberId(), cbPayRemtStatusChangeReqDto.getBatchNo());
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0081);
            }

            Boolean lockFlag = redisManager.lockRedis(Constants.CBPAY_AUDIT_REMITTANCE_KEY + cbPayRemtStatusChangeReqDto.getMemberId()
                    + ":" + cbPayRemtStatusChangeReqDto.getBatchNo(), FlagEnum.TRUE.getCode(), Constants.TIME_OUT);
            if (!lockFlag) {
                log.info("商户：{},文件批次：{}锁住失败，汇款订单线下处理失败，请稍后重试", cbPayRemtStatusChangeReqDto.getMemberId(), cbPayRemtStatusChangeReqDto.getBatchNo());
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0081);
            }
            CbPayRemtStatusChangeReqBo cbPayRemtStatusChangeReqBo = CbPayRemittanceConvert.statusChangeParamConvert(cbPayRemtStatusChangeReqDto);
            if (AuditStatus.SUCCESS_SECOND_TRUE.getCode().equals(cbPayRemtStatusChangeReqDto.getAuditStatus())) {
                //银行处理成功，清算复审通过，从用户在途账户扣除汇款金额到备付金
                cbPayRemittanceBiz.deductionAmount(cbPayRemtStatusChangeReqBo);
            } else if (AuditStatus.FAIL_SECOND_TRUE.getCode().equals(cbPayRemtStatusChangeReqDto.getAuditStatus())) {
                //银行处理失败，清算复审通过，从用户在途账户扣除汇款金额到用户基本账户，并且返回汇款手续费
                cbPayRemittanceBiz.returnAmount(cbPayRemtStatusChangeReqBo);
            } else {
                cbPayRemittanceBiz.backgroundAudit(cbPayRemtStatusChangeReqBo);
            }
            result = new Result<>(Boolean.TRUE);
        } catch (Exception e) {
            log.error("call 汇款订单线下，后台审核接口异常 Exception:", e);
            result = ExceptionUtils.getResponse(e);
        } finally {
            redisManager.deleteObject(Constants.CBPAY_AUDIT_REMITTANCE_KEY + cbPayRemtStatusChangeReqDto.getMemberId() + ":" + cbPayRemtStatusChangeReqDto.getBatchNo());
            log.info("商户{}汇款后台审核结束，释放锁完成", cbPayRemtStatusChangeReqDto.getMemberId());
        }
        log.info("call 汇款订单线下，后台审核接口 RESULT:{}", result);
        return result;
    }

    /**
     * 付汇入账费用，后台更新接口
     *
     * @param cbPayRemittanceBankFeeReqDto 更新请求参数
     * @param traceLogId                   日志ID
     * @return 返回是否成功
     */
    @Override
    public Result<Boolean> updateBankFee(CbPayRemittanceBankFeeReqDto cbPayRemittanceBankFeeReqDto, String traceLogId) {
        Result<Boolean> result;
        try {
            MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
            log.info("call 付汇入账费用，后台更新接口，请求参数:{}", cbPayRemittanceBankFeeReqDto);
            //参数转化
            CbPayRemittanceBankFeeReqBo cbPayRemittanceBankFeeReqBo = CbPayRemittanceConvert.bankFeeParamConvert(cbPayRemittanceBankFeeReqDto);
            //调用Biz层
            cbPayRemittanceBiz.updateBankFee(cbPayRemittanceBankFeeReqBo);

            result = new Result<>(Boolean.TRUE);
        } catch (Exception e) {
            log.error("call 付汇入账费用，后台更新接口异常 Exception:", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("call 付汇入账费用，后台更新 RESULT:{}", result);
        return result;
    }

    /**
     * 付汇入账费用，后台审核接口
     *
     * @param cbPayRemittanceBankFeeReqDto 更新请求参数
     * @param traceLogId                   日志ID
     * @return 返回是否成功
     */
    @Override
    public Result<Boolean> bankFeeAudit(CbPayRemittanceBankFeeReqDto cbPayRemittanceBankFeeReqDto, String traceLogId) {
        Result<Boolean> result;
        try {
            MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
            log.info("call 付汇入账费用，后台审核接口，请求参数:{}", cbPayRemittanceBankFeeReqDto);
            //参数转化
            CbPayRemittanceBankFeeReqBo cbPayRemittanceBankFeeReqBo = CbPayRemittanceConvert.bankFeeParamConvert(cbPayRemittanceBankFeeReqDto);
            //调用Biz层
            cbPayRemittanceBiz.bankFeeAudit(cbPayRemittanceBankFeeReqBo);

            result = new Result<>(Boolean.TRUE);
        } catch (Exception e) {
            log.error("call 付汇入账费用，后台审核接口异常 Exception:", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("call 付汇入账费用，后台审核 RESULT:{}", result);
        return result;
    }

    /**
     * 汇款异常重发
     *
     * @param batchNo    批次号
     * @param memberId   商户号
     * @param updateBy   操作人
     * @param traceLogId 日志ID
     * @return 返回结果
     */
    @Override
    public Result<Boolean> remittanceResend(String batchNo, Long memberId, String updateBy, String traceLogId) {
        Result<Boolean> result;
        try {
            MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
            log.info("call 汇款重发，请求参数:批次号:{},商户号:{},操作人:{}", batchNo, memberId, updateBy);
            //调用biz层
            cbPayRemittanceBiz.remittanceResend(batchNo, memberId, updateBy);
            result = new Result<>(Boolean.TRUE);
        } catch (Exception e) {
            log.error("汇款重发异常 Exception:", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("call 汇款重发 RESULT:{}", result);
        return result;
    }
}
