package com.baofu.cbpayservice.facade;

import com.baofu.cbpayservice.facade.models.RemitFileUploadDto;
import com.system.commons.result.Result;

/**
 * 上传文件服务
 * <p>
 * User: 不良人 Date:2017/9/28 ProjectName: cbpayservice Version: 1.0
 */
public interface FileUploadFacade {

    /**
     * 汇款上传文件
     *
     * @param remitFileUploadDto 上传文件信息
     * @param traceLogId         上传文件信息
     * @return 文件批次号
     */
    Result<Long> remitFileUpload(RemitFileUploadDto remitFileUploadDto, String traceLogId);
}
