package com.baofu.international.global.account.core.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述：文件服务类型 0:服务贸易类型、1：货物贸易类型
 * User: feng_jiang Date:2017/11/5 ProjectName: globalaccount-core Version: 1.0
 */
@Getter
@AllArgsConstructor
public enum UploadFileOrderEnum {

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
