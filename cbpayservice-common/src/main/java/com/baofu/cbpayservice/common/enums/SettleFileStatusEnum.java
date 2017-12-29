package com.baofu.cbpayservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文件状态：1-待上传，2-上传成功，3-上传失败
 * <p>
 * User: wanght Date:2016/12/26 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@AllArgsConstructor
public enum SettleFileStatusEnum {

    /**
     * 待上传
     */
    WAIT_UPLOAD(1, "待上传"),

    /**
     * 上传成功
     */
    UPLOAD_SUCCESS(2, "上传成功"),

    /**
     * 上传失败
     */
    UPLOAD_FAIL(3, "上传失败"),

    /**
     * 上传处理中
     */
    UPLOAD_PROCESS(4, "上传处理中"),

    /**
     * 上传处理中
     */
    UPLOAD_DATA_ERROR(5, "上传数据有误"),;

    /**
     * 文件状态码
     */
    private Integer code;

    /**
     * 描述
     */
    private String desc;
}
