package com.baofu.international.global.account.client.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 根据币种获取站点名称
 * <p>
 *
 * @author 莫小阳 on 2017/11/21.
 */

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public enum PlatformEnum {

    /**
     * 美国站
     */
    USD("USD", "AMAZON-US", "US", "美国站"),

    /**
     * 欧洲站
     */
    EUR("EUR", "AMAZON-EU", "EU", "欧洲站"),

    /**
     * 日本站
     */
    JPY("JPY", "AMAZON-JP", "JP", "日本站"),

    /**
     * 英镑
     */
    GBP("GBP", "AMAZON-UK", "UK", "英国站"),;


    /**
     * 币种
     */
    private String ccy;


    /**
     * 平台编码
     */
    private String platformCode;

    /**
     * 国家编码
     */
    private String country;

    /**
     * 站点名称
     */
    private String platformName;


    /**
     * 根据币种获取站点名称
     *
     * @param ccy 币种
     * @return 站点名称
     */
    public static String getPlatformNameByCcy(String ccy) {
        for (PlatformEnum enums : values()) {
            if (ccy.equals(enums.getCcy())) {
                return enums.getPlatformName();
            }
        }
        return "";
    }

    /**
     * 根据币种获取站点名称
     *
     * @param country 地区编码
     * @return 站点名称
     */
    public static PlatformEnum getPlatformNameByCountry(String country) {
        for (PlatformEnum enums : values()) {
            if (country.equals(enums.getCountry())) {
                return enums;
            }
        }
        return null;
    }


}
