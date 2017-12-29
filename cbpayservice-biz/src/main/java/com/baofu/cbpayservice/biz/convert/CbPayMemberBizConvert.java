package com.baofu.cbpayservice.biz.convert;

import com.baofu.cbpayservice.biz.models.CbPayMemberRateReqBo;
import com.baofu.cbpayservice.dal.models.FiCbPayMemberRateDo;

/**
 * User: yangjian  Date: 2017-05-15 ProjectName:  Version: 1.0
 */
public final class CbPayMemberBizConvert {

    private CbPayMemberBizConvert() {

    }

    /**
     * @param cbPayMemberRateReqBo 费率请求参数
     * @return FiCbPayMemberRateDo
     */
    public static FiCbPayMemberRateDo paramConvert(CbPayMemberRateReqBo cbPayMemberRateReqBo) {

        FiCbPayMemberRateDo fiCbPayMemberRateDo = new FiCbPayMemberRateDo();
        fiCbPayMemberRateDo.setRecordId(cbPayMemberRateReqBo.getRecordId());
        fiCbPayMemberRateDo.setMemberId(cbPayMemberRateReqBo.getMemberId());
        fiCbPayMemberRateDo.setCcy(cbPayMemberRateReqBo.getCcy());
        fiCbPayMemberRateDo.setMemberRate(cbPayMemberRateReqBo.getMemberRate());
        fiCbPayMemberRateDo.setStatus(cbPayMemberRateReqBo.getStatus());
        fiCbPayMemberRateDo.setId(cbPayMemberRateReqBo.getId());
        fiCbPayMemberRateDo.setCreateAt(cbPayMemberRateReqBo.getCreateAt());
        fiCbPayMemberRateDo.setCreateBy(cbPayMemberRateReqBo.getCreateBy());
        fiCbPayMemberRateDo.setUpdateAt(cbPayMemberRateReqBo.getUpdateAt());
        fiCbPayMemberRateDo.setUpdateBy(cbPayMemberRateReqBo.getUpdateBy());
        fiCbPayMemberRateDo.setBusinessType(cbPayMemberRateReqBo.getBusinessType());
        fiCbPayMemberRateDo.setMemberRateBp(cbPayMemberRateReqBo.getMemberRateBp());
        fiCbPayMemberRateDo.setRateSetType(cbPayMemberRateReqBo.getRateSetType());
        fiCbPayMemberRateDo.setBeginDate(cbPayMemberRateReqBo.getBeginDate());
        fiCbPayMemberRateDo.setEndDate(cbPayMemberRateReqBo.getEndDate());
        return fiCbPayMemberRateDo;
    }

    /**
     * @param fiCbPayMemberRateDo 费率请求参数
     * @return FiCbPayMemberRateDo
     */
    public static CbPayMemberRateReqBo paramConvert(FiCbPayMemberRateDo fiCbPayMemberRateDo) {

        CbPayMemberRateReqBo cbPayMemberRateReqBo = new CbPayMemberRateReqBo();
        cbPayMemberRateReqBo.setRecordId(fiCbPayMemberRateDo.getRecordId());
        cbPayMemberRateReqBo.setMemberId(fiCbPayMemberRateDo.getMemberId());
        cbPayMemberRateReqBo.setCcy(fiCbPayMemberRateDo.getCcy());
        cbPayMemberRateReqBo.setMemberRate(fiCbPayMemberRateDo.getMemberRate());
        cbPayMemberRateReqBo.setStatus(fiCbPayMemberRateDo.getStatus());
        cbPayMemberRateReqBo.setId(fiCbPayMemberRateDo.getId());
        cbPayMemberRateReqBo.setCreateAt(fiCbPayMemberRateDo.getCreateAt());
        cbPayMemberRateReqBo.setCreateBy(fiCbPayMemberRateDo.getCreateBy());
        cbPayMemberRateReqBo.setUpdateAt(fiCbPayMemberRateDo.getUpdateAt());
        cbPayMemberRateReqBo.setUpdateBy(fiCbPayMemberRateDo.getUpdateBy());
        cbPayMemberRateReqBo.setBusinessType(fiCbPayMemberRateDo.getBusinessType());
        cbPayMemberRateReqBo.setMemberRateBp(fiCbPayMemberRateDo.getMemberRateBp());
        cbPayMemberRateReqBo.setRateSetType(fiCbPayMemberRateDo.getRateSetType());
        cbPayMemberRateReqBo.setBeginDate(fiCbPayMemberRateDo.getBeginDate());
        cbPayMemberRateReqBo.setEndDate(fiCbPayMemberRateDo.getEndDate());
        return cbPayMemberRateReqBo;
    }
}
