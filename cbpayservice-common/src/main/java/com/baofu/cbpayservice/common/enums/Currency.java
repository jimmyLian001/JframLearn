package com.baofu.cbpayservice.common.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by yuan on 16/9/11.
 */
@Getter
@AllArgsConstructor
public enum Currency {

    CNY("CNY", "人民币"),
    HKD("HKD", "港元"),
    USD("USD", "美元"),
    EUR("EUR", "欧元"),
    JPY("JPY", "日元"),
    GBP("GBP", "英镑"),
    CAD("CAD", "加拿大元"),
    SGD("SGD", "新加坡元"),
    CHF("CHF", "瑞士法郎"),
    AUD("AUD", "澳元"),
    THB("THB", "泰铢"),
    NZD("NZD", "新西兰元"),
    KRW("KRW", "韩元"),
    DEM("DEM", "德国马克"),
    NLG("NLG", "荷兰盾"),
    ESP("ESP", "西班牙比塞塔"),
    DKK("DKK", "丹麦克朗"),
    NOK("NOK", "挪威克朗"),
    TWD("TWD", "台币"),
    ARS("ARS", "阿根廷比索");

    private String code;
    private String desc;


    public Currency findByCode(final String code) {

        if (StringUtils.isBlank(code)) {

            return null;
        }

        Currency result = null;
        for (Currency c : Currency.values()) {

            if (c.code.equals(code)) {

                result = c;
                break;
            }
        }

        return result;
    }

    public boolean isExist(final String code) {

        return (null == findByCode(code)) ? false : true;
    }
}
