package com.baofu.international.global.account.core.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * description:密码状态
 * <p/>
 * Created by liy on 2017/11/15 ProjectName：account-core
 */
@Getter
@AllArgsConstructor
public enum PwdStateEnum {

    /**
     * 1-正常
     */
    NORMAL(1, "正常"),

    /**
     * 2-锁定
     */
    LOCK(2, "锁定"),

    /**
     * 3-失效
     */
    INVALID(3, "失效");

    /**
     * state
     */
    private int state;

    /**
     * 描述
     */
    private String desc;
}
