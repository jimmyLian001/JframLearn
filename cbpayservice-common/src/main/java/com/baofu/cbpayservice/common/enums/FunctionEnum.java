package com.baofu.cbpayservice.common.enums;

import com.baofu.cbpayservice.common.constants.Constants;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 产品对应功能集合
 * User: 不良人 Date:2017/02/22 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@AllArgsConstructor
public enum FunctionEnum {

    /**
     * 跨境人民币结算
     */
    FUNCTION_10160001(1016, 10160001),

    /**
     * 跨境外汇结算
     */
    FUNCTION_10160002(1016, 10160002),;

    /**
     * 产品ID
     */
    private Integer productId;
    /**
     * 功能ID
     */
    private Integer FunctionId;

    /**
     * 根据订单类型获取产品ID
     *
     * @param orderType 订单类型
     * @return 返回结果
     */
    public static Integer getProductId(String orderType) {

        if (RemittanceOrderType.REMITTANCE_ORDER.getCode().equals(orderType)
                || RemittanceOrderType.PROXY_REMITTANCE_ORDER.getCode().equals(orderType)) {
            return FunctionEnum.FUNCTION_10160001.getProductId();
        }
        return 0;
    }

    /**
     * 根据币种和订单类型获取功能ID
     *
     * @param orderType 订单类型
     * @param currency  币种
     * @return 返回结果
     */
    public static Integer getFunctionId(String orderType, String currency) {
        if (RemittanceOrderType.REMITTANCE_ORDER.getCode().equals(orderType)
                || RemittanceOrderType.PROXY_REMITTANCE_ORDER.getCode().equals(orderType)) {
            return Currency.CNY.getCode().equals(currency) ? FunctionEnum.FUNCTION_10160001.getFunctionId()
                    : FunctionEnum.FUNCTION_10160002.getFunctionId();
        }
        return 0;
    }
}
