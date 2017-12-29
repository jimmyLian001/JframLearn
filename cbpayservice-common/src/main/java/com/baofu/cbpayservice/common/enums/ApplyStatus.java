package com.baofu.cbpayservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 申请状态
 * User: wanght Date:2016/11/11 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@AllArgsConstructor
public enum ApplyStatus {

    /**
     * 初始化( 汇款批次创建 )
     */
    INIT("INIT", "汇款批次创建"),

    /**
     * 商户管理员审核通过
     */
    TRUE("TRUE", "商户管理员审核通过"),

    /**
     * 商户管理员审核不通过
     */
    FALSE("FALSE", "商户管理员审核不通过");

    /**
     * code
     */
    private String code;

    /**
     * 描述
     */
    private String desc;
}
