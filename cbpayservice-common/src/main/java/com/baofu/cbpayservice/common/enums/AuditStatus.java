package com.baofu.cbpayservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 审核状态
 * <p>
 * 后台审核状态：init:初始，sucess：运营设置处理成功 、SuccessFirstTrue:成功：清算初审通过 、SuccessSecondTrue:成功：清算复审通过（汇款成功） 、
 * false：运营设置处理失败 、FailFirstTrue 失败：清算初审通过 FailSecondTrue:失败：清算复审通过（汇款失败）';
 * <p>
 * User: wanght Date:2016/11/11 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@AllArgsConstructor
public enum AuditStatus {

    /**
     * 初始化
     */
    INIT("init", "初始化"),

    /**
     * 运营设置处理成功
     */
    SUCCESS("success", "运营设置处理成功"),

    /**
     * 成功：清算初审通过
     */
    SUCCESS_FIRST_TRUE("SuccessFirstTrue", "成功：清算初审通过"),

    /**
     * 成功：清算复审通过（汇款成功）
     */
    SUCCESS_SECOND_TRUE("SuccessSecondTrue", "成功：清算复审通过（汇款成功）"),

    /**
     * 运营设置处理失败
     */
    FALSE("false", "运营设置处理失败"),

    /**
     * 失败：清算初审通过
     */

    FAIL_FIRST_TRUE("FailFirstTrue", "失败：清算初审通过 "),

    /**
     * 失败：清算复审通过（汇款成功）
     */
    FAIL_SECOND_TRUE("FailSecondTrue", "失败：清算复审通过（汇款成功）");

    /**
     * code
     */
    private String code;

    /**
     * 描述
     */
    private String desc;
}
