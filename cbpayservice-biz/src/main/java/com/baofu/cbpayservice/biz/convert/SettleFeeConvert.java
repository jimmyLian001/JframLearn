package com.baofu.cbpayservice.biz.convert;

import com.baofu.cbpayservice.biz.models.CbPaySettleBo;
import com.baofu.cbpayservice.biz.models.ClearRequestBo;
import com.baofu.cbpayservice.biz.models.MemberFeeResBo;
import com.baofu.cbpayservice.common.enums.DealCodeEnums;
import com.baofu.cbpayservice.common.enums.ProFunEnum;
import com.system.commons.utils.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.math.BigDecimal;

/**
 * 清算计费转换
 * <p>
 * 1，计费参数转换
 * 2，清算参数转换
 * </p>
 * User: 康志光 Date: 2017/1/3 ProjectName: cbpay-customs-service Version: 1.0
 */
public final class SettleFeeConvert {

    private SettleFeeConvert() {

    }

    /**
     * @param feeResult     计费对象参数
     * @param cbPaySettleBo 结汇信息
     * @return CmRequest 清算请求对象
     */
    public static ClearRequestBo convertToCmRequest(MemberFeeResBo feeResult, CbPaySettleBo cbPaySettleBo) {

        ClearRequestBo cmRequest = new ClearRequestBo();
        cmRequest.setMemberId(cbPaySettleBo.getMemberId());
        cmRequest.setOrderId(cbPaySettleBo.getOrderId());
        cmRequest.setFunctionId(ProFunEnum.PRO_FUN_10180001.getFunctionId());
        cmRequest.setProductId(ProFunEnum.PRO_FUN_10180001.getProductId());
        cmRequest.setDealAmount(cbPaySettleBo.getMemberSettleAmt());
        cmRequest.setDealCode(DealCodeEnums.SETTLE_CODE.getCode());
        cmRequest.setHasCalculatePrice(0);
        cmRequest.setPayerAcctCode("0");
        cmRequest.setPayeeAcctCode(cbPaySettleBo.getMemberId().toString());
        cmRequest.setPayerCurrencyCode(cbPaySettleBo.getSettleCcy());
        // 收款方币种
        cmRequest.setPayeeCurrencyCode(cbPaySettleBo.getSettleCcy());
        cmRequest.setChannelId(cbPaySettleBo.getChannelId());

        cmRequest.setBusinessNo(DateUtil.getCurrent("yyyyMMddHHmmssSSS") + "0311" +
                StringUtils.leftPad(String.valueOf(RandomUtils.nextLong(1L, 99999999999L)), 11, "0"));
        cmRequest.setTradeNo("0");
        cmRequest.setMemberId(0L);
        cmRequest.setMemberTransId(DateUtil.getCurrent());
        cmRequest.setFeeAcctId(feeResult.getFeeAccId());
        cmRequest.setPayerFee(BigDecimal.ZERO);
        cmRequest.setPayeeFee(feeResult.getFeeAmount());
        // 收款订单成功时间，以便清算方便记录对账
        cmRequest.setSuccessTime(DateUtil.getCurrentDate());
        cmRequest.setSynNoticeMa(true);

        return cmRequest;
    }

    /**
     * 结汇垫资清算对象封装
     *
     * @param cbPaySettleBo 结汇信息
     * @param preMemberId   收款方账号
     * @return ClearRequestBo 清算请求对象
     */
    public static ClearRequestBo convertToCmRequest(CbPaySettleBo cbPaySettleBo, String preMemberId) {

        ClearRequestBo cmRequest = new ClearRequestBo();
        cmRequest.setMemberId(cbPaySettleBo.getMemberId());
        cmRequest.setOrderId(cbPaySettleBo.getOrderId());
        cmRequest.setFunctionId(ProFunEnum.PRO_FUN_10180003.getFunctionId());
        cmRequest.setProductId(ProFunEnum.PRO_FUN_10180003.getProductId());
        cmRequest.setDealAmount(cbPaySettleBo.getMemberSettleAmt());
        cmRequest.setDealCode(DealCodeEnums.SETTLE_CODE.getCode());
        cmRequest.setHasCalculatePrice(1);
        cmRequest.setPayerAcctCode("0");
        cmRequest.setPayeeAcctCode(preMemberId);
        cmRequest.setPayerCurrencyCode(cbPaySettleBo.getSettleCcy());
        // 收款方币种
        cmRequest.setPayeeCurrencyCode(cbPaySettleBo.getSettleCcy());
        cmRequest.setChannelId(cbPaySettleBo.getChannelId());

        cmRequest.setBusinessNo(DateUtil.getCurrent("yyyyMMddHHmmssSSS") + "0311" +
                StringUtils.leftPad(String.valueOf(RandomUtils.nextLong(1L, 99999999999L)), 11, "0"));
        cmRequest.setTradeNo("0");
        cmRequest.setMemberId(0L);
        cmRequest.setMemberTransId(DateUtil.getCurrent());
        cmRequest.setPayerFee(BigDecimal.ZERO);
        cmRequest.setPayeeFee(BigDecimal.ZERO);
        // 收款订单成功时间，以便清算方便记录对账
        cmRequest.setSuccessTime(DateUtil.getCurrentDate());
        cmRequest.setSynNoticeMa(true);

        return cmRequest;
    }

}
