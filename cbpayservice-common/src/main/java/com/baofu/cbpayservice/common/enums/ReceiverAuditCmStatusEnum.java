package com.baofu.cbpayservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 清算处理流程，20-初始状态，21-初审，22-复审
 * <p>
 * User: wanght Date:2016/12/26 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@AllArgsConstructor
public enum ReceiverAuditCmStatusEnum {

    /**
     * 初始状态
     */
    INIT(20, "初始状态"),

    /**
     * 初审
     */
    FIRST_CHECK(21, "初审通过"),

    /**
     * 复审
     */
    SECOND_CHECK(22, "复审通过"),

    /**
     * 初审不通过
     */
    NO_FIRST_CHECK(23, "初审不通过"),

    /**
     * 复审不通过
     */
    NO_SECOND_CHECK(24, "复审不通过"),;

    /**
     * 产品id
     */
    private int code;

    /**
     * 描述
     */
    private String desc;
}
