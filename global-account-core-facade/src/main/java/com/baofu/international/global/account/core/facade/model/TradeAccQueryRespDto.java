package com.baofu.international.global.account.core.facade.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 交易入账查询RespDto
 *
 * @author 莫小阳  on 2017/11/7.
 */

@Getter
@Setter
@ToString
public class TradeAccQueryRespDto implements Serializable {

    private static final long serialVersionUID = 3673221187096598276L;

    /**
     * 流水号
     */
    private Long serialNumber;

    /**
     * 交易时间
     */
    private Date tradeTime;


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
