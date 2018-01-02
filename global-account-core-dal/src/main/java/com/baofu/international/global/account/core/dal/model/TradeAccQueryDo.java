package com.baofu.international.global.account.core.dal.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 交易入账查询Do
 *
 * @author 莫小阳  on 2017/11/7.
 */

@Getter
@Setter
@ToString
public class TradeAccQueryDo {

    /**
     * 交易时间
     */
    private Date tradeTime;

    /**
     * 账号
     */
    private String accountNumber;

    /**
     * 入账金额
     */
    private String tradeInAccMoney;

    /**
     * 出账金额
     */
    private String tradeOutAccMoney;

    /**
     * 备注
     */
    private String remark;

}
