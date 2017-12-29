package com.baofu.cbpayservice.biz.convert;

import com.baofu.cbpayservice.biz.models.SettleIncomeApplyBo;
import com.baofu.cbpayservice.biz.models.SettleNotifyApplyBo;
import com.baofu.cbpayservice.dal.models.FiCbPaySettleApplyDo;

/**
 * <p/>
 * <p>
 * 1、
 * </p>
 * User: 白玉京 Date:2017/9/9 0009 ProjectName: cbpay-service
 */
public class SettleApplyConvert {

    /**
     * 异步通知商户申请参数信息转换
     *
     * @param settleApply 商户申请参数信息
     * @return 返回通知申请参数信息
     */
    public static SettleNotifyApplyBo paramConvert(FiCbPaySettleApplyDo settleApply) {

        SettleNotifyApplyBo settleNotifyApplyBo = new SettleNotifyApplyBo();
        settleNotifyApplyBo.setMemberId(settleApply.getMemberId());
        settleNotifyApplyBo.setNotifyUrl(settleApply.getNotifyUrl());
        settleNotifyApplyBo.setRemitReqNo(settleApply.getIncomeNo());
        settleNotifyApplyBo.setTerminalId(settleApply.getTerminalId());

        return settleNotifyApplyBo;
    }

    /**
     * 异步通知商户申请参数信息转换
     *
     * @param settleIncomeApplyBo 商户API申请参数信息
     * @return 返回通知申请参数信息
     */
    public static SettleNotifyApplyBo paramConvert(SettleIncomeApplyBo settleIncomeApplyBo) {

        SettleNotifyApplyBo settleNotifyApplyBo = new SettleNotifyApplyBo();
        settleNotifyApplyBo.setMemberId(settleIncomeApplyBo.getMemberId());
        settleNotifyApplyBo.setNotifyUrl(settleIncomeApplyBo.getNotifyUrl());
        settleNotifyApplyBo.setRemitReqNo(settleIncomeApplyBo.getIncomeNo());
        settleNotifyApplyBo.setTerminalId(settleIncomeApplyBo.getTerminalId());

        return settleNotifyApplyBo;
    }
}
