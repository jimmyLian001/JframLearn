package com.baofu.cbpayservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文件类型 0:汇款文件、1：结汇文件
 * <p>
 * User: 不良人 Date:2017/02/22 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@AllArgsConstructor
public enum UploadFileType {

    /**
     * 0-汇款文件
     */
    REMITTANCE_FILE("0", "汇款文件"),

    /**
     * 1-结汇文件
     */
    SETTLE_FILE("1", "结汇文件"),

    /**
     * 2-API订单汇款文件
     */
    API_FILE("2", "API订单汇款文件"),

    /**
     * 3-批量汇款文件
     */
    BATCH_REMIT_FILE("3", "批量汇款文件"),

    /**
     * 4-系统汇款文件
     */
    SYSTEM_REMIT_FILE("4", "系统汇款文件"),

    ;

    /**
     * 值
     */
    private String code;

    /**
     * 描述
     */
    private String desc;
}
