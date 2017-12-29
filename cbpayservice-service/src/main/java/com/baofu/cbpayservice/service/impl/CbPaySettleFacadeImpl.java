package com.baofu.cbpayservice.service.impl;

import com.baofu.cbpayservice.biz.CbPaySettleBiz;
import com.baofu.cbpayservice.biz.SettleEmailBiz;
import com.baofu.cbpayservice.biz.SettleQueryBiz;
import com.baofu.cbpayservice.biz.impl.MqSendServiceImpl;
import com.baofu.cbpayservice.biz.models.ReceiveAuditBo;
import com.baofu.cbpayservice.biz.models.SettleFileUploadReqBo;
import com.baofu.cbpayservice.biz.models.SettleIncomeApplyBo;
import com.baofu.cbpayservice.biz.models.SettleNotifyMemberBo;
import com.baofu.cbpayservice.common.constants.Constants;
import com.baofu.cbpayservice.common.constants.RedisKeyConstants;
import com.baofu.cbpayservice.common.enums.*;
import com.baofu.cbpayservice.common.util.StringUtil;
import com.baofu.cbpayservice.dal.mapper.FiCbPaySettleApplyMapper;
import com.baofu.cbpayservice.dal.models.FiCbPaySettleApplyDo;
import com.baofu.cbpayservice.dal.models.FiCbPaySettleDo;
import com.baofu.cbpayservice.facade.CbPaySettleFacade;
import com.baofu.cbpayservice.facade.models.*;
import com.baofu.cbpayservice.facade.models.res.SettleNotifyRespDto;
import com.baofu.cbpayservice.manager.CbPaySettleManager;
import com.baofu.cbpayservice.manager.OrderIdManager;
import com.baofu.cbpayservice.manager.RedisManager;
import com.baofu.cbpayservice.service.convert.CbPaySettleConvert;
import com.system.commons.exception.BizServiceException;
import com.system.commons.result.Result;
import com.system.commons.utils.ExceptionUtils;
import com.system.commons.utils.ParamValidate;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.slf4j.helpers.SystemMarker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 结汇操作接口服务实现
 * <p/>
 * User: 不良人 Date:2017/4/13 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
@Service
public class CbPaySettleFacadeImpl implements CbPaySettleFacade {

    /**
     * 结汇操作业务逻辑实现接口
     */
    @Autowired
    private CbPaySettleBiz cbPaySettleBiz;

    /**
     * 缓存服务
     */
    @Autowired
    private RedisManager redisManager;

    /**
     * 创建宝付订单号
     */
    @Autowired
    private OrderIdManager orderIdManager;

    /**
     * 发送mq服务
     */
    @Autowired
    private MqSendServiceImpl mqSendService;

    /**
     * 收结汇查询服务Manger
     */
    @Autowired
    private CbPaySettleManager cbPaySettleManager;

    /**
     * 结汇邮件通知服务
     */
    @Autowired
    private SettleEmailBiz settleEmailBiz;

    /**
     * 汇入API查询服务
     */
    @Autowired
    private SettleQueryBiz settleQueryBiz;

    /**
     * 汇入申请Mapper服务
     */
    @Autowired
    private FiCbPaySettleApplyMapper settleApplyMapper;

    /**
     * 结汇上传文件服务
     * 由商户前台导入excel发起
     *
     * @param settleFileUploadDto 请求参数
     * @return 批次ID
     */
    @Override
    public Result<Long> settleFileUpload(SettleFileUploadDto settleFileUploadDto, String traceLogId) {

        MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
        log.info("call 跨境结汇，上传文件参数:{}", settleFileUploadDto);

        Result<Long> result;
        try {
            ParamValidate.validateParams(settleFileUploadDto);
            Long fileBatchNo = cbPaySettleBiz.settleFileUpload(CbPaySettleConvert.toSettleFileUploadReqBo(settleFileUploadDto));
            result = new Result<>(fileBatchNo);
        } catch (Exception e) {
            log.error("call 跨境结汇，上传文件异常", e);
            result = ExceptionUtils.getResponse(e);
        }

        log.info("call 跨境结汇,上传文件返回结果:{}", result);
        return result;
    }

    /**
     * 结汇上传订单明细文件服务(API)
     * 由商户前台导入excel发起
     *
     * @param settleFileUploadApiDto 请求参数
     * @param traceLogId             日志ID
     * @return 批次ID
     */
    @Override
    public Result<Long> settleFileUploadFromApi(SettleFileUploadApiDto settleFileUploadApiDto, String traceLogId) {

        MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
        log.info("call 跨境结汇，上传文件参数:{}", settleFileUploadApiDto);

        Result<Long> result;
        try {
            ParamValidate.validateParams(settleFileUploadApiDto);
            Long fileBatchNo = cbPaySettleBiz.settleFileUpload(CbPaySettleConvert.toSettleFileUploadReqApiBo(settleFileUploadApiDto));
            result = new Result<>(fileBatchNo);
        } catch (Exception e) {
            log.error("call 跨境结汇，上传文件异常", e);
            result = ExceptionUtils.getResponse(e);
        }

        log.info("call 跨境结汇,上传文件返回结果:{}", result);
        return result;

    }

    /**
     * 结汇上传文件服务明细校验
     * 由商户前台导入excel发起
     * 此文件不入库，校验信息保存到redis
     *
     * @param settleFileVerifyDto 请求参数
     * @return 批次ID
     */
    @Override
    public Result<Long> settleFileVerify(SettleFileVerifyDto settleFileVerifyDto, String traceLogId) {

        MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
        log.info("call 跨境结汇文件明细校验工具，上传文件参数:{}", settleFileVerifyDto);

        Result<Long> result;
        try {
            ParamValidate.validateParams(settleFileVerifyDto);
            Long fileBatchNo = orderIdManager.orderIdCreate();
            SettleFileUploadReqBo settleFileUploadReqBo = CbPaySettleConvert.toSettleFileVerifyReqBo(settleFileVerifyDto);
            settleFileUploadReqBo.setFileBatchNo(fileBatchNo);
            log.info("结汇文件明细校验工具上传文件产生的文件批次号fileBatchNo：{}", fileBatchNo);

            //发送Mq消息
            mqSendService.sendMessage(MqSendQueueNameEnum.CBPAY_SETTLE_FILE_TEST_AND_VERIFY_PROCESS_QUEUE_NAME, settleFileUploadReqBo);
            log.info("call excel代理跨境结汇文件明细校验工具异步处理数据，生产者，消息队列:{},内容为:{}",
                    MqSendQueueNameEnum.CBPAY_SETTLE_FILE_TEST_AND_VERIFY_PROCESS_QUEUE_NAME, settleFileUploadReqBo);
            result = new Result<>(fileBatchNo);
            redisManager.insertObject(0L, fileBatchNo.toString());
        } catch (Exception e) {
            log.error("call 跨境结汇文件明细校验工具，上传文件异常", e);
            result = ExceptionUtils.getResponse(e);
        }

        log.info("call 跨境结汇文件明细校验工具,上传文件返回结果:{}", result);
        return result;
    }

    /**
     * 结汇上传文件服务明细校验
     * 由商户前台导入excel发起
     * 此文件不入库，校验信息保存到redis
     *
     * @param fileBatchNo 请求参数
     * @return 批次ID
     */
    @Override
    public Result<Long> settleFileVerifyQuery(Long fileBatchNo, String traceLogId) {

        MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
        log.info("call 跨境结汇文件明细校验工具查询，查询的文件批次参数:{}", fileBatchNo);

        Result<Long> result;
        try {
            if (fileBatchNo == null) {
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00160);
            }
            String value = redisManager.queryObjectByKey(String.valueOf(fileBatchNo));
            if (StringUtil.isBlank(value)) {
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0097);
            }
            Long errorDfsId = Long.parseLong(value);
            result = new Result<>(errorDfsId);
            if (errorDfsId != 0L) {
                redisManager.deleteObject(fileBatchNo.toString());
            }
        } catch (Exception e) {
            log.error("call 跨境结汇文件明细校验工具查询，明细校验结果查询异常", e);
            result = ExceptionUtils.getResponse(e);
        }

        log.info("call 跨境结汇文件明细校验工具查询,明细校验结果查询返回结果:{}", result);
        return result;
    }

    /**
     * 外汇汇入申请(客户在商户前台发起)
     *
     * @param settleIncomeApplyDto 外汇汇入申请参数
     * @param traceLogId           日志ID
     * @return 申请单号
     */
    @Override
    public Result<Long> settleIncomeApply(SettleIncomeApplyDto settleIncomeApplyDto, String traceLogId) {

        MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
        Boolean lockFlag = Boolean.FALSE;
        log.info("call 跨境结汇，(客户在商户前台发起)外汇汇入申请参数:{}", settleIncomeApplyDto);
        Result<Long> result;
        try {
            ParamValidate.validateParams(settleIncomeApplyDto);
            //判定商户是否锁住
            lockFlag = settleApplyMerchantLock(settleIncomeApplyDto.getIncomeNo());
            SettleIncomeApplyBo settleIncomeApplyBo = CbPaySettleConvert.toSettleIncomeApplyBo(settleIncomeApplyDto);
            //业务参数信息校验
            cbPaySettleBiz.settleApplyValidate(settleIncomeApplyBo, Boolean.FALSE);
            //汇入申请保存
            Long applyNo = cbPaySettleBiz.settleIncomeApply(settleIncomeApplyBo);

            //收到商户前台录入的汇入汇款申请后提示清算人员和结汇相关人员可以进行人工匹配
            settleEmailBiz.importApplyNotify(settleIncomeApplyBo.getMemberId(), settleIncomeApplyDto.getIncomeNo(),
                    settleIncomeApplyDto.getOrderAmt(),
                    settleIncomeApplyDto.getOrderCcy());
            result = new Result<>(applyNo);
        } catch (Exception e) {
            log.error("call 跨境结汇，外汇汇入申请异常", e);
            result = ExceptionUtils.getResponse(e);
        } finally {
            if (lockFlag) {
                redisManager.deleteObject(Constants.CBPAY_CREATE_SUM_FILE_KEY + settleIncomeApplyDto.getIncomeNo());
                log.info("商户{}外汇汇入申请结束，释放锁完成", settleIncomeApplyDto.getIncomeNo());
            }
        }

        log.info("call 跨境结汇,外汇汇入申请返回结果:{}", result);
        return result;
    }

    /**
     * 外汇汇入申请(客户API发起)
     *
     * @param settleIncomeApplyDto 外汇汇入申请参数
     * @param traceLogId           日志ID
     * @return 申请单号
     */
    @Override
    public Result<Long> settleIncomeApplyAPI(SettleIncomeApplyDto settleIncomeApplyDto, String traceLogId) {

        MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
        Boolean lockFlag = Boolean.FALSE;
        log.info("call 跨境结汇，(客户API发起)外汇汇入申请参数:{}", settleIncomeApplyDto);
        Result<Long> result;
        try {
            //参数基本格式校验
            ParamValidate.validateParams(settleIncomeApplyDto);
            //判定商户是否锁住
            lockFlag = settleApplyMerchantLock(settleIncomeApplyDto.getIncomeNo());
            SettleIncomeApplyBo settleIncomeApplyBo = CbPaySettleConvert.toSettleIncomeApplyBo(settleIncomeApplyDto);

            //业务参数信息校验
            cbPaySettleBiz.settleApplyValidate(settleIncomeApplyBo, Boolean.TRUE);

            //汇入申请信息订单入库，保存在汇入申请表中
            Long applyNo = cbPaySettleBiz.settleIncomeApply(settleIncomeApplyBo);

            //异步校验文件格式内容并保存结汇申请信息,由于使用异步形式需把日志ID传入
            cbPaySettleBiz.asynchronousRequestAPI(settleIncomeApplyBo, applyNo, traceLogId);

            result = new Result<>(applyNo);
        } catch (Exception e) {
            log.error("call 跨境结汇，(客户API发起)外汇汇入申请异常", e);
            result = ExceptionUtils.getResponse(e);
        } finally {
            if (lockFlag) {
                redisManager.deleteObject(Constants.CBPAY_CREATE_SUM_FILE_KEY + settleIncomeApplyDto.getIncomeNo());
                log.info("商户{}外汇汇入申请结束，释放锁完成", settleIncomeApplyDto.getIncomeNo());
            }
        }
        log.info("call 跨境结汇,外汇汇入申请返回结果:{}", result);
        return result;
    }

    /**
     * 判定商户是否锁住
     *
     * @param incomeNo 外汇汇入编号
     * @return Boolean
     */
    private Boolean settleApplyMerchantLock(String incomeNo) {

        Boolean lockFlag = isLock(Constants.CBPAY_CREATE_SUM_FILE_KEY + incomeNo);
        log.info("call ： 外汇汇入申请TT编号：{}，是否锁住：{}", incomeNo, lockFlag);
        if (lockFlag) {
            log.error("call 外汇汇入申请： TT编号{}已锁定，请勿频繁操作！", incomeNo);
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0081);
        }
        log.info("call 外汇汇入申请：TT编号{}未锁住。", incomeNo);
        lockFlag = lock(Constants.CBPAY_CREATE_SUM_FILE_KEY + incomeNo);
        if (!lockFlag) {
            log.info("外汇汇入申请{}锁住失败，暂不支持外汇汇入申请！", incomeNo);
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00163);
        }
        return lockFlag;
    }

    /**
     * @param traceLogId       日志ID
     * @param receiverAuditDto 审核对象
     * @param traceLogId       日志ID
     * @return 成功或失败
     */
    @Override
    public Result<Boolean> receiveAudit(ReceiverAuditDto receiverAuditDto, String traceLogId) {

        MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
        log.info("call 跨境结汇，外汇汇入申请审核参数:{}", receiverAuditDto);
        Result<Boolean> result;
        String redisKey = RedisKeyConstants.RECEIVE_AUDIT_REDIS_KEY + receiverAuditDto.getOrderId();
        Boolean redisLockFlag = Boolean.FALSE;
        try {

            ParamValidate.validateParams(receiverAuditDto);
            redisLockFlag = redisManager.lockRedis(redisKey, FlagEnum.TRUE.getCode(), Constants.TIME_OUT);
            if (!redisLockFlag) {
                log.warn("收汇编号：{},系统在正在处理中，重复操作", receiverAuditDto.getOrderId());
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00197);
            }
            ReceiveAuditBo receiveAuditBo = CbPaySettleConvert.toSettleIncomeApplyCheckBo(receiverAuditDto);

            cbPaySettleBiz.receiveAudit(receiveAuditBo);
            result = new Result<>(true);
        } catch (Exception e) {
            log.error("call 跨境结汇，外汇汇入申请审核异常", e);
            result = ExceptionUtils.getResponse(e);
        } finally {
            if (redisLockFlag) {
                redisManager.deleteObject(redisKey);
            }
        }

        log.info("call 跨境结汇,外汇汇入申请审核返回结果:{}", result);
        return result;
    }

    /**
     * 运营设置匹配信息
     *
     * @param settleOperationSetDto 审核对象
     * @param traceLogId            日志ID
     * @return 成功或失败
     */
    @Override
    public Result<Boolean> operationSet(SettleOperationSetDto settleOperationSetDto, String traceLogId) {

        MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
        log.info("call 跨境结汇，运营设置匹配信息请求参数:{}", settleOperationSetDto);
        Result<Boolean> result;
        try {

            ParamValidate.validateParams(settleOperationSetDto);
            cbPaySettleBiz.operationSet(CbPaySettleConvert.toSettleOperationSetReqBo(settleOperationSetDto));
            result = new Result<>(true);
        } catch (Exception e) {
            log.error("call 跨境结汇，运营设置匹配信息异常", e);
            result = ExceptionUtils.getResponse(e);
        }

        log.info("call 跨境结汇,运营设置匹配信息返回结果:{}", result);
        return result;
    }

    /**
     * 手工重新清算
     *
     * @param orderId    宝付结汇批次号
     * @param traceLogId 日志ID
     * @return 成功或失败
     */
    @Override
    public Result<Boolean> manualSettlement(Long orderId, String traceLogId) {

        MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
        log.info("call 手工重新清算参数:{}", orderId);
        Result<Boolean> result;
        try {
            cbPaySettleBiz.manualSettlement(orderId);
            result = new Result<>(true);
        } catch (Exception e) {
            log.error("call 手工重新清算异常", e);
            result = ExceptionUtils.getResponse(e);
        }
        return result;
    }

    /**
     * 商户汇入申请运营人员审核(后台运营人员操作)
     *
     * @param operateConfirmDto 汇入申请参数
     * @param traceLogId        日志ID
     * @return 成功失败
     */
    @Override
    public Result<Boolean> operateConfirm(OperateConfirmDto operateConfirmDto, String traceLogId) {

        MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
        log.info("call 商户汇入申请运营人员审核请求参数:{}", operateConfirmDto);
        Result<Boolean> result;

        try {
            ParamValidate.validateParams(operateConfirmDto);
            cbPaySettleBiz.operateConfirm(CbPaySettleConvert.toOperateConfirmBo(operateConfirmDto));
            result = new Result<>(true);
        } catch (Exception e) {
            log.error("call 商户汇入申请运营人员审核异常", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("call 商户汇入申请运营人员审核返回参数:{}", result);

        return result;
    }

    /**
     * 收汇补录接口
     *
     * @param collectionMakeupDto 收汇补录参数
     * @param traceLogId          日志ID
     * @return 成功失败
     */
    @Override
    public Result<Boolean> collectionMakeup(CollectionMakeupDto collectionMakeupDto, String traceLogId) {

        MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
        log.info("call 收汇补录请求参数:{}", collectionMakeupDto);
        Result<Boolean> result;

        try {
            ParamValidate.validateParams(collectionMakeupDto);

            cbPaySettleBiz.collectionMakeup(CbPaySettleConvert.toCollectionMakeupBo(collectionMakeupDto));
            result = new Result<>(true);
        } catch (Exception e) {
            log.error("call 收汇补录异常", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("call 收汇补录返回参数:{}", result);

        return result;
    }

    /**
     * 重发结汇申请
     *
     * @param batchNo       文件批次号
     * @param settleOrderId 结汇订单ID
     * @param traceLogId    日志ID
     * @return return
     */
    @Override
    public Result<Boolean> reSendSettleApply(Long batchNo, Long settleOrderId, String traceLogId) {

        MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
        log.info("call 跨境结汇，重发结汇申请参数:{}", settleOrderId);
        Result<Boolean> result;
        try {
            cbPaySettleBiz.sendSettleConfirmMq(settleOrderId);
            //收结汇订单状态查询并状态校验，防止重复请求
            FiCbPaySettleDo fiCbPaySettleDo = cbPaySettleManager.queryByOrderId(settleOrderId);
            if (fiCbPaySettleDo == null) {
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0094);
            }
            if (ReceiverAuditCmStatusEnum.SECOND_CHECK.getCode() != fiCbPaySettleDo.getCmAuditState()) {
                log.warn("结汇订单：{},清算初审复审状态异常", fiCbPaySettleDo);
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00160, "收汇订单清算初审复审状态异常");
            }
            if (fiCbPaySettleDo.getSettleStatus() != SettleStatusEnum.FAIL.getCode() &&
                    fiCbPaySettleDo.getSettleStatus() != SettleStatusEnum.WAIT_SETTLEMENT.getCode()) {
                log.warn("结汇订单：{}，正在处理中或结汇成功，重发结汇申请异常", fiCbPaySettleDo);
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00105);
            }
            cbPaySettleBiz.sendSettleConfirmMq(settleOrderId);
            result = new Result<>(true);
        } catch (Exception e) {
            log.error("call 跨境结汇，重发结汇申请异常:{}", e);
            result = ExceptionUtils.getResponse(e);
        }

        log.info("call 跨境结汇,重发结汇申请返回结果:{}", result);
        return result;
    }

    /**
     * 文件处理，处理结果，匹配结果，结汇结果查询
     *
     * @param memberId 商户号
     * @param incomeNo 汇款流水号
     * @return SettleNotifyRespDto 查询结果
     */
    @Override
    public Result<SettleNotifyRespDto> settlementResultQuery(Long memberId, String incomeNo, String traceLogId) {

        MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
        log.info("call 跨境结汇结果查询参数:{},{}", memberId, incomeNo);
        Result<SettleNotifyRespDto> result;
        try {
            SettleNotifyMemberBo settleNotifyBo = settleQueryBiz.querySettleNotifyByReqNo(memberId, incomeNo);

            result = new Result<>(CbPaySettleConvert.paramConvert(settleNotifyBo));
        } catch (Exception e) {
            log.error("call 跨境结汇结果查询异常:{}", e);
            result = ExceptionUtils.getResponse(e);
        }

        log.info("call 跨境结汇结果查询返回结果:{}", result);
        return result;
    }

    /**
     * 商户汇入申请商户redis锁住
     *
     * @param key redisKey
     * @return 返回是否锁住
     */

    private boolean lock(String key) {
        Boolean lockFlag = redisManager.lockRedis(key, FlagEnum.TRUE.getCode(), Constants.TIME_OUT);
        if (!lockFlag) {
            log.info("key：{},锁住失败，无法创建汇款订单，请稍后重试", key);
            return Boolean.FALSE;
        }
        log.info("key：{}锁定成功", key);
        return Boolean.TRUE;
    }

    /**
     * 判断商户是否锁住
     *
     * @param key 传输对象
     * @return 处理结果
     */
    private Boolean isLock(String key) {
        String value = redisManager.queryObjectByKey(key);
        if (FlagEnum.TRUE.getCode().equals(value)) {
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    /**
     * 运营后台上传结汇申请汇款凭证
     *
     * @param paymentFileUploadDto 汇款凭证信息对象
     * @param traceLogId           日志ID
     * @return 成功或失败
     */
    @Override
    public Result<Boolean> operationSettlePaymentUpload(SettlePaymentFileUploadDto paymentFileUploadDto, String traceLogId) {
        MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
        log.info("call 结汇汇款凭证上传传入对象:{}", paymentFileUploadDto);
        Result<Boolean> result;
        try {
            ParamValidate.validateParams(paymentFileUploadDto);
            //查询汇入申请信息
            FiCbPaySettleApplyDo fiCbPaySettleApplyDo = settleApplyMapper.queryByParams(paymentFileUploadDto.getMemberId(),
                    paymentFileUploadDto.getIncomeNo());
            if (fiCbPaySettleApplyDo == null) {
                log.warn("商户编号、商户流水号查汇入申请为空，参数信息：memberId:{},incomeNo:{}", paymentFileUploadDto.getMemberId(),
                        paymentFileUploadDto.getIncomeNo());
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00163);
            }
            if (fiCbPaySettleApplyDo.getPaymentFileId() != 0L && fiCbPaySettleApplyDo.getPaymentFileId() != null) {
                log.error("商户汇款凭证信息已存在，商户号：{}，商户流水号;{}", paymentFileUploadDto.getMemberId(),
                        paymentFileUploadDto.getIncomeNo());
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00222);
            }
            cbPaySettleBiz.uploadPaymentFile(CbPaySettleConvert.toSettlePaymentFileUploadBo(paymentFileUploadDto));
            result = new Result<>(true);
        } catch (Exception e) {
            log.error("call 线下上传结汇汇款凭证异常", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("call 运营后台上传结汇申请汇款凭证返回参数:{}", result);
        return result;
    }
}
