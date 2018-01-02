package com.baofu.international.global.account.core.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 认证类型枚举类
 * <p>
 *
 * @author : hetao Date: 2017/11/06 account-core version: 1.0.0
 * </p>
 */
@Getter
@AllArgsConstructor
public enum AuthMethodEnum {
    /**
     * 个人信息认证
     */
    AUTH_PERSONAL(1, "个人信息认证"),

    /**
     * 企业信息认证
     */
    AUTH_ORG(2, "企业信息认证");

    /**
     * code
     */
    private int code;

    /**
     * 描述
     */
    private String desc;
}
