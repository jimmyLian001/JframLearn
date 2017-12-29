package com.baofu.cbpayservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 产品对应功能集合
 * User: 不良人 Date:2017/02/22 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@AllArgsConstructor
public enum UploadFileAuditStatus {

    /**
     * 代理跨境人民币结算
     */
    INIT("0", "初始"),

    /**
     * 代理跨境外汇结算
     */
    PASS("1", "审核通过"),

    /**
     * 跨境人民币结算
     */
    NO_PASS("2", "审核不通过"),;

    /**
     * 值
     */
    private String code;

    /**
     * 描述
     */
    private String desc;
}
