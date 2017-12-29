package com.baofu.cbpayservice.biz;

import com.baofu.cbpayservice.biz.models.RemitFileUploadBo;

/**
 * 上传文件服务
 * <p>
 * User: 不良人 Date:2017/9/28 ProjectName: cbpayservice Version: 1.0
 */
public interface FileUploadBiz {

    /**
     * 汇款上传文件
     *
     * @param remitFileUploadBo 上传文件信息
     * @return 文件批次号
     */
    Long remitFileUpload(RemitFileUploadBo remitFileUploadBo);
}
