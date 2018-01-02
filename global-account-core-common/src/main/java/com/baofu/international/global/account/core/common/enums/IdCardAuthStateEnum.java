package com.baofu.international.global.account.core.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * 1、身份证二要素认证信息
 * </p>
 *
 * @author : 香克斯
 * @version : 1.0
 * @date : 2017/11/4
 */
@Getter
@AllArgsConstructor
public enum IdCardAuthStateEnum {

    /**
     * 认证成功
     */
    AUTH_SUCCESS(0, "认证成功"),

    /**
     * 身份证和姓名不匹配
     */
    AUTH_INFO_FAIL(1, "身份证和姓名不匹配"),

    /**
     * 认证信息不存在
     */
    AUTH_INFO_NULL(2, "认证信息不存在"),

    /**
     * 认证信息格式有误
     */
    AUTH_INFO_FORMAT_ERROR(3, "认证信息格式有误"),

    /**
     * 认证渠道系统异常
     */
    AUTH_CHANNEL_ERROR(9, "认证渠道系统异常"),
    /**
     * 系统内部异常
     */
    AUTH_SYS_ERROR(99, "系统内部异常"),;

    /**
     * code
     */
    private int code;

    /**
     * 描述
     */
    private String desc;

    /**
     * 判断code是否存在
     *
     * @param code code
     * @return 返回枚举
     */
    public static IdCardAuthStateEnum find(int code) {

        for (IdCardAuthStateEnum idCardAuthStateEnum : IdCardAuthStateEnum.values()) {
            if (code == idCardAuthStateEnum.getCode()) {
                return idCardAuthStateEnum;
            }
        }
        return null;
    }
}
