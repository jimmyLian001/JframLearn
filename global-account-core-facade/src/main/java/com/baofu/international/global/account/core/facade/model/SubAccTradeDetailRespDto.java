package com.baofu.international.global.account.core.facade.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author 莫小阳  on 2017/11/22.
 */
@Getter
@Setter
@ToString
public class SubAccTradeDetailRespDto implements Serializable {
    private static final long serialVersionUID = 3052353124198106605L;
    /**
     * 交易ID
     */
    private String tradeId;

    /**
     * 用户号
     */
    private String userNo;


    /**
     * 用户名
     */
    private String userName;

    /**
     * 海外银行账户
     */
    private String bankAccNo;

    /**
     * 交易时间
     */
    private String tradeTime;

    /**
     * 币种
     */
    private String ccy;

    /**
     * 收入
     */
    private String tradeInAccMoney;

    /**
     * 支出
     */
    private String tradeOutAccMoney;

}
