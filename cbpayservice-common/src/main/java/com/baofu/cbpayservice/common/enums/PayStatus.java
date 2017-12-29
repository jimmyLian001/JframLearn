package com.baofu.cbpayservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 支付状态
 * User: suqier Date:2016/10/27 ProjectName: asias-icpaygate Version: 1.0
 **/
@Getter
@AllArgsConstructor
public enum PayStatus {

    INIT("INIT", "未支付"),
    TRUE("TRUE", "支付成功"),
    FALSE("FALSE", "支付失败");

    private String code;
    private String desc;

}
