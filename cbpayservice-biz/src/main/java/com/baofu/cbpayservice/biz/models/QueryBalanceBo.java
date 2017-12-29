package com.baofu.cbpayservice.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * User: yangjian  Date: 2017-05-23 ProjectName:  Version: 1.0
 */
@Setter
@Getter
@ToString
public class QueryBalanceBo {
    /**
     * 会员号
     */
    private int memberId;

    /**
     * 账户类型
     */
    private int accountType;

    public QueryBalanceBo() {

    }

    public QueryBalanceBo(Integer memberId, Integer accountType) {
        this.memberId = memberId;
        this.accountType = accountType;
    }
}
