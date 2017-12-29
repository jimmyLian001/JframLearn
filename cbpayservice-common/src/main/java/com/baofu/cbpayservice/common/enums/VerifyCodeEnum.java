package com.baofu.cbpayservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 响应码枚举信息
 * User: wanght Date: 2017/1/12 ProductName: cbpay-service Version: 1.0
 */
@Getter
@AllArgsConstructor
public enum VerifyCodeEnum {

    RESULT_0("0", 0),
    RESULT_1("1", 1),
    RESULT_2("2", -1),
    RESULT_9("9", -1),;

    /**
     * 异常码
     */
    private String code;

    /**
     * 异常描述
     */
    private int respCode;

    /**
     * 根据错响应码获取转换后的信息
     *
     * @param code 响应码
     * @return 错误描述
     */
    public static int query(String code) {
        for (VerifyCodeEnum errorCodeEnum : VerifyCodeEnum.values()) {
            if (errorCodeEnum.getCode().equals(code)) {
                return errorCodeEnum.getRespCode();
            }
        }
        return -1;
    }
}
