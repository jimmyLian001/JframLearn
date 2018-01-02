package com.baofu.international.global.account.core.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * description:登录账户状态
 * <p/>
 * Created by liy on 2017/11/15 ProjectName：account-core
 */
@Getter
@AllArgsConstructor
public enum LoginStateEnum {

    /**
     * 1-正常
     */
    NORMAL(1, "正常");

    /**
     * state
     */
    private int state;

    /**
     * 描述
     */
    private String desc;
}
