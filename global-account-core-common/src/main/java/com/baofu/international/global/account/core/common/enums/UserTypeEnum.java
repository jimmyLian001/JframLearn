package com.baofu.international.global.account.core.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户类型枚举类
 * <p>
 *
 * @author : hetao Date: 2017/11/06 account-core version: 1.0.0
 * </p>
 */
@Getter
@AllArgsConstructor
public enum UserTypeEnum {
    /**
     * 个人
     */
    PERSONAL(1, "个人"),

    /**
     * 企业
     */
    ORG(2, "企业"),;

    /**
     * code
     */
    private int type;

    /**
     * 描述
     */
    private String desc;
}
