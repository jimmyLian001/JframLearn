package com.baofu.international.global.account.core.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 微信枚举类
 * <p>
 * User: 蒋文哲 Date: 2017/8/8 Version: 1.0
 * </p>
 */
@AllArgsConstructor
@Getter
public enum BaiDuEnum {
    /**
     * CORP_ID
     */
    CLIENT_ID("clientId", "bvlXbUeOWXeSvGhckoI4FxiP"),
    /**
     * 获取token
     */
    CLIENT_SECRET("clientSecret", "xoGgmK4knobQHDSQzRGGdMFCgf9sIYVx"),
    /**
     * 发送消息
     */
    GRANT_TYPE("grant_type", "client_credentials"),
    /**
     * 发送消息
     */
    GET_TOKEN("getToken", "https://aip.baidubce.com/oauth/2.0/token?grant_type=%s&client_id=%s&client_secret=%s"),
    /**
     * 发送消息
     */
    ID_CARD("idCard", "https://aip.baidubce.com/rest/2.0/ocr/v1/idcard?access_token=%s");
    /**
     * key值
     */
    private String key;
    /**
     * val值
     */
    private String val;
}
