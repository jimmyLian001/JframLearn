package com.baofu.cbpayservice.biz.convert;

import com.baofoo.cache.service.facade.utils.SecurityUtil;
import com.baofu.cbpayservice.biz.models.*;
import com.baofu.cbpayservice.common.constants.Constants;
import com.baofu.cbpayservice.common.constants.NumberConstants;
import com.baofu.cbpayservice.common.enums.*;
import com.baofu.cbpayservice.dal.models.*;
import com.baofu.order.pick.model.Order;
import com.baofu.order.pick.model.PickOrderSum;
import org.apache.commons.lang.StringUtils;
import org.slf4j.MDC;
import org.slf4j.helpers.SystemMarker;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 批量汇款接口biz层参数转换
 * <p>
 * </p>
 * User: 不良人 Date:2017/10/20 ProjectName: cbpayservice Version: 1.0
 */
public final class BatchRemitBizConvert {

    private BatchRemitBizConvert() {
    }

    /**
     * 文件信息转换
     *
     * @param remitFileBo 文件上传请求信息
     * @param fileBatchNo 批次号
     * @return 文件信息
     */
    public static FiCbpayFileUploadBo toFiCbPayFileUploadBo(RemitFileBo remitFileBo, Long fileBatchNo) {

        FiCbpayFileUploadBo fiCbpayFileUploadBo = new FiCbpayFileUploadBo();
        fiCbpayFileUploadBo.setFileBatchNo(fileBatchNo);
        fiCbpayFileUploadBo.setFileName(remitFileBo.getFileName());
        fiCbpayFileUploadBo.setDfsFileId(remitFileBo.getDfsFileId());
        fiCbpayFileUploadBo.setFileType(remitFileBo.getFileType());
        fiCbpayFileUploadBo.setMemberId(remitFileBo.getMemberId());
        fiCbpayFileUploadBo.setCreateBy(remitFileBo.getCreateBy());
        fiCbpayFileUploadBo.setFailCount(NumberConstants.ZERO);
        fiCbpayFileUploadBo.setRecordCount(NumberConstants.ZERO);
        fiCbpayFileUploadBo.setSuccessCount(NumberConstants.ZERO);
        fiCbpayFileUploadBo.setStatus(UploadFileStatus.PROCESSING.getCode());
        fiCbpayFileUploadBo.setAuditStatus(UploadFileAuditStatus.INIT.getCode());
        fiCbpayFileUploadBo.setOrderType(UploadFileOrderType.SERVICE_TRADE.getCode());
        return fiCbpayFileUploadBo;
    }

    /**
     * 代理跨境结算mq消息内容对象
     *
     * @param remitFileBo 文件请求信息
     * @param fileBatchNo 文件批次号
     * @return 代理跨境结算对象
     */
    public static ProxyCustomsMqBo toProxyCustomsMqBo(RemitFileBo remitFileBo, Long fileBatchNo) {

        ProxyCustomsMqBo mqBo = new ProxyCustomsMqBo();
        mqBo.setFileBatchNo(fileBatchNo);
        mqBo.setDfsFileId(remitFileBo.getDfsFileId());
        mqBo.setMemberId(remitFileBo.getMemberId());
        mqBo.setFileType(remitFileBo.getFileType());
        mqBo.setCreateBy(remitFileBo.getCreateBy());
        mqBo.setSourceFlag(NumberConstants.THREE);
        return mqBo;
    }

    /**
     * 试算返回参数转换
     *
     * @param rate            汇率
     * @param balanceAllMoney 汇款金额
     * @param feeResult       手续费
     * @param batchRemitBo    批量汇款参数
     * @return 返回参数
     */
    public static BatchRemitTrialBo toBatchRemitTrialBo(BigDecimal rate, BigDecimal balanceAllMoney,
                                                        MemberFeeResBo feeResult, BatchRemitBo batchRemitBo) {

        BatchRemitTrialBo remitTrialBo = new BatchRemitTrialBo();
        remitTrialBo.setAccountInfo(batchRemitBo.getAccountInfo());
        remitTrialBo.setRemitAmt(batchRemitBo.getRemitAmt());
        remitTrialBo.setSellRateOfCcy(rate);
        remitTrialBo.setTargetAmt(balanceAllMoney);
        remitTrialBo.setTargetCcy(batchRemitBo.getTargetCcy());
        remitTrialBo.setTransferFee(feeResult.getFeeAmount());
        return remitTrialBo;
    }

    /**
     * 批量汇款-创建文件批次信息
     *
     * @param bo          批量汇款参数
     * @param pick        凑单汇总
     * @param fileBatchNo 文件批次
     * @return 文件批次订单信息
     */
    public static FiCbPayFileUploadDo toFiCbPayFileUploadDo(BatchRemitBo bo, PickOrderSum pick, Long fileBatchNo) {

        FiCbPayFileUploadDo uploadDo = new FiCbPayFileUploadDo();
        uploadDo.setFileName("批量汇款_" + bo.getMemberId() + ".xls");
        if(StringUtils.isNotBlank(bo.getFileName())){
            uploadDo.setFileName(bo.getFileName() + ".xls");
        }
        uploadDo.setRecordCount(pick.getTotalNum());
        uploadDo.setSuccessCount(pick.getTotalNum());
        uploadDo.setTotalAmount(pick.getTotalAmt());
        uploadDo.setFileBatchNo(fileBatchNo);
        uploadDo.setMemberId(bo.getMemberId());
        uploadDo.setCreateBy(bo.getCreateBy());
        uploadDo.setFailCount(NumberConstants.ZERO);
        uploadDo.setStatus(UploadFileStatus.PASS.getCode());
        uploadDo.setDfsFileId(Long.parseLong(String.valueOf(NumberConstants.ZERO)));
        uploadDo.setFileType(UploadFileType.SYSTEM_REMIT_FILE.getCode());
        uploadDo.setOrderType(UploadFileOrderType.SERVICE_TRADE.getCode());
        uploadDo.setAuditStatus(UploadFileAuditStatus.PASS.getCode());
        uploadDo.setCareerType(CareerTypeEnum.GOODS_TRADE.getCode());
        return uploadDo;
    }

    /**
     * 系统订单创建
     *
     * @param order       系统订单信息
     * @param orderId     订单ID
     * @param bo          批量汇款参数
     * @param fileBatchNo 文件批次号
     * @return 订单信息
     */
    public static FiCbPayOrderDo toFiCbPayOrderDo(Order order, Long orderId, BatchRemitBo bo, Long fileBatchNo) {

        FiCbPayOrderDo fiCbPayOrderDo = new FiCbPayOrderDo();
        fiCbPayOrderDo.setOrderId(orderId);
        fiCbPayOrderDo.setMemberId(bo.getMemberId());
        fiCbPayOrderDo.setMemberTransId(String.valueOf(orderId));
        fiCbPayOrderDo.setOrderMoney(order.getOrderAmt());
        fiCbPayOrderDo.setOrderCcy(CcyEnum.CNY.getKey());
        fiCbPayOrderDo.setTransMoney(order.getOrderAmt());
        fiCbPayOrderDo.setTransCcy(CcyEnum.CNY.getKey());
        fiCbPayOrderDo.setProductId(ProductAndFunctionEnum.PRO_1016.getProductId());
        fiCbPayOrderDo.setFunctionId(ProductAndFunctionEnum.PRO_1016.getFunctionId());
        fiCbPayOrderDo.setPayId(NumberConstants.ZERO);
        fiCbPayOrderDo.setChannelId(NumberConstants.ZERO);
        fiCbPayOrderDo.setPayStatus(Constants.BATCH_UPLOAD_TRUE_STATUS);
        fiCbPayOrderDo.setOrderCompleteTime(new Date());
        fiCbPayOrderDo.setTransRate(new BigDecimal(String.valueOf(NumberConstants.ONE)));
        fiCbPayOrderDo.setTerminalId(NumberConstants.ZERO);
        fiCbPayOrderDo.setTransTime(new Date());
        fiCbPayOrderDo.setTransFee(new BigDecimal(NumberConstants.ONE));
        fiCbPayOrderDo.setOrderType(OrderType.BATCH_REMIT_ORDER.getCode());
        fiCbPayOrderDo.setRemittanceStatus(RemittanceStatus.PROCESSING.getCode());
        fiCbPayOrderDo.setBatchFileId(fileBatchNo);
        fiCbPayOrderDo.setAckStatus(OrederAckStatusEnum.YES.getCode());
        fiCbPayOrderDo.setCareerType(CareerTypeEnum.GOODS_TRADE.getCode());
        fiCbPayOrderDo.setId(fiCbPayOrderDo.getId());
        return fiCbPayOrderDo;
    }

    /**
     * 系统订单商品信息
     *
     * @param orderDo 汇款订单
     * @return 商品信息
     */
    public static FiCbPayOrderItemDo toFiCbPayOrderItemDo(FiCbPayOrderDo orderDo) {

        FiCbPayOrderItemDo itemDo = new FiCbPayOrderItemDo();
        itemDo.setOrderId(orderDo.getOrderId());
        itemDo.setCommodityAmount(NumberConstants.ONE);
        itemDo.setCommodityName(Constants.PRODUCT_NAME);
        itemDo.setCommodityPrice(orderDo.getOrderMoney());
        itemDo.setCreateBy(orderDo.getCreateBy());
        itemDo.setUpdateBy(orderDo.getUpdateBy());
        itemDo.setRemarks(orderDo.getRemarks());
        return itemDo;
    }

    /**
     * 系统订单附加信息信息
     *
     * @param orderDo 请求订单信息
     * @return 网关订单附加信息
     */
    public static FiCbPayOrderAdditionDo additionConvert(FiCbPayOrderDo orderDo) {

        FiCbPayOrderAdditionDo fiCbPayOrderAdditionDo = new FiCbPayOrderAdditionDo();
        fiCbPayOrderAdditionDo.setOrderId(orderDo.getOrderId());
        fiCbPayOrderAdditionDo.setBankCardNo(SecurityUtil.desEncrypt(Constants.BANK_CARD_NO, Constants.CARD_DES_PASSWD));
        fiCbPayOrderAdditionDo.setIdCardNo(Constants.ID_CARD_NO);
        fiCbPayOrderAdditionDo.setIdHolder(Constants.ID_HOLDER);
        fiCbPayOrderAdditionDo.setClientIp("0.0.0.0");
        fiCbPayOrderAdditionDo.setIsBankAccount("Y");
        fiCbPayOrderAdditionDo.setPayeeIdType(NumberConstants.ONE);
        return fiCbPayOrderAdditionDo;
    }

    /**
     * 批量汇款-创建文件mq信息参数
     *
     * @param batchRemitBo 汇款信息
     * @param fileBatchNo  文件批次
     * @return mq参数
     */
    public static CreateOrderDetailBo toCreateOrderDetailBo(BatchRemitBo batchRemitBo, Long fileBatchNo) {

        CreateOrderDetailBo detailBo = new CreateOrderDetailBo();
        detailBo.setFileBatchNo(fileBatchNo);
        detailBo.setMemberId(batchRemitBo.getMemberId());
        detailBo.setTraceLogId(MDC.get(SystemMarker.TRACE_LOG_ID));
        detailBo.setCareerType(CareerTypeEnum.GOODS_TRADE.getCode());
        return detailBo;
    }

    /**
     * 跨境订单接口请求参数转换成Biz层请求参数信息
     *
     * @param remitBo 汇款参数
     * @param batchNo 汇款批次号
     * @param bankDo  账户信息
     * @return 汇款订单信息
     */
    public static FiCbPayRemittanceDo remittanceParamConvert(BatchRemitBo remitBo, Long batchNo,
                                                             FiCbPaySettleBankDo bankDo) {
        FiCbPayRemittanceDo fiCbPayRemittanceDo = new FiCbPayRemittanceDo();
        fiCbPayRemittanceDo.setBatchNo(String.valueOf(batchNo));
        fiCbPayRemittanceDo.setMemberNo(remitBo.getMemberId());
        fiCbPayRemittanceDo.setTransMoney(remitBo.getRemitAmt());
        fiCbPayRemittanceDo.setTransCcy(CcyEnum.CNY.getKey());
        fiCbPayRemittanceDo.setSwiftCode(bankDo.getBankSwiftCode());
        fiCbPayRemittanceDo.setTransType(TransType.CASH.getCode());
        fiCbPayRemittanceDo.setChannelId(BigDecimal.ZERO.longValue());
        fiCbPayRemittanceDo.setAuditStatus(AuditStatus.INIT.getCode());
        fiCbPayRemittanceDo.setChannelStatus(ChannelStatus.FALSE.getCode());
        fiCbPayRemittanceDo.setBankCardNo(bankDo.getBankAccNo());
        fiCbPayRemittanceDo.setSystemRate(BigDecimal.ZERO);
        fiCbPayRemittanceDo.setRealRate(BigDecimal.ZERO);
        fiCbPayRemittanceDo.setSystemMoney(BigDecimal.ZERO);
        fiCbPayRemittanceDo.setSystemCcy(remitBo.getTargetCcy());
        fiCbPayRemittanceDo.setApplyStatus(ApplyStatus.INIT.getCode());
        fiCbPayRemittanceDo.setCmStatus(CMStatus.INIT.getCode());
        fiCbPayRemittanceDo.setRemarks("订单余额不足或账户余额不足");
        fiCbPayRemittanceDo.setTransFee(BigDecimal.ZERO);
        fiCbPayRemittanceDo.setOrderType(RemittanceOrderType.REMITTANCE_ORDER.getCode());
        fiCbPayRemittanceDo.setBusinessNo(StringUtils.leftPad(String.valueOf(batchNo), NumberConstants.THIRTY_TWO,
                "0"));
        fiCbPayRemittanceDo.setExchangeType(NumberConstants.ZERO);
        // 购汇状态 1：已购汇 0：未购汇
        fiCbPayRemittanceDo.setPurchaseStatus(NumberConstants.ZERO);
        fiCbPayRemittanceDo.setRemitMoney(BigDecimal.ZERO);
        return fiCbPayRemittanceDo;
    }

    /**
     * 创建汇款文件批次参数转换
     *
     * @param reqBo 汇款参数请求
     * @return 创建汇款文件批次参数
     */
    public static BatchRemitBo toBatchRemitBo(CbPayRemittanceReqBo reqBo) {

        BatchRemitBo batchRemitBo = new BatchRemitBo();
        batchRemitBo.setTargetCcy(reqBo.getTargetCcy());
        batchRemitBo.setRemitAmt(reqBo.getOriginalAmt());
        batchRemitBo.setEntityNo(reqBo.getEntityNo());
        batchRemitBo.setMemberId(reqBo.getMemberId());
        batchRemitBo.setCreateBy(reqBo.getCreateBy());
        return batchRemitBo;
    }

    /**
     * 跨境订单接口请求参数转换成Biz层请求参数信息
     *
     * @param remitBo 汇款参数
     * @param batchNo 汇款批次号
     * @param bankDo  账户信息
     * @return 汇款附加信息
     */
    public static FiCbPayRemittanceAdditionDo additionParamConvert(BatchRemitBo remitBo, Long batchNo,
                                                                   FiCbPaySettleBankDo bankDo) {
        FiCbPayRemittanceAdditionDo additionDo = new FiCbPayRemittanceAdditionDo();
        additionDo.setBatchNo(String.valueOf(batchNo));
        additionDo.setMemberNo(remitBo.getMemberId());
        additionDo.setOceanic(bankDo.getOceanicFlag());
        additionDo.setCostBorne(bankDo.getCostBorne());
        additionDo.setBankName(bankDo.getBankName());
        additionDo.setBankAccName(bankDo.getBankAccName());
        additionDo.setBankAccNo(bankDo.getBankAccNo());
        additionDo.setEntityNo(remitBo.getEntityNo());
        return additionDo;
    }
}
