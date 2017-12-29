package com.baofu.cbpayservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 描述
 * <p>
 * User: 不良人 Date:2017/7/17 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public enum CcyEnum {

    /**
     * 美元
     */
    USD("USD", "美元"),

    /**
     * 港币
     */
    HKD("HKD", "港币"),

    /**
     * 欧元
     */
    EUR("EUR", "欧元"),
    /**
     * 美元
     */
    GBP("GBP", "英镑"),

    /**
     * 日元
     */
    JPY("JPY", "日元"),

    /**
     * 新元
     */
    CAD("CAD", "新元"),
    /**
     * 新币
     */
    NZD("NZD", "新币"),

    /**
     * 瑞郎
     */
    CHF("CHF", "瑞郎"),

    /**
     * 澳元
     */
    AUD("AUD", "澳元"),

    /**
     * 人民币
     */
    RMB("RMB", "人民币"),

    /**
     * 俄罗斯卢布
     */
    RUB("RUB", "俄罗斯卢布"),


    /**
     * 韩元
     */
    KRW("KRW", "韩元"),

    /**
     * 泰铢
     */
    THB("THB", "泰铢"),

    /**
     * 菲律宾比索
     */
    PHP("PHP", "菲律宾比索"),

    /**
     * 菲律宾比索
     */
    MOP("MOP", "澳门元"),


    /**
     * 挪威克朗
     */
    NOK("NOK", "挪威克朗"),


    /**
     * 丹麦克朗
     */
    DKK("DKK", "丹麦克朗"),

    /**
     * 瑞典克朗
     */
    SEK("SEK", "瑞典克朗"),

    /**
     * 新加坡元
     */
    SGD("SGD", "新加坡元"),


    /**
     * 新台币
     */
    TWD("TWD", "新台币"),


    /**
     * 人民币
     */
    CNY("CNY", "人民币"),

    /**
     * 巴西雷亚尔
     */
    BRL("BRL", "巴西雷亚尔"),

    /**
     * 马来西亚林吉特
     */
    MYR("MYR", "马来西亚林吉特"),;

    /**
     * 币种简称
     */
    private String key;

    /**
     * 币总中文
     */
    private String value;

    public static String getCcyCHN(String key) {
        for (CcyEnum enums : values()) {
            if (key.equals(enums.getKey())) {
                return enums.getValue();
            }
        }
        return null;
    }
}
