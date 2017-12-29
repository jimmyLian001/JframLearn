package com.baofu.cbpayservice.service.convert;

import com.baofu.cbpayservice.biz.models.*;
import com.baofu.cbpayservice.facade.models.*;
import com.baofu.cbpayservice.facade.models.res.SettleNotifyRespDto;

/**
 * 描述
 * <p/>
 * User: 不良人 Date:2017/4/14 ProjectName: cbpayservice Version: 1.0
 */
public class CbPaySettleConvert {

    /**
     * 结汇文件上传参数转换
     *
     * @param settleFileUploadDto 结汇文件上传请求参数
     * @return 结汇文件上传参数
     */
    public static SettleFileUploadReqBo toSettleFileUploadReqBo(SettleFileUploadDto settleFileUploadDto) {

        SettleFileUploadReqBo settleFileUploadReqBo = new SettleFileUploadReqBo();
        settleFileUploadReqBo.setMemberId(settleFileUploadDto.getMemberId());
        settleFileUploadReqBo.setSettleOrderId(settleFileUploadDto.getSettleOrderId());
        settleFileUploadReqBo.setDfsFileId(settleFileUploadDto.getDfsFileId());
        settleFileUploadReqBo.setFileType(settleFileUploadDto.getFileType());
        settleFileUploadReqBo.setFileName(settleFileUploadDto.getFileName());
        settleFileUploadReqBo.setMemberName(settleFileUploadDto.getMemberName());
        settleFileUploadReqBo.setCreateBy(settleFileUploadDto.getCreateBy());
        return settleFileUploadReqBo;
    }

    /**
     * 结汇文件上传明细校验工具参数转换
     *
     * @param settleFileVerifyDto 结汇文件上传请求参数
     * @return 结汇文件上传参数
     */
    public static SettleFileUploadReqBo toSettleFileVerifyReqBo(SettleFileVerifyDto settleFileVerifyDto) {

        SettleFileUploadReqBo settleFileUploadReqBo = new SettleFileUploadReqBo();
        settleFileUploadReqBo.setMemberId(settleFileVerifyDto.getMemberId());
        settleFileUploadReqBo.setDfsFileId(settleFileVerifyDto.getDfsFileId());
        settleFileUploadReqBo.setFileType(settleFileVerifyDto.getFileType());
        settleFileUploadReqBo.setFileName(settleFileVerifyDto.getFileName());
        settleFileUploadReqBo.setMemberName(settleFileVerifyDto.getMemberName());
        settleFileUploadReqBo.setCreateBy(settleFileVerifyDto.getCreateBy());

        settleFileUploadReqBo.setManCcy(settleFileVerifyDto.getManCcy());
        settleFileUploadReqBo.setManTotalMoney(settleFileVerifyDto.getManTotalMoney());
        return settleFileUploadReqBo;
    }

    /**
     * 结汇文件上传参数转换(API)
     *
     * @param settleFileUploadApiDto 结汇文件上传请求参数
     * @return 结汇文件上传参数
     */
    public static SettleFileUploadReqBo toSettleFileUploadReqApiBo(SettleFileUploadApiDto settleFileUploadApiDto) {

        SettleFileUploadReqBo settleFileUploadReqBo = new SettleFileUploadReqBo();
        settleFileUploadReqBo.setMemberId(settleFileUploadApiDto.getMemberId());
        settleFileUploadReqBo.setIncomeNo(settleFileUploadApiDto.getIncomeNo());
        settleFileUploadReqBo.setDfsFileId(settleFileUploadApiDto.getDfsFileId());
        settleFileUploadReqBo.setFileType(settleFileUploadApiDto.getFileType());
        settleFileUploadReqBo.setFileName(settleFileUploadApiDto.getFileName());
        settleFileUploadReqBo.setMemberName(settleFileUploadApiDto.getMemberName());
        settleFileUploadReqBo.setCreateBy(settleFileUploadApiDto.getCreateBy());
        return settleFileUploadReqBo;
    }

    /**
     * 商户发起汇入申请
     *
     * @param settleIncomeApplyDto 汇入申请参数
     * @return 汇入申请Bo对象
     */
    public static SettleIncomeApplyBo toSettleIncomeApplyBo(SettleIncomeApplyDto settleIncomeApplyDto) {

        SettleIncomeApplyBo settleIncomeApplyBo = new SettleIncomeApplyBo();
        settleIncomeApplyBo.setMemberId(settleIncomeApplyDto.getMemberId());
        settleIncomeApplyBo.setTerminalId(settleIncomeApplyDto.getTerminalId());
        settleIncomeApplyBo.setIncomeNo(settleIncomeApplyDto.getIncomeNo());
        settleIncomeApplyBo.setOrderAmt(settleIncomeApplyDto.getOrderAmt());
        settleIncomeApplyBo.setOrderCcy(settleIncomeApplyDto.getOrderCcy());
        settleIncomeApplyBo.setIncomeAccountName(settleIncomeApplyDto.getIncomeAccountName());
        settleIncomeApplyBo.setIncomeAccount(settleIncomeApplyDto.getIncomeAccount());
        settleIncomeApplyBo.setPaymentFileId(Long.parseLong(settleIncomeApplyDto.getPaymentFileId()));
        settleIncomeApplyBo.setRemittanceAcc(settleIncomeApplyDto.getRemittanceAcc());
        settleIncomeApplyBo.setRemittanceCountry(settleIncomeApplyDto.getRemittanceCountry());
        settleIncomeApplyBo.setRemittanceName(settleIncomeApplyDto.getRemittanceName());
        settleIncomeApplyBo.setIncomeBankName(settleIncomeApplyDto.getIncomeBankName());
        settleIncomeApplyBo.setNotifyUrl(settleIncomeApplyDto.getNotifyUrl());
        settleIncomeApplyBo.setSettleDFSId(settleIncomeApplyDto.getSettleDFSId());
        settleIncomeApplyBo.setFileName(settleIncomeApplyDto.getFileName());
        settleIncomeApplyBo.setMemberName(settleIncomeApplyDto.getMemberName());
        return settleIncomeApplyBo;
    }

    /**
     * 后台审核参数转换
     *
     * @param checkDto 后台审核请求参数
     * @return 后台审核参数
     */
    public static ReceiveAuditBo toSettleIncomeApplyCheckBo(ReceiverAuditDto checkDto) {

        ReceiveAuditBo settleIncomeApplyCheckBo = new ReceiveAuditBo();
        settleIncomeApplyCheckBo.setMemberId(checkDto.getMemberId());
        settleIncomeApplyCheckBo.setOrderId(checkDto.getOrderId());
        settleIncomeApplyCheckBo.setCmAuditStatus(checkDto.getCmAuditState());

        return settleIncomeApplyCheckBo;
    }

    /**
     * 运营设置商户编号转换
     *
     * @param settleOperationSetDto 运营设置商户编号参数
     * @return 运营设置商户编号对象
     */
    public static SettleOperationSetReqBo toSettleOperationSetReqBo(SettleOperationSetDto settleOperationSetDto) {

        SettleOperationSetReqBo settleOperationSetReqBo = new SettleOperationSetReqBo();
        settleOperationSetReqBo.setMemberId(settleOperationSetDto.getMemberId());
        settleOperationSetReqBo.setApplyNo(settleOperationSetDto.getApplyNo());
        settleOperationSetReqBo.setOrderId(settleOperationSetDto.getOrderId());
        return settleOperationSetReqBo;
    }

    /**
     * 运营设置匹配状态参数转换
     *
     * @param operateConfirmDto 运营设置匹配状态请求参数
     * @return 运营设置匹配状态参数
     */
    public static OperateConfirmBo toOperateConfirmBo(OperateConfirmDto operateConfirmDto) {
        OperateConfirmBo operateConfirmBo = new OperateConfirmBo();
        operateConfirmBo.setMemberId(operateConfirmDto.getMemberId());
        operateConfirmBo.setApplyNo(operateConfirmDto.getApplyNo());
        operateConfirmBo.setMatchingStatus(operateConfirmDto.getMatchingStatus());
        operateConfirmBo.setBeforeMatchingStatus(operateConfirmDto.getBeforeMatchingStatus());
        operateConfirmBo.setIncomeNo(operateConfirmDto.getIncomeNo());
        operateConfirmBo.setRemarks(operateConfirmDto.getRemarks());
        return operateConfirmBo;
    }

    /**
     * 收汇补录参数转换
     *
     * @param collectionMakeupDto 收汇补录请求参数
     * @return 收汇补录
     */
    public static CollectionMakeupBo toCollectionMakeupBo(CollectionMakeupDto collectionMakeupDto) {

        CollectionMakeupBo collectionMakeupBo = new CollectionMakeupBo();
        collectionMakeupBo.setMemberId(collectionMakeupDto.getMemberId());
        collectionMakeupBo.setChannelId(collectionMakeupDto.getChannelId());
        collectionMakeupBo.setIncomeNo(collectionMakeupDto.getIncomeNo());
        collectionMakeupBo.setIncomeAmt(collectionMakeupDto.getIncomeAmt());
        collectionMakeupBo.setIncomeCcy(collectionMakeupDto.getIncomeCcy());
        collectionMakeupBo.setIncomeAt(collectionMakeupDto.getIncomeAt());
        collectionMakeupBo.setBankName(collectionMakeupDto.getBankName());
        return collectionMakeupBo;
    }

    /**
     * 结汇API查询结果查询返回参数信息转换
     *
     * @param settleNotifyBo 通知商户父类
     * @return 返回参数信息转换
     */
    public static SettleNotifyRespDto paramConvert(SettleNotifyMemberBo settleNotifyBo) {

        SettleNotifyRespDto settleNotifyRespDto = new SettleNotifyRespDto();
        settleNotifyRespDto.setOrderAmt(settleNotifyBo.getOrderAmt());
        settleNotifyRespDto.setOrderCcy(settleNotifyBo.getOrderCcy());
        settleNotifyRespDto.setRemitAmt(settleNotifyBo.getRemitAmt());
        settleNotifyRespDto.setRemitFee(settleNotifyBo.getRemitFee());
        settleNotifyRespDto.setProcessStatus(settleNotifyBo.getProcessStatus());
        settleNotifyRespDto.setRealSettleAmt(settleNotifyBo.getRealSettleAmt());
        settleNotifyRespDto.setExchangeRate(settleNotifyBo.getExchangeRate());
        settleNotifyRespDto.setExchangeAmt(settleNotifyBo.getExchangeAmt());
        settleNotifyRespDto.setSettleFee(settleNotifyBo.getSettleFee());
        settleNotifyRespDto.setSettleAmt(settleNotifyBo.getSettleAmt());
        settleNotifyRespDto.setMemberId(settleNotifyBo.getMemberId());
        settleNotifyRespDto.setTerminalId(settleNotifyBo.getTerminalId());
        settleNotifyRespDto.setRemitReqNo(settleNotifyBo.getRemitReqNo());
        settleNotifyRespDto.setRemarks(settleNotifyBo.getRemarks());
        settleNotifyRespDto.setErrorFileName(settleNotifyBo.getErrorFileName());

        return settleNotifyRespDto;
    }

    /**
     * 结汇账户管理参数转换
     *
     * @param settleAccountDto 结汇账户管理请求参数
     * @return 收汇补录
     */
    public static CbPaySettleAccountBo toCbPaySettleAccountBo(SettleAccountDto settleAccountDto) {

        CbPaySettleAccountBo cbPaySettleAccountBo = new CbPaySettleAccountBo();
        cbPaySettleAccountBo.setMemberId(settleAccountDto.getMemberId());
        cbPaySettleAccountBo.setCreateBy(settleAccountDto.getCreateBy());
        cbPaySettleAccountBo.setIncomeAccountName(settleAccountDto.getIncomeAccountName());
        cbPaySettleAccountBo.setIncomeAccountNo(settleAccountDto.getIncomeAccountNo());
        cbPaySettleAccountBo.setIncomeCountry(settleAccountDto.getIncomeCountry());
        return cbPaySettleAccountBo;
    }

    /**
     * 运营设置匹配状态参数转换
     *
     * @param accountArtificialMatchDto 运营设置匹配状态请求参数
     * @return 运营设置匹配状态参数
     */
    public static AccountArtificialMatchBo toAccountArtificialMatchDto(AccountArtificialMatchDto accountArtificialMatchDto) {
        AccountArtificialMatchBo accountArtificialMatchBo = new AccountArtificialMatchBo();
        accountArtificialMatchBo.setOrderId(accountArtificialMatchDto.getOrderId());
        accountArtificialMatchBo.setIncomeNo(accountArtificialMatchDto.getIncomeNo());
        accountArtificialMatchBo.setChannelId(accountArtificialMatchDto.getChannelId());
        accountArtificialMatchBo.setMemberId(accountArtificialMatchDto.getMemberId());
        return accountArtificialMatchBo;
    }

    /**
     * 结汇申请汇款凭证对象参数转换
     * @param settlePaymentFileUploadDto 结汇申请汇款凭证信息
     * @return paymentFileUploadBo
     */
    public static SettlePaymentFileUploadBo toSettlePaymentFileUploadBo(SettlePaymentFileUploadDto settlePaymentFileUploadDto) {
        SettlePaymentFileUploadBo paymentFileUploadBo = new SettlePaymentFileUploadBo();
        paymentFileUploadBo.setMemberId(settlePaymentFileUploadDto.getMemberId());
        paymentFileUploadBo.setApplyNo(settlePaymentFileUploadDto.getApplyNo());
        paymentFileUploadBo.setIncomeNo(settlePaymentFileUploadDto.getIncomeNo());
        paymentFileUploadBo.setPaymentFileId(settlePaymentFileUploadDto.getPaymentFileId());
        return paymentFileUploadBo;
    }
}
