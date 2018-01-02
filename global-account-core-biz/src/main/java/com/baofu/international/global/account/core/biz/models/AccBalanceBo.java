package com.baofu.international.global.account.core.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 描述：币种账户余额
 * User: kangzhiguang Date:2017/11/11 ProjectName: globalaccount-core Version: 1.0
 */
@Getter
@Setter
@ToString
public class AccBalanceBo {

    /**
     * 币种
     */
    private String ccy;

    /**
     * 余额
     */
    private BigDecimal balance;

    /**
     * 约人民币余额
     */
    private BigDecimal rmbBalance;

    /**
     * 待入账金额
     */
    private BigDecimal enteringBalance;
}
