package com.baofu.international.global.account.core.dal.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
@ToString(callSuper = true)
public class UserWithdrawOrderDo extends BaseDo {

    /**
     * 用户号
     */
    private Long userNo;

    /**
     * 订单号(商户订单号)
     */
    private String userTransId;

    /**
     * 订单号
     */
    private Long orderId;

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
     * 收款人证件类型：1-身份证
     */
    private int payeeIdType;

    /**
     * 收款人证件号码
     */
    private String payeeIdNo;

    /**
     * 收款人证件姓名
     */
    private String payeeName;

    /**
     * 收款人帐号
     */
    private String payeeAccNo;

    /**
     * 文件批次号
     */
    private Long fileBatchNo;
}