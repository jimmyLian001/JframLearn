package com.baofu.international.global.account.core.facade.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 子账户收支明细查询
 *
 * @author 莫小阳  on 2017/11/22.
 */

@Getter
@Setter
@ToString
public class SubAccTradeDetailQueryReqDto implements Serializable {
    private static final long serialVersionUID = -8269884769801092675L;

    /**
     * 商户类型
     */
    private String userType;

    /**
     * 用户号
     */
    private String userNo;

    /**
     * 币种
     */
    private String ccy;

    /**
     * 开始时间
     */
    private String beginTime;


    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 银行账户
     */
    private String bankAccNo;


    /**
     * 每页记录数
     */
    private int pageSize;

    /**
     * 当前页
     */
    private int currPageNum;


}
