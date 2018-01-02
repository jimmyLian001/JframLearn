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
public enum CompanyAuthStateEnum {

    /**
     * 成功
     */
    AUTH_SUCCESS(0, "查询成功"),

    /**
     * 其它异常，请稍后再试
     */
    AUTH_OTHER_ERROR(1, "其它异常，请稍后再试"),

    /**
     * 查询无结果
     */
    AUTH_NO_DATA(2, "查询无结果"),

    /**
     * 认证信息格式有误,请检查
     */
    AUTH_PARAM_VALID_ERROR(3, "企业信息有误,请检查"),
    /**
     * 无法验证
     */
    AUTH_UNABLE_TO_VALID(4, "无法验证"),
    /**
     * 认证数据不一致
     */
    AUTH_DATA_INCONSISTENT(5, "认证数据不一致"),
    /**
     * 查询成功
     */
    AUTH_DATA_EXISTED(6, "企业存在"),

    /**
     * 认证渠道异常
     */
    AUTH_CHANNEL_ERROR(9, "认证渠道异常"),
    /**
     * 系统内部异常
     */
    AUTH_SYS_ERROR(99, "系统繁忙，请稍后再试"),;

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
    public static CompanyAuthStateEnum find(int code) {

        for (CompanyAuthStateEnum idCardAuthStateEnum : CompanyAuthStateEnum.values()) {
            if (code == idCardAuthStateEnum.getCode()) {
                return idCardAuthStateEnum;
            }
        }
        return null;
    }
}
