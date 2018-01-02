package com.baofu.international.global.account.core.facade.model.res;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 描述：用户发起提现返回店铺账户信息
 * User: feng_jiang Date:2017/11/22 ProjectName: globalaccount-core Version: 1.0
 */
@Setter
@Getter
@ToString
public class WithdrawAccountRespDto implements Serializable {

    /**
     * 店铺名称
     */
    private String storeName;

    /**
     * 店铺编号
     */
    private Long storeNo;

    /**
     * 用户帐号
     */
    private Long userAccNo;


    /**
     * 币种信息
     */
    private String ccy;

    /**
     * 银行账号
     */
    private String bankAccNo;

    /**
     * 账户余额
     */
    private BigDecimal balanceAmt;

    /**
     * 渠道方虚拟账户
     */
    private String walletId;
}
