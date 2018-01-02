package com.baofu.international.global.account.core.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * description：密码类型
 * <p/>
 *
 * @author : liy on 2017/11/15
 * @version : 1.0.0
 */
@Getter
@AllArgsConstructor
public enum PwdTypeEnum {


    /**
     * 1-登录密码
     */
    LOGIN(1, "登录密码"),

    /**
     * 2-支付密码
     */
    PAY(2, "支付密码");

    /**
     * type
     */
    private int type;

    /**
     * 描述
     */
    private String desc;
}
