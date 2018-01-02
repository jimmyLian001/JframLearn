package com.baofu.international.global.account.core.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * description:安全问题状态
 * <p/>
 * Created by liy on 2017/11/28 ProjectName：account
 */
@Getter
@AllArgsConstructor
public enum QuestionStateEnum {

    /**
     * 失效
     */
    INVALID(0, "失效"),

    /**
     * 有效
     */
    EFFECTIVE(1, "有效");

    /**
     * code
     */
    private int state;

    /**
     * 描述
     */
    private String desc;
}
