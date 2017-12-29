package com.baofu.cbpayservice.service.convert;

import com.baofu.cbpayservice.biz.models.FiCbpayFileUploadBo;
import com.baofu.cbpayservice.biz.models.RemitFileUploadBo;
import com.baofu.cbpayservice.common.constants.Constants;
import com.baofu.cbpayservice.common.enums.UploadFileAuditStatus;
import com.baofu.cbpayservice.common.enums.UploadFileOrderType;
import com.baofu.cbpayservice.common.enums.UploadFileStatus;
import com.baofu.cbpayservice.facade.models.ProxyCustomsDto;
import com.baofu.cbpayservice.facade.models.RemitFileUploadDto;

/**
 * 上传文件参数转换
 * <p>
 * User: 不良人 Date:2017/9/28 ProjectName: cbpayservice Version: 1.0
 */
public class FileUploadConvert {

    /**
     * 汇款API文件上传参数转换
     * @param remitFileUploadDto 汇款API文件上传参数
     * @return 结果
     */
    public static RemitFileUploadBo toRemitFileUploadDto(RemitFileUploadDto remitFileUploadDto) {

        RemitFileUploadBo remitFileUploadBo = new RemitFileUploadBo();
        remitFileUploadBo.setMemberId(Long.parseLong(remitFileUploadDto.getMemberId()));
        remitFileUploadBo.setTerminalId(Long.parseLong(remitFileUploadDto.getTerminalId()));
        remitFileUploadBo.setMemberReqId(remitFileUploadDto.getMemberReqId());
        remitFileUploadBo.setOrderFileName(remitFileUploadDto.getOrderFileName());
        remitFileUploadBo.setNotifyUrl(remitFileUploadDto.getNotifyUrl());
        remitFileUploadBo.setDfsFileId(remitFileUploadDto.getDfsFileId());
        return remitFileUploadBo;
    }

    /**
     * 汇款API文件上传参数转换
     * @param proxyCustomsDto 汇款API文件上传参数
     * @return 结果
     */
    public static RemitFileUploadBo toFileDto(ProxyCustomsDto proxyCustomsDto) {

        RemitFileUploadBo remitFileUploadBo = new RemitFileUploadBo();
        remitFileUploadBo.setMemberId(proxyCustomsDto.getMemberId());
        return remitFileUploadBo;
    }

}
