package com.baofu.international.global.account.client.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户类型
 * <p>
 * @author : hetao Date: 2017/11/06 account-client version: 1.0.0
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
    ORG(2, "企业");

    /**
     * type
     */
    private int type;

    /**
     * 描述
     */
    private String desc;
}
