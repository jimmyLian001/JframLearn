package com.baofu.international.global.account.core.biz.impl;

import com.baofoo.cbcgw.facade.dict.BaseResultEnum;
import com.baofoo.cbcgw.facade.dto.gw.response.CgwTransferRespDto;
import com.baofoo.cbcgw.facade.dto.gw.response.result.CgwExchangeRateResultDto;
import com.baofoo.cbcgw.facade.dto.gw.response.result.CgwTransferResultDto;
import com.baofu.international.global.account.core.biz.*;
import com.baofu.international.global.account.core.biz.models.UserAccountBalBo;
import com.baofu.international.global.account.core.biz.models.UserSettleAppReqBo;
import com.baofu.international.global.account.core.common.constant.ConfigDict;
import com.baofu.international.global.account.core.common.constant.Constants;
import com.baofu.international.global.account.core.common.constant.NumberDict;
import com.baofu.international.global.account.core.common.enums.*;
import com.baofu.international.global.account.core.dal.mapper.ExchangeRateMapper;
import com.baofu.international.global.account.core.dal.model.ExchangeRateDo;
import com.baofu.international.global.account.core.dal.model.UserAccountBalDo;
import com.baofu.international.global.account.core.dal.model.UserWithdrawApplyDo;
import com.baofu.international.global.account.core.dal.model.UserWithdrawFileUploadDo;
import com.baofu.international.global.account.core.manager.PayeeTransferDepositManager;
import com.baofu.international.global.account.core.manager.UserAccountBalManager;
import com.baofu.international.global.account.core.manager.UserWithdrawCashManager;
import com.baofu.international.global.account.core.manager.UserWithdrawRelationManager;
import com.system.commons.exception.BizServiceException;
import com.system.commons.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 功能：用户提现处理渠道通知服务
 * User: feng_jiang Date:2017/11/5 ProjectName: globalaccount-core Version: 1.0
 **/
@Slf4j
@Service
public class UserWithdrawCgwDealBizImpl implements UserWithdrawCgwDealBiz {

    /**
     * 用户提现服务
     */
    @Autowired
    private UserWithdrawCashManager userWithdrawCashManager;

    /**
     * 发送mq服务
     */
    @Autowired
    private MqSendServiceImpl mqSendService;

    /**
     * 请求锁服务
     */
    @Autowired
    private LockBiz lockBiz;

    /**
     * 宝付转账至备付金Manager
     */
    @Autowired
    private PayeeTransferDepositManager payeeTransferDepositManager;

    /**
     * 转账关系管理
     */
    @Autowired
    private UserWithdrawRelationManager userWithdrawRelationManager;

    /**
     * 计费汇率操作
     */
    @Autowired
    private ExchangeRateMapper exchangeRateMapper;

    /**
     * 渠道汇率服务
     */
    @Autowired
    private ChannelRateQueryBiz channelRateQueryBiz;

    /**
     * 配置中心
     */
    @Autowired
    private ConfigDict configDict;

    /**
     * 用户余额相关服务
     */
    @Autowired
    private UserBalBiz userBalBiz;

    /**
     * 用户账户相关
     */
    @Autowired
    private UserAccountBalManager userAccountBalManager;

    /**
     * 功能：用户自主注册平台提现订单操作
     */
    @Autowired
    private UserWithdrawOrderBiz userWithdrawOrderBiz;

    /**
     * 功能：用户转账渠道第一次异步通知处理
     *
     * @param transferRespDto 渠道通知对象
     */
    @Override
    public void userWithdrawOneProcess(CgwTransferRespDto transferRespDto) {

        log.info("call 用户转账渠道第一次异步通知返回结果：{}", transferRespDto);
        //查询提现订单信息
        UserWithdrawApplyDo userWithdrawApplyDo = userWithdrawCashManager.selectTransferDetailByOrder(transferRespDto.getBfBatchId());
        try {
            if (BaseResultEnum.FAIL.getCode() == transferRespDto.getCode()) {
                //失败
                log.info("call 用户转账渠道第一次通知失败：{}", transferRespDto.getMessage());
                this.failProcess(transferRespDto);
                lockBiz.unLock(Constants.GLOBAL_ACCOUNT_WITHDRAW_CASH + userWithdrawApplyDo.getUserNo()
                        + ":" + userWithdrawApplyDo.getWithdrawCcy() + ":" + userWithdrawApplyDo.getAccountNo());
            } else {
                //更新提现中金额+、可提现余额-
                UserAccountBalBo userAccountBalBo = userBalBiz.queryUserAccountBal(userWithdrawApplyDo.getUserNo(), userWithdrawApplyDo.getAccountNo());
                //可提现余额-
                userAccountBalBo.setAvailableAmt((userAccountBalBo.getAvailableAmt().subtract(userWithdrawApplyDo.getWithdrawAmt())).
                        setScale(NumberDict.TWO, BigDecimal.ROUND_DOWN));
                //提现中金额+
                userAccountBalBo.setWithdrawProcessAmt((userAccountBalBo.getWithdrawProcessAmt().add(userWithdrawApplyDo.getWithdrawAmt())).
                        setScale(NumberDict.TWO, BigDecimal.ROUND_DOWN));
                UserAccountBalDo userAccountBalDo = new UserAccountBalDo();
                BeanUtils.copyProperties(userAccountBalBo, userAccountBalDo);
                userAccountBalManager.modifyAccountBal(userAccountBalDo);
            }
        } catch (Exception e) {
            lockBiz.unLock(Constants.GLOBAL_ACCOUNT_WITHDRAW_CASH + userWithdrawApplyDo.getUserNo()
                    + ":" + userWithdrawApplyDo.getWithdrawCcy() + ":" + userWithdrawApplyDo.getAccountNo());
            log.info("call wyre 转账渠道第一次通知处理异常。", e);
        }

    }

    /**
     * 功能：提现渠道第一次处理通知-失败
     *
     * @param transferRespDto 渠道通知对象
     */
    private void failProcess(CgwTransferRespDto transferRespDto) {

        log.info("call 用户转账渠道异步第一次通知,订单号:{}", transferRespDto.getBfBatchId());
        //更新文件状态为取消
        UserWithdrawFileUploadDo userWithdrawFileUploadDo = new UserWithdrawFileUploadDo();
        userWithdrawFileUploadDo.setBatchNo(String.valueOf(transferRespDto.getBfBatchId()));
        userWithdrawFileUploadDo.setStatus(UploadFileStateEnum.CANCEL.getCode());
        userWithdrawCashManager.updateFileByBatch(userWithdrawFileUploadDo);

        //更新提现订单状态
        UserWithdrawApplyDo userWithdrawApplyDo = new UserWithdrawApplyDo();
        userWithdrawApplyDo.setWithdrawId(transferRespDto.getBfBatchId());
        userWithdrawApplyDo.setTransferState(TransferStateEnum.TRANSFER_ACCOUNTS_FAIL.getCode());
        userWithdrawApplyDo.setRemarks(transferRespDto.getMessage());
        userWithdrawCashManager.updateTUserWithdrawApply(userWithdrawApplyDo);

        //根据提现批次号更新外部订单为可用
        userWithdrawOrderBiz.updateExternalOrder(transferRespDto.getBfBatchId());
    }

    /**
     * 功能：提现失败更新余额
     *
     * @param withdrawApplyDo 提现订单信息
     */
    private void modifyAccountBal(UserWithdrawApplyDo withdrawApplyDo) {
        UserAccountBalBo userAccountBalBo = userBalBiz.queryUserAccountBal(withdrawApplyDo.getUserNo(), withdrawApplyDo.getAccountNo());
        //可提现余额+
        userAccountBalBo.setAvailableAmt((userAccountBalBo.getAvailableAmt().add(withdrawApplyDo.getWithdrawAmt())).
                setScale(NumberDict.TWO, BigDecimal.ROUND_DOWN));
        //提现中金额-
        userAccountBalBo.setWithdrawProcessAmt((userAccountBalBo.getWithdrawProcessAmt().subtract(withdrawApplyDo.getWithdrawAmt())).
                setScale(NumberDict.TWO, BigDecimal.ROUND_DOWN));
        UserAccountBalDo userAccountBalDo = new UserAccountBalDo();
        BeanUtils.copyProperties(userAccountBalBo, userAccountBalDo);
        userAccountBalManager.modifyAccountBal(userAccountBalDo);
    }

    /**
     * 功能：用户转账渠异步通知处理
     *
     * @param cgwTransferResultDto 渠道通知对象
     */
    @Override
    public void userWithdrawTwoProcess(CgwTransferResultDto cgwTransferResultDto) {

        log.info("call 用户转账渠道第二次异步通知返回结果：{}", cgwTransferResultDto);
        //查询提现订单信息
        UserWithdrawApplyDo userWithdrawApplyDo = userWithdrawCashManager.
                selectTransferDetailByOrder(Long.valueOf(cgwTransferResultDto.getBfBatchId()));
        try {
            if (BaseResultEnum.SUCCESS.getCode() == cgwTransferResultDto.getCode()) {
                //成功，此步成功后才订单入库
                this.successProcess(cgwTransferResultDto, userWithdrawApplyDo);
            } else if (BaseResultEnum.FAIL.getCode() == cgwTransferResultDto.getCode()) {
                //失败
                this.failProcess(cgwTransferResultDto, userWithdrawApplyDo);
            } else {
                log.info("call 用户转账渠道异步第二次通知,渠道通知状态不正确：{}");
                throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190018);
            }
            lockBiz.unLock(Constants.GLOBAL_ACCOUNT_WITHDRAW_CASH + userWithdrawApplyDo.getUserNo() + ":" + userWithdrawApplyDo.getWithdrawCcy()
                    + ":" + userWithdrawApplyDo.getAccountNo());
        } catch (Exception e) {
            log.info("call wyre 转账渠道第二次通知处理异常。", e);
            lockBiz.unLock(Constants.GLOBAL_ACCOUNT_WITHDRAW_CASH + userWithdrawApplyDo.getUserNo() + ":" + userWithdrawApplyDo.getWithdrawCcy()
                    + ":" + userWithdrawApplyDo.getAccountNo());
        }

    }

    /**
     * 功能：提现渠道处理-成功
     *
     * @param cgwTransferResultDto 渠道通知对象
     * @param withdrawApplyDo      提现订单信息
     */
    private void successProcess(CgwTransferResultDto cgwTransferResultDto, UserWithdrawApplyDo withdrawApplyDo) {

        log.info("call 用户转账渠道异步第二次通知,订单号:{}提现渠道处理-成功", cgwTransferResultDto.getBfBatchId());
        Long orderId = Long.valueOf(cgwTransferResultDto.getBfBatchId());
        //更新提现订单信息
        UserWithdrawApplyDo userWithdrawApplyDo = new UserWithdrawApplyDo();
        userWithdrawApplyDo.setWithdrawId(orderId);
        userWithdrawApplyDo.setTransferState(TransferStateEnum.TRANSFER_ACCOUNTS_SUCCESS.getCode());
        userWithdrawCashManager.updateTUserWithdrawApply(userWithdrawApplyDo);
        //更新余额-
        UserAccountBalBo userAccountBalBo = userBalBiz.queryUserAccountBal(withdrawApplyDo.getUserNo(), withdrawApplyDo.getAccountNo());
        userAccountBalBo.setAccountBal((userAccountBalBo.getAccountBal().subtract(withdrawApplyDo.getWithdrawAmt())).
                setScale(NumberDict.TWO, BigDecimal.ROUND_DOWN));
        UserAccountBalDo userAccountBalDo = new UserAccountBalDo();
        BeanUtils.copyProperties(userAccountBalBo, userAccountBalDo);
        userAccountBalManager.modifyAccountBal(userAccountBalDo);
    }

    /**
     * 功能：提现渠道处理-失败
     *
     * @param cgwTransferResultDto 渠道通知对象
     * @param userWithdrawApplyDo  用户提现对象
     */
    private void failProcess(CgwTransferResultDto cgwTransferResultDto, UserWithdrawApplyDo userWithdrawApplyDo) {

        log.info("call 用户转账渠道异步第二次通知,订单号:{}提现渠道处理-失败：{}", cgwTransferResultDto.getBfBatchId(),
                cgwTransferResultDto.getMessage());
        //更新提现订单状态
        Long withdrawId = Long.valueOf(cgwTransferResultDto.getBfBatchId());
        UserWithdrawApplyDo tUserWithdrawApplyDo = new UserWithdrawApplyDo();
        tUserWithdrawApplyDo.setWithdrawId(withdrawId);
        tUserWithdrawApplyDo.setTransferState(TransferStateEnum.TRANSFER_ACCOUNTS_FAIL.getCode());
        userWithdrawCashManager.updateTUserWithdrawApply(tUserWithdrawApplyDo);

        //更新文件状态为取消
        UserWithdrawFileUploadDo fileUploadDo = new UserWithdrawFileUploadDo();
        fileUploadDo.setBatchNo(cgwTransferResultDto.getBfBatchId());
        fileUploadDo.setStatus(UploadFileStateEnum.CANCEL.getCode());
        userWithdrawCashManager.updateFileByBatch(fileUploadDo);

        //更新可提现金额+、提现中金额-
        this.modifyAccountBal(userWithdrawApplyDo);

        //根据提现批次号更新外部订单为可用
        userWithdrawOrderBiz.updateExternalOrder(withdrawId);
    }

    /**
     * 功能：第一次异步通知
     *
     * @param transferRespDto 参数
     */
    @Override
    @Transactional
    public void userWithdrawMergeOneProcess(CgwTransferRespDto transferRespDto) {
        log.info("宝付转账至备付金一次异步通知，渠道返回结果：{}", transferRespDto);
        Integer state;
        if (transferRespDto.getCode() == BaseResultEnum.FAIL.getCode()) {
            state = TransferStateEnum.TRANSFER_ACCOUNTS_FAIL.getCode();
        } else if (transferRespDto.getCode() == BaseResultEnum.DISPOSE.getCode()) {
            state = TransferStateEnum.TRANSFER_ACCOUNTS_PROCESS.getCode();
        } else {
            log.info("宝付转账至备付金一次异步通知,渠道返回状态错误,状态：{}", transferRespDto.getCode());
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190018);
        }
        this.disposeOne(transferRespDto.getBfBatchId(), state, transferRespDto.getMessage());
    }

    /**
     * 功能：第二次异步通知
     *
     * @param cgwTransferResultDto 参数
     */
    @Override
    public void userWithdrawMergeTwoProcess(CgwTransferResultDto cgwTransferResultDto) {
        log.info("宝付转账至备付金第二次异步通知，渠道返回结果：{}", cgwTransferResultDto);
        Integer state;
        if (cgwTransferResultDto.getCode() == BaseResultEnum.FAIL.getCode()) {
            state = TransferStateEnum.TRANSFER_ACCOUNTS_FAIL.getCode();
        } else if (cgwTransferResultDto.getCode() == BaseResultEnum.SUCCESS.getCode()) {
            state = TransferStateEnum.TRANSFER_ACCOUNTS_SUCCESS.getCode();
        } else {
            log.info("宝付转账至备付金二次异步通知,渠道返回状态错误,状态：{}", cgwTransferResultDto.getCode());
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190018);
        }
        this.disposeTwo(cgwTransferResultDto.getBfBatchId(), state, cgwTransferResultDto.getMessage());
    }


    /**
     * 功能：第一次异步通知处理订单
     *
     * @param withdrawBatchId 汇总信息的平台流水号
     * @param state           状态
     * @param message         备注
     */
    private void disposeOne(Long withdrawBatchId, Integer state, String message) {

        //根据汇总批次号修改汇总信息的转账状态
        payeeTransferDepositManager.updateWithdrawSumState(withdrawBatchId, state, message);
        log.info("[1]根据汇总批次号:{},修改汇总信息的转账状态为:{},备注：{}", withdrawBatchId, state, message);
        //根据批次号获取明细编号
        List<Long> batchNo = userWithdrawRelationManager.queryWithdrawIdByBatchNo(withdrawBatchId);
        log.info("[1]根据汇总批次号:{},获取用户提现申请明细条数：{}", withdrawBatchId, batchNo.size());
        if (CollectionUtils.isEmpty(batchNo)) {
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190020);
        }
        //根据平台流水号批量修改用户提现申请的提现状态
        payeeTransferDepositManager.updateUserWithdrawApplyState(batchNo, state);
        log.info("[1]批量修改用户提现申请的提现状态为：{},影响条数：{}", state, batchNo.size());
    }

    /**
     * 功能：第二次异步通知处理订单
     *
     * @param bfBatchId 汇总信息的平台流水号
     * @param state     状态
     * @param message   备注
     */
    private void disposeTwo(String bfBatchId, Integer state, String message) {

        Long withdrawBatchId = Long.valueOf(bfBatchId);
        //根据汇总批次号修改汇总信息的转账状态
        payeeTransferDepositManager.updateWithdrawSumState(withdrawBatchId, state, message);
        log.info("[2]根据汇总批次号:{},修改汇总信息的转账状态为:{},备注：{}", withdrawBatchId, state, message);
        //根据批次号获取明细编号
        List<Long> batchNo = userWithdrawRelationManager.queryWithdrawIdByBatchNo(withdrawBatchId);
        log.info("[2]根据汇总批次号:{},获取用户提现申请明细条数：{}", withdrawBatchId, batchNo.size());
        if (CollectionUtils.isEmpty(batchNo)) {
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190020);
        }
        //转账成功
        if (TransferStateEnum.TRANSFER_ACCOUNTS_SUCCESS.getCode().compareTo(state) == 0) {
            state = WithdrawStateEnum.WITHDRAW_PROCESS.getCode();
            UserSettleAppReqBo appReqBo = new UserSettleAppReqBo();
            appReqBo.setWithdrawBatchId(withdrawBatchId);
            mqSendService.sendMessage(MqSendQueueNameEnum.GLOBAL_USER_SETTLE_APPLY_QUEUE_NAME, JsonUtil.toJSONString(appReqBo));
            log.info("call 用户发起汇入汇款申请，生产者、队列名：{},内容：{}", MqSendQueueNameEnum.GLOBAL_USER_SETTLE_APPLY_QUEUE_NAME, appReqBo);
        }
        //根据平台流水号批量修改用户提现申请的提现状态
        payeeTransferDepositManager.updateUserWithdrawApplyState(batchNo, state);
        log.info("[2]批量修改用户提现申请的提现状态为：{},影响条数：{}", state, batchNo.size());
    }

    /**
     * 功能：定时获取中行汇率
     */
    @Override
    public void loadCgwBocRate() {
        Long channelId = Long.valueOf(configDict.getBocSettleChannelId());
        ExchangeRateDo exchangeRateDo = new ExchangeRateDo();
        exchangeRateDo.setCcy("USD");
        exchangeRateDo.setStatus(NumberDict.ONE);
        exchangeRateDo.setChannelId(channelId);
        exchangeRateDo.setCreateAt(new Date());
        ExchangeRateDo rateDo = exchangeRateMapper.selectRateInfo(exchangeRateDo);
        CgwExchangeRateResultDto exchangeRate = channelRateQueryBiz.getExchangeRate(channelId, "USD");
        exchangeRateDo.setBidRateOfCcy(exchangeRate.getBuyRateOfCcy());
        if (rateDo != null) {
            exchangeRateMapper.updateExchangeRate(exchangeRateDo);
        } else {
            exchangeRateMapper.insert(exchangeRateDo);
        }
    }
}
