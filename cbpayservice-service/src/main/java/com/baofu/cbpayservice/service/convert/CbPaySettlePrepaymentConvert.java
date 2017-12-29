package com.baofu.cbpayservice.service.convert;

import com.baofu.cbpayservice.biz.models.CbPaySettlePrepaymentBo;
import com.baofu.cbpayservice.facade.models.CbPaySettlePrepaymentDto;

/**
 * 垫资结汇对象封装
 * <p>
 * </p>
 * User: 康志光 Date: 2017/8/18 ProjectName: cbpay-customs-service Version: 1.0
 */
public class CbPaySettlePrepaymentConvert {

    public static CbPaySettlePrepaymentDto convertTo(CbPaySettlePrepaymentBo cbPaySettlePrepaymentBo) {
        if (cbPaySettlePrepaymentBo == null) {
            return null;
        }
        CbPaySettlePrepaymentDto cbPaySettlePrepaymentDto = new CbPaySettlePrepaymentDto();
        cbPaySettlePrepaymentDto.setIncomeNo(cbPaySettlePrepaymentBo.getIncomeNo());
        cbPaySettlePrepaymentDto.setIncomeAmt(cbPaySettlePrepaymentBo.getIncomeAmt());
        cbPaySettlePrepaymentDto.setIncomeCcy(cbPaySettlePrepaymentBo.getIncomeCcy());
        cbPaySettlePrepaymentDto.setPreSettleRate(cbPaySettlePrepaymentBo.getPreSettleRate());
        cbPaySettlePrepaymentDto.setPreSettleAmt(cbPaySettlePrepaymentBo.getPreSettleAmt());
        cbPaySettlePrepaymentDto.setSettleAcc(cbPaySettlePrepaymentBo.getSettleAcc());
        cbPaySettlePrepaymentDto.setRemarks(cbPaySettlePrepaymentBo.getRemarks());
        cbPaySettlePrepaymentDto.setMemberId(cbPaySettlePrepaymentBo.getMemberId());
        cbPaySettlePrepaymentDto.setMemberName(cbPaySettlePrepaymentBo.getMemberName());
        return cbPaySettlePrepaymentDto;
    }

}
