package com.baofu.international.global.account.core.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 描述：币种
 * User: feng_jiang Date:2017/11/5 ProjectName: globalaccount-core Version: 1.0
 **/
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public enum CcyEnum {

    /**
     * 美元
     */
    USD("USD", "美元", "美国"),

    /**
     * 港币
     */
    HKD("HKD", "港币", "美国"),

    /**
     * 欧元
     */
    EUR("EUR", "欧元", "欧洲"),
    /**
     * 美元
     */
    GBP("GBP", "英镑", "英国"),

    /**
     * 日元
     */
    JPY("JPY", "日元", "日本"),

    /**
     * 新元
     */
    CAD("CAD", "加元", "加拿大"),
    /**
     * 新币
     */
    NZD("NZD", "新币", "新西兰"),

    /**
     * 瑞郎
     */
    CHF("CHF", "瑞郎", "瑞士"),

    /**
     * 澳元
     */
    AUD("AUD", "澳元", "澳大利亚"),

    /**
     * 人民币
     */
    RMB("RMB", "人民币", "中国"),

    /**
     * 俄罗斯卢布
     */
    RUB("RUB", "俄罗斯卢布", "俄罗斯"),


    /**
     * 韩元
     */
    KRW("KRW", "韩元", "韩国"),

    /**
     * 泰铢
     */
    THB("THB", "泰铢", "泰国"),

    /**
     * 菲律宾比索
     */
    PHP("PHP", "菲律宾比索", "菲律宾"),

    /**
     * 菲律宾比索
     */
    MOP("MOP", "澳门元", "澳元"),


    /**
     * 挪威克朗
     */
    NOK("NOK", "挪威克朗", "挪威"),


    /**
     * 丹麦克朗
     */
    DKK("DKK", "丹麦克朗", "丹麦"),

    /**
     * 瑞典克朗
     */
    SEK("SEK", "瑞典克朗", "瑞典"),

    /**
     * 新加坡元
     */
    SGD("SGD", "新加坡元", "新加坡"),


    /**
     * 新台币
     */
    TWD("TWD", "新台币", "中国台湾"),


    /**
     * 人民币
     */
    CNY("CNY", "人民币", "中国"),

    /**
     * 巴西雷亚尔
     */
    BRL("BRL", "巴西雷亚尔", "巴西雷亚尔"),

    /**
     * 马来西亚林吉特
     */
    MYR("MYR", "马来西亚林吉特", "马来西亚"),;

    /**
     * 币种简称
     */
    private String key;

    /**
     * 币总中文
     */
    private String value;

    /**
     * 国家
     */
    private String country;

    public static String getCcyCHN(String key) {
        for (CcyEnum enums : values()) {
            if (key.equals(enums.getKey())) {
                return enums.getValue();
            }
        }
        return null;
    }

    /**
     * 根据币种返回币种枚举
     *
     * @param key 币种
     * @return 返回枚举
     */
    public static CcyEnum getCcyEnum(String key) {
        for (CcyEnum enums : values()) {
            if (key.equals(enums.getKey())) {
                return enums;
            }
        }
        return null;
    }
}
