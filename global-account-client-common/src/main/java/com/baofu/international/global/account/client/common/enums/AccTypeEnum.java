package com.baofu.international.global.account.client.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 账户类型
 * <p>
 * @author : hetao Date: 2017/11/06 account-core version: 1.0.0
 * </p>
 */
@Getter
@AllArgsConstructor
public enum AccTypeEnum {

    /**
     * 对公
     */
    PUBLIC("1", "对公"),

    /**
     * 对私
     */
    PRIVATE("2", "对私"),;

    /**
     * code
     */
    private String code;

    /**
     * 描述
     */
    private String desc;
}
