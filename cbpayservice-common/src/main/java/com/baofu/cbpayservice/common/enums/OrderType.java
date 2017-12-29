package com.baofu.cbpayservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 订单类型：B2C-b2c订单、WEIXIN-微信订单、AUTH-认证、ALIPAY-支付宝订单、PC-代理报关、PCB-代理跨境、
 *           APIPC-接口代理跨境、QUICK-快捷、BRO-批量汇款订单
 *
 * User: 不良人 Date:2017/02/22 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@AllArgsConstructor
public enum OrderType {

    /**
     * b2c-网银支付
     */
    B2C_PAY_TYPE("B2C", "b2c-网银支付"),

    /**
     * quick-快捷支付
     */
    QUICK_PAY_TYPE("QUICK", "quick-快捷支付"),

    /**
     * auth-认证支付
     */
    AUTH_PAY_TYPE("AUTH", "auth-认证支付"),

    /**
     * wxs-微信扫码
     */
    WXS_PAY_TYPE("WEIXIN", "wxs-微信扫码"),

    /**
     * alis-支付宝扫码
     */
    ALIS_PAY_TYPE("ALIPAY", "alis-支付宝扫码"),

    /**
     * PC-代理报关
     */
    PROXY_GOODS_TRADE("PC", " PC-代理报关"),

    /**
     * PCB-代理跨境
     */
    PROXY_SERVICE_TRADE("PCB", "PCB-代理跨境"),

    /**
     * API代理报关
     */
    API_PROXY_CUSTOMS("APIPC", "API-代理报关"),

    /**
     * 批量汇款订单
     */
    BATCH_REMIT_ORDER("BRO", "批量汇款订单"),

    ;

    /**
     * code
     */
    private String code;

    /**
     * 描述
     */
    private String desc;
}
