package com.baofu.international.global.account.core.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * description:认证状态
 * <p/>
 *
 * @author 李岩  on 2017/11/15 ProjectName：account-core
 */
@Getter
@AllArgsConstructor
public enum RealNameStatusEnum {

    /**
     * 0-未认证
     */
    NOT(0, "未认证"),

    /**
     * 1-认证中
     */
    WAIT(1, "认证中"),

    /**
     * 2-已认证
     */
    SUCCESS(2, "已认证"),

    /**
     * 3-认证失败
     */
    FAIL(3, "认证失败");

    /**
     * state
     */
    private int state;

    /**
     * 描述
     */
    private String desc;


}
