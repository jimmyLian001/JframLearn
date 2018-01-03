package com.baofu.international.global.account.client.service.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 提现试算参数（提现第二步）
 *
 * @author: 不良人 Date:2017/11/21 ProjectName: account-client Version: 1.0
 */
@Setter
@Getter
@ToString
public class CashTrialDto {

    /**
     * 收款账户编号
     */
    private String payeeAccNo;

    /**
     * 提现订单金额
     */
    private BigDecimal withdrawAmt;

    /**
     * 提现订单币种
     */
    private String withdrawCcy;

    /**
     * 渠道方虚拟账户
     */
    private String walletId;

    /**
     * 店铺编号
     */
    private String storeNo;

}
