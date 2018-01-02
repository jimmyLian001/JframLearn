package com.baofu.international.global.account.core.biz.impl;

import com.baofu.international.global.account.core.biz.UserBalBiz;
import com.baofu.international.global.account.core.biz.UserWithdrawBiz;
import com.baofu.international.global.account.core.biz.UserWithdrawCbPayDealBiz;
import com.baofu.international.global.account.core.biz.external.SettleApplyBizImpl;
import com.baofu.international.global.account.core.biz.models.*;
import com.baofu.international.global.account.core.common.constant.ConfigDict;
import com.baofu.international.global.account.core.common.constant.Constants;
import com.baofu.international.global.account.core.common.constant.NumberDict;
import com.baofu.international.global.account.core.common.enums.ErrorCodeEnum;
import com.baofu.international.global.account.core.common.enums.SettleStatusEnum;
import com.baofu.international.global.account.core.common.enums.TransferStateEnum;
import com.baofu.international.global.account.core.common.enums.WithdrawStateEnum;
import com.baofu.international.global.account.core.dal.mapper.UserSettleApplyMapper;
import com.baofu.international.global.account.core.dal.mapper.UserWithdrawDistributeMapper;
import com.baofu.international.global.account.core.dal.model.*;
import com.baofu.international.global.account.core.manager.*;
import com.system.commons.exception.BizServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 功能：用户提现与跨境API交互服务
 * User: feng_jiang Date:2017/11/5 ProjectName: globalaccount-core Version: 1.0
 **/
@Slf4j
@Service
public class UserWithdrawCbPayDealBizImpl implements UserWithdrawCbPayDealBiz {

    /**
     * 宝付转账至备付金Manager
     */
    @Autowired
    private PayeeTransferDepositManager payeeTransferDepositManager;

    /**
     * 结汇API服务
     */
    @Autowired
    private SettleApplyBizImpl settleApplyBiz;

    /**
     * 用户提现转账服务
     */
    @Autowired
    private UserWithdrawBiz userWithdrawBiz;

    /**
     * 订单主键生成服务
     */
    @Autowired
    private OrderIdManager orderIdManager;

    /**
     * 配置中心
     */
    @Autowired
    private ConfigDict configDict;

    /**
     * 用户汇入汇款申请操作类
     */
    @Autowired
    private UserSettleApplyMapper userSettleApplyMapper;

    /**
     * 用户前台提现服务
     */
    @Autowired
    private UserWithdrawCashManager userWithdrawCashManager;

    /**
     * 用户提前申请表操作类
     */
    @Autowired
    private UserWithdrawApplyManager withdrawApplyManager;

    /**
     * 用户余额服务
     */
    @Autowired
    private UserBalBiz userBalBiz;

    /**
     * 用户账户余额相关
     */
    @Autowired
    private UserAccountBalManager userAccountBalManager;

    /**
     * 用户自己分发表操作类
     */
    @Autowired
    private UserWithdrawDistributeMapper userWithdrawDistributeMapper;
    /**
     * 功能：发起结汇申请API请求
     *
     * @param withdrawBatchId 提现汇总订单号
     * @param fileName        汇入汇款申请文件
     */
    @Override
    public void processSettleAPI(Long withdrawBatchId, String fileName) {
        UserWithdrawSumDo userWithdrawSumDo = payeeTransferDepositManager.queryTUserWithdrawSumDoByBatch(withdrawBatchId);
        if (userWithdrawSumDo == null) {
            log.error("用户转账请求汇总批次不存在，汇总批次号：{}", withdrawBatchId);
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190026);
        }
        SettleApplyBo settleApplyBo = new SettleApplyBo();
        settleApplyBo.setRemitReqNo("" + orderIdManager.orderIdCreate());
        settleApplyBo.setOrderAmt(userWithdrawSumDo.getWithdrawAmt().multiply((BigDecimal.ONE.
                subtract(new BigDecimal(configDict.getWyreChannelFeeRate())))).setScale(NumberDict.TWO, BigDecimal.ROUND_DOWN));
        settleApplyBo.setOrderCcy(userWithdrawSumDo.getWithdrawCcy());
        settleApplyBo.setPayeeAccount(userWithdrawSumDo.getDestAccNo());
        settleApplyBo.setRemitAcc(userWithdrawSumDo.getSourceAccNo());
        settleApplyBo.setNotifyUrl(configDict.getGlobalAccountDomain() + Constants.PAYEE_NOTIFY_URL);
        settleApplyBo.setVoucherFileName("");
        settleApplyBo.setDetailFileName(fileName);
        settleApplyBo.setPayeeBankName(Constants.PAYEE_BANK_NAME);
        settleApplyBo.setFileType(Constants.SETTLE_APPLY_FILE_TYPE);
        settleApplyBo.setRemitCountry(Constants.PAYEE_COUNTRY);
        settleApplyBo.setRemitName(Constants.PAYEE_CHANNEL);
        settleApplyBo.setRemarks("" + userWithdrawSumDo.getWithdrawBatchId());
        settleApplyBiz.settleApply(settleApplyBo);
        //落地申请表
        this.saveSettleApplyInfo(fileName, userWithdrawSumDo, settleApplyBo);
    }

    /**
     * 功能：保存用户汇入汇款申请信息
     *
     * @param fileName          提现明细文件名
     * @param userWithdrawSumDo 转账汇总信息
     * @param settleApplyBo     结汇申请信息
     * @param settleApplyBo
     */
    private void saveSettleApplyInfo(String fileName, UserWithdrawSumDo userWithdrawSumDo, SettleApplyBo settleApplyBo) {
        UserSettleApplyDo userSettleApplyDo = new UserSettleApplyDo();
        userSettleApplyDo.setMemberId(userWithdrawSumDo.getMemberId());
        userSettleApplyDo.setMemberReqId(settleApplyBo.getRemitReqNo());
        userSettleApplyDo.setBusinessNo(userWithdrawSumDo.getWithdrawBatchId());
        userSettleApplyDo.setBusinessType("" + NumberDict.ELEVEN);
        userSettleApplyDo.setFileType(settleApplyBo.getFileType());
        userSettleApplyDo.setSettleName(fileName);
        userSettleApplyDo.setNotifyUrl(settleApplyBo.getNotifyUrl());
        userSettleApplyDo.setApplyStatus(NumberDict.ZERO);
        userSettleApplyMapper.insert(userSettleApplyDo);
    }

    /**
     * 功能：处理结汇申请API请求
     *
     * @param userSettleApplyRespBo 结汇处理结果
     */
    @Override
    public void dealSettleAPIResult(UserSettleApplyRespBo userSettleApplyRespBo) {
        UserSettleApplyDo userSettleApplyDo = userSettleApplyMapper.selectByReqId(userSettleApplyRespBo.getRemitReqNo());
        if (userSettleApplyDo == null) {
            log.info("汇入汇款申请不存在，申请流水号：{}", userSettleApplyRespBo.getRemitReqNo());
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190034);
        }
        if (!SettleStatusEnum.SETTLE_SUCCESS.getCode().equals(userSettleApplyRespBo.getStatus())) {
            userSettleApplyDo.setSettleStatus(NumberDict.FIVE);
            userSettleApplyDo.setRemarks(SettleStatusEnum.getEnumsByCode(userSettleApplyRespBo.getStatus()));
        } else {
            userSettleApplyDo.setSettleStatus(NumberDict.SIX);
            UserWithdrawDistributeBo userWithdrawDistributeBo = new UserWithdrawDistributeBo();
            userWithdrawDistributeBo.setBusinessNo(userSettleApplyDo.getBusinessNo());
            BeanUtils.copyProperties(userSettleApplyRespBo, userWithdrawDistributeBo);
            userWithdrawBiz.userWithdrawDistribute(userWithdrawDistributeBo);
        }
        userSettleApplyDo.setApplyStatus(NumberDict.ONE);
        userSettleApplyMapper.updateUserSettleApply(userSettleApplyDo);
    }

    /**
     * 功能：处理内卡下发处理结果
     * @return 提现订单
     */
    @Override
    public void dealWithdrawDistributeAPIResult(UserDistributeApplyRespBo userDistributeApplyRespBo) {
        String transNo = userDistributeApplyRespBo.getTransNo();
        if(!transNo.startsWith(configDict.getSettleMemberId() + "")) {
            log.info("提现下发申请不存在，申请流水号：{}", transNo);
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190037);
        }
        transNo = transNo.replaceAll(configDict.getSettleMemberId()+"","");
        UserWithdrawDistributeDo withdrawDistributeDo = userWithdrawDistributeMapper.selectDistributeByOrder(Long.valueOf(transNo));
        UserWithdrawApplyDo userWithdrawApplyDo = userWithdrawCashManager.selectTransferDetailByOrder(withdrawDistributeDo.getWithdrawId());
        if (userWithdrawApplyDo == null) {
            log.warn("根据提现订单号查询提现信息为空，提现订单号：{}", transNo);
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190020);
        }
        UserWithdrawApplyDo detailModifyDo = new UserWithdrawApplyDo();
        detailModifyDo.setWithdrawId(userWithdrawApplyDo.getWithdrawId());
        detailModifyDo.setTransferState(TransferStateEnum.TRANSFER_ACCOUNTS_SUCCESS.getCode());
        //提现成功
        if (NumberDict.ONE == userDistributeApplyRespBo.getState()){
            detailModifyDo.setWithdrawState(WithdrawStateEnum.WITHDRAW_SUCCESS.getCode());
            detailModifyDo.setWithdrawAt(new Date());
            this.modifyAccountBal(userWithdrawApplyDo);
        } else if (NumberDict.MINUS_ONE == userDistributeApplyRespBo.getState()){
            //提现失败
            detailModifyDo.setWithdrawState(WithdrawStateEnum.WITHDRAW_FAIL.getCode());
            detailModifyDo.setRemarks(userDistributeApplyRespBo.getTransRemark());
        } else if (NumberDict.ZERO == userDistributeApplyRespBo.getState()){
            detailModifyDo.setWithdrawState(WithdrawStateEnum.WITHDRAW_PROCESS.getCode());
        }
        withdrawApplyManager.modifyWithdrawApplyDo(detailModifyDo);
    }

    /**
     * 功能：提现成功更新提现中金额
     *
     * @param withdrawApplyDo 提现订单信息
     */
    private void modifyAccountBal(UserWithdrawApplyDo withdrawApplyDo) {
        //更新提现中金额-
        UserAccountBalBo userAccountBalBo = userBalBiz.queryUserAccountBal(withdrawApplyDo.getUserNo(), withdrawApplyDo.getAccountNo());
        userAccountBalBo.setWithdrawProcessAmt((userAccountBalBo.getWithdrawProcessAmt().subtract(withdrawApplyDo.getWithdrawAmt())).
                setScale(NumberDict.TWO, BigDecimal.ROUND_DOWN));
        UserAccountBalDo userAccountBalDo = new UserAccountBalDo();
        BeanUtils.copyProperties(userAccountBalBo, userAccountBalDo);
        userAccountBalManager.modifyAccountBal(userAccountBalDo);
    }
}
