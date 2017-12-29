package com.baofu.cbpayservice.service.convert;

import com.baofu.cbpayservice.biz.models.CbPaySumFileMqBo;
import com.baofu.cbpayservice.biz.models.FiCbpayFileUploadBo;
import com.baofu.cbpayservice.biz.models.ProxyCustomsMqBo;
import com.baofu.cbpayservice.common.constants.Constants;
import com.baofu.cbpayservice.common.enums.*;
import com.baofu.cbpayservice.facade.models.CbPaySumFileDto;
import com.baofu.cbpayservice.facade.models.OrderRemittanceDto;

import java.math.BigDecimal;

/**
 * 订单汇款(非文件上传订单)参数转换
 * <p>
 * User: 不良人 Date:2017/5/10 ProjectName: cbpayservice Version: 1.0
 */
public final class CbPayOrderRemittanceConvert {

    private CbPayOrderRemittanceConvert() {

    }

    /**
     * 提现文件批次信息转换
     *
     * @param orderRemittanceDto 提现文件批次请求参数
     * @param fileBatchNo        文件批次号
     * @return 文件批次信息
     */
    public static FiCbpayFileUploadBo toFiCbpayFileUploadBo(OrderRemittanceDto orderRemittanceDto, Long fileBatchNo) {

        FiCbpayFileUploadBo fiCbpayFileUploadBo = new FiCbpayFileUploadBo();
        fiCbpayFileUploadBo.setFileName(orderRemittanceDto.getFileName());
        fiCbpayFileUploadBo.setDfsFileId(orderRemittanceDto.getDfsFileId());
        fiCbpayFileUploadBo.setFileType(orderRemittanceDto.getFileType());
        fiCbpayFileUploadBo.setMemberId(orderRemittanceDto.getMemberId());
        fiCbpayFileUploadBo.setCreateBy(orderRemittanceDto.getCreateBy());

        fiCbpayFileUploadBo.setRecordCount(Integer.parseInt(Constants.ZERO));
        fiCbpayFileUploadBo.setSuccessCount(Integer.parseInt(Constants.ZERO));
        fiCbpayFileUploadBo.setFailCount(Integer.parseInt(Constants.ZERO));
        fiCbpayFileUploadBo.setStatus(UploadFileStatus.PROCESSING.getCode());
        fiCbpayFileUploadBo.setAuditStatus(UploadFileAuditStatus.INIT.getCode());
        fiCbpayFileUploadBo.setFileBatchNo(fileBatchNo);
        fiCbpayFileUploadBo.setFileType(UploadFileType.API_FILE.getCode());
        fiCbpayFileUploadBo.setOrderType(UploadFileOrderType.SERVICE_TRADE.getCode());
        fiCbpayFileUploadBo.setCareerType(CareerTypeEnum.GOODS_TRADE.getCode());
        return fiCbpayFileUploadBo;
    }

    /**
     * 发送mq信息转换
     *
     * @param orderRemittanceDto 提现文件批次请求参数
     * @param fileBatchNo        文件批次号
     * @return mq信息对象
     */
    public static ProxyCustomsMqBo toProxyCustomsMqBo(OrderRemittanceDto orderRemittanceDto, Long fileBatchNo) {

        ProxyCustomsMqBo proxyCustomsMqBo = new ProxyCustomsMqBo();
        proxyCustomsMqBo.setFileBatchNo(fileBatchNo);
        proxyCustomsMqBo.setMemberId(orderRemittanceDto.getMemberId());
        proxyCustomsMqBo.setDfsFileId(orderRemittanceDto.getDfsFileId());
        proxyCustomsMqBo.setFileType(orderRemittanceDto.getFileType());
        proxyCustomsMqBo.setCreateBy(orderRemittanceDto.getCreateBy());
        return proxyCustomsMqBo;
    }

    /**
     * 文件上传参数转换
     *
     * @param cbPaySumFileDto 请求参数
     * @param batchNo         文件批次号
     * @return FiCbpayFileUploadBo
     */
    public static FiCbpayFileUploadBo convertFileUpload(CbPaySumFileDto cbPaySumFileDto, Long batchNo) {
        FiCbpayFileUploadBo fiCbpayFileUploadBo = new FiCbpayFileUploadBo();
        fiCbpayFileUploadBo.setFileBatchNo(batchNo);
        fiCbpayFileUploadBo.setFileName(batchNo + ".xls");
        fiCbpayFileUploadBo.setRecordCount(0);
        fiCbpayFileUploadBo.setSuccessCount(0);
        fiCbpayFileUploadBo.setFailCount(0);
        fiCbpayFileUploadBo.setStatus(UploadFileStatus.CREATING.getCode());
        fiCbpayFileUploadBo.setDfsFileId(0L);
        fiCbpayFileUploadBo.setFileType(UploadFileType.API_FILE.getCode());
        fiCbpayFileUploadBo.setCreateBy(cbPaySumFileDto.getCreateBy());
        fiCbpayFileUploadBo.setOrderType("0");
        fiCbpayFileUploadBo.setTotalAmout(BigDecimal.ZERO);
        fiCbpayFileUploadBo.setMemberId(cbPaySumFileDto.getMemberId());
        fiCbpayFileUploadBo.setUpdateBy(cbPaySumFileDto.getCreateBy());
        fiCbpayFileUploadBo.setCareerType(CareerTypeEnum.GOODS_TRADE.getCode());
        return fiCbpayFileUploadBo;
    }

    /**
     * 发送mq信息转换
     *
     * @param cbPaySumFileDto 提现文件批次请求参数
     * @param fileBatchNo     文件批次号
     * @return mq信息对象
     */
    public static CbPaySumFileMqBo toSumFileMqBo(CbPaySumFileDto cbPaySumFileDto, Long fileBatchNo) {

        CbPaySumFileMqBo cbPaySumFileMqBo = new CbPaySumFileMqBo();
        cbPaySumFileMqBo.setFileBatchNo(fileBatchNo);
        cbPaySumFileMqBo.setMemberId(cbPaySumFileDto.getMemberId());
        cbPaySumFileMqBo.setBeginTime(cbPaySumFileDto.getBeginTime());
        cbPaySumFileMqBo.setEndTime(cbPaySumFileDto.getEndTime());
        cbPaySumFileMqBo.setCreateBy(cbPaySumFileDto.getCreateBy());
        return cbPaySumFileMqBo;
    }
}
