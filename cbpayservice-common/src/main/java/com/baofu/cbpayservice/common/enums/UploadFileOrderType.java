package com.baofu.cbpayservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文件服务类型 0:服务贸易类型、1：货物贸易类型
 * User: 不良人 Date:2017/02/22 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@AllArgsConstructor
public enum UploadFileOrderType {

    /**
     * 服务贸易类型
     */
    SERVICE_TRADE("0", "服务贸易类型"),

    /**
     * 货物贸易类型
     */
    GOODS_TRADE("1", "货物贸易类型"),

    /**
     * 未知贸易类型
     */
    ERROR_TYPE("2", "未知贸易类型");

    /**
     * 值
     */
    private String code;

    /**
     * 描述
     */
    private String desc;
}
