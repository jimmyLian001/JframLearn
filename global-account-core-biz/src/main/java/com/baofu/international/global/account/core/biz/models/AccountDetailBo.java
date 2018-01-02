package com.baofu.international.global.account.core.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * <p>
 * 1、收款账户收支明细
 * </p>
 * ProjectName : global-account-core-parent
 *
 * @author 香克斯
 * @version : 1.0
 * @date : 2017/12/26
 */
@Setter
@Getter
@ToString
public class AccountDetailBo {

    /**
     * 银行返回流水号
     */
    private String bankRespNo;

    /**
     * 用户编号
     */
    private Long userNo;

    /**
     * 收款账户账户编号
     */
    private Long accountNo;

    /**
     * 订单金额
     */
    private BigDecimal orderAmt;

    /**
     * 订单币种
     */
    private String orderCcy;

    /**
     * 订单类型 1-收入；2-支出
     */
    private Integer orderType;

    /**
     * 描述
     */
    private String remarks;


    /**
     * 渠道方虚拟账户
     */
    private String walletId;
}
