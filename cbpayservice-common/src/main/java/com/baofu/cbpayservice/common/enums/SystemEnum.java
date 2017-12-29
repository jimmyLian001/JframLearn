package com.baofu.cbpayservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 系统操作用户
 * User: 香克斯 Date:2016/9/26 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@AllArgsConstructor
public enum SystemEnum {

    /**
     * 系统操作用户
     */
    SYSTEM("SYSTEM", "系统操作用户"),
    //操作成功提示
    SUCCESS("200", "操作成功");

    /**
     * code
     */
    private String code;

    /**
     * 描述
     */
    private String desc;
}
