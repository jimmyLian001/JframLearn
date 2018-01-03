package com.baofu.international.global.account.client.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 账户类型
 *
 * User:buliangren  Date: 2017/11/06 account-core version: 1.0.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum CcySymbolEnum {

    /**
     * 英镑
     */
    GBP("GBP", "￡"),

    /**
     * 人民币
     */
    CNY("CNY", "￥"),

    /**
     * 日元
     */
    JPY("JPY", "￥"),

    /**
     * 欧元
     */
    EUR("EUR", "€"),

    /**
     *  美元
     */
    USD("USD", "＄"),

    /**
     * 泰铢
     */
    THB("THB", "฿"),

    /**
     * 瑞士法郎
     */
    CHF("CHF", "CHF"),

    ;

    /**
     * code
     */
    private String code;

    /**
     * 描述
     */
    private String desc;

    /**
     * 根据币种获取币种符号
     *
     * @param ccy 币种
     * @return 站点名称
     */
    public static String getSymbol(String ccy) {
        for (CcySymbolEnum enums : values()) {
            if (ccy.equals(enums.getCode())) {
                return enums.getDesc();
            }
        }
        return "";
    }
}
