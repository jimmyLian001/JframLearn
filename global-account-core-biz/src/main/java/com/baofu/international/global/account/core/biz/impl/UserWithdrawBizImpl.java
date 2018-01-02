package com.baofu.international.global.account.core.biz.impl;

import com.baofoo.cbcgw.facade.dto.gw.request.CgwTransferReqDto;
import com.baofu.global.cross.border.logback.ex.constant.MDCPropertyConsts;
import com.baofu.international.global.account.core.biz.MqSendService;
import com.baofu.international.global.account.core.biz.UserBalBiz;
import com.baofu.international.global.account.core.biz.UserPayeeAccountBiz;
import com.baofu.international.global.account.core.biz.UserWithdrawBiz;
import com.baofu.international.global.account.core.biz.convert.UserWithdrawBizConvert;
import com.baofu.international.global.account.core.biz.external.BankWithdrawBizImpl;
import com.baofu.international.global.account.core.biz.external.UserAccountBizImpl;
import com.baofu.international.global.account.core.biz.models.*;
import com.baofu.international.global.account.core.common.constant.ConfigDict;
import com.baofu.international.global.account.core.common.constant.Constants;
import com.baofu.international.global.account.core.common.constant.NumberDict;
import com.baofu.international.global.account.core.common.enums.*;
import com.baofu.international.global.account.core.common.util.BeanCopyUtils;
import com.baofu.international.global.account.core.common.util.SecurityUtil;
import com.baofu.international.global.account.core.common.util.WithdrawFeeUtil;
import com.baofu.international.global.account.core.dal.mapper.ExchangeRateMapper;
import com.baofu.international.global.account.core.dal.mapper.UserWithdrawDistributeMapper;
import com.baofu.international.global.account.core.dal.mapper.UserWithdrawRateMapper;
import com.baofu.international.global.account.core.dal.model.*;
import com.baofu.international.global.account.core.manager.*;
import com.baofu.international.global.agent.core.facade.AgentUserRelationFacade;
import com.baofu.international.global.agent.core.facade.model.AgentShareReqDto;
import com.google.common.collect.Lists;
import com.system.commons.exception.BizServiceException;
import com.system.commons.result.Result;
import com.system.commons.utils.DateUtil;
import com.system.commons.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 功能：用户自主注册平台提现
 *
 * @author feng_jiang Date:2017/11/5 ProjectName: globalaccount-core Version: 1.0
 **/
@Slf4j
@Service
public class UserWithdrawBizImpl implements UserWithdrawBiz {

    /**
     * 用户前台提现服务
     */
    @Autowired
    private UserWithdrawCashManager userWithdrawCashManager;

    /**
     * 订单主键生成服务
     */
    @Autowired
    private OrderIdManager orderIdManager;

    /**
     * 发送mq服务
     */
    @Autowired
    private MqSendService mqSendService;

    /**
     * 宝付转账至备付金Manager
     */
    @Autowired
    private PayeeTransferDepositManager payeeTransferDepositManager;

    /**
     * 配置中心
     */
    @Autowired
    private ConfigDict configDict;

    /**
     * 用户银行卡manager
     */
    @Autowired
    private UserBankCardManager userBankCardManager;

    /**
     * 用户提前申请表操作类
     */
    @Autowired
    private UserWithdrawApplyManager withdrawApplyManager;

    /**
     * 代付API统一服务
     */
    @Autowired
    private BankWithdrawBizImpl bankWithdrawBiz;

    /**
     * 转账关系管理
     */
    @Autowired
    private UserWithdrawRelationManager userWithdrawRelationManager;

    /**
     * 用户账户服务
     */
    @Autowired
    private UserAccountBizImpl userAccountBiz;

    /**
     * 用户余额服务
     */
    @Autowired
    private UserBalBiz userBalBiz;

    /**
     * 用户自己分发表操作类
     */
    @Autowired
    private UserWithdrawDistributeMapper userWithdrawDistributeMapper;

    /**
     * 用户信息表操作类
     */
    @Autowired
    private UserInfoManager userInfoManager;

    /**
     * 用户信息服务
     */
    @Autowired
    private ApplyAccountManager applyAccountManager;

    /**
     * 用户收款账户相关Biz接口
     */
    @Autowired
    private UserPayeeAccountBiz userPayeeAccountBiz;

    /**
     * 用户收款账户相关Biz接口
     */
    @Autowired
    private UserStoreManager userStoreManager;

    /**
     * 计费汇率操作
     */
    @Autowired
    private ExchangeRateMapper exchangeRateMapper;

    /**
     * 用户设置费率
     */
    @Autowired
    private UserWithdrawRateMapper userWithdrawRateMapper;

    /**
     * 企业用户Manager服务
     */
    @Autowired
    private UserOrgManager userOrgManager;

    /**
     * 个人用户Manager服务
     */
    @Autowired
    private UserPersonalManager userPersonalManager;

    /**
     * 代理商用户关系对外接口
     */
    @Autowired
    private AgentUserRelationFacade agentUserRelationFacade;

    /**
     * 渠道路由
     */
    @Autowired
    private ChannelRouteBizImpl channelRouteBiz;

    /**
     * 功能：用户提现
     *
     * @param userWithdrawCashBo 提现参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void userWithdrawCash(UserWithdrawCashBo userWithdrawCashBo) {
        //校验用户账户信息是否在
        UserPayeeAccountDo userPayeeAccountDo = userAccountBiz.queryUserAccountInfo(userWithdrawCashBo.getUserNo(),
                userWithdrawCashBo.getAccountNo());
        userWithdrawCashBo.setChannelId(userPayeeAccountDo.getChannelId());
        //查询银行卡信息
        UserBankCardInfoDo userBankCardInfoDo = userBankCardManager.selectUserBankCardByUserNo(userWithdrawCashBo.getUserNo(),
                userWithdrawCashBo.getBankCardRecordNo());
        userWithdrawCashBo.setBankCardHolder(userBankCardInfoDo.getCardHolder());
        userWithdrawCashBo.setBankCardNo(SecurityUtil.desEncrypt(userBankCardInfoDo.getCardNo(), Constants.CARD_DES_KEY));
        userWithdrawCashBo.setBankName(userBankCardInfoDo.getBankName());
        //计算用户手续费&校验余额是否足够
        this.calcWithdrawFee(userWithdrawCashBo);
        //提现订单批次号
        Long orderId = orderIdManager.orderIdCreate();
        //获取创建提现订单对象
        BatchWithdrawBo batchWithdrawBo = this.getWithdrawPayeeInfo(userWithdrawCashBo);
        batchWithdrawBo.setBatchNo(orderId);
        //调用创建订单MQ信息
        mqSendService.sendMessage(MqSendQueueNameEnum.GLOBAL_USER_CREATE_ORDER_QUEUE_NAME, JsonUtil.toJSONString(batchWithdrawBo));
        log.info("call 用户提现创建订单MQ，生产者、队列名：{},内容：{}",
                MqSendQueueNameEnum.GLOBAL_USER_CREATE_ORDER_QUEUE_NAME, batchWithdrawBo);
        //新增提现订单信息
        this.saveUserWithdrawApplyInfo(userWithdrawCashBo, orderId);
    }

    /**
     * 功能：发送渠道转账申请
     *
     * @param userNo     用户号
     * @param accountNo  账户号
     * @param withdrawId 提现订单号
     */
    @Override
    public void sendCgwTransferApply(Long userNo, Long accountNo, Long withdrawId) {
        //查询提现明细
        UserWithdrawApplyDo userWithdrawApplyDo = userWithdrawCashManager.selectTransferDetailByOrder(withdrawId);
        //查询用户对应的账户
        UserPayeeAccountDo userPayeeAccountDo = userAccountBiz.queryUserAccountInfo(userNo, accountNo);
        //调用渠道提现MQ信息
        CgwTransferReqDto cgwTransferReqDto = UserWithdrawBizConvert.toCgwTransferReqDto(userWithdrawApplyDo,
                userPayeeAccountDo, Long.valueOf(configDict.getWyreChannelId()));
        mqSendService.sendMessage(MqSendQueueNameEnum.CGW_CROSS_BORDER_SERVICE_TRANSFER,
                JsonUtil.toJSONString(cgwTransferReqDto));
        log.info("call 用户提现MQ，生产者、队列名：{},内容：{}",
                MqSendQueueNameEnum.CGW_CROSS_BORDER_SERVICE_TRANSFER, cgwTransferReqDto);
    }

    /**
     * 功能：计算用户提现手续费&校验用户账户余额是否足够
     *
     * @param userWithdrawCashBo 用户提现信息
     */
    private void calcWithdrawFee(UserWithdrawCashBo userWithdrawCashBo) {
        //计算手续费
        BigDecimal withdrawFeeRate;
        WithdrawRateRespDo withdrawRateRespDo = userWithdrawRateMapper.queryUserSetWithdrawRate(userWithdrawCashBo.getUserNo(),
                userWithdrawCashBo.getWithdrawCcy());
        if (withdrawRateRespDo != null) {
            log.info("call 用户提现，用户费率未设置，使用默认费率，用户号：{},币种：{}", userWithdrawCashBo.getUserNo(),
                    userWithdrawCashBo.getWithdrawCcy());
            withdrawFeeRate = new BigDecimal(withdrawRateRespDo.getRate()).divide(new BigDecimal("100"));
        } else {
            withdrawFeeRate = new BigDecimal(configDict.getWithdrawFeeRate());
        }
        BigDecimal withdrawFee = WithdrawFeeUtil.calcWithdrawCashFee(userWithdrawCashBo.getWithdrawAmt(), withdrawFeeRate);
        userWithdrawCashBo.setWithdrawFee(withdrawFee);
        userWithdrawCashBo.setWithdrawFeeRate(withdrawFeeRate);
        //查询子账户余额
        UserAccountBalBo
                userAccountBalBo = userBalBiz.queryUserAccountBal(userWithdrawCashBo.getUserNo(), userWithdrawCashBo.getAccountNo());
        BigDecimal balanceAmt = userAccountBalBo.getAccountBal();
        //判断金额
        if (balanceAmt.compareTo(userWithdrawCashBo.getWithdrawAmt().add(withdrawFee)) < 0) {
            log.info("call 主要账户余额:{},提现金额：{},交易手续费金额：{}", balanceAmt,
                    userWithdrawCashBo.getWithdrawAmt(), withdrawFee);
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190017);
        }
    }

    /**
     * 功能：获取创建提现订单对象（补充收款人信息&店铺卖家ID信息）
     *
     * @param userWithdrawCashBo 用户提现信息
     * @return 收款人信息
     */
    private BatchWithdrawBo getWithdrawPayeeInfo(UserWithdrawCashBo userWithdrawCashBo) {
        BatchWithdrawBo batchWithdrawBo = new BatchWithdrawBo();
        BeanUtils.copyProperties(userWithdrawCashBo, batchWithdrawBo);
        WithdrawUserInfoBo withdrawUserInfoBo = this.queryWithdrawUserInfoBo(userWithdrawCashBo.getUserNo());
        batchWithdrawBo.setUserNo(userWithdrawCashBo.getUserNo());
        batchWithdrawBo.setPayeeIdType(withdrawUserInfoBo.getPayeeIdType());
        batchWithdrawBo.setPayeeIdNo(withdrawUserInfoBo.getPayeeIdNo());
        batchWithdrawBo.setPayeeName(withdrawUserInfoBo.getPayeeName());
        UserStoreDo userStoreDo = userStoreManager.queryByStoreNo(userWithdrawCashBo.getStoreNo());
        if (userStoreDo == null) {
            log.info("call 用户提现，店铺信息不存在，用户号：{}，店铺编号：{}", userWithdrawCashBo.getUserNo(),
                    userWithdrawCashBo.getStoreNo());
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190105);
        }
        batchWithdrawBo.setSellerId(userStoreDo.getSellerId());
        return batchWithdrawBo;
    }

    /**
     * 根据用户号查询用户信息
     *
     * @param userNo 用户号
     * @return 用户信息
     */
    private WithdrawUserInfoBo queryWithdrawUserInfoBo(Long userNo) {
        WithdrawUserInfoBo withdrawUserInfoBo = new WithdrawUserInfoBo();
        //查询用户收款人信息
        UserInfoDo userInfoDo = userInfoManager.selectUserInfoByUserNo(userNo);
        withdrawUserInfoBo.setUserType(userInfoDo.getUserType());
        if (UserTypeEnum.PERSONAL.getType() == userInfoDo.getUserType()) {
            UserPersonalDo userPersonalDo = userPersonalManager.queryByUserInfoNo(userInfoDo.getUserInfoNo());
            withdrawUserInfoBo.setPayeeIdType(userPersonalDo.getIdType());
            withdrawUserInfoBo.setPayeeIdNo(SecurityUtil.desEncrypt(userPersonalDo.getIdNo(), Constants.CARD_DES_KEY));
            withdrawUserInfoBo.setPayeeName(userPersonalDo.getName());
            withdrawUserInfoBo.setName(userPersonalDo.getName());
        } else if (UserTypeEnum.ORG.getType() == userInfoDo.getUserType()) {
            UserOrgDo userOrgDo = userOrgManager.queryByUserInfoNo(userInfoDo.getUserInfoNo());
            withdrawUserInfoBo.setPayeeIdType(userOrgDo.getLegalIdType());
            withdrawUserInfoBo.setPayeeIdNo(SecurityUtil.desEncrypt(userOrgDo.getLegalIdNo(), Constants.CARD_DES_KEY));
            withdrawUserInfoBo.setPayeeName(userOrgDo.getLegalName());
            withdrawUserInfoBo.setName(userOrgDo.getName());
        }
        return withdrawUserInfoBo;
    }

    /**
     * 新增用户提现信息
     *
     * @param userWithdrawCashBo 提现参数
     * @param orderId            提现订单号
     * @return 提现订单
     */
    private UserWithdrawApplyDo saveUserWithdrawApplyInfo(UserWithdrawCashBo userWithdrawCashBo, Long orderId) {
        UserWithdrawApplyDo userWithdrawApplyDo = new UserWithdrawApplyDo();
        BeanUtils.copyProperties(userWithdrawCashBo, userWithdrawApplyDo);
        userWithdrawApplyDo.setWithdrawId(orderId);
        userWithdrawApplyDo.setDeductAmt(userWithdrawCashBo.getWithdrawAmt());
        userWithdrawApplyDo.setTransferState(TransferStateEnum.TRANSFER_ACCOUNTS_PROCESS.getCode());
        userWithdrawApplyDo.setWithdrawState(WithdrawStateEnum.WITHDRAW_WAIT.getCode());
        //获取中行锁定汇率
        BigDecimal settleRate = this.queryWithdrawRate(userWithdrawCashBo.getWithdrawCcy());
        userWithdrawApplyDo.setTransferRate(settleRate);
        userWithdrawApplyDo.setSettleFee(BigDecimal.ZERO);
        userWithdrawApplyDo.setDestAmt((userWithdrawCashBo.getWithdrawAmt().subtract(userWithdrawCashBo.getWithdrawFee()))
                .multiply(settleRate.divide(new BigDecimal("100"))).setScale(NumberDict.TWO, BigDecimal.ROUND_DOWN));
        userWithdrawApplyDo.setDestCcy(CcyEnum.CNY.getKey());
        userWithdrawApplyDo.setCreateBy(userWithdrawCashBo.getCreateBy());
        userWithdrawApplyDo.setUpdateBy(userWithdrawCashBo.getCreateBy());
        userWithdrawApplyDo.setChannelId(userWithdrawCashBo.getChannelId());

        userWithdrawCashManager.addTUserWithdrawApply(userWithdrawApplyDo);
        return userWithdrawApplyDo;
    }

    /**
     * 获取中行锁定汇率
     *
     * @param withdrawCcy 提现币种
     * @return 锁定中行汇率
     */
    @Override
    public BigDecimal queryWithdrawRate(String withdrawCcy) {
        ExchangeRateDo exchangeRateDo = new ExchangeRateDo();
        exchangeRateDo.setCcy(withdrawCcy);
        exchangeRateDo.setStatus(NumberDict.ONE);
        exchangeRateDo.setChannelId(Long.valueOf(configDict.getBocSettleChannelId()));
        if (DateUtil.getCurrentDate().compareTo(DateUtil.parse(DateUtil.getCurrentStr() + NumberDict.ONE_HUNDRED_THOUSAND)) > 0) {
            exchangeRateDo.setCreateAt(new Date());
        } else {
            exchangeRateDo.setCreateAt(DateUtil.addDay(DateUtil.getCurrentDate(), NumberDict.MINUS_ONE));
        }
        ExchangeRateDo rateDo = exchangeRateMapper.selectRateInfo(exchangeRateDo);
        if (rateDo == null) {
            log.error("币种锁定中行汇率不存在，币种 {} ", withdrawCcy);
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190033);
        }
        return rateDo.getBidRateOfCcy();
    }

    /**
     * 宝付转账至备付金
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void execTransferDeposit(Long channelId) {
        //获取批次信息
        List<UserWithdrawApplyDo> withdrawApplyDoList = payeeTransferDepositManager.queryUserWithdrawApplyList(channelId);
        log.info("根据时间查询批次，共{}条", withdrawApplyDoList.size());
        if (CollectionUtils.isEmpty(withdrawApplyDoList)) {
            log.info("商户申请提现申请明细不存在，渠道编号：{}", channelId);
            return;
        }
        //生成汇总批次号
        Long batchNo = orderIdManager.orderIdCreate();
        //生成关系唯一编号
        Long relationId = orderIdManager.orderIdCreate();
        UserWithdrawSumDo userWithdrawSumDo = new UserWithdrawSumDo();
        userWithdrawSumDo.setWithdrawBatchId(batchNo);
        for (UserWithdrawApplyDo detailDo : withdrawApplyDoList) {
            log.info("宝付转账至备付金，批次号：{},明细订单明细：{}", batchNo, detailDo.getWithdrawId());
            //新增汇总和批次关系记录
            this.addPayeeRelation(relationId, batchNo, detailDo.getWithdrawId());
            //转账金额汇总
            userWithdrawSumDo.setWithdrawAmt(userWithdrawSumDo.getWithdrawAmt().add(detailDo.getWithdrawAmt()));
            //转账手续费汇总
            userWithdrawSumDo.setWithdrawFee(BigDecimal.ZERO);
        }
        userWithdrawSumDo.setWithdrawCcy(withdrawApplyDoList.get(0).getWithdrawCcy());
        ChannelRouteBo channelRouteBo = channelRouteBiz.channelRouteQuery(userWithdrawSumDo.getWithdrawCcy());
        userWithdrawSumDo.setChannelName(channelRouteBo.getChannelName());
        //新增汇总信息
        this.addUserWithdrawSum(userWithdrawSumDo);
        if (!channelRouteBo.getLinkedModel()) {
            log.info("该渠道为线下渠道，无需发送渠道下家，渠道信息：{}", channelRouteBo);
            return;
        }
        //发送转账通知渠道MQ消息
        this.sendMessage(userWithdrawSumDo);

    }

    /**
     * 功能：查询提现店铺账户余额信息
     *
     * @param userNo 用户号
     * @return 可提现店铺账户信息
     */
    @Override
    public List<WithdrawAccountRespBo> queryWithdrawAccountInfo(Long userNo) {
        List<WithdrawAccountRespBo> withdrawAccountRespList = Lists.newArrayList();
        try {
            UserPayeeAccountApplyDo userPayeeAccountApplyDo;
            WithdrawAccountRespBo withdrawAccountRespDto;
            List<UserStoreAccountBo> userStoreAccountBoList = userPayeeAccountBiz.queryUserStorePayAccount(userNo);
            for (UserStoreAccountBo userStoreAccountBo : userStoreAccountBoList) {
                userPayeeAccountApplyDo = applyAccountManager.queryApplyAccountByStoreNo(userStoreAccountBo.getStoreNo());
                if (!"Y".equals(userStoreAccountBo.getStoreExist()) || NumberDict.TWO != userPayeeAccountApplyDo.getStatus()) {
                    continue;
                }
                withdrawAccountRespDto = BeanCopyUtils.objectConvert(userStoreAccountBo, WithdrawAccountRespBo.class);
                withdrawAccountRespDto.setUserAccNo(userPayeeAccountApplyDo.getAccountNo());
                UserAccountBalBo userAccountBalBo = userBalBiz.queryUserAccountBal(userNo, userPayeeAccountApplyDo.getAccountNo());
                withdrawAccountRespDto.setBalanceAmt(userAccountBalBo.getAccountBal().
                        setScale(NumberDict.TWO, BigDecimal.ROUND_DOWN));
                withdrawAccountRespList.add(withdrawAccountRespDto);
            }
        } catch (Exception e) {
            log.error("call 查询店铺信息异常 Exception：{}", e);
        }
        return withdrawAccountRespList;
    }

    /**
     * 功能：计算用户提现手续费
     *
     * @param userWithdrawFeeReqBo 计算用户提现手续费参数
     * @return 计算用户提现手续费
     */
    @Override
    public WithdrawCashFeeRespBo withdrawCashFee(UserWithdrawFeeReqBo userWithdrawFeeReqBo) {

        //查询账户余额
        UserAccountBalBo userAccountBalBo = userBalBiz.queryUserAccountBal(userWithdrawFeeReqBo.getUserNo(), userWithdrawFeeReqBo.getAccountNo());
        BigDecimal balanceAmt = userAccountBalBo.getAccountBal();
        //获取用户费率
        BigDecimal feeRate;
        WithdrawRateRespDo withdrawRateRespDo = userWithdrawRateMapper.queryUserSetWithdrawRate(userWithdrawFeeReqBo.getUserNo(),
                userWithdrawFeeReqBo.getWithdrawCcy());
        if (withdrawRateRespDo != null) {
            log.info("call 用户提现，用户费率未设置，使用默认费率，用户号：{},币种：{}", userWithdrawFeeReqBo.getUserNo(),
                    userWithdrawFeeReqBo.getWithdrawCcy());
            feeRate = new BigDecimal(withdrawRateRespDo.getRate()).divide(new BigDecimal("100"));
        } else {
            feeRate = new BigDecimal(configDict.getWithdrawFeeRate());
        }
        //获取中行锁定汇率
        BigDecimal rate = this.queryWithdrawRate(userWithdrawFeeReqBo.getWithdrawCcy());
        //计算手续费
        BigDecimal withdrawFee = WithdrawFeeUtil.calcWithdrawCashFee(userWithdrawFeeReqBo.getWithdrawAmt(), feeRate);
        WithdrawCashFeeRespBo withdrawCashFeeRespBo = new WithdrawCashFeeRespBo();
        //提现金额
        withdrawCashFeeRespBo.setWithdrawAmt(userWithdrawFeeReqBo.getWithdrawAmt());
        //提现费率
        withdrawCashFeeRespBo.setTransferRate(feeRate.multiply(new BigDecimal("100")).
                setScale(NumberDict.THREE, BigDecimal.ROUND_DOWN));
        //提现手续费
        withdrawCashFeeRespBo.setWithdrawFee(withdrawFee);
        //到账金额
        withdrawCashFeeRespBo.setDestAmt((userWithdrawFeeReqBo.getWithdrawAmt().subtract(withdrawFee))
                .multiply(rate.divide(new BigDecimal("100"))).
                        setScale(NumberDict.TWO, BigDecimal.ROUND_DOWN));
        //提现账户
        withdrawCashFeeRespBo.setAccountNo(userWithdrawFeeReqBo.getAccountNo());
        //账户余额
        withdrawCashFeeRespBo.setBalanceAmt(balanceAmt);
        //店铺编号
        withdrawCashFeeRespBo.setStoreNo(userWithdrawFeeReqBo.getStoreNo());
        //提现币种
        withdrawCashFeeRespBo.setWithdrawCcy(userWithdrawFeeReqBo.getWithdrawCcy());

        return withdrawCashFeeRespBo;
    }

    /**
     * 新增汇总信息
     *
     * @param userWithdrawSumDo 汇总参数
     */
    private void addUserWithdrawSum(UserWithdrawSumDo userWithdrawSumDo) {
        userWithdrawSumDo.setMemberId(Long.valueOf(configDict.getPayeeMemberId()));
        userWithdrawSumDo.setSourceAccNo(configDict.getSourceAccNo());
        userWithdrawSumDo.setDestAccNo(configDict.getDestAccNo());
        userWithdrawSumDo.setDestAmt(BigDecimal.ZERO);
        userWithdrawSumDo.setDestCcy(CcyEnum.USD.getKey());
        userWithdrawSumDo.setDeductAmt(userWithdrawSumDo.getWithdrawAmt().add(userWithdrawSumDo.getWithdrawFee()));
        userWithdrawSumDo.setTransferState(TransferStateEnum.TRANSFER_ACCOUNTS_WATTING.getCode());

        payeeTransferDepositManager.addUserWithdrawSum(userWithdrawSumDo);
    }

    /**
     * 新增汇总和批次关系记录
     *
     * @param relationId 关系唯一编号
     * @param batchNo    批次号
     * @param detailId   明细编号
     */
    private void addPayeeRelation(Long relationId, Long batchNo, Long detailId) {
        UserWithdrawRelationDo userWithdrawRelationDo = new UserWithdrawRelationDo();
        userWithdrawRelationDo.setRelationId(relationId);
        userWithdrawRelationDo.setWithdrawBatchId(batchNo);
        userWithdrawRelationDo.setWithdrawId(detailId);
        userWithdrawRelationDo.setBizType(BizTypeEnum.ONE_TRANSFER.getCode());
        userWithdrawRelationManager.addUserWithdrawRelation(userWithdrawRelationDo);
    }

    /**
     * 发送转账通知渠道MQ消息
     *
     * @param transferSumDo 参数
     */
    private void sendMessage(UserWithdrawSumDo transferSumDo) {
        CgwTransferReqDto cgwTransferReqDto = new CgwTransferReqDto();
        cgwTransferReqDto.setSourceAmount(transferSumDo.getDeductAmt());
        cgwTransferReqDto.setBusinessType(1);
        cgwTransferReqDto.setSourceCurrency(transferSumDo.getWithdrawCcy());
        cgwTransferReqDto.setDestCurrency(transferSumDo.getDestCcy());
        cgwTransferReqDto.setBfBatchId(String.valueOf(transferSumDo.getWithdrawBatchId()));
        cgwTransferReqDto.setChannelId(Integer.valueOf(configDict.getWyreChannelId()));
        log.info("发送宝付转账通知渠道MQ消息,Param:{},key:{}", cgwTransferReqDto,
                MqSendQueueNameEnum.CGW_CROSS_BORDER_SERVICE_TRANSFER);
        mqSendService.sendMessage(MqSendQueueNameEnum.CGW_CROSS_BORDER_SERVICE_TRANSFER,
                cgwTransferReqDto);
    }

    /**
     * 功能：用户转账分发
     *
     * @param userWithdrawDistributeBo 用户转账分发参数
     */
    @Override
    public void userWithdrawDistribute(UserWithdrawDistributeBo userWithdrawDistributeBo) {
        if (!configDict.getPayeeMemberId().equals(userWithdrawDistributeBo.getMemberId() + "")) {
            log.error("请求资金分发商户不合法，商户号：{}", userWithdrawDistributeBo.getMemberId());
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190028);
        }
        //查询转账明细批次
        Long withdrawBatchId = userWithdrawDistributeBo.getBusinessNo();
        List<UserWithdrawApplyDo> applyDetailDoList = withdrawApplyManager.queryUserWithdrawByOrderId(withdrawBatchId);
        if (CollectionUtils.isEmpty(applyDetailDoList)) {
            log.error("用户提现请求明细不存在，汇总批次号：{}", withdrawBatchId);
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190027);
        }
        UserWithdrawApplyDo detailModifyDo;
        Date withdrawDate = new Date();
        for (UserWithdrawApplyDo model : applyDetailDoList) {
            if (WithdrawStateEnum.WITHDRAW_SUCCESS.getCode().equals(model.getWithdrawState())) {
                continue;
            }
            model.setWithdrawAt(withdrawDate);
            try {
                this.userWithdrawApplyPayAPI(model, withdrawBatchId);
                detailModifyDo = new UserWithdrawApplyDo();
                detailModifyDo.setWithdrawId(model.getWithdrawId());
                detailModifyDo.setWithdrawState(WithdrawStateEnum.WITHDRAW_PROCESS.getCode());
                detailModifyDo.setTransferState(TransferStateEnum.TRANSFER_ACCOUNTS_SUCCESS.getCode());
                withdrawApplyManager.modifyWithdrawApplyDo(detailModifyDo);
                this.sendAgentMessage(model.getWithdrawId());
                log.info("call wary商户对各个用户明细转账，用户转账明细订单号:{}，转账金额:{}，转账汇率:{}，转账币种{}，转账手续费(USD){}",
                        model.getUserNo(), model.getDestAmt(), model.getTransferRate(), model.getDestCcy(), model.getWithdrawFee());
            } catch (Exception e) {
                log.error(" 用户代付申请异常，商户号：{}，用户号：{}", model.getUserNo(), userWithdrawDistributeBo.getMemberId());
            }
        }
    }

    /**
     * 发送代理商分润MQ消息
     *
     * @param withdrawId 提现订单号
     */
    private void sendAgentMessage(Long withdrawId) {
        UserWithdrawApplyDo userWithdrawApplyDo = userWithdrawCashManager.selectTransferDetailByOrder(withdrawId);
        log.info("call 代理商查询用户代理商关系，请求参数：{}" , userWithdrawApplyDo.getUserNo());
        Result<Long> result = agentUserRelationFacade.queryAgentNoByUserNo(userWithdrawApplyDo.getUserNo(), MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
        log.info("call 代理商查询用户代理商关系，请求返回参数：{}" , result);
        if (result.isSuccess()) {
            WithdrawUserInfoBo userInfoBo = this.queryWithdrawUserInfoBo(userWithdrawApplyDo.getUserNo());
            AgentShareReqDto agentShareReqBo = new AgentShareReqDto();
            agentShareReqBo.setAgentNo(result.getResult());
            agentShareReqBo.setUserNo(userWithdrawApplyDo.getUserNo());
            agentShareReqBo.setUserName(userInfoBo.getName());
            agentShareReqBo.setWithdrawId(userWithdrawApplyDo.getWithdrawId());
            agentShareReqBo.setOrderAmt(userWithdrawApplyDo.getWithdrawAmt());
            agentShareReqBo.setOrderCcy(userWithdrawApplyDo.getWithdrawCcy());
            agentShareReqBo.setCnyOrderFeeAmt(userWithdrawApplyDo.getWithdrawFee());
            agentShareReqBo.setWithdrawRate(userWithdrawApplyDo.getWithdrawFeeRate());
            agentShareReqBo.setCnyOrderAmt(userWithdrawApplyDo.getDestAmt());
            log.info("发送代理商分润MQ消息,Param:{},key:{}", agentShareReqBo,
                    MqSendQueueNameEnum.GLOBAL_AGENT_SHARE_PROFIT_QUEUE_NAME);
            mqSendService.sendMessage(MqSendQueueNameEnum.GLOBAL_AGENT_SHARE_PROFIT_QUEUE_NAME,
                    agentShareReqBo);
        }
    }

    /**
     * 描述：代付API 请求申请
     *
     * @param userWithdrawApplyDo 代付参数
     * @param withdrawBatchId     提现汇总流水号
     * @throws Exception 异常信息
     */
    private void userWithdrawApplyPayAPI(UserWithdrawApplyDo userWithdrawApplyDo, Long withdrawBatchId) throws Exception {
        UserWithdrawDistributeDo userWithdrawDistributeDo = new UserWithdrawDistributeDo();
        BeanUtils.copyProperties(userWithdrawApplyDo, userWithdrawDistributeDo);
        Long orderId = orderIdManager.orderIdCreate();
        userWithdrawDistributeDo.setOrderId(orderId);
        userWithdrawDistributeDo.setWithdrawBatchId(withdrawBatchId);
        userWithdrawDistributeDo.setStatus(NumberDict.ONE);
        userWithdrawDistributeDo.setWithdrawState(WithdrawStateEnum.WITHDRAW_SUCCESS.getCode());
        //保存下发信息
        userWithdrawDistributeMapper.insert(userWithdrawDistributeDo);
        UserWithdrawReqBo userWithdrawReqBo = new UserWithdrawReqBo();
        userWithdrawReqBo.setRequestNo(configDict.getSettleMemberId() + "" + orderId);
        userWithdrawReqBo.setOrderAmt(userWithdrawApplyDo.getDestAmt());
        userWithdrawReqBo.setRemarks("全球收款账户转账分发");
        userWithdrawReqBo.setBankName(userWithdrawApplyDo.getBankName());
        userWithdrawReqBo.setCardHolder(userWithdrawApplyDo.getBankCardHolder());
        userWithdrawReqBo.setBankCardNo(SecurityUtil.desDecrypt(userWithdrawApplyDo.getBankCardNo(), Constants.CARD_DES_KEY));
        WithdrawUserInfoBo withdrawUserInfoBo = this.queryWithdrawUserInfoBo(userWithdrawApplyDo.getUserNo());
        userWithdrawReqBo.setCardId(SecurityUtil.desDecrypt(withdrawUserInfoBo.getPayeeIdNo(), Constants.CARD_DES_KEY));
        //发送下发申请
        bankWithdrawBiz.userWithdrawApply(userWithdrawReqBo);
    }
}