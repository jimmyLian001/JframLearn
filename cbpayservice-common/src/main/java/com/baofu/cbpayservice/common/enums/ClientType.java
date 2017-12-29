package com.baofu.cbpayservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * description: 客户端类型
 * <p>
 * <p>
 * </p>
 * User: fengsx  Date: 2016/12/2  ProjectName: cbpayservice Version: 1.0
 */
@Getter
@AllArgsConstructor
public enum ClientType {

    /**
     * PC端
     */
    PC("PC", "PC端"),

    /**
     * WAP端
     */
    WAP("WAP", "WAP端");

    /**
     * code
     */
    private String code;

    /**
     * 描述
     */
    private String desc;
}
