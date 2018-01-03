package com.baofu.international.global.account.client.web.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 交易查询模型
 *
 * @author 莫小阳 on 2017/11/7.
 */
@Setter
@Getter
@ToString
public class TradeQueryVo implements Serializable {

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
     * 状态
     */
    private String state;

    /**
     * 当前页
     */
    private int currPageNum;

    /**
     * 每页记录数
     */
    private int pageSize;

    /**
     * 1:入账查询 2：出账查询 3：提现查询
     */
    private int flag;

}
