package com.baofu.cbpayservice.biz.convert;

import com.baofoo.cbcgw.facade.dto.gw.base.CgwBaseRespDto;
import com.baofoo.cbcgw.facade.dto.gw.response.result.CgwRemitResultDto;
import com.baofu.accountcenter.service.facade.dto.req.RechargeReqDto;
import com.baofu.accountcenter.service.facade.dto.req.TransferReqDto;
import com.baofu.accountcenter.service.facade.dto.req.WithdrawReqDto;
import com.baofu.cbpayservice.biz.models.CbPayRemittanceAuditReqBo;
import com.baofu.cbpayservice.biz.models.CbPayRemittanceBankFeeReqBo;
import com.baofu.cbpayservice.biz.models.CbPayRemtStatusChangeReqBo;
import com.baofu.cbpayservice.common.enums.ApplyStatus;
import com.baofu.cbpayservice.common.enums.CmOrderSubTypeEnum;
import com.baofu.cbpayservice.dal.models.FiCbPayOrderDo;
import com.baofu.cbpayservice.dal.models.FiCbPayRemittanceDo;

/**
 * Service层参数转换
 * <p>
 * 1、跨境人民币订单状态修正请求参数信息转换
 * </p>
 * User: wanght Date:2016/11/28 ProjectName: cbpayservice Version: 1.0
 */
public final class CbPayRemittanceConvert {

    private CbPayRemittanceConvert() {

    }

    /**
     * 跨境人民币订单状态修正请求参数信息转换
     *
     * @param remittanceStatus 汇款状态
     * @param updateBy         更新人
     * @param orderId          宝付订单号
     * @return FiCbPayOrderDo
     */
    public static FiCbPayOrderDo paramConvert(String remittanceStatus, String updateBy, Long orderId) {
        FiCbPayOrderDo fiCbPayOrderDo = new FiCbPayOrderDo();
        fiCbPayOrderDo.setOrderId(orderId);
        fiCbPayOrderDo.setRemittanceStatus(remittanceStatus);
        fiCbPayOrderDo.setUpdateBy(updateBy);
        return fiCbPayOrderDo;
    }

    /**
     * 跨境人民币汇款订单自动审核请求参数信息转换
     *
     * @param fiCbPayRemittanceDo 汇款信息
     * @return CbPayRemittanceAuditReqBo
     */
    public static CbPayRemittanceAuditReqBo autoAuditParamConvert(FiCbPayRemittanceDo fiCbPayRemittanceDo) {
        CbPayRemittanceAuditReqBo cbPayRemittanceAuditReqBo = new CbPayRemittanceAuditReqBo();
        cbPayRemittanceAuditReqBo.setMemberId(fiCbPayRemittanceDo.getMemberNo());
        cbPayRemittanceAuditReqBo.setAuditStatus(ApplyStatus.TRUE.getCode());
        cbPayRemittanceAuditReqBo.setAuditBy("SYSTEM");
        cbPayRemittanceAuditReqBo.setBatchNo(fiCbPayRemittanceDo.getBatchNo());
        cbPayRemittanceAuditReqBo.setTargetCcy(fiCbPayRemittanceDo.getSystemCcy());
        cbPayRemittanceAuditReqBo.setExchangeType(fiCbPayRemittanceDo.getExchangeType());

        return cbPayRemittanceAuditReqBo;
    }

    /**
     * 后台审核对象转换
     *
     * @param cbPayRemtStatusChangeReqBo 汇款状态更新信息
     * @param fiCbPayRemittanceDo        汇款信息
     * @return FiCbPayRemittanceDo
     */
    public static FiCbPayRemittanceDo toFiCbPayRemittanceDo(CbPayRemtStatusChangeReqBo cbPayRemtStatusChangeReqBo,
                                                            FiCbPayRemittanceDo fiCbPayRemittanceDo) {
        fiCbPayRemittanceDo.setAuditStatus(cbPayRemtStatusChangeReqBo.getAuditStatus());
        fiCbPayRemittanceDo.setChannelStatus(cbPayRemtStatusChangeReqBo.getChannelStatus());
        fiCbPayRemittanceDo.setCmStatus(cbPayRemtStatusChangeReqBo.getCmStatus());
        fiCbPayRemittanceDo.setBeforeAuditStatus(cbPayRemtStatusChangeReqBo.getBeforeAuditStatus());
        fiCbPayRemittanceDo.setRemarks(cbPayRemtStatusChangeReqBo.getRemarks());
        fiCbPayRemittanceDo.setBatchNo(cbPayRemtStatusChangeReqBo.getBatchNo());
        fiCbPayRemittanceDo.setExchangeType(cbPayRemtStatusChangeReqBo.getExchangeType());
        return fiCbPayRemittanceDo;
    }

    /**
     * 后台审核对象转换
     *
     * @param cbPayRemtStatusChangeReqBo 汇款状态更新信息
     * @return FiCbPayRemittanceDo
     */
    public static FiCbPayRemittanceDo toFiCbPayRemittanceDo(CbPayRemtStatusChangeReqBo cbPayRemtStatusChangeReqBo) {
        FiCbPayRemittanceDo fiCbPayRemittanceDo = new FiCbPayRemittanceDo();
        fiCbPayRemittanceDo.setAuditStatus(cbPayRemtStatusChangeReqBo.getAuditStatus());
        fiCbPayRemittanceDo.setChannelStatus(cbPayRemtStatusChangeReqBo.getChannelStatus());
        fiCbPayRemittanceDo.setCmStatus(cbPayRemtStatusChangeReqBo.getCmStatus());
        fiCbPayRemittanceDo.setBeforeAuditStatus(cbPayRemtStatusChangeReqBo.getBeforeAuditStatus());
        fiCbPayRemittanceDo.setRemarks(cbPayRemtStatusChangeReqBo.getRemarks());
        fiCbPayRemittanceDo.setBatchNo(cbPayRemtStatusChangeReqBo.getBatchNo());
        fiCbPayRemittanceDo.setExchangeType(cbPayRemtStatusChangeReqBo.getExchangeType());
        return fiCbPayRemittanceDo;
    }

    /**
     * 付汇到账手续费更新请求参数信息转换
     *
     * @param cbPayRemittanceBankFeeReqBo 付汇到账手续费更新请求参数
     * @return Manager层请求参数信息
     */
    public static FiCbPayRemittanceDo bankFeeParamConvert(CbPayRemittanceBankFeeReqBo cbPayRemittanceBankFeeReqBo) {
        FiCbPayRemittanceDo fiCbPayRemittanceDo = new FiCbPayRemittanceDo();
        fiCbPayRemittanceDo.setMemberNo(cbPayRemittanceBankFeeReqBo.getMemberId());
        fiCbPayRemittanceDo.setBatchNo(cbPayRemittanceBankFeeReqBo.getBatchNo());
        fiCbPayRemittanceDo.setUpdateBy(cbPayRemittanceBankFeeReqBo.getUpdateBy());
        fiCbPayRemittanceDo.setRemarks(cbPayRemittanceBankFeeReqBo.getRemarks());
        fiCbPayRemittanceDo.setBankFee(cbPayRemittanceBankFeeReqBo.getBankFee());
        fiCbPayRemittanceDo.setFeeStatus(cbPayRemittanceBankFeeReqBo.getBankFeeStatus());
        fiCbPayRemittanceDo.setReceiptId(cbPayRemittanceBankFeeReqBo.getReceiptId());
        fiCbPayRemittanceDo.setExchangeType(cbPayRemittanceBankFeeReqBo.getExchangeType());
        return fiCbPayRemittanceDo;
    }

    /**
     * 购汇成功后，对用户的外币账户进行充值
     *
     * @param cgwRemitBatchResultDto 传入参数
     * @param fiCbPayRemittanceDo    传入参数
     * @return 返回结果
     */
    public static RechargeReqDto toRechargeReqDto(CgwRemitResultDto cgwRemitBatchResultDto,
                                                  FiCbPayRemittanceDo fiCbPayRemittanceDo) {

        RechargeReqDto rechargeReqDto = new RechargeReqDto();

        rechargeReqDto.setChannelId(fiCbPayRemittanceDo.getChannelId());
        rechargeReqDto.setMemberId(fiCbPayRemittanceDo.getMemberNo());
        rechargeReqDto.setOrderCcy(cgwRemitBatchResultDto.getCgwExChangeRespDto().getExcHCurr());
        rechargeReqDto.setOrderAmt(cgwRemitBatchResultDto.getCgwExChangeRespDto().getExcHAmt());
        rechargeReqDto.setOrderId(Long.parseLong(cgwRemitBatchResultDto.getRemSerialNo()));
        rechargeReqDto.setOrderSubType(CmOrderSubTypeEnum.ACCOUNT_RECHARGE.getCode());

        return rechargeReqDto;
    }

    /**
     * 付汇申请成功后，对用户的外币账户进行充值
     *
     * @param cgwBaseResultDO     传入参数
     * @param fiCbPayRemittanceDo 传入参数
     * @return 返回结果
     */
    public static TransferReqDto toTransferReqDto(CgwBaseRespDto cgwBaseResultDO,
                                                  FiCbPayRemittanceDo fiCbPayRemittanceDo) {

        TransferReqDto transferReqDto = new TransferReqDto();

        transferReqDto.setTargetMemberId(fiCbPayRemittanceDo.getMemberNo());
        transferReqDto.setMemberId(fiCbPayRemittanceDo.getMemberNo());
        transferReqDto.setOrderAmt(fiCbPayRemittanceDo.getSystemMoney());
        transferReqDto.setOrderCcy(fiCbPayRemittanceDo.getSystemCcy());
        transferReqDto.setOrderId(Long.valueOf(cgwBaseResultDO.getBfBatchId()));
        transferReqDto.setOrderSubType(CmOrderSubTypeEnum.ACCOUNT_TRANSFER.getCode());

        return transferReqDto;
    }

    /**
     * 购汇成功后，对用户的外币账户进行充值
     *
     * @param cgwRemitBatchResultDto 传入参数
     * @param fiCbPayRemittanceDo    传入参数
     * @return 返回结果
     */
    public static WithdrawReqDto toWithdrawReqDto(CgwRemitResultDto cgwRemitBatchResultDto,
                                                  FiCbPayRemittanceDo fiCbPayRemittanceDo) {

        WithdrawReqDto withdrawReqDto = new WithdrawReqDto();
        withdrawReqDto.setMemberId(fiCbPayRemittanceDo.getMemberNo());
        withdrawReqDto.setChannelId(fiCbPayRemittanceDo.getChannelId());
        withdrawReqDto.setOrderSubType(CmOrderSubTypeEnum.ACCOUNT_WITHDRAW.getCode());
        withdrawReqDto.setOrderId(Long.valueOf(cgwRemitBatchResultDto.getRemSerialNo()));
        withdrawReqDto.setOrderCcy(fiCbPayRemittanceDo.getSystemCcy());
        withdrawReqDto.setOrderAmt(fiCbPayRemittanceDo.getSystemMoney());
        withdrawReqDto.setActualCcy(fiCbPayRemittanceDo.getTransCcy());
        withdrawReqDto.setActualAmt(fiCbPayRemittanceDo.getTransMoney());
        withdrawReqDto.setTargetCcy(fiCbPayRemittanceDo.getTransCcy());
        withdrawReqDto.setTargetAmt(fiCbPayRemittanceDo.getTransMoney());
        withdrawReqDto.setTargetRate(fiCbPayRemittanceDo.getSystemRate());
        withdrawReqDto.setActualRate(fiCbPayRemittanceDo.getSystemRate());

        return withdrawReqDto;
    }

}
