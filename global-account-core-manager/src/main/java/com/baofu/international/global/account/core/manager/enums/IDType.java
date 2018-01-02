package com.baofu.international.global.account.core.manager.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述：
 * <p>
 * 1、
 * </p>
 * author: daoxuan
 * date: 2017/11/29
 */
@AllArgsConstructor
@Getter
public enum IDType {

    /**
     * 个人用户号
     */
    PERSONAL_USER(1, "个人用户号"),

    /**
     * 企业用户号
     */
    ORG_USER(2, "企业用户号"),

    /**
     * 账户号
     */
    ACCOUNT(3, "账户号");

    /**
     * code
     */
    private int code;

    /**
     * 描述
     */
    private String desc;
}
