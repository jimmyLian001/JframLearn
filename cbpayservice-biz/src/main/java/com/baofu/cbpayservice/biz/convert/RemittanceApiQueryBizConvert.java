package com.baofu.cbpayservice.biz.convert;

import com.baofu.cbpayservice.biz.models.RemitDetailsQueryResultBo;
import com.baofu.cbpayservice.biz.models.RemitOrderApiQueryResultBo;
import com.baofu.cbpayservice.common.constants.NumberConstants;
import com.baofu.cbpayservice.common.util.other.FormatUtil;
import com.baofu.cbpayservice.dal.models.FiCbPayFileUploadDo;
import com.baofu.cbpayservice.dal.models.FiCbPayMemberApiRqstDo;
import com.baofu.cbpayservice.dal.models.FiCbPayRemittanceDo;
import com.baofu.cbpayservice.dal.models.FiCbPaySettleBankDo;

/**
 * 描述
 * <p>
 * User: 不良人 Date:2017/9/22 ProjectName: cbpayservice Version: 1.0
 */
public class RemittanceApiQueryBizConvert {

    /**
     * 对象转换
     *
     * @param remittanceDo 汇款订单信息
     * @param settleBankDo 账户信息
     * @return 查询结果对象
     */
    public static RemitOrderApiQueryResultBo toRemitOrderApiQueryResultBo(FiCbPayRemittanceDo remittanceDo,
                                                                       FiCbPaySettleBankDo settleBankDo,
                                                                       FiCbPayMemberApiRqstDo fiCbpayMemberApiRqstDo) {

        RemitOrderApiQueryResultBo remitOrderApiQueryResultBo = new RemitOrderApiQueryResultBo();

        remitOrderApiQueryResultBo.setBatchNo(FormatUtil.toString(fiCbpayMemberApiRqstDo.getBusinessNo()));
        remitOrderApiQueryResultBo.setMemberId(fiCbpayMemberApiRqstDo.getMemberId());
        remitOrderApiQueryResultBo.setMemberReqId(fiCbpayMemberApiRqstDo.getMemberReqId());
        remitOrderApiQueryResultBo.setTerminalId(fiCbpayMemberApiRqstDo.getTerminalId());

        if("INIT".equals(remittanceDo.getChannelStatus())){
            remitOrderApiQueryResultBo.setPurchaseStatus(NumberConstants.ONE);
        }else if("TRUE".equals(remittanceDo.getChannelStatus())){
            remitOrderApiQueryResultBo.setPurchaseStatus(NumberConstants.FOUR);
        }else if("PROCESSING".equals(remittanceDo.getChannelStatus())){
            remitOrderApiQueryResultBo.setPurchaseStatus(NumberConstants.TWO);
        }else{
            remitOrderApiQueryResultBo.setPurchaseStatus(NumberConstants.THREE);
        }

            remitOrderApiQueryResultBo.setTransMoney(remittanceDo.getTransMoney());
            remitOrderApiQueryResultBo.setTransCcy(remittanceDo.getTransCcy());
            remitOrderApiQueryResultBo.setTransFee(remittanceDo.getTransFee());
            remitOrderApiQueryResultBo.setSystemMoney(remittanceDo.getSystemMoney());
            remitOrderApiQueryResultBo.setSystemCcy(remittanceDo.getSystemCcy());
            remitOrderApiQueryResultBo.setSystemRate(remittanceDo.getSystemRate());
            remitOrderApiQueryResultBo.setAccountName(settleBankDo.getBankAccName());
            remitOrderApiQueryResultBo.setBankName(settleBankDo.getBankName());
            remitOrderApiQueryResultBo.setAccountNumber(settleBankDo.getBankAccNo());
            remitOrderApiQueryResultBo.setRemitSuccDate(remittanceDo.getRemitSuccDate());

        return remitOrderApiQueryResultBo;
    }

    /**
     * 对象转换
     *
     * @param fiCbPayFileUploadDo 文件上传信息
     * @param memberReqId 商户申请流水号
     * @param errorFileName 错误文件名
     * @return 查询结果对象
     */
    public static RemitDetailsQueryResultBo toRemitDetailsQueryResultBo(FiCbPayFileUploadDo fiCbPayFileUploadDo,
                                                            String memberReqId,String errorFileName,Long terminalId){

        RemitDetailsQueryResultBo queryResultBo = new RemitDetailsQueryResultBo();

        queryResultBo.setMemberId(fiCbPayFileUploadDo.getMemberId());
        queryResultBo.setFailCount(fiCbPayFileUploadDo.getFailCount());
        queryResultBo.setFileBatchNo(FormatUtil.toString(fiCbPayFileUploadDo.getFileBatchNo()));
        queryResultBo.setRecordCount(fiCbPayFileUploadDo.getRecordCount());
        queryResultBo.setSuccessCount(fiCbPayFileUploadDo.getSuccessCount());
        queryResultBo.setStatus(fiCbPayFileUploadDo.getStatus());
        queryResultBo.setTotalAmount(FormatUtil.toString(fiCbPayFileUploadDo.getTotalAmount()));
        queryResultBo.setTerminalId(terminalId);
        queryResultBo.setMemberReqId(memberReqId);
        queryResultBo.setErrorFileName(errorFileName);

        return queryResultBo;

    }

}
