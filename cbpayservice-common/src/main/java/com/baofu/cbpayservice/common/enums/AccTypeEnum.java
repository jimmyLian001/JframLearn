package com.baofu.cbpayservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 账户类型集合
 */
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public enum AccTypeEnum {

    /**
     * 企业账户
     */
    TYPE_1(1, "C"),
    /**
     * 个人账户
     */
    TYPE_2(2, "D");

    /**
     * 控台定义类型
     */
    private Integer key;

    /**
     * 渠道定义类型
     */
    private String value;
}
