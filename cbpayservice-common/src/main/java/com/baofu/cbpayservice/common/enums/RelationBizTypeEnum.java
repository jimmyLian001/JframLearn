package com.baofu.cbpayservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
/**
 * 功能：关系业务类型枚举
 * User: feng_jiang
 * Date:2017/9/8
 * ProjectName: cbPayService
 * Version: 1.0
 */
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public enum RelationBizTypeEnum {

    /**
     * 批量转账
     */
    BATCH_TRANSFER(1, "批量转账，"),
    /**
     * 批量结汇
     */
    BATCH_SETTLE(2, "批量结汇"),
    /**
     * 收汇对应转账关系
     */
    SETTLE_TRANSFER(3, "收汇对应转账关系");

    /**
     * code
     */
    private Integer key;

    /**
     * 描述
     */
    private String value;
}
