package com.baofu.cbpayservice.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 结汇账户参数
 * <p>
 * User: lian zd Date:2017/7/31 ProjectName: cbpayservice Version: 1.0
 */
@Setter
@Getter
@ToString
public class CbPaySettleAccountBo {

    /**
     * 商户编号
     */
    private Long memberId;

    /**
     * 汇款人名称
     */
    private String incomeAccountName;

    /**
     * 汇款人账户
     */
    private String incomeAccountNo;

    /**
     * 汇款人常驻国家
     */
    private String incomeCountry;

    /**
     * 操作人
     */
    private String createBy;
}
