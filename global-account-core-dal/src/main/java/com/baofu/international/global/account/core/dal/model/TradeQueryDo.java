package com.baofu.international.global.account.core.dal.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 莫小阳 on 2017/11/7.
 */
@Getter
@Setter
@ToString
public class TradeQueryDo {

    /**
     * 用户号
     */
    private Long userNo;

    /**
     * 账户类型
     */
    private String accountType;

    /**
     * 账户
     */
    private Long accountNo;

    /**
     * 交易开始时间
     */
    private String beginTime;

    /**
     * 交易结束时间
     */
    private String endTime;

    /**
     * 状态 0-待提现；1-提现处理中，2-提现成功，3-提现失败
     */
    private String state;

    /**
     * 1：入账 2：出账  3：提现查询
     */
    private String flag;


}
