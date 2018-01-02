package com.baofu.international.global.account.core.dal.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
@ToString(callSuper = true)
public class ExternalOrderDo extends BaseDo {
    /**
     * 用户号
     */
    private Long userNo;

    /**
     * 宝付订单号
     */
    private Long orderId;

    /**
     * 订单号(商户订单号)
     */
    private String userTransId;

    /**
     * 交易时间
     */
    private Date tradeAt;

    /**
     * 订单金额
     */
    private BigDecimal orderAmt;

    /**
     * 订单币种，一般是外币
     */
    private String orderCcy;

    /**
     * 收款人帐号
     */
    private String buyAccNo;

    /**
     * 订单状态；0-未使用 1-已使用
     */
    private Integer state;

    /**
     * 卖家编号
     */
    private String sellerId;

    /**
     * 商品信息字符串
     */
    private String commodityInfo;
}