package com.baofu.international.global.account.core.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * description:登录账户类型
 * <p/>
 * Created by liy on 2017/11/15 ProjectName：account-core
 */
@Getter
@AllArgsConstructor
public enum LoginTypeEnum {

    /**
     * 1-邮箱
     */
    EMAIL(1, "邮箱"),

    /**
     * 2-手机号
     */
    PHONE(2, "手机号");

    /**
     * type
     */
    private int type;

    /**
     * 描述
     */
    private String desc;
}
