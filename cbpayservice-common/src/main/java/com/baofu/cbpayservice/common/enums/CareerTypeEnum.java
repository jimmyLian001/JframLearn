package com.baofu.cbpayservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 成功状态标识
 * User: 香克斯 Date:2016/9/24 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@AllArgsConstructor
public enum CareerTypeEnum {

    /**
     * 货物贸易
     */
    GOODS_TRADE("01", "货物贸易"),

    /**
     * 机票
     */
    AIR_TICKETS("02", "机票"),

    /**
     * 留学
     */
    STUDY_ABROAD("03", "留学"),

    /**
     * 酒店
     */
    HOTEL("04", "酒店"),

    /**
     * 旅游
     */
    TRIP("05", "旅游"),;

    /**
     * code
     */
    private String code;

    /**
     * 描述
     */
    private String desc;
}
