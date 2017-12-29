package com.baofu.cbpayservice.dal.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 跨境人民币订单信息
 * User: wanght Date:2016/10/25 ProjectName: asias-icpaygate Version: 1.0
 */
@Setter
@Getter
@ToString
public class FiCbPayOrderSumDo {

    /**
     * 交易金额
     */
    private BigDecimal transMoney;

    /**
     * 商户编号
     */
    private Integer count;
}