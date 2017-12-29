package com.baofu.cbpayservice.service.impl;

import com.baofoo.cbcgw.facade.api.gw.CgwCbPayReqFacade;
import com.baofoo.cbcgw.facade.dto.gw.request.CgwVerifyMerchantReqDto;
import com.baofu.cbpayservice.biz.*;
import com.baofu.cbpayservice.biz.models.AccountBalanceRespBo;
import com.baofu.cbpayservice.biz.models.BatchRemitTrialBo;
import com.baofu.cbpayservice.common.constants.Constants;
import com.baofu.cbpayservice.common.constants.NumberConstants;
import com.baofu.cbpayservice.common.enums.*;
import com.baofu.cbpayservice.common.util.ParamCheckUtil;
import com.baofu.cbpayservice.common.util.StringUtil;
import com.baofu.cbpayservice.common.util.StringUtils;
import com.baofu.cbpayservice.dal.models.FiCbPaySettleBankDo;
import com.baofu.cbpayservice.facade.BatchRemitFacade;
import com.baofu.cbpayservice.facade.models.BatchRemitDto;
import com.baofu.cbpayservice.facade.models.BatchRemitTrialDto;
import com.baofu.cbpayservice.facade.models.ProxyCustomsDto;
import com.baofu.cbpayservice.facade.models.res.AccountBalanceRespDto;
import com.baofu.cbpayservice.facade.models.res.BatchRemitTrialRespDto;
import com.baofu.cbpayservice.manager.CbPayCacheManager;
import com.baofu.cbpayservice.manager.CbPaySettleBankManager;
import com.baofu.cbpayservice.manager.RedisManager;
import com.baofu.cbpayservice.service.convert.BalanceParamConvert;
import com.baofu.cbpayservice.service.convert.BatchRemitFacadeConvert;
import com.baofu.cbpayservice.service.convert.CbPayRemittanceConvert;
import com.system.commons.exception.BizServiceException;
import com.system.commons.result.Result;
import com.system.commons.utils.ExceptionUtils;
import com.system.commons.utils.ParamValidate;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.slf4j.helpers.SystemMarker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 批量汇款接口
 * <p>
 * </p>
 * User: 不良人 Date:2017/10/20 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
@Service
public class BatchRemitFacadeImpl implements BatchRemitFacade {

    /**
     * 批量汇款接口biz服务
     */
    @Autowired
    private BatchRemitBiz batchRemitBiz;

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
     * 跨境人民币订单信息
     */
    @Autowired
    private LockBiz lockBiz;


    /**
     * @param memberId   商户号
     * @param traceLogId 日志ID
     * @return 账户余额
     */
    @Override
    public Result<AccountBalanceRespDto> queryBalance(Long memberId, String traceLogId) {

        MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
        log.info("call 查询账户余额 商户号:{}", memberId);
        Result<AccountBalanceRespDto> result;
        AccountBalanceRespBo accountBalanceRespBo;
        try {
            accountBalanceRespBo = batchRemitBiz.queryBalance(memberId);
            result = new Result<>(BalanceParamConvert.convertBalanceBoToDto(accountBalanceRespBo));
        } catch (Exception e) {
            log.error("call 查询账户余额异常", e);
            result = ExceptionUtils.getResponse(e);
        }

        log.info("call 查询账户余额返回结果:{}", result);
        return result;
    }

    /**
     * 批量汇款文件上传
     *
     * @param proxyCustomsDto 请求参数
     * @param traceLogId      日志id
     * @return 批次ID
     */
    @Override
    public Result<Long> batchRemitFileUpload(ProxyCustomsDto proxyCustomsDto, String traceLogId) {

        MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
        log.info("[批量汇款文件上传] 上传文件参数:{}", proxyCustomsDto);

        Result<Long> result;
        try {
            ParamValidate.validateParams(proxyCustomsDto);
            Long fileBatchNo = batchRemitBiz.batchRemitFileUpload(BatchRemitFacadeConvert.toRemitFileBo(proxyCustomsDto));
            result = new Result<>(fileBatchNo);
        } catch (Exception e) {
            log.error("[批量汇款文件上传] 上传文件异常", e);
            result = ExceptionUtils.getResponse(e);
        }

        log.info("[批量汇款文件上传] 上传文件返回结果:{}", result);
        return result;
    }

    /**
     * 批量汇款-试算
     *
     * @param batchRemitDtoList 汇款参数集合
     * @param traceLogId        日志id
     * @return 创建结果
     */
    @Override
    public Result<List<BatchRemitTrialRespDto>> trial(List<BatchRemitTrialDto> batchRemitDtoList, String traceLogId) {

        MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
        log.info("[批量汇款-试算] 试算参数:{}", batchRemitDtoList);

        Result<List<BatchRemitTrialRespDto>> result;
        try {
            ParamCheckUtil.listCheck(batchRemitDtoList);
            List<BatchRemitTrialBo> trialBoList = batchRemitBiz.batchRemitTrial(BatchRemitFacadeConvert
                    .toBatchRemitBoList(batchRemitDtoList));
            verifyAmount(trialBoList, batchRemitDtoList.get(NumberConstants.ZERO).getMemberId());
            List<BatchRemitTrialRespDto> trialRespDtoList = BatchRemitFacadeConvert.toTrialRespDto(trialBoList);
            result = new Result<>(trialRespDtoList);
        } catch (Exception e) {
            log.error("[批量汇款-试算] 异常", e);
            result = ExceptionUtils.getResponse(e);
        }

        log.info("[批量汇款-试算] 返回结果:{}", result);
        return result;
    }

    /**
     * 创建批量汇款
     *
     * @param batchRemitDtoList 汇款参数集合
     * @param traceLogId        日志id
     * @return 创建结果
     */
    @Override
    public Result<Boolean> batchRemit(final List<BatchRemitDto> batchRemitDtoList, String traceLogId) {

        Result<Boolean> result;
        MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
        String key = Constants.BATCH_REMITTANCE_KEY;
        try {
            log.info("[创建批量汇款] 批量创建汇款订单请求参数:{}", batchRemitDtoList);
            ParamCheckUtil.listCheck(batchRemitDtoList);
            key = key + batchRemitDtoList.get(NumberConstants.ZERO).getMemberId();
            lockBiz.lock(key,Constants.TIME_OUT);
            //判断商户是否已经备案
            verifyMerchant(batchRemitDtoList);
            //启动线程
            ExecutorService fixedThreadPool = Executors.newFixedThreadPool(NumberConstants.ONE);
            final String finalKey = key;
            fixedThreadPool.execute(new Runnable() {
                public void run() {
                    batchRemitRun(batchRemitDtoList, finalKey);
                }
            });
            result = new Result<>(true);
        } catch (Exception e) {
            lockBiz.unLock(key);
            log.error("[创建批量汇款] 批量创建汇款订单异常", e);
            result = ExceptionUtils.getResponse(e);
        }

        log.info("[创建批量汇款] 批量创建汇款订单返回结果:{}", result);
        return result;
    }

    /**
     * 线程创建订单
     *
     * @param batchRemitDtoList 汇款订单参数
     * @param key               锁key
     */
    private void batchRemitRun(final List<BatchRemitDto> batchRemitDtoList, final String key) {

        log.info("[创建批量汇款] 线程参数:{}", batchRemitDtoList);
        for (int i = 0; i < batchRemitDtoList.size(); i++) {
            BatchRemitDto batchRemitDto = batchRemitDtoList.get(i);
            log.info("[创建批量汇款] 参数:{}", i, batchRemitDto);
            Boolean flag = Boolean.FALSE;
            if (i == batchRemitDtoList.size() - NumberConstants.ONE) {
                flag = Boolean.TRUE;
            }
            remitOrder(batchRemitDto, flag, key);
            if (flag) {
                lockBiz.unLock(key);
            }
        }
    }

    /**
     * 单个汇款订单创建
     *
     * @param batchRemitDto 请求汇款参数
     * @param flag          标识
     * @param key           锁key
     */
    private void remitOrder(BatchRemitDto batchRemitDto, Boolean flag, String key) {

        log.info("[创建批量汇款] 批量创建汇款订单，单个参数:{}", batchRemitDto);
        //创建失败汇款订单
        AccountBalanceRespBo respBo = batchRemitBiz.queryBalance(batchRemitDto.getMemberId());
        if (respBo.getOrderAmount().compareTo(batchRemitDto.getRemitAmt()) < NumberConstants.ZERO) {
            batchRemitBiz.createErrorRemitOrder(BatchRemitFacadeConvert.toBatchRemitBo(batchRemitDto));
            if (flag) {
                lockBiz.unLock(key);
            }
            return;
        }
        //创建文件
        Long fileBatchNo = batchRemitBiz.createFileBatch(BatchRemitFacadeConvert.toBatchRemitBo(batchRemitDto), flag, key);
        // 判定商户是否锁住
        isLock(batchRemitDto, fileBatchNo);
        verifyEntityNO(batchRemitDto, fileBatchNo);
        //更新汇款文件状态
        proxyCustomsBiz.batchUpdateFileStatus(BatchRemitFacadeConvert.batchUpdateStatusConvert(batchRemitDto,
                UploadFileStatus.CREATING.getCode(), fileBatchNo));
        log.info("[创建批量汇款] 更新文件批次表成功：{},{}", fileBatchNo, UploadFileStatus.CREATING.getCode());
        //发送mq创建汇款订单
        mqSendService.sendMessage(MqSendQueueNameEnum.CBPAY_CREATE_REMITTANCE_ORDER_QUEUE_NAME,
                BatchRemitFacadeConvert.orderParamConvertV2(batchRemitDto, fileBatchNo));
    }

    /**
     * 锁
     *
     * @param batchRemitDto 批量汇款参数
     * @param fileBatchNo   文件ID
     */
    private void isLock(BatchRemitDto batchRemitDto, Long fileBatchNo) {

        String value = redisManager.queryObjectByKey(Constants.CBPAY_CREATE_REMITTANCE_KEY
                + batchRemitDto.getMemberId() + Constants.REDIS_SYMBOL + fileBatchNo);
        if (FlagEnum.TRUE.getCode().equals(value)) {
            log.info("[创建批量汇款] 商户{}已锁定，请勿频繁操作！", batchRemitDto.getMemberId());
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0081);
        }
        log.info("[创建批量汇款] 商户{}未锁住。", batchRemitDto.getMemberId());
    }

    /**
     * 账户地址信息长度校验
     *
     * @param bankDo 银行开户信息
     */
    private void addressLengthCheck(FiCbPaySettleBankDo bankDo) {
        String totalAddress = bankDo.getBankAccName() + bankDo.getPayeeAddress();
        boolean totalAddressFlag = totalAddress.length() > NumberConstants.ONE_HUNDRED_AND_TWENTY ? false : true;
        boolean bankAccNameFlag = StringUtils.lengthCheck(bankDo.getBankAccName(), "\\s+",
                NumberConstants.THIRTY_FIVE);
        boolean payeeAddressFlag = StringUtils.lengthCheck(bankDo.getPayeeAddress(), "\\s+",
                NumberConstants.THIRTY_FIVE);
        if (!(totalAddressFlag && bankAccNameFlag && payeeAddressFlag)) {
            log.error("[创建批量汇款] 账户地址信息总长度超过120字节或分割后的信息长度超过35字节：{}，{}",
                    bankDo.getBankAccName(), bankDo.getPayeeAddress());
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00186,
                    bankDo.getSettleCcy() + "账户地址信息总长度超长");
        }
    }

    /**
     * 判断商户是否备案
     *
     * @param batchRemitDtoList 批量汇款信息
     */
    private void verifyMerchant(List<BatchRemitDto> batchRemitDtoList) {

        Integer functionId = ProFunEnum.PRO_FUN_10160002.getFunctionId();
        for (BatchRemitDto batchRemitDto : batchRemitDtoList) {
            //是否人民币汇款
            if (CcyEnum.CNY.getKey().equals(batchRemitDto.getTargetCcy())) {
                functionId = ProFunEnum.PRO_FUN_10160001.getFunctionId();
            }
            //判断是否开通产品
            cbPayCacheManager.getProductFunctions(batchRemitDto.getMemberId().intValue(),
                    ProFunEnum.PRO_FUN_10160001.getProductId(), functionId);
            Long channelId = cbPayCommonBiz.queryChannelId(batchRemitDto.getMemberId(),
                    batchRemitDto.getTargetCcy(), ChannelTypeEnum.PURCHASE_PAYMENT.getCode());
            CgwVerifyMerchantReqDto reqDto = CbPayRemittanceConvert.verifyMerchantConvert(batchRemitDto, channelId);
            Boolean merchantFlag = cgwCbPayReqFacade.verifyMerchant(reqDto);
            log.info("[创建批量汇款] 商户是否已备案：{}", merchantFlag);
            if (!merchantFlag) {
                log.error("[创建批量汇款] 商户未备案：商户号：{}，子商户号：{}，币种：{}，渠道号：{}",
                        batchRemitDto.getMemberId(), reqDto.getChildMemberId(), reqDto.getCurrency(),
                        reqDto.getChannelId());
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00184, "币种["
                        + reqDto.getCurrency() + "]未备案");
            }
            // 判断账户主体编号是否一致
            FiCbPaySettleBankDo fiCbPaySettleBankDo = cbPaySettleBankManager.selectBankAccByEntityNo(
                    batchRemitDto.getMemberId(), batchRemitDto.getTargetCcy(), batchRemitDto.getEntityNo());
            //校验账户的地址信息长度
            addressLengthCheck(fiCbPaySettleBankDo);
        }
    }

    /**
     * 商户账户主体编号不一致校验
     *
     * @param batchRemitDto 批量汇款信息
     * @param fileBatchNo   文件批次号
     */
    private void verifyEntityNO(BatchRemitDto batchRemitDto, Long fileBatchNo) {

        //redis中key
        String key = batchRemitDto.getMemberId() + Constants.REDIS_SYMBOL + batchRemitDto.getTargetCcy()
                + Constants.REDIS_SYMBOL + fileBatchNo;
        //根据key查询
        String entityNo = redisManager.queryObjectByKey(Constants.CBPAY_AML_ENTITY_KEY + key);
        if (!StringUtil.isBlank(entityNo) && !batchRemitDto.getEntityNo().equals(entityNo)) {
            log.info("[创建批量汇款] 商户账户主体编号不一致，汇款选择编号：{}，反洗钱选择编号：{}",
                    batchRemitDto.getEntityNo(), entityNo);
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00150);
        }
        //添加主体编号到redis
        redisManager.insertObject(String.valueOf(batchRemitDto.getEntityNo()),
                Constants.CBPAY_AML_ENTITY_KEY + batchRemitDto.getMemberId() + key);
    }

    /**
     * 余额校验
     *
     * @param trialBoList 汇款参数
     * @param memberId    商户号
     */
    private void verifyAmount(List<BatchRemitTrialBo> trialBoList, Long memberId) {

        BigDecimal totalRemitAmt = BigDecimal.ZERO;
        BigDecimal totalFee = BigDecimal.ZERO;
        for (BatchRemitTrialBo bo : trialBoList) {
            totalRemitAmt = totalRemitAmt.add(bo.getRemitAmt());
            totalFee = totalFee.add(bo.getTransferFee());
        }
        AccountBalanceRespBo bo = batchRemitBiz.queryBalance(memberId);
        if (bo.getOrderAmount().compareTo(totalRemitAmt) < NumberConstants.ZERO) {
            log.info("[批量汇款-试算] 批量汇款订单金额不足，汇款总金额:{},订单余额:{}", totalRemitAmt,
                    bo.getOrderAmount());
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00214);
        }
        if (bo.getAccountAmount().compareTo(totalRemitAmt.add(totalFee)) < NumberConstants.ZERO) {
            log.info("[批量汇款-试算] 批量汇款账户余额不足，汇款总金额:{},账户余额:{}", totalRemitAmt,
                    bo.getAccountAmount());
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00213);
        }

    }
}