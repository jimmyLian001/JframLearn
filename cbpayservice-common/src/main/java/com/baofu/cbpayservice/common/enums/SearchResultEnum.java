package com.baofu.cbpayservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 结汇结果查询类型
 * User: 康志光 Date:2017/07/19 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@AllArgsConstructor
public enum SearchResultEnum {

    /**
     * 待处理
     */
    WAITING("1", "待处理"),

    /**
     * 失败
     */
    FAILED("2", "失败"),

    /**
     * 全部成功
     */
    SUCCESS("3", "全部成功"),

    /**
     * 部分成功
     */
    PART_SUCCESS("4", "部分成功");

    /**
     * code
     */
    private String code;

    /**
     * 描述
     */
    private String desc;
}
