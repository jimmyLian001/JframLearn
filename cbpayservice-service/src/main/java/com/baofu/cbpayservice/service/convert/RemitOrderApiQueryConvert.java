package com.baofu.cbpayservice.service.convert;

import com.baofu.cbpayservice.biz.models.RemitDetailsQueryResultBo;
import com.baofu.cbpayservice.biz.models.RemitOrderApiQueryResultBo;
import com.baofu.cbpayservice.facade.models.res.RemitDetailsQueryRespDto;
import com.baofu.cbpayservice.facade.models.res.RemitOrderQueryRespDto;

/**
 * API汇款信息查询接口参数转换
 * <p>
 * User: 不良人 Date:2017/9/22 ProjectName: cbpayservice Version: 1.0
 */
public class RemitOrderApiQueryConvert {

    /**
     * API汇款信息查询信息返回参数转换
     *
     * @param queryResultBo 汇款查询信息
     * @return 汇款信息返回参数
     */
    public static RemitOrderQueryRespDto toRemitOrderQueryRespDto(RemitOrderApiQueryResultBo queryResultBo) {

        RemitOrderQueryRespDto remitOrderQueryRespDto = new RemitOrderQueryRespDto();
        remitOrderQueryRespDto.setMemberId(queryResultBo.getMemberId());
        remitOrderQueryRespDto.setBatchNo(queryResultBo.getBatchNo());
        remitOrderQueryRespDto.setTransMoney(queryResultBo.getTransMoney());
        remitOrderQueryRespDto.setTransCcy(queryResultBo.getTransCcy());
        remitOrderQueryRespDto.setTransFee(queryResultBo.getTransFee());
        remitOrderQueryRespDto.setSystemMoney(queryResultBo.getSystemMoney());
        remitOrderQueryRespDto.setSystemCcy(queryResultBo.getSystemCcy());
        remitOrderQueryRespDto.setAccountNumber(queryResultBo.getAccountNumber());
        remitOrderQueryRespDto.setAccountName(queryResultBo.getAccountName());
        remitOrderQueryRespDto.setSystemRate(queryResultBo.getSystemRate());
        remitOrderQueryRespDto.setRemitSuccDate(queryResultBo.getRemitSuccDate());
        remitOrderQueryRespDto.setFlag(queryResultBo.getRemitResult());
        remitOrderQueryRespDto.setErrorMsg(queryResultBo.getErrorMsg());
        remitOrderQueryRespDto.setPurchaseStatus(queryResultBo.getPurchaseStatus());

        remitOrderQueryRespDto.setTerminalId(queryResultBo.getTerminalId());
        remitOrderQueryRespDto.setMemberReqId(queryResultBo.getMemberReqId());
        remitOrderQueryRespDto.setBankName(queryResultBo.getBankName());
        return remitOrderQueryRespDto;
    }

    /**
     * API订单明细上传查询信息返回参数转换
     * @param queryResultBo 订单明细上传查询
     * @return 订单明细返回参数
     */
    public static RemitDetailsQueryRespDto toRemitDetailsQueryRespDto(RemitDetailsQueryResultBo queryResultBo){

        RemitDetailsQueryRespDto dot = new RemitDetailsQueryRespDto();

        dot.setMemberReqId(queryResultBo.getMemberReqId());
        dot.setMemberId(queryResultBo.getMemberId());
        dot.setFileBatchNo(queryResultBo.getFileBatchNo());
        dot.setFailCount(queryResultBo.getFailCount());
        dot.setErrorFileName(queryResultBo.getErrorFileName());
        dot.setStatus(queryResultBo.getStatus());
        dot.setRecordCount(queryResultBo.getRecordCount());
        dot.setSuccessCount(queryResultBo.getSuccessCount());
        dot.setTerminalId(queryResultBo.getTerminalId());
        dot.setTotalAmount(queryResultBo.getTotalAmount());

         return dot;
    }
}
