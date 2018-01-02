package com.baofu.international.global.account.core.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 卡类型枚举类
 * <p>
 *
 * @author : hetao Date: 2017/11/06 account-core version: 1.0.0
 * </p>
 */
@Getter
@AllArgsConstructor
public enum CardTypeEnum {
    /**
     * 1-借记卡
     */
    CARD_TYPE_1(1, "借记卡"),

    /**
     * 2-货记卡
     */
    CARD_TYPE_2(2, "货记卡"),

    /**
     * 3-预付卡
     */
    CARD_TYPE_3(3, "预付卡"),

    /**
     * 4-准贷记卡
     */
    CARD_TYPE_4(4, "准贷记卡"),;

    /**
     * code
     */
    private int code;

    /**
     * 描述
     */
    private String desc;
}
