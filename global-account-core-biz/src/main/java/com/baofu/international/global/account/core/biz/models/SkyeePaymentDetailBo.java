package com.baofu.international.global.account.core.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 1、Skyee收支明细信息
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
public class SkyeePaymentDetailBo {

    /**
     * Skyee订单编号
     */
    private String orderId;

    /**
     * 币种
     */
    private String ccy;

    /**
     * 订单日期
     */
    private Date orderDate;

    /**
     * 订单金额
     */
    private BigDecimal orderAmt;

    /**
     * 卖家编号
     */
    private String sellerId;

    /**
     * 银行卡账户
     */
    private String bankCardNo;
}
