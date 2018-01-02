package com.baofu.international.global.account.core.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述：文件类型 0:汇款文件、1：结汇文件
 * User: feng_jiang Date:2017/11/5 ProjectName: globalaccount-core Version: 1.0
 */
@Getter
@AllArgsConstructor
public enum UploadFileEnum {

    /**
     * 0-汇款文件
     */
    REMITTANCE_FILE("0", "汇款文件"),

    /**
     * 1-结汇文件
     */
    SETTLE_FILE("1", "结汇文件"),;

    /**
     * 值
     */
    private String code;

    /**
     * 描述
     */
    private String desc;
}
