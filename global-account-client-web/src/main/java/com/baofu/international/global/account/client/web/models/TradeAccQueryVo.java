package com.baofu.international.global.account.client.web.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 莫小阳  on 2017/11/13.
 */
@Getter
@Setter
@ToString
public class TradeAccQueryVo {


    /**
     * 流水号
     */
    private String serialNumber;

    /**
     * 交易时间
     */
    private String tradeTime;


    /**
     * 账户类型
     */
    private String accountType;

    /**
     * 账号
     */
    private String accountNumber;

    /**
     * 币种
     */
    private String ccy;

    /**
     * 交易金额 入账
     */
    private String tradeInAccMoney;

    /**
     * 交易金额  出账
     */
    private String tradeOutAccMoney;


    /**
     * 备注
     */
    private String remark;
}
