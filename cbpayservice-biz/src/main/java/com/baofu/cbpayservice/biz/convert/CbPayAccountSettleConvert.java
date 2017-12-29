package com.baofu.cbpayservice.biz.convert;

import com.baofoo.cache.service.facade.utils.SecurityUtil;
import com.baofu.accountcenter.service.facade.dto.req.RechargeReqDto;
import com.baofu.cbpayservice.biz.models.*;
import com.baofu.cbpayservice.common.constants.Constants;
import com.baofu.cbpayservice.common.constants.NumberConstants;
import com.baofu.cbpayservice.common.enums.*;
import com.baofu.cbpayservice.dal.models.FiCbPaySettleDo;
import com.baofu.cbpayservice.dal.models.FiCbPaySettleOrderDo;
import com.system.commons.utils.DateUtil;
import com.system.config.Configuration;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 功能：收款账户跨境结汇对象转换
 * User: feng_jiang
 * Date:2017/9/10
 * ProjectName: cbPayService
 * Version: 1.0
 */
public final class CbPayAccountSettleConvert {
    private CbPayAccountSettleConvert() {
    }

    /**
     * 功能：转换成生成结汇订单参数
     *
     * @param sMTAListenerBo 收汇信息
     * @param orderId        订单号
     * @return FiCbPaySettleDo
     */
    public static FiCbPaySettleDo toFiCbPaySettleDo(SettleMoneyToAccountListenerBo sMTAListenerBo, Long orderId) {
        FiCbPaySettleDo fiCbPaySettleDo = new FiCbPaySettleDo();
        fiCbPaySettleDo.setOrderId(orderId);
        fiCbPaySettleDo.setMemberId(sMTAListenerBo.getMemberId());
        fiCbPaySettleDo.setChannelId(sMTAListenerBo.getChannelId());
        fiCbPaySettleDo.setIncomeNo(sMTAListenerBo.getIncomeNo());
        fiCbPaySettleDo.setIncomeAmt(sMTAListenerBo.getIncomeAmt());
        fiCbPaySettleDo.setIncomeCcy(sMTAListenerBo.getIncomeCcy());
        fiCbPaySettleDo.setIncomeAt(sMTAListenerBo.getIncomeAt());
        fiCbPaySettleDo.setIncomeStatus(sMTAListenerBo.getIncomeStatus());
        fiCbPaySettleDo.setIsIncome(sMTAListenerBo.getIsIncome());
        fiCbPaySettleDo.setSettleStatus(SettleStatusEnum.WAIT_SETTLEMENT.getCode());
        fiCbPaySettleDo.setIncomeAccountNo(sMTAListenerBo.getIncomeAccountNo());
        fiCbPaySettleDo.setIncomeAccountName(sMTAListenerBo.getIncomeAccountName());
        fiCbPaySettleDo.setIncomeAddress(sMTAListenerBo.getIncomeAddress());
        fiCbPaySettleDo.setIncomeFee(sMTAListenerBo.getIncomeFee());
        fiCbPaySettleDo.setBankName(sMTAListenerBo.getBankName());
        fiCbPaySettleDo.setRemarks(sMTAListenerBo.getRemarks());
        fiCbPaySettleDo.setFileStatus(SettleFileStatusEnum.UPLOAD_SUCCESS.getCode());
        fiCbPaySettleDo.setSettleFlag(SettleFlagEnum.WAIT_SETTLEMENT.getCode());
        fiCbPaySettleDo.setFileBatchNo(Long.valueOf(sMTAListenerBo.getRemarks()));
        if (InComeStatusEnum.COMPELTED_INCOME.getCode() == fiCbPaySettleDo.getIsIncome()) {
            fiCbPaySettleDo.setRelieveAt(sMTAListenerBo.getIncomeAt());
        }
        fiCbPaySettleDo.setCmAuditState(ReceiverAuditCmStatusEnum.INIT.getCode());
        return fiCbPaySettleDo;
    }

    /**
     * 功能：收款账户结汇订单信息转换
     *
     * @param memberId 结汇订单校验信息
     * @param orderId  结汇订单编号
     * @param orderAmt 订单金额
     * @param batchNo  汇款批次号
     * @return 结汇订单信息
     */
    public static FiCbPaySettleOrderDo toFiCbPaySettleOrderDo(Long memberId, Long orderId, BigDecimal orderAmt, Long batchNo) {
        FiCbPaySettleOrderDo fiCbPaySettleOrderDo = new FiCbPaySettleOrderDo();
        fiCbPaySettleOrderDo.setMemberId(memberId);
        fiCbPaySettleOrderDo.setOrderId(orderId);
        fiCbPaySettleOrderDo.setMemberTransId(orderId + "");
        fiCbPaySettleOrderDo.setMemberTransDate(DateUtil.getCurrentDate());
        fiCbPaySettleOrderDo.setOrderAmt(orderAmt);
        fiCbPaySettleOrderDo.setOrderCcy("USD");
        String payeeStr = Configuration.getString(Constants.CBPAY_WYRE_PAYEE_USER);
        String[] payeeUser = payeeStr == null ? null : payeeStr.split("\\" + Constants.SPLIT_MARK);
        if (payeeUser != null && payeeUser.length > 0) {
            fiCbPaySettleOrderDo.setPayeeIdType(Integer.parseInt(payeeUser[0]));
            fiCbPaySettleOrderDo.setPayeeName(payeeUser[1]);
            fiCbPaySettleOrderDo.setPayeeIdNo(SecurityUtil.desEncrypt(payeeUser[2], Constants.CARD_DES_PASSWD));
        } else {
            fiCbPaySettleOrderDo.setPayeeIdType(NumberConstants.ONE);
            fiCbPaySettleOrderDo.setPayeeName("杨翠");
            fiCbPaySettleOrderDo.setPayeeIdNo(SecurityUtil.desEncrypt("532130196402061524", Constants.CARD_DES_PASSWD));
        }
        fiCbPaySettleOrderDo.setPayeeAccNo(SecurityUtil.desEncrypt("568018967", Constants.CARD_DES_PASSWD));
        fiCbPaySettleOrderDo.setBatchNo(batchNo);
        fiCbPaySettleOrderDo.setAmlStatus(1);
        fiCbPaySettleOrderDo.setRisk_flag(1);
        return fiCbPaySettleOrderDo;
    }

    /**
     * 功能：结汇完成组装结汇订单结汇人民币信息
     *
     * @param cbPaySettleBo 结汇对象
     * @return CbPaySettleBo
     */
    public static CbPaySettleBo convert2SettleCompleteBo(CbPaySettleBo cbPaySettleBo) {
        CbPaySettleBo cbPaySettleCallbackReqBo = new CbPaySettleBo();
        cbPaySettleCallbackReqBo.setOrderId(cbPaySettleBo.getOrderId());
        cbPaySettleCallbackReqBo.setSettleRate(cbPaySettleBo.getSettleRate());
        cbPaySettleCallbackReqBo.setSettleAmt(cbPaySettleBo.getSettleAmt());
        cbPaySettleCallbackReqBo.setSettleCcy(cbPaySettleBo.getSettleCcy());
        cbPaySettleCallbackReqBo.setSettleStatus(SettleStatusEnum.TURE.getCode());
        cbPaySettleCallbackReqBo.setOldSettleStatus(SettleStatusEnum.SETTLEMENT_PROCESSING.getCode());
        cbPaySettleCallbackReqBo.setOldIncomeStatus(InComeStatusEnum.COMPELTED_INCOME.getCode());
        cbPaySettleCallbackReqBo.setMemberSettleAmt(cbPaySettleBo.getMemberSettleAmt());
        cbPaySettleCallbackReqBo.setMemberSettleRate(cbPaySettleBo.getMemberSettleRate());
        cbPaySettleCallbackReqBo.setProfitAndLoss(BigDecimal.ZERO);
        cbPaySettleCallbackReqBo.setRealIncomeAmt(cbPaySettleBo.getRealIncomeAmt());
        return cbPaySettleCallbackReqBo;
    }

    /**
     * 功能：结汇完成转换成转账分发服务队列队形
     *
     * @param cbPaySettleBo 结汇订单
     * @return AccountSettleDistributeBo
     */
    public static AccountSettleDistributeBo convert2DisBo(CbPaySettleBo cbPaySettleBo) {
        if (cbPaySettleBo == null) {
            return null;
        }
        AccountSettleDistributeBo cbPaySettleCallbackReqBo = new AccountSettleDistributeBo();
        cbPaySettleCallbackReqBo.setOrderID(cbPaySettleBo.getOrderId());
        cbPaySettleCallbackReqBo.setSettleRate(cbPaySettleBo.getMemberSettleRate());
        cbPaySettleCallbackReqBo.setMemberId(cbPaySettleBo.getMemberId());
        cbPaySettleCallbackReqBo.setCcy(cbPaySettleBo.getIncomeCcy());
        return cbPaySettleCallbackReqBo;
    }

    /**
     * 功能：清算审核通过，对用户的外币账户进行充值
     *
     * @param fiCbPaySettleDo 结汇对象（申请对象）
     * @return 充值对象
     */
    public static RechargeReqDto toWithdrawReqDto(FiCbPaySettleDo fiCbPaySettleDo) {
        RechargeReqDto rechargeReqDto = new RechargeReqDto();
        rechargeReqDto.setOrderId(fiCbPaySettleDo.getOrderId());
        rechargeReqDto.setMemberId(fiCbPaySettleDo.getMemberId());
        rechargeReqDto.setOrderAmt(fiCbPaySettleDo.getIncomeAmt());
        rechargeReqDto.setOrderCcy(fiCbPaySettleDo.getIncomeCcy());
        rechargeReqDto.setChannelId(fiCbPaySettleDo.getChannelId());
        rechargeReqDto.setOrderSubType(CmOrderSubTypeEnum.ACCOUNT_RECHARGE.getCode());
        return rechargeReqDto;
    }

    /**
     * 功能：转换成结汇完成更新清算相关对象
     *
     * @param orderId 结汇订单号
     * @param fee     手续费
     * @return CbPaySettleBo
     */
    public static CbPaySettleBo convert2SettleBo(Long orderId, BigDecimal fee) {
        //更新清算申请时间、完成时间、清算标识，目前由于是宝付内部转账无损益，故后续无损益科目记账
        CbPaySettleBo cbPaySettleModifyBo = new CbPaySettleBo();
        Date applyDate = DateUtil.getCurrentDate();
        cbPaySettleModifyBo.setOrderId(orderId);
        cbPaySettleModifyBo.setSettleAt(applyDate);
        cbPaySettleModifyBo.setSettleCompleteAt(applyDate);
        cbPaySettleModifyBo.setSettleFlag(SettleFlagEnum.SETTLE_SUCCESS.getCode());
        cbPaySettleModifyBo.setSettleFee(fee);
        cbPaySettleModifyBo.setOldSettleStatus(SettleStatusEnum.TURE.getCode());
        cbPaySettleModifyBo.setOldIncomeStatus(InComeStatusEnum.COMPELTED_INCOME.getCode());
        return cbPaySettleModifyBo;
    }

    /**
     * 功能：转换结汇订单更新对象DO
     *
     * @param cbPaySettleBo 结汇订单对象
     * @return FiCbPaySettleDo
     */
    public static FiCbPaySettleDo toFiCbPaySettleDo(CbPaySettleBo cbPaySettleBo) {
        if (cbPaySettleBo == null) {
            return null;
        }
        FiCbPaySettleDo fiCbPaySettleDo = new FiCbPaySettleDo();
        fiCbPaySettleDo.setMemberId(cbPaySettleBo.getMemberId());
        fiCbPaySettleDo.setChannelId(cbPaySettleBo.getChannelId());
        fiCbPaySettleDo.setOrderId(cbPaySettleBo.getOrderId());
        fiCbPaySettleDo.setIncomeAmt(cbPaySettleBo.getIncomeAmt());
        fiCbPaySettleDo.setIncomeCcy(cbPaySettleBo.getIncomeCcy());
        fiCbPaySettleDo.setSettleRate(cbPaySettleBo.getSettleRate());
        fiCbPaySettleDo.setSettleCcy(cbPaySettleBo.getSettleCcy());
        fiCbPaySettleDo.setSettleAmt(cbPaySettleBo.getSettleAmt());
        fiCbPaySettleDo.setSettleAt(cbPaySettleBo.getSettleAt());
        fiCbPaySettleDo.setSettleCompleteAt(cbPaySettleBo.getSettleCompleteAt());
        fiCbPaySettleDo.setSettleFee(cbPaySettleBo.getSettleFee());
        fiCbPaySettleDo.setSettleFlag(cbPaySettleBo.getSettleFlag());
        fiCbPaySettleDo.setSettleStatus(cbPaySettleBo.getSettleStatus());
        fiCbPaySettleDo.setIncomeStatus(cbPaySettleBo.getIncomeStatus());
        fiCbPaySettleDo.setOldSettleStatus(cbPaySettleBo.getOldSettleStatus());
        fiCbPaySettleDo.setOldIncomeStatus(cbPaySettleBo.getOldIncomeStatus());
        fiCbPaySettleDo.setMemberSettleAmt(cbPaySettleBo.getMemberSettleAmt());
        fiCbPaySettleDo.setMemberSettleRate(cbPaySettleBo.getMemberSettleRate());
        fiCbPaySettleDo.setProfitAndLoss(cbPaySettleBo.getProfitAndLoss());
        fiCbPaySettleDo.setRealIncomeAmt(cbPaySettleBo.getRealIncomeAmt());
        fiCbPaySettleDo.setFileBatchNo(cbPaySettleBo.getFileBatchNo());
        fiCbPaySettleDo.setFileStatus(cbPaySettleBo.getFileStatus());

        return fiCbPaySettleDo;
    }

    /**
     * 功能：结汇结果查询参数转换
     *
     * @param memberId      商户号
     * @param incomeNo      汇款流水号
     * @param settleOrderId 结汇订单号
     * @param searchType    查询类型
     * @return SettleQueryReqParamBo
     */
    public static SettleQueryReqParamBo toSettleQueryReqParamBo(Long memberId, String incomeNo, Long settleOrderId, String searchType) {
        SettleQueryReqParamBo settleQueryReqParamBo = new SettleQueryReqParamBo();
        settleQueryReqParamBo.setIncomeNo(incomeNo);
        settleQueryReqParamBo.setMemberId(memberId);
        settleQueryReqParamBo.setSettleOrderId(settleOrderId);
        settleQueryReqParamBo.setSearchType(searchType);
        return settleQueryReqParamBo;
    }
}
