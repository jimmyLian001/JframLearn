package com.baofu.cbpayservice.biz.convert;

import com.baofoo.cache.service.facade.model.CacheMemberDto;
import com.baofu.cbpayservice.biz.models.CbPaySettlePrepaymentBo;
import com.baofu.cbpayservice.dal.models.CbPaySettlePrepaymentDo;
import com.baofu.cbpayservice.dal.models.FiCbPaySettleDo;

import java.math.BigDecimal;

/**
 * <p>
 * </p>
 * User: 康志光 Date: 2017/8/17 ProjectName: cbpay-customs-service Version: 1.0
 */
public class CbPaySettlePrepaymentConvert {

    public static CbPaySettlePrepaymentDo convertTo(FiCbPaySettleDo fiCbPaySettleDo, Long applyId,
                                                    BigDecimal settleAmt, BigDecimal settleRate, String settleAcc, Integer autoFlag) {
        CbPaySettlePrepaymentDo cbPaySettlePrepaymentDo = new CbPaySettlePrepaymentDo();
        cbPaySettlePrepaymentDo.setMemberId(fiCbPaySettleDo.getMemberId());
        cbPaySettlePrepaymentDo.setApplyId(applyId);
        cbPaySettlePrepaymentDo.setIncomeNo(fiCbPaySettleDo.getIncomeNo());
        cbPaySettlePrepaymentDo.setIncomeAmt(fiCbPaySettleDo.getIncomeAmt());
        cbPaySettlePrepaymentDo.setIncomeCcy(fiCbPaySettleDo.getIncomeCcy());
        cbPaySettlePrepaymentDo.setPreSettleRate(settleRate);
        cbPaySettlePrepaymentDo.setPreSettleAmt(settleAmt);
        cbPaySettlePrepaymentDo.setSettleAcc(settleAcc);
        cbPaySettlePrepaymentDo.setAutoFlag(autoFlag);
        cbPaySettlePrepaymentDo.setCreateBy("system");
        cbPaySettlePrepaymentDo.setUpdateBy("system");
        return cbPaySettlePrepaymentDo;
    }

    public static CbPaySettlePrepaymentDo convertTo(Long applyId, Integer cmStatus, Integer preStatus, String remarks, BigDecimal feeAmount) {
        CbPaySettlePrepaymentDo cbPaySettlePrepaymentDo = new CbPaySettlePrepaymentDo();
        cbPaySettlePrepaymentDo.setApplyId(applyId);
        cbPaySettlePrepaymentDo.setCmStatus(cmStatus);
        cbPaySettlePrepaymentDo.setPreStatus(preStatus);
        cbPaySettlePrepaymentDo.setPreSettleFee(feeAmount);
        cbPaySettlePrepaymentDo.setRemarks(remarks);
        cbPaySettlePrepaymentDo.setUpdateBy("System");
        return cbPaySettlePrepaymentDo;
    }

    public static CbPaySettlePrepaymentBo convertToPrepaymentBo(CacheMemberDto cacheMemberDto, FiCbPaySettleDo fiCbPaySettleDo, String settleAcc,
                                                                BigDecimal settleAmt, BigDecimal settleRate, String remarks) {
        CbPaySettlePrepaymentBo cbPaySettlePrepaymentBo = new CbPaySettlePrepaymentBo();
        cbPaySettlePrepaymentBo.setIncomeNo(fiCbPaySettleDo.getIncomeNo());
        cbPaySettlePrepaymentBo.setIncomeAmt(fiCbPaySettleDo.getIncomeAmt());
        cbPaySettlePrepaymentBo.setIncomeCcy(fiCbPaySettleDo.getIncomeCcy());
        cbPaySettlePrepaymentBo.setPreSettleRate(settleRate);
        cbPaySettlePrepaymentBo.setPreSettleAmt(settleAmt);
        cbPaySettlePrepaymentBo.setSettleAcc(settleAcc);
        cbPaySettlePrepaymentBo.setRemarks(remarks);
        cbPaySettlePrepaymentBo.setMemberId(Long.valueOf(cacheMemberDto.getMemberId()));
        cbPaySettlePrepaymentBo.setMemberName(cacheMemberDto.getName());
        return cbPaySettlePrepaymentBo;
    }

}
