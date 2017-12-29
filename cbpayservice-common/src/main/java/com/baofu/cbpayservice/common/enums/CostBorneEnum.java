package com.baofu.cbpayservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 费用承担方集合
 */
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public enum CostBorneEnum {

    /**
     * 商户承担
     */
    TYPE_1("1", "BEN"),
    /**
     * 共同承担
     */
    TYPE_2("2", "SHA"),

    /**
     * 宝付承担
     */
    TYPE_3("3", "OUR");

    /**
     * 控台定义类型
     */
    private String key;

    /**
     * 渠道定义类型
     */
    private String value;

    public static String getCostBorne(String key) {
        for (CostBorneEnum enums : values()) {
            if (key.equals(enums.getKey())) {
                return enums.getValue();
            }
        }
        return "";
    }
}
