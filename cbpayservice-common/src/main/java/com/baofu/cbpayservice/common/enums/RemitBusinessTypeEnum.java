package com.baofu.cbpayservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 业务类型
 * User: feng_jiang
 */
@Getter
@AllArgsConstructor
public enum RemitBusinessTypeEnum {

    /**
     * 11-购付汇订单明细上传，
     */
    REMIT_FILE_UPLOAD("11", "购付汇订单明细上传"),

    /**
     * 12-购付汇跨境汇款申请
     */
    REMIT_APPLY("12", "购付汇跨境汇款申请"),

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
