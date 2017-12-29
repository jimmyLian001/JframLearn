package com.baofu.cbpayservice.biz.convert;

import com.baofu.cbpayservice.biz.models.FiCbpayFileUploadBo;
import com.baofu.cbpayservice.biz.models.ProxyCustomsMqBo;
import com.baofu.cbpayservice.biz.models.RemitFileUploadBo;
import com.baofu.cbpayservice.common.constants.Constants;
import com.baofu.cbpayservice.common.constants.NumberConstants;
import com.baofu.cbpayservice.common.enums.UploadFileAuditStatus;
import com.baofu.cbpayservice.common.enums.UploadFileOrderType;
import com.baofu.cbpayservice.common.enums.UploadFileStatus;
import com.baofu.cbpayservice.common.enums.UploadFileType;
import com.baofu.cbpayservice.dal.models.FiCbPayMemberApiRqstDo;

/**
 * 上传文件参数转换
 * <p>
 * User: 不良人 Date:2017/9/28 ProjectName: cbpayservice Version: 1.0
 */
public class FileUploadBizConvert {

    /**
     * 上传文件发送mq对象转换
     *
     * @param fileBatchNo       文件批次号
     * @param remitFileUploadBo 上传文件参数
     * @return mq参数
     */
    public static ProxyCustomsMqBo toProxyCustomsMqBo(Long fileBatchNo, RemitFileUploadBo remitFileUploadBo) {
        ProxyCustomsMqBo proxyCustomsMqBo = new ProxyCustomsMqBo();
        proxyCustomsMqBo.setFileBatchNo(fileBatchNo);
        proxyCustomsMqBo.setMemberId(remitFileUploadBo.getMemberId());
        proxyCustomsMqBo.setDfsFileId(remitFileUploadBo.getDfsFileId());
        proxyCustomsMqBo.setFileType(UploadFileType.REMITTANCE_FILE.getCode());
        proxyCustomsMqBo.setSourceFlag(NumberConstants.TWO);
        proxyCustomsMqBo.setCreateBy("SYSTEM");
        return proxyCustomsMqBo;
    }

    /**
     * 上传文件信息转换
     *
     * @param uploadBo    mq对象
     * @param fileBatchNo 文件批次号
     * @return 文件批次信息
     */
    public static FiCbpayFileUploadBo toFiCbPayFileUploadBo(RemitFileUploadBo uploadBo, Long fileBatchNo) {

        FiCbpayFileUploadBo fiCbpayFileUploadBo = new FiCbpayFileUploadBo();
        fiCbpayFileUploadBo.setFileBatchNo(fileBatchNo);
        fiCbpayFileUploadBo.setDfsFileId(uploadBo.getDfsFileId());
        fiCbpayFileUploadBo.setFileName(uploadBo.getOrderFileName());
        fiCbpayFileUploadBo.setMemberId(uploadBo.getMemberId());
        fiCbpayFileUploadBo.setCreateBy("SYSTEM");
        fiCbpayFileUploadBo.setRecordCount(Integer.parseInt(Constants.ZERO));
        fiCbpayFileUploadBo.setSuccessCount(Integer.parseInt(Constants.ZERO));
        fiCbpayFileUploadBo.setFailCount(Integer.parseInt(Constants.ZERO));
        fiCbpayFileUploadBo.setStatus(UploadFileStatus.PROCESSING.getCode());
        fiCbpayFileUploadBo.setFileType(UploadFileType.REMITTANCE_FILE.getCode());
        fiCbpayFileUploadBo.setAuditStatus(UploadFileAuditStatus.INIT.getCode());
        fiCbpayFileUploadBo.setOrderType(UploadFileOrderType.SERVICE_TRADE.getCode());
        return fiCbpayFileUploadBo;
    }

    /**
     * 商户接口请求参数组织
     *
     * @param uploadBo    上传文件信息
     * @param fileBatchNo 文件批次
     * @return 商户接口请求对象
     */
    public static FiCbPayMemberApiRqstDo toFiCbPayMemberApiRqstDo(RemitFileUploadBo uploadBo, Long fileBatchNo) {

        FiCbPayMemberApiRqstDo rqstDo = new FiCbPayMemberApiRqstDo();
        rqstDo.setMemberId(uploadBo.getMemberId());
        rqstDo.setTerminalId(uploadBo.getTerminalId());
        rqstDo.setMemberReqId(uploadBo.getMemberReqId());
        rqstDo.setBusinessType(String.valueOf(NumberConstants.ELEVEN));
        rqstDo.setBusinessNo(fileBatchNo);
        rqstDo.setNotifyUrl(uploadBo.getNotifyUrl());
        rqstDo.setProcessStatus(NumberConstants.ONE);
        return rqstDo;
    }
}
