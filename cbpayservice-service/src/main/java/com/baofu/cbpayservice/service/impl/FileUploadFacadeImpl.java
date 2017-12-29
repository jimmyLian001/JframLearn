package com.baofu.cbpayservice.service.impl;

import com.baofu.cbpayservice.biz.FileUploadBiz;
import com.baofu.cbpayservice.service.convert.FileUploadConvert;
import com.baofu.cbpayservice.facade.FileUploadFacade;
import com.baofu.cbpayservice.facade.models.RemitFileUploadDto;
import com.system.commons.result.Result;
import com.system.commons.utils.ExceptionUtils;
import com.system.commons.utils.ParamValidate;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.slf4j.helpers.SystemMarker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 上传文件服务
 * <p>
 * User: 不良人 Date:2017/9/28 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
@Service
public class FileUploadFacadeImpl implements FileUploadFacade {

    /**
     * 上传文件服务
     */
    @Autowired
    private FileUploadBiz fileUploadBiz;

    /**
     * 汇款上传文件
     *
     * @param remitFileUploadDto 上传文件信息
     * @param traceLogId         上传文件信息
     * @return 文件批次号
     */
    @Override
    public Result<Long> remitFileUpload(RemitFileUploadDto remitFileUploadDto, String traceLogId) {

        MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
        log.info("call 汇款API，上传文件参数:{}", remitFileUploadDto);

        Result<Long> result;
        try {
            ParamValidate.validateParams(remitFileUploadDto);
            Long fileBatchNo = fileUploadBiz.remitFileUpload(FileUploadConvert.toRemitFileUploadDto(remitFileUploadDto));
            result = new Result<>(fileBatchNo);
        } catch (Exception e) {
            log.error("call 跨境结汇，上传文件异常", e);
            result = ExceptionUtils.getResponse(e);
        }

        log.info("call 跨境结汇,上传文件返回结果:{}", result);
        return result;
    }
}
