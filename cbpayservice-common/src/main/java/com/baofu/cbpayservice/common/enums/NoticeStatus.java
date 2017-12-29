package com.baofu.cbpayservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 通知商户状态
 * User: suqier Date:2016/10/27 ProjectName: asias-icpaygate Version: 1.0
 **/
@Getter
@AllArgsConstructor
public enum NoticeStatus {

    INIT("INIT", "未通知"),
    TRUE("TRUE", "通知成功"),
    FALSE("FALSE", "通知失败");
    private String code;
    private String desc;

}
