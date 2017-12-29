package com.baofu.cbpayservice.service.impl;

import com.baofu.cbpayservice.biz.CbPayCmBiz;
import com.baofu.cbpayservice.biz.CbPayOrderBiz;
import com.baofu.cbpayservice.biz.EmailSendService;
import com.baofu.cbpayservice.biz.ProxyCustomsBiz;
import com.baofu.cbpayservice.biz.impl.MqSendServiceImpl;
import com.baofu.cbpayservice.biz.integration.cm.CmClearBizImpl;
import com.baofu.cbpayservice.biz.integration.ma.MemberEmailQueryBizImpl;
import com.baofu.cbpayservice.biz.models.*;
import com.baofu.cbpayservice.common.constants.Constants;
import com.baofu.cbpayservice.common.enums.*;
import com.baofu.cbpayservice.dal.mapper.FiCbpayRemitConfigMapper;
import com.baofu.cbpayservice.dal.models.FiCbPayOrderSumDo;
import com.baofu.cbpayservice.dal.models.FiCbPaySettleBankDo;
import com.baofu.cbpayservice.dal.models.FiCbpayRemitConfigDo;
import com.baofu.cbpayservice.facade.CbPayOrderRemittanceFacade;
import com.baofu.cbpayservice.facade.models.CbPaySumFileDto;
import com.baofu.cbpayservice.facade.models.OrderRemittanceDto;
import com.baofu.cbpayservice.manager.CbPayOrderRemittanceManage;
import com.baofu.cbpayservice.manager.CbPaySettleBankManager;
import com.baofu.cbpayservice.manager.OrderIdManager;
import com.baofu.cbpayservice.manager.RedisManager;
import com.baofu.cbpayservice.service.convert.CbPayOrderRemittanceConvert;
import com.google.common.collect.Lists;
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
import java.util.ArrayList;

/**
 * 提现订单(非文件上传订单)
 * <p>
 * User: 不良人 Date:2017/5/10 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
@Service
public class CbPayOrderRemittanceFacadeImpl implements CbPayOrderRemittanceFacade {

    /**
     * 主键ID生成服务
     */
    @Autowired
    private OrderIdManager orderIdManager;

    /**
     * 代理报关服务
     */
    @Autowired
    private ProxyCustomsBiz proxyCustomsBiz;

    /**
     * 发送mq服务
     */
    @Autowired
    private MqSendServiceImpl mqSendService;

    /**
     * 缓存服务
     */
    @Autowired
    private RedisManager redisManager;

    /**
     * 跨境订单服务
     */
    @Autowired
    private CbPayOrderBiz cbPayOrderBiz;

    /**
     * 自动汇款配置信息服务
     */
    @Autowired
    FiCbpayRemitConfigMapper fiCbpayRemitConfigMapper;

    /**
     * 提现订单操作服务
     */
    @Autowired
    private CbPayOrderRemittanceManage cbPayOrderRemittanceManage;

    /**
     * 清算服务
     */
    @Autowired
    private CmClearBizImpl cmClearBizImpl;

    /**
     * 计费操作服务
     */
    @Autowired
    private CbPayCmBiz cbPayCmBiz;

    /**
     * 多币种账户信息数据服务接口
     */
    @Autowired
    private CbPaySettleBankManager cbPaySettleBankManager;

    /**
     * 邮件服务
     */
    @Autowired
    private EmailSendService emailSendService;

    /**
     * 商户邮箱查询服务
     */
    @Autowired
    private MemberEmailQueryBizImpl memberEmailQueryBiz;

    /**
     * 提现订单（汇款订单）文件上传
     *
     * @param orderRemittanceDto 请求参数
     * @return 批次ID
     */
    @Override
    public Result<Long> orderRemitFileUpload(OrderRemittanceDto orderRemittanceDto, String traceLogId) {

        Result<Long> result;
        MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
        log.info("call excel提现订单请求参数——》{}", orderRemittanceDto);

        try {
            ParamValidate.validateParams(orderRemittanceDto);

            // 判定商户是否锁住
            Boolean lockFlag = isLock(Constants.CBPAY_CREATE_SUM_FILE_KEY + orderRemittanceDto.getMemberId());
            log.info("call excel提现订单锁信息： 商户号：{}，是否锁住：{}", orderRemittanceDto.getMemberId(), lockFlag);
            if (lockFlag) {
                log.error("call excel提现订单锁信息： 商户{}已锁定，请勿频繁操作！", orderRemittanceDto.getMemberId());
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0081);
            }
            log.info("call excel提现订单锁信息：商户{}未锁住，创建汇款订单。", orderRemittanceDto.getMemberId());

            //文件批次插入
            Long fileBatchNo = orderIdManager.orderIdCreate();
            proxyCustomsBiz.insertFileUpload(CbPayOrderRemittanceConvert.toFiCbpayFileUploadBo(orderRemittanceDto, fileBatchNo));

            //发送Mq消息
            ProxyCustomsMqBo proxyCustomsMqBo = CbPayOrderRemittanceConvert.toProxyCustomsMqBo(orderRemittanceDto, fileBatchNo);
            mqSendService.sendMessage(MqSendQueueNameEnum.CBPAY_WITHDRAW_FILE_UPLOAD_QUEUE_NAME, proxyCustomsMqBo);
            log.info("call excel提现订单异步处理数据，生产者，队列名称：{},队列内容为：{}",
                    MqSendQueueNameEnum.CBPAY_WITHDRAW_FILE_UPLOAD_QUEUE_NAME, proxyCustomsMqBo);

            result = new Result<>(fileBatchNo);
        } catch (Exception e) {

            result = ExceptionUtils.getResponse(e);
            log.error("call excel提现订单失败，Exceprion ——>{}", e);
        }

        log.info("call excel提现订单返回结果——》{}", result);
        return result;
    }

    /**
     * 根据时间创建汇款订单
     *
     * @param cbPaySumFileDto 请求对象
     * @param traceLogId      日志ID
     * @return 处理结果
     */
    @Override
    public Result<Boolean> createRemittanceOrderByTime(CbPaySumFileDto cbPaySumFileDto, String traceLogId) {
        Result<Boolean> result;

        try {
            MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
            log.info("根据时间创建汇总文件信息：请求参数:{}", cbPaySumFileDto);

            //校验参数
            ParamValidate.validateParams(cbPaySumFileDto);

            // 判定商户是否锁住
            Boolean lockFlag = isLock(Constants.CBPAY_CREATE_SUM_FILE_KEY + cbPaySumFileDto.getMemberId());
            log.info("根据时间创建汇总文件信息： 商户号：{}，是否锁住：{}", cbPaySumFileDto.getMemberId(), lockFlag);
            if (lockFlag) {
                log.error("根据时间创建汇总文件信息： 商户{}已锁定，请勿频繁操作！", cbPaySumFileDto.getMemberId());
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0081);
            }
            log.info("根据时间创建汇总文件信息：商户{}未锁住，创建汇款订单。", cbPaySumFileDto.getMemberId());

            // 查询订单数量
            Long count = cbPayOrderBiz.queryCbPayOrderCount(cbPaySumFileDto.getBeginTime(), cbPaySumFileDto.getEndTime(),
                    cbPaySumFileDto.getMemberId());
            log.info("查询跨境订单数量：{}", count);
            if (count < 1) {
                log.error("未查询到订单信息");
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00134);
            }

            Long batchNo = orderIdManager.orderIdCreate();

            //文件批次插入
            proxyCustomsBiz.insertFileUpload(CbPayOrderRemittanceConvert.convertFileUpload(cbPaySumFileDto, batchNo));

            //发送Mq消息
            CbPaySumFileMqBo mqBo = CbPayOrderRemittanceConvert.toSumFileMqBo(cbPaySumFileDto, batchNo);
            mqSendService.sendMessage(MqSendQueueNameEnum.CBPAY_WITHTIME_FILE_UPLOAD_QUEUE_NAME, mqBo);
            log.info("call excel提现订单异步处理数据，生产者，队列名称：{},队列内容为：{}",
                    MqSendQueueNameEnum.CBPAY_WITHTIME_FILE_UPLOAD_QUEUE_NAME, mqBo);

            result = new Result<>(Boolean.TRUE);
        } catch (Exception e) {
            log.error("根据时间创建汇款订单：  创建汇款订单 Exception:", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("根据时间创建汇款订单：  创建汇款订单 RESULT:{}", result);
        return result;
    }

    /**
     * 根据时间生成汇款订单(增加了商户自动汇款配置)
     *
     * @param cbPaySumFileDto 请求对象
     * @param traceLogId      日志ID
     * @return 处理结果
     */
    @Override
    public Result<Boolean> autoRemittanceOrderByTime(CbPaySumFileDto cbPaySumFileDto, String traceLogId) {
        Result<Boolean> result;
        try {
            //获取商户自动汇款配置
            FiCbpayRemitConfigDo fiCbpayRemitConfigDo = fiCbpayRemitConfigMapper.selectByKey(cbPaySumFileDto.getMemberId());
            //1判断商户订单总金额是否带到配置金额
            if (fiCbpayRemitConfigDo == null) {
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00223);
            }
            //判断自动汇款配置是否开启 state 1-启用，2-关闭
            if (fiCbpayRemitConfigDo.getState() == 2) {
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00226);
            }
            //2判断商户订单总金额是否带到配置金额
            FiCbPayOrderSumDo fiCbPayOrderSumDo = cbPayOrderRemittanceManage
                    .queryOrderByTime(cbPaySumFileDto.getBeginTime(), cbPaySumFileDto.getEndTime(), cbPaySumFileDto.getMemberId());
            if (fiCbPayOrderSumDo == null) {
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00224);
            }
            if (fiCbpayRemitConfigDo.getRemitMinAmt().compareTo(fiCbPayOrderSumDo.getTransMoney()) > 0) {
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00225);
            }
            //查询币种账户
            FiCbPaySettleBankDo fiCbPaySettleBankDo = cbPaySettleBankManager.selectByRecordId(fiCbpayRemitConfigDo.getRemitAccId());
            if (fiCbPaySettleBankDo == null || BankSettleStatus.NO_SETTLE.getCode() == fiCbPaySettleBankDo.getSettleStatus()) {
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00131);
            }
            //判断账户余额是否满足此次汇款
            QueryBalanceBo accountBalanceReq = new QueryBalanceBo(cbPaySumFileDto.getMemberId().intValue(), 1);
            BigDecimal accountMoney = cmClearBizImpl.queryBalance(accountBalanceReq);
            // 查询手续费
            Long orderId = orderIdManager.orderIdCreate();
            int functionId = FunctionEnum.getFunctionId(RemittanceOrderType.PROXY_REMITTANCE_ORDER.getCode(), fiCbPaySettleBankDo.getSettleCcy());
            int productId = FunctionEnum.getProductId(RemittanceOrderType.PROXY_REMITTANCE_ORDER.getCode());
            MemberFeeResBo feeResult = cbPayCmBiz.getFeeResult(String.valueOf(orderId), fiCbPayOrderSumDo.getTransMoney(),
                    cbPaySumFileDto.getMemberId(), functionId, productId, traceLogId);
            MemberEmailBo memberEmailBo = memberEmailQueryBiz.findBusinessEmail(cbPaySumFileDto.getMemberId());
            if (fiCbPayOrderSumDo.getTransMoney().add(feeResult.getFeeAmount()).compareTo(accountMoney) > 0) {
                emailSendService.sendEmailHtml(Constants.autoRemitEmail, Lists.newArrayList(memberEmailBo.getBusinessEmail()),
                        null, null, null,
                        "自动汇款申请失败---宝付网络科技（上海）有限公司",
                        null, null, null, "宝付网络科技（上海）有限公司");
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0065);
            }

            return createRemittanceOrderByTime(cbPaySumFileDto, traceLogId);
        } catch (Exception e) {
            log.error("根据时间创建汇款批次：  创建汇款批次 Exception:", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("根据时间创建汇款批次：  创建汇款批次 RESULT:{}", result);
        return result;
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
}
