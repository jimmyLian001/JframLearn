package com.baofu.cbpayservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 结汇结果查询类型
 * User: 康志光 Date:2017/07/19 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@AllArgsConstructor
public enum SearchTypeEnum {

    /**
     * 匹配结果
     */
    MATCH_RESULT("01", "匹配结果"),

    /**
     * 结汇结果
     */
    SETTLEMENT_RESULT("02", "结汇结果");

    /**
     * code
     */
    private String code;

    /**
     * 描述
     */
    private String desc;
}
