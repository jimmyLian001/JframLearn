package com.baofu.cbpayservice.biz.convert;

import com.baofu.cbpayservice.biz.models.ApiRemitCbPayNotfiyBo;
import com.baofu.cbpayservice.biz.models.CbPayRemittanceReqBo;
import com.baofu.cbpayservice.dal.models.FiCbPayMemberApiRqstDo;
import com.baofu.cbpayservice.dal.models.FiCbPayRemittanceAdditionDo;
import com.baofu.cbpayservice.dal.models.FiCbPayRemittanceDo;
import com.system.commons.utils.DateUtil;

/**
 * API汇款结果通知商户信息转换类
 * <p>
 * Created by 莫小阳 on 2017/9/29.
 */
public final class ApiCbPayRemitNotifyConvert {

    private ApiCbPayRemitNotifyConvert() {
    }
    /**
     * 组装通知商户信息
     *
     * @param fiCbPayRemittanceDo         汇款订单信息
     * @param fiCbPayRemittanceAdditionDo 汇款订单附加信息
     * @param fiCbPayMemberApiRqstDo      汇款申请信息
     * @param remitStatus                 汇款状态：3-汇款失败 4-汇款成功
     * @param remitFailMsg                汇款失败原因
     * @return 结果
     */
    public static ApiRemitCbPayNotfiyBo convertApiRemitCbPayNotfiyBo(FiCbPayRemittanceDo fiCbPayRemittanceDo,
                                                                     FiCbPayMemberApiRqstDo fiCbPayMemberApiRqstDo,
                                                                     FiCbPayRemittanceAdditionDo fiCbPayRemittanceAdditionDo,
                                                                     int remitStatus, String remitFailMsg) {
        ApiRemitCbPayNotfiyBo apiRemitCbPayNotfiyBo = new ApiRemitCbPayNotfiyBo();
        apiRemitCbPayNotfiyBo.setMemberId(fiCbPayMemberApiRqstDo.getMemberId());
        apiRemitCbPayNotfiyBo.setTerminalId(fiCbPayMemberApiRqstDo.getTerminalId());
        apiRemitCbPayNotfiyBo.setRemitApplyNo(fiCbPayMemberApiRqstDo.getMemberReqId());
        apiRemitCbPayNotfiyBo.setRemitNo(String.valueOf(fiCbPayMemberApiRqstDo.getBusinessNo()));
        apiRemitCbPayNotfiyBo.setOriginalCcy(fiCbPayRemittanceDo.getTransCcy());
        apiRemitCbPayNotfiyBo.setOriginalAmt(fiCbPayRemittanceDo.getTransMoney());
        apiRemitCbPayNotfiyBo.setBankAccName(fiCbPayRemittanceAdditionDo.getBankAccName());
        apiRemitCbPayNotfiyBo.setBankName(fiCbPayRemittanceAdditionDo.getBankName());
        apiRemitCbPayNotfiyBo.setBankAccNo(fiCbPayRemittanceAdditionDo.getBankAccNo());
        apiRemitCbPayNotfiyBo.setRemitCcy(fiCbPayRemittanceDo.getSystemCcy());
        apiRemitCbPayNotfiyBo.setRemitAmt(fiCbPayRemittanceDo.getSystemMoney());
        apiRemitCbPayNotfiyBo.setRemitRate(fiCbPayRemittanceDo.getSystemRate());
        apiRemitCbPayNotfiyBo.setRemitFee(fiCbPayRemittanceDo.getTransFee());
        apiRemitCbPayNotfiyBo.setSuccessTime(fiCbPayRemittanceDo.getRemitSuccDate() == null ? ""
                : DateUtil.format(fiCbPayRemittanceDo.getRemitSuccDate(), DateUtil.fullPattern));
        apiRemitCbPayNotfiyBo.setRemitStatus(remitStatus);
        apiRemitCbPayNotfiyBo.setRemitFailMsg(remitFailMsg);
        return apiRemitCbPayNotfiyBo;
    }


    /**
     * 组装通知商户信息
     *
     * @param cbPayRemittanceReqBo 汇款订单信息
     * @param remitStatus          汇款状态：3-汇款失败 4-汇款成功
     * @param remitFailMsg         汇款失败原因
     * @return 结果
     */
    public static ApiRemitCbPayNotfiyBo convertApiRemitCbPayNotfiyBo(CbPayRemittanceReqBo cbPayRemittanceReqBo,
                                                                     int remitStatus, String remitFailMsg) {
        ApiRemitCbPayNotfiyBo apiRemitCbPayNotfiyBo = new ApiRemitCbPayNotfiyBo();
        apiRemitCbPayNotfiyBo.setMemberId(cbPayRemittanceReqBo.getMemberId());
        apiRemitCbPayNotfiyBo.setTerminalId(cbPayRemittanceReqBo.getTerminalId());
        apiRemitCbPayNotfiyBo.setRemitApplyNo(cbPayRemittanceReqBo.getRemitApplyNo());
        apiRemitCbPayNotfiyBo.setRemitNo("");
        apiRemitCbPayNotfiyBo.setOriginalCcy(cbPayRemittanceReqBo.getOriginalCcy());
        apiRemitCbPayNotfiyBo.setOriginalAmt(cbPayRemittanceReqBo.getOriginalAmt());
        apiRemitCbPayNotfiyBo.setBankAccName(cbPayRemittanceReqBo.getBankAccName());
        apiRemitCbPayNotfiyBo.setBankName(cbPayRemittanceReqBo.getBankName());
        apiRemitCbPayNotfiyBo.setBankAccNo(cbPayRemittanceReqBo.getBankAccNo());
        apiRemitCbPayNotfiyBo.setRemitCcy(cbPayRemittanceReqBo.getRemitCcy());
        apiRemitCbPayNotfiyBo.setRemitAmt(null);
        apiRemitCbPayNotfiyBo.setRemitRate(null);
        apiRemitCbPayNotfiyBo.setRemitFee(null);
        apiRemitCbPayNotfiyBo.setSuccessTime(null);
        apiRemitCbPayNotfiyBo.setRemitStatus(remitStatus);
        apiRemitCbPayNotfiyBo.setRemitFailMsg(remitFailMsg);
        return apiRemitCbPayNotfiyBo;
    }


}
