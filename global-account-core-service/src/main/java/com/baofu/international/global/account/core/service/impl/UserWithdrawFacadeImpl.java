package com.baofu.international.global.account.core.service.impl;

import com.baofu.global.cross.border.logback.ex.constant.MDCPropertyConsts;
import com.baofu.international.global.account.core.biz.LockBiz;
import com.baofu.international.global.account.core.biz.MqSendService;
import com.baofu.international.global.account.core.biz.UserBalBiz;
import com.baofu.international.global.account.core.biz.UserWithdrawBiz;
import com.baofu.international.global.account.core.biz.models.AccBalanceBo;
import com.baofu.international.global.account.core.biz.models.UserWithdrawCashBo;
import com.baofu.international.global.account.core.biz.models.UserWithdrawFeeReqBo;
import com.baofu.international.global.account.core.biz.models.WithdrawAccountRespBo;
import com.baofu.international.global.account.core.common.constant.Constants;
import com.baofu.international.global.account.core.common.enums.MqSendQueueNameEnum;
import com.baofu.international.global.account.core.common.util.BeanCopyUtils;
import com.baofu.international.global.account.core.facade.UserWithdrawFacade;
import com.baofu.international.global.account.core.facade.model.*;
import com.baofu.international.global.account.core.facade.model.res.WithdrawAccountRespDto;
import com.baofu.international.global.account.core.facade.model.res.WithdrawCashFeeRespDto;
import com.google.common.collect.Lists;
import com.system.commons.result.Result;
import com.system.commons.utils.ExceptionUtils;
import com.system.commons.utils.JsonUtil;
import com.system.commons.utils.ParamValidate;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 功能：用户自主注册平台提现
 * User: feng_jiang Date:2017/11/5 ProjectName: globalaccount-core Version: 1.0
 **/
@Slf4j
@Service
public class UserWithdrawFacadeImpl implements UserWithdrawFacade {

    /**
     * redis锁服务
     */
    @Autowired
    private LockBiz lockBiz;

    /**
     * 用户前台提现服务
     */
    @Autowired
    private UserWithdrawBiz userWithdrawBiz;

    /**
     * 用户余额服务
     */
    @Autowired
    private UserBalBiz userBalBiz;

    /**
     * 发送mq服务
     */
    @Autowired
    private MqSendService mqSendService;

    /**
     * 功能：用户前台提现
     *
     * @param userWithdrawDto 用户提现请求参数
     * @return 提现订单
     */
    @Override
    public Result<Boolean> userWithdrawCash(UserWithdrawDto userWithdrawDto, String traceLogId) {

        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
        log.info("call 用户前台提现,请求参数:{}", userWithdrawDto);
        Result<Boolean> result;
        String key = "";
        try {
            ParamValidate.validateParams(userWithdrawDto);
            UserWithdrawCashBo userWithdrawCashBo = new UserWithdrawCashBo();
            BeanUtils.copyProperties(userWithdrawDto, userWithdrawCashBo);
            List<UserWithdrawDetailDto> list = userWithdrawDto.getUserWithdrawDetailDtoList();
            List<UserWithdrawCashBo> userWithdrawList = Lists.newArrayList();
            for (UserWithdrawDetailDto withdrawDetailDto : list) {
                ParamValidate.validateParams(withdrawDetailDto);
                BeanUtils.copyProperties(withdrawDetailDto, userWithdrawCashBo);
                //锁用户
                key = lockBiz.lock(Constants.GLOBAL_ACCOUNT_WITHDRAW_CASH + userWithdrawDto.getUserNo() + ":" + withdrawDetailDto.getWithdrawCcy()
                        + ":" + withdrawDetailDto.getAccountNo());
                userWithdrawList.add(userWithdrawCashBo);
            }
            for (UserWithdrawCashBo withdrawCashBo : userWithdrawList) {
                //调用提现申请MQ信息
                mqSendService.sendMessage(MqSendQueueNameEnum.GLOBAL_USER_WITHDRAW_APPLY_QUEUE_NAME, JsonUtil.toJSONString(withdrawCashBo));
                log.info("call 用户提现MQ，生产者、队列名：{},内容：{}", MqSendQueueNameEnum.GLOBAL_USER_WITHDRAW_APPLY_QUEUE_NAME, withdrawCashBo);
            }
            result = new Result<>(Boolean.TRUE);
        } catch (Exception e) {
            lockBiz.unLock(key);
            log.error("call 用户前台提现异常 {}", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("call 用户前台提现，返回提现订单信息:{}", result);
        return result;
    }

    /**
     * 功能：计算用户提现手续费
     *
     * @param userWithdrawFeeDto 提现金额
     * @param traceLogId         日志ID
     * @return 手续费金额
     */
    @Override
    public Result<List<WithdrawCashFeeRespDto>> withdrawCashFee(UserWithdrawFeeDto userWithdrawFeeDto, String traceLogId) {

        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
        log.info("call 计算用户提现手续费,请求参数:{}", userWithdrawFeeDto);
        Result<List<WithdrawCashFeeRespDto>> result;
        try {
            ParamValidate.validateParams(userWithdrawFeeDto);
            List<WithdrawCashFeeRespDto> retList = Lists.newArrayList();
            UserWithdrawFeeReqBo userWithdrawFeeReqBo = new UserWithdrawFeeReqBo();
            for (UserWithdrawFeeDetailDto dto : userWithdrawFeeDto.getUserWithdrawFeeDetailDtoList()) {
                ParamValidate.validateParams(dto);
                userWithdrawFeeReqBo.setUserNo(userWithdrawFeeDto.getUserNo());
                BeanUtils.copyProperties(dto, userWithdrawFeeReqBo);
                //查询用户提现手续费
                retList.add(BeanCopyUtils.objectConvert(userWithdrawBiz.withdrawCashFee(userWithdrawFeeReqBo), WithdrawCashFeeRespDto.class));
            }
            result = new Result<>(retList);
        } catch (Exception e) {
            log.error("call 计算用户提现手续费异常 {}", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("call 计算用户提现手续费,返回参数:{}", result);
        return result;
    }

    /**
     * 功能：查询提现店铺账户余额信息
     *
     * @param userNo     用户号
     * @param traceLogId 日志ID
     * @return 可提现店铺账户信息
     */
    @Override
    public Result<List<WithdrawAccountRespDto>> queryWithdrawAccountInfo(Long userNo, String traceLogId) {

        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
        log.info("call 查询提现店铺账户,请求参数:{}", userNo);
        Result<List<WithdrawAccountRespDto>> result;
        try {
            //查询提现店铺账户余额信息
            List<WithdrawAccountRespBo> list = userWithdrawBiz.queryWithdrawAccountInfo(userNo);
            List<WithdrawAccountRespDto> accountRespDtoArrayList = Lists.newArrayList();
            for (WithdrawAccountRespBo bo : list) {
                WithdrawAccountRespDto withdrawAccountRespDto = new WithdrawAccountRespDto();
                BeanUtils.copyProperties(bo, withdrawAccountRespDto);
                accountRespDtoArrayList.add(withdrawAccountRespDto);
            }
            result = new Result<>(accountRespDtoArrayList);
        } catch (Exception e) {
            log.error("call 查询提现店铺账户异常 {}", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("call 查询提现店铺账户,返回参数:{}", result);
        return result;
    }

    /**
     * 功能：查询账户币种账户余额，人民币余额，待入账金额
     *
     * @param userNo     用户号
     * @param ccy        币种
     * @param traceLogId 日志IID
     * @return AccBalanceDto 币种账户信息
     */
    @Override
    public Result<AccBalanceDto> queryCcyBalance(Long userNo, String ccy, String traceLogId) {
        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
        log.info("call 查询币种账户余额,请求参数:{},{}", userNo, ccy);
        Result<AccBalanceDto> result;
        try {
            //查询账户余额
            AccBalanceBo accBalanceBo = userBalBiz.queryCcyBalance(userNo, ccy);
            AccBalanceDto accBalanceDto = new AccBalanceDto();
            BeanUtils.copyProperties(accBalanceBo, accBalanceDto);
            result = new Result<>(accBalanceDto);
        } catch (Exception e) {
            log.error("call 查询币种账户余额异常 {}", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("call 查询币种账户余额,返回参数:{}", result);
        return result;
    }
}
